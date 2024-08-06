package com.yeonieum.scheduledTeskserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ThreadpoolTaskSchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler ThreadPoolTaskScheduler() {
        // Thread Pool 설정
        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();

        // Thread 개수 설정
        //int n = Runtime.getRuntime().availableProcessors();
        threadPool.setPoolSize(10);
        threadPool.initialize();
        threadPool.setThreadNamePrefix("ThreadPoolTaskScheduler");

        return threadPool;
    }


    //@PostConstruct
    //빈 포스트프로세서를 통해 데이터베이스에서 스케쥴 내용 읽어오기
}
