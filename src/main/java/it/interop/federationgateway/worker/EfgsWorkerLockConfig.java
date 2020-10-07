package it.interop.federationgateway.worker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

/**
   * @Description SchedulerLock based on Redis configuration
   * @Author Yao Guangxing
 * @Date 2020/2/22 18:27
 **/
@Configuration
//defaultLockAtMostFor specifies the default time that the lock should be retained at the end of the execution node, using the ISO8601 Duration format
//The effect is that when the locked node is hung up, the lock cannot be released, causing other nodes to be unable to perform the next task
//The default here is 20M
//About the ISO8601 Duration format is not available, you can check the relevant information on the Internet. It should be a set of specifications that stipulate some time expressions
@EnableSchedulerLock(defaultLockAtMostFor = "${efgs.worker.defaultLockAtMostFor}")
public class EfgsWorkerLockConfig {

    @Bean
    public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
        //Environmental variables-Different environments need to be distinguished to avoid conflicts, such as dev environment and test environment. When both are deployed, only one instance will be used. At this time, the related environment will not start
        return new RedisLockProvider(connectionFactory);
    }
}