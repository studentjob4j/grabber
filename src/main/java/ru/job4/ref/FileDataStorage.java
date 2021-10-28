package ru.job4.ref;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Читает данные из файла
 * @author Shegai Evgenii
 * @since 29.10.2021
 * @version 1.0
 * @param <K> - key
 * @param <V> - value
 */

public class FileDataStorage<K, V> implements DataStorage<K, V> {

    private static final Logger LOG = LoggerFactory.getLogger(FileDataStorage.class.getName());

    @Override
    public V getValue(K path) {
       V rsl = null;
        try (BufferedReader read = new BufferedReader(new FileReader((String) path))) {
            rsl = (V) read.readLine();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }
}
