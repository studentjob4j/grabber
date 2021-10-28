package ru.job4.ref;

/**
 * @author Shegai Evgenii
 * @since 29.10.2021
 * @version 1.0
 */

public interface DataStorage<K, V> {

    V getValue(K key);
}
