package ru.job4.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Объекты, на которые ссылаются слабые ссылки удаляются сразу, если на них нет сильных
 * или безопасных ссылок.
 * Данный тип ссылок служит для реализации структур для которых у одного значения типа может быть
 * только один объект, например пул строк,
 * и объекты чаще всего используется всего один раз, т.е. сохранили-получили-забыли.
 * Сначала GC очистит weak-ссылку, то есть weakRef.get() – будет возвращать null.
 * Потом weakRef будет добавлен в queue и соответственно queue.poll() вернет ссылку на weakRef.
 * * @author Shegai Evgenii
 *  * @since 28.10.2021
 *  * @version 1.0
 */

public class WeakRefDemo {

    public static void main(String[] args) throws InterruptedException {
        example1();
        example2();
        example3();
    }

    private static void example1() throws InterruptedException {
        Object object = new Object() {
            @Override
            protected void finalize() throws Throwable {
                System.out.println("Removed");
            }
        };
        WeakReference<Object> weak = new WeakReference<>(object);
        object = null;
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(weak.get());
    }

    private static void example2() throws InterruptedException {
        List<WeakReference<Object>> objects = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            objects.add(new WeakReference<Object>(new Object() {
                @Override
                protected void finalize() throws Throwable {
                    System.out.println("Removed!");
                }
            }));
        }
        System.gc();
        TimeUnit.SECONDS.sleep(3);
    }

    private static void example3() throws InterruptedException {
        Object object = new Object() {
            @Override
            protected void finalize() throws Throwable {
                System.out.println("Removed");
            }
        };
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        WeakReference<Object> weak = new WeakReference<>(object, queue);
        object = null;
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("from link " + weak.get());
        System.out.println("from queue " + queue.poll());
    }
}
