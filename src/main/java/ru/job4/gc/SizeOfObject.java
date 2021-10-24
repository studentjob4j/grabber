package ru.job4.gc;

import static com.carrotsearch.sizeof.RamUsageEstimator.sizeOf;

/**
 * @author Shegai Evgenii
 * @since 24.10.2021
 * @version 1.0
 * Клаас определяет размер объекта используя библиотеку carrotsearch в байтах
 * размер пустого объекта без полей равен 16 байт (64 раз-я ос) и 8 байт (32 разр ос)
 */

public class SizeOfObject {

    public static void main(String[] args) {
        Person one = new Person(1, "name");
        System.out.println("размер объекта = " + sizeOf(one) + "byte");
        System.out.println(sizeOf(new Temp()));
    }

    private static class Temp {

    }
}
