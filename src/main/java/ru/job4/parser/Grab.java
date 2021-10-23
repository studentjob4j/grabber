package ru.job4.parser;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * используем quartz для запуска парсера , через интерфейс
 */

public interface Grab {

    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}
