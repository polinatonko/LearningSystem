package org.example.learningsystem.course.job.notifications;

import org.example.learningsystem.multitenancy.context.TenantInfoTaskDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

@EnableAsync
@Configuration
public class TaskExecutorConfiguration {

    private static final int MAX_POOL_SIZE = 50;
    private static final int QUEUE_CAPACITY = 100;
    private static final String THREAD_NAME_PREFIX = "course-reminder-";

    @Bean
    public Executor courseRemindersTaskExecutor() {
        int coreCount = Runtime.getRuntime().availableProcessors();

        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreCount * 2);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setTaskDecorator(new TenantInfoTaskDecorator());
        executor.initialize();
        return executor;
    }
}
