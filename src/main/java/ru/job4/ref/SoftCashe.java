package ru.job4.ref;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Хранилище ссылок
 * @author Shegai Evgenii
 * @since 29.10.2021
 * @version 1.0
 * @param <K> - key
 * @param <V> - value
 */

public class SoftCashe<K, V> implements Cashe<K, V> {

    private final Map<K, SoftReference<V>> map = new HashMap<>();

    @Override
    public V get(K key) {
        V rsl = null;
        SoftReference<V> softRef = map.get(key);
        if (softRef != null) {
            rsl = softRef.get();
        }
        return rsl;
    }

    @Override
    public boolean put(K key, V value) {
        boolean rsl = false;
        if (value != null) {
            SoftReference<V> softRef = new SoftReference<>(value);
            map.put(key, softRef);
            rsl = true;
        }
        return rsl;
    }
}
