package ru.job4.solid.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранилище продуктов использую шаблон проектирования - стратегия
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class Trash implements Store {

    private List<Food> trash;

    public Trash() {
        this.trash = new ArrayList<>();
    }

    public List<Food> getStorage() {
        return trash;
    }

    @Override
    public boolean add(Food food) {
        boolean result = false;
        ControlQualityClient quality = new ControlQualityClient();
        if (quality.countExpirationDatePercentage(food) == 100) {
            this.trash.add(food);
            result = true;
        }
        return result;
    }
}
