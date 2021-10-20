package ru.job4.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

/**
 * @author Shegai Evgenii
 * @since 20.10.2021
 * @version 1.0
 * планировщик запускаетс с интервалом заданным в файле rabbit.properties
 */

public class AlertRabbit {

    private  final Properties properties = new Properties();

    public static void main(String[] args) {
        ClassLoader loader = AlertRabbit.class.getClassLoader();
        AlertRabbit rabbit = new AlertRabbit();
        try (InputStream is = loader.getResourceAsStream("rabbit.properties")) {
            rabbit.properties.load(is);
            is.close();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(
                            rabbit.properties.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger().startNow().withSchedule(times).build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException | IOException e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here and i read data from rabbit.properties ...");
        }
    }
}
