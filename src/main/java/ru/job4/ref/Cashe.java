package ru.job4.ref;

/**
 * @author Shegai Evgenii
 * @since 29.10.2021
 * @version 1.0
 */

public interface Cashe<K, V> {

    V get(K key);

    boolean put(K key, V value);
}
