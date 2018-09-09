package com.k.seckill.redis;

import com.k.seckill.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRedis extends BaseRedis<User> {

    private static final String REDIS_KEY = "com.k.seckill.redis.UserRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }

//
//    public void add(String key, Long time, User user) {
//        Gson gson = new Gson();
//
//    }

}
