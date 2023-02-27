package com.example.smev_gisgmp.exception_handling.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;

@Component
public class HandlerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return t;
    }
}
