package ru.job4.ref;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Объекты, на которые ссылаются безопасные ссылки, удаляются только если JVM не хватает памяти,
 * т.е. они могут пережить более одной сборки мусора.
 * Данный тип ссылок подходит для реализации кэша - такой структуры данных,
 * при которой часть данных запоминается,
 * а потом часто переиспользуется.
 * Например, можно запоминать данные из файлов или тяжелых запросов.
 * При нехватке памяти JVM может удалить объекты по этим ссылкам, если на них нет сильных ссылок.
 * Есть контракт для данного типа ссылок: GC гарантировано удалит с кучи все объекты, доступные
 * только по soft-ссылке, перед тем как бросит OutOfMemoryError
 * get() — вернет strong-ссылку на объект  в случае если GC не удалил этот объект из памяти.
 * В другом случае вернется null.
 * clear() — удалит ссылку на объект (то есть soft-ссылки на этот объект больше нет)
 * @author Shegai Evgenii
 * @since 28.10.2021
 * @version 1.0
 */

public class SoftRefDemo {

    public static void main(String[] args) {
        example1();
        example2();
    }

    private static void example1() {
        Object strong = new Object();
        SoftReference<Object> soft = new SoftReference<>(strong);
        strong = null;
        System.out.println(soft.get());
    }

    private static void example2() {
        List<SoftReference<Object>> objects = new ArrayList<>();
        for (int i = 0; i < 100000000; i++) {
            objects.add(new SoftReference<Object>(new Object() {
                //String value = String.valueOf(System.currentTimeMillis());

                @Override
                protected void finalize() throws Throwable {
                    System.out.println("Object removed!");
                }
            }));
        }
        System.gc();
        int liveObject = 0;
        for (SoftReference<Object> ref : objects) {
            Object object = ref.get();
            if (object != null) {
                liveObject++;
            }
        }
        System.out.println(liveObject);
    }

    /**
     *  Корректным использованием безопасных ссылок является сначала получение
     *  сильной ссылки на данные,
     *  а потом работы с сильной ссылок.
     * Это гарантирует, что в интервалах получения сильной ссылки из безопасной GC не затрет объект.
     *  Это касается не только локальных переменных, но и возвращаемых значений и аргументов.
     */

  /* private static void unsafe() {
        List<SoftReference<Object>> someData = new ArrayList<>();
        if (someData.get(0).get() != null) {
            // do something
        } else {
            // do something
        }
        // do something
        someData.get(0).get();
    }

    private static void safe() {
        List<SoftReference<Object>> someData = new ArrayList<>();
        Object strong = someData.get(0).get();
        if (strong != null) {
            // do something
        } else {
            // do something
        }
        // work with strong
    }*/
}
