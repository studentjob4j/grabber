package ru.job4.solid.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранилище продуктов использую шаблон проектирования - стратегия
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class Shop implements Store {

    private List<Food> storage;

    public Shop() {
        this.storage = new ArrayList<>();
    }

    public List<Food> getStorage() {
        return storage;
    }

    @Override
    public boolean add(Food food) {
        boolean result = false;
        ControlQualityClient control = new ControlQualityClient();
        int temp = control.countExpirationDatePercentage(food);
        if (temp >= 25 && temp <= 75) {
            this.storage.add(food);
            result = true;
        } else if (temp > 75 && temp < 100) {
            double newPrice = food.getPrice() - food.getDiscount();
            food.setPrice(newPrice);
            this.storage.add(food);
            result = true;
        }
        return result;
    }
}
