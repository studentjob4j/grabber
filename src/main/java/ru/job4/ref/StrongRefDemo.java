package ru.job4.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * При данном типе ссылок объекты удаляются только если
 * на них нет сильной ссылки или они находятся в составе объекта на который нет ссылки.
 * * @author Shegai Evgenii
 *  * @since 28.10.2021
 *  * @version 1.0
 */

public class StrongRefDemo {

    public static void main(String[] args) throws InterruptedException {
        example1();
        example2();
        example3();
    }

    private static void example1() throws InterruptedException {
        Object[] objects = new Object[100];
        for (int i = 0; i < 100; i++) {
            objects[i] = new Object() {
                @Override
                protected void finalize() throws Throwable {
                    System.out.println("Object removed!");
                }
            };
        }
        for (int i = 0; i < 100; i++) {
            objects[i] = null;
        }
        System.gc();
        TimeUnit.SECONDS.sleep(5);
    }

    private static void example2() throws InterruptedException {
        Object[] objects = new Object[100];
        for (int i = 0; i < 100; i++) {
            Object object = new Object() {
                private Object innerObject = new Object() {
                    @Override
                    protected void finalize() throws Throwable {
                        System.out.println("Remove inner object!");
                    }
                };
            };
            objects[i] = object;
        }
        for (int i = 0; i < 100; i++) {
            objects[i] = null;
        }
        System.gc();
        TimeUnit.SECONDS.sleep(5);
    }

    private static void example3() {
        List<String> strings = new ArrayList<>();
        while (true) {
            strings.add(String.valueOf(System.currentTimeMillis()));
        }
    }
}
