package com.example.distributelockredissson.api;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Lixiangping
 * @createTime 2022年03月27日 00:14
 * @decription: 测试redisson分布式锁:spring boot starter方式
 */
@Slf4j
@RestController
public class RedissonStarterController {

    @Autowired
    private RedissonClient redissonClient;
    @RequestMapping(value = "/redissonLock")
    public String redissonLock(){
        log.info("进入了方法");
        RLock rLock = redissonClient.getLock("order");//区分不同业务
        rLock.lock(30, TimeUnit.SECONDS);//超时自动释放锁
        log.info("获得了锁");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //释放锁
            rLock.unlock();
            log.info("释放了锁");
        }
        return "释放了锁";
    }

}
