### redisson客户端分布式锁
#### 1、加依赖
    <!--引入redisson-->
    <dependency>
        <groupId>org.redisson</groupId>
        <artifactId>redisson</artifactId>
        <version>3.17.0</version>
    </dependency>
#### 接口测试
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
    public class RedissonController {
    
        @RequestMapping(value = "/redissonLock")
        public String redissonLock(){
            log.info("进入了方法");
            // 1. Create config object
            Config config = new Config();
            config.useSingleServer()//单节点redis
                    // use "rediss://" for SSL connection
                    .setAddress("redis://127.0.0.1:6379");
    
            // 2. Create Redisson instance
            RedissonClient redisson = Redisson.create(config);// Sync and Async API
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
            return "释放了锁";
        }
     }
#### 结果：先访问9001端口服务，接着访问9000服务。看到【2022-03-27 00:27:35】9001释放锁后9000才获得了锁
    9001服务：
        2022-03-27 00:27:22.861  INFO 32480 --- [nio-9001-exec-1] c.e.d.api.RedissonController             : 进入了方法
        2022-03-27 00:27:25.373  INFO 32480 --- [nio-9001-exec-1] c.e.d.api.RedissonController             : 获得了锁
        2022-03-27 00:27:35.386  INFO 32480 --- [nio-9001-exec-1] c.e.d.api.RedissonController             : 释放了锁
    9000服务：
        2022-03-27 00:27:29.794  INFO 32836 --- [nio-9000-exec-2] c.e.d.api.RedissonController             : 进入了方法
        2022-03-27 00:27:35.391  INFO 32836 --- [nio-9000-exec-2] c.e.d.api.RedissonController             : 获得了锁
        2022-03-27 00:27:45.402  INFO 32836 --- [nio-9000-exec-2] c.e.d.api.RedissonController             : 释放了锁
       