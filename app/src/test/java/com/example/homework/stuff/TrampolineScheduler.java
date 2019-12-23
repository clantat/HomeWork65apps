package com.example.homework.stuff;

import com.example.homework.schedulers.SchedulerManager;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TrampolineScheduler implements SchedulerManager {
    @Override
    public Scheduler ioThread() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler mainThread() {
        return Schedulers.trampoline();
    }
}
