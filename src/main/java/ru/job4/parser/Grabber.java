package ru.job4.parser;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Shegai Evgenii
 * @since 23.10.2021
 * @version 1.0
 * Класс читает даные из файла  и загружает их в пропертис - создает конекшн к бд и создает
 * таблицу в бд
 * - создаем планировщик и запускаем его - передаем в метод init объект типа store(PsqlStore) ,
 * объект типа parse(SqlRuParse) , планировщик  -  помещаем данные в хранилище - создаем задачи и
 * расписания запуска - метод получает по ключу из хранилища объект store -
 * PsqlStore(он записывает данные в бд)
 * и объект Parse - SqlRuParse(он парсит главную страницу и по ссылкам создает лист
 * постов с информацией
 * о вакансиях) и результат помещает в БД .
 * Сервер поодключается к порту 9000 и при появлении подключения отправляет ответ в виде
 *  постов полученных из store(PsqlStore) из бд
 * Программа парсит с интервалом 10 сек
 *
 */

public class Grabber implements Grab {

    private static final Logger LOG = LoggerFactory.getLogger(Grabber.class.getName());
    private final Properties cfg = new Properties();

    /**
     * метод создает конекшн к бд и создает таблицу в бд
     * @param tableName
     * @return store
     */
    public Store store(String tableName) {
        PsqlStore store = new PsqlStore(this.cfg);
        store.createTable(tableName);
        return store;
    }

    /**
     * создаем планировщик и запускаем его
     * @return
     * @throws SchedulerException
     */

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    /**
     * класс читает даные из файла  и загружает их в пропертис
     */
    public void cfg() {
        ClassLoader loader = PsqlStore.class.getClassLoader();
        try (InputStream in = loader.getResourceAsStream("psql.properties")) {
            cfg.load(in);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * помещаем данные в хранилище - создаем задачи и расписания запуска
     * @param parse
     * @param store
     * @param scheduler
     */

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * метод получает по ключу из хранилища объект store -
     * PsqlStore(он записывает данные в бд)
     * и объект Parse - SqlRuParse(он парсит главную страницу
     * и по ссылкам создает лист постов с информацией
     * о вакансиях) и результат помещает в БД
     */

    public static class GrabJob implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parse parse = (Parse) map.get("parse");
                List<Post> list = parse.list("https://www.sql.ru/forum/job-offers");
                for (Post post : list) {
                    store.save(post);
                }
            }
        }

    /**
     * Сервер поодключается к порту 9000 и при появлении подключения отправляет ответ в виде
     * постов полученных из store(PsqlStore) из бд
     * @param store
     */
    public void web(Store store) {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(
                    Integer.parseInt(cfg.getProperty("port")))) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    try (OutputStream out = socket.getOutputStream()) {
                        out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                        for (Post post : store.getAll()) {
                            out.write(post.toString().getBytes(Charset.forName("Windows-1251")));
                            out.write(System.lineSeparator().getBytes());
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

        public static void main(String[] args) throws Exception {
            Grabber grab = new Grabber();
            grab.cfg();
            Scheduler scheduler = grab.scheduler();
            Store store = grab.store("post");
            grab.init(new SqlRuParse(), store, scheduler);
            grab.web(store);
        }
}
