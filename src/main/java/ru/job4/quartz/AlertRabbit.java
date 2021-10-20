package ru.job4.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import java.sql.*;

/**
 * @author Shegai Evgenii
 * @since 20.10.2021
 * @version 2.0
 * планировщик запускаетс с интервалом заданным в файле rabbit.properties
 * и записывает текущую дату в БД используя планировщик
 */

public class AlertRabbit {

    //private  final Properties properties = new Properties();
    private Connection connection;

    public static void main(String[] args) {

       /* version 1.0
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
        }*/

      //version 2.0
        AlertRabbit rabbit = new AlertRabbit();
            Properties properties = rabbit.getProperties();
            try (Connection connection = rabbit.init(properties)) {
                // Начало работы происходит с создания класса управляющего всеми работами.
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            // хранилище помещаем по ключу - значение
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
                // Создание задачи.  создать класс реализующий этот интерфейс.
            JobDetail job = newJob(Rabbit.class)
                        .usingJobData(data)
                        .build();
             // Создание расписания.
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(
                            properties.getProperty("rabbit.interval")))
                     .repeatForever();

            // Задача выполняется через триггер.
            Trigger trigger = newTrigger()
                        .startNow()
                        .withSchedule(times)
                        .build();
                scheduler.scheduleJob(job, trigger);
                // планировщик засыпает на 10 сек и выкл
                Thread.sleep(10000);
                scheduler.shutdown();
        } catch (SchedulerException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Connection init(Properties properties) {
        try  {
            Class.forName(properties.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private Properties getProperties() {
        Properties pro = new Properties();
        ClassLoader loader = AlertRabbit.class.getClassLoader();
        try (InputStream is = loader.getResourceAsStream("rabbit.properties")) {
            pro.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }

    public static class Rabbit implements Job {

        /* ver 1.0
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here and i read data from rabbit.properties ...");
        }*/


        //Внутри этого класса нужно описать требуемые действия.
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            Connection connection = (Connection) context.getJobDetail().getJobDataMap()
                    .get("connection");
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into rabbit(created_day) values (?)")) {
                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
