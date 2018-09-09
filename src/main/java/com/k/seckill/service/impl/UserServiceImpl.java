package com.k.seckill.service.impl;

import com.k.seckill.model.User;
import com.k.seckill.redis.UserRedis;
import com.k.seckill.repository.UserRepository;
import com.k.seckill.service.UserService;
import com.k.seckill.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserRedis userRedis;

    @Override
    public User register(User user) {
        return userRepository.saveAndFlush(user);
    }

//    @Override
//    public User getUser(String username) {
//        return userRepository.getOne(username);
//    }

    @Override
    public UserVO getUser(String username) {
        UserVO userVO = new UserVO();
        User user = userRedis.get("username");
        if (user == null) {
            user = userRepository.findByUsername(username);
            if (user != null) {
                userRedis.put(user.getUsername(), user, -1);

            }else {
                return null;
            }
        }
        BeanUtils.copyProperties(user, userVO);

        return userVO;
    }


    @Override
    public void saveUserToRedisByToken(UserVO dbUser, String token){

        User user = new User();
        BeanUtils.copyProperties(dbUser, user);
        //将信息放入redis
        userRedis.put(token, user, 3600);
    }

}
