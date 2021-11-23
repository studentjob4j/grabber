package ru.job4.solid.lsp;

import java.time.LocalDate;

/**
 * Хранилище продуктов использую шаблон проектирования - стратегия
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class Food {

    private String name;
    private LocalDate expireDate;
    private LocalDate createDate;
    private double price;
    private double discount;

    public Food(String name, LocalDate expireDate, LocalDate createDate,
                double price, double discount) {
        this.name = name;
        this.expireDate = expireDate;
        this.createDate = createDate;
        this.price = price;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
