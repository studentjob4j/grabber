package ru.job4.solid.lsp;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public interface Store {

    void add(Food food);

    boolean accept(Food food);

    List<Food> clear();

    /**
     * Данный метод считает оставшийся срок годности продукта в процентах
     * @param food
     * @return число (процент)
     */

    default int calculatePercent(Food food) {
        LocalDate expire = food.getExpireDate();
        LocalDate create = food.getCreateDate();
        LocalDate now = LocalDate.now();
        Period allTime = Period.between(expire, create);
        Period current = Period.between(expire, now);
        int currentDays = current.getDays();
        int allDays = allTime.getDays();
        int result = Math.abs(100 - (currentDays * 100) / allDays);
        return result;
    }
}
