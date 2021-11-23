package ru.job4.solid.lsp;

import java.time.LocalDate;
import java.time.Period;
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
     * Данный метод считает оставшийся срок годности продукта в процентах
     * @param food
     * @return число (процент)
     */
    public int countExpirationDatePercentage(Food food) {
        LocalDate expire = food.getExpireDate();
        LocalDate create = food.getCreateDate();
        LocalDate now = LocalDate.now();
        Period allTime = Period.between(expire, create);
        Period current = Period.between(expire, now);
        int currentDays = Math.abs(current.getDays());
        int allDays = Math.abs(allTime.getDays());
        int result = 100 - (currentDays * 100) / allDays;
        return result;
    }

    /**
     * метод проверяет куда поместить продукт у каждого хранилища вызывается метод добавления
     * и проверяется условие . Если условие выполняется то продук помещается
     * в данное хранилище
     * @param food
     */

    public void action(Food food) {
        for (Store temp : this.store) {
            if (temp.add(food)) {
                break;
            }
        }
    }
}
