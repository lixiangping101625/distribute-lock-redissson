package com.example.distributelockredissson.api;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Lixiangping
 * @createTime 2022年03月27日 00:14
 * @decription: 测试redisson分布式锁
 */
@Slf4j
@RestController
public class RedissonApiController {

    /**
     * redisson springboot starter 方式
     */
//    @RequestMapping(value = "/redissonLock")
//    public String redissonLock(){
//        log.info("进入了方法");
//        // 1. Create config object
//        Config config = new Config();
//        config.useSingleServer()//单节点redis
//                // use "rediss://" for SSL connection
//                .setAddress("redis://127.0.0.1:6379");
//
//        // 2. Create Redisson instance
//        RedissonClient redisson = Redisson.create(config);// Sync and Async API
//        RLock rLock = redisson.getLock("order");//区分不同业务
//        rLock.lock(30, TimeUnit.SECONDS);//超时自动释放锁
//        log.info("获得了锁");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }finally {
//            //释放锁
//            rLock.unlock();
//            log.info("释放了锁");
//        }
//        return "释放了锁";
//    }

}
