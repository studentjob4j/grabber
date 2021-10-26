package ru.job4.gc;

import java.util.Random;

/**
 * @author Shegai Evgenii
 * @since 26.10.2021
 * @version 1.0
 * Запуск приложения с различными сборщиками мусора
 * флаг -Xlog:gc* (до JDK 9 нужно использовать -XX:-PrintGCDetails) если мы его зададим то,
 * сможем увидеть лог сборщика в консоли
 * Ключи для запуска:
 * - Serial => -XX:+UseSerialGC
 * - Parallel => -XX:+UseParallelGC
 * - CMS => -XX:+UseConcMarkSweepGC (допуступен до JDK 14)
 * - G1 => -XX:+UseG1GC
 * - ZGC => -XX:+UseZGC
 */

public class GCTypeDemo {

    public static void main(String[] args) {
        Random random = new Random();
        int length = 100;
        String[] data = new String[1_000_000];
        for (int i = 0; ; i = (i + 1) % data.length) {
            data[i] = String.valueOf(
                    (char) random.nextInt(255)
            ).repeat(length);
        }
    }
}
