package ru.job4.solid.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранилище продуктов использую шаблон проектирования - стратегия
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class Warehouse implements Store {

    private List<Food> storage;

    public Warehouse() {
        this.storage = new ArrayList<>();
    }

    public List<Food> getStorage() {
        return storage;
    }

    @Override
    public void add(Food food) {
        this.storage.add(food);
    }

    @Override
    public boolean accept(Food food) {
        boolean result = false;
        int value = calculatePercent(food);
        if (value < 25) {
            result = true;
        }
        return result;
    }

    @Override
    public List<Food> clear() {
        List<Food> list = new ArrayList<>();
        list.addAll(this.storage);
        this.storage.clear();
        return list;
    }
}
