package com.duty.service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class Config {

    @Bean
    public NewTopic scheduleTopic() {
        return TopicBuilder
                .name("schedule-topic")
                .build();
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("DynamicScheduler-");
        scheduler.initialize();
        return scheduler;
    }
}
