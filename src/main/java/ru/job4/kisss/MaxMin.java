package ru.job4.kisss;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Comparator возвращает int по следующей схеме:
 * отрицательный int (первый объект меньше второго)
 * положительный int (первый объект  больший)
 * ноль = объекты равны
 * @author Shegai Evgenii
 * @since 08.11.2021
 * @version 1.0
 */

public class MaxMin {

    /**
     * Если первое число меньше второго , то возвращается отрицательное число
     */

    public <T> T max(List<T> value, Comparator<T> comparator) {
        Predicate<Integer> predicate = x -> x < 0;
        return minmax(comparator, value, predicate);
    }

    /**
     * Если первое число больше второго, то возвращается положительное число
     */

    public <T> T min(List<T> value, Comparator<T> comparator) {
        Predicate<Integer> predicate = x -> x > 0;
        return minmax(comparator, value, predicate);
    }

    /**
     * Поиск  макс.числа = получаем 1(резалт) элемент и  сравниваем его со 2ым (темп).
     * Если 1 элемент меньше второго ,
     * то помещаем в переменную резалт это число. Далее получаем опять число из
     * списка в переменную темп
     * и сравниваем их . Если  первое число (резалт) у нас меньше , чем число в пеерменной темп ,
     * то опять перезаписываем макс. число  в переменную резалт и продолжаем цикл.
     * А если первое число(резалт)
     * больше , чем второе(темп) , то предикат не срабатывает и продолжаем цикл далее.
     *
     * Поиск мин числа наоборот - первое число больше второго, то возвращается положительное число
     */

    public <T> T minmax(Comparator<T> comparator, List<T> value, Predicate<Integer> predicate) {
        T result = value.get(0);
        for (int i = 1; i < value.size(); i++) {
            T temp = value.get(i);
            if (predicate.test(comparator.compare(result, temp))) {
                result = value.get(i);
            }
        }
        return result;
    }
}
