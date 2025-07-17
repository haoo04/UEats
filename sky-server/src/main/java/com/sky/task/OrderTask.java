package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Scheduled task class, scheduled processing order status
 */
@Component
@Slf4j
public class OrderTask {

    //private OrderMapper orderMapper;

    /**
     * Processing overdue orders
     */
    @Scheduled(cron = "0 * * * * ? ") //Triggered every minute
    public void processTimeoutOrder(){
        log.info("Timed processing of overdue orders : {}", LocalDateTime.now());

        //
    }
}
