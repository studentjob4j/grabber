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
    public boolean add(Food food) {
        boolean result = false;
        ControlQualityClient control = new ControlQualityClient();
        if (control.countExpirationDatePercentage(food) < 25) {
            this.storage.add(food);
            result = true;
        }
        return result;
    }
}
