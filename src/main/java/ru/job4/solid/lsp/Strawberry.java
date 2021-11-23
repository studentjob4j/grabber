package ru.job4.solid.lsp;

import java.time.LocalDate;

/**
 * Хранилище продуктов использую шаблон проектирования - стратегия
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class Strawberry extends Food {

    public Strawberry(String name, LocalDate expireDate, LocalDate createDate,
                      double price, double discount) {
        super(name, expireDate, createDate, price, discount);
    }
}
