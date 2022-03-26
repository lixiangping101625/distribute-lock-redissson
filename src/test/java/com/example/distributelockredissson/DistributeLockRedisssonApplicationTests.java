package com.example.distributelockredissson;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class DistributeLockRedisssonApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testRedisson() {
        // 1. Create config object
        Config config = new Config();
        config.useSingleServer()
                // use "rediss://" for SSL connection
                .setAddress("redis://127.0.0.1:6379");

        // or read config from file
//        config = Config.fromYAML(new File("config-file.yaml"));

        // 2. Create Redisson instance
            // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        RLock rLock = redisson.getLock("order");//区分不同业务
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

    }

}
