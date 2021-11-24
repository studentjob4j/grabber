package ru.job4.solid.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранилище продуктов использую шаблон проектирования - стратегия
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class ControlQualityClient {

    private List<Store> store;

    public void setStore(List<Store> store) {
        this.store = store;
    }

    public List<Store> getStore() {
        return store;
    }

    /**
     * достает все продукты из хранилищ и помещает их в один лист
     * и очищает хранилища вызывая метод clear
     */

    public void resort() {
        List<Food> allFood = new ArrayList<>();
        for (Store storage : this.store) {
            allFood.addAll(storage.clear());
        }
        for (Food food : allFood) {
            action(food);
        }
    }

    /**
     * у каждого хранилища вызывается метод проверки соответсвия продукта условию
     * если соответсвует , то продукт добавляется в данное хранилище
     * @param food
     */

    public void action(Food food) {
        for (Store temp : this.store) {
            if (temp.accept(food)) {
                temp.add(food);
                break;
            }
        }
    }

}
