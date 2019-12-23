package com.example.homework.schedulers;

import io.reactivex.Scheduler;

public interface SchedulerManager {
    Scheduler ioThread();
    Scheduler mainThread();
}
