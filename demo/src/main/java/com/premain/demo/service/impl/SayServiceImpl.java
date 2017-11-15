package com.premain.demo.service.impl;

import com.premain.demo.service.SayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * Created by zhongjing on 2017/11/10.
 */
@Service
public class SayServiceImpl implements SayService {
    Logger logger = LoggerFactory.getLogger(SayServiceImpl.class);
    
    @Autowired
    private JedisPool jedisPool ;
    
    @Override
    public void sayHello() {
        System.out.println("say hello");
    }

    @Override
    public void redisInsert(String name) {
        String uuid = UUID.randomUUID().toString();
        logger.info("jedisPool uuid : " + uuid);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(uuid, 1000, name);
        }
    }

    @Override
    public String getName(String name) {
        return  "hello " + name;
    }
}
