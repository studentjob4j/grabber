package ru.job4.ref;

/**
 * Кеш
 * @author Shegai Evgenii
 * @since 29.10.2021
 * @version 1.0
 * @param <K> - key
 * @param <V> - value
 */

public class CasheDemo<K, V> {

    private final Cashe<K, V> cache;
    private final DataStorage<K, V> dataStorage;

    public CasheDemo(Cashe<K, V> cache, DataStorage<K, V> dataStorage) {
        this.cache = cache;
        this.dataStorage = dataStorage;
    }

    /**
     * метод по ключу получает из кеша значение - если его нет тогда читаем значение из файла
     * и помещаем его в кеш
     * @param key
     * @return значение
     */
    public V get(K key) {
        V value = cache.get(key);
        if (value == null) {
            value = dataStorage.getValue(key);
            cache.put(key, value);
        }
        return value;
    }
}
