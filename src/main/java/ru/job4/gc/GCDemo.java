package ru.job4.gc;

/**
 * @author Shegai Evgenii
 * @since 24.10.2021
 * @version 1.0
 * Демонстрация работы сборщика мусора - при заполнении памяти он начинает удалять объекты
 */

public class GCDemo {

    private static final long KB = 1000;
    private static final long MB = KB * KB;
    private static final Runtime ENVIRONMENT = Runtime.getRuntime();

    public static void info() {
        final long freeMemory = ENVIRONMENT.freeMemory();
        final long totalMemory = ENVIRONMENT.totalMemory();
        final long maxMemory = ENVIRONMENT.maxMemory();
        System.out.println("=== Environment state ===");
        System.out.printf("Free: %d%n", freeMemory / MB);
        System.out.printf("Total: %d%n", totalMemory / MB);
        System.out.printf("Max: %d%n", maxMemory / MB);
    }

    public static void main(String[] args) {
        info();
        for (int i = 0; i < 10000; i++) {
            new Person(i, "N" + i);
        }
        // просим jvm запустить сборщик мусора но это не всегда срабатывает
        System.gc();
        info();
    }
}
