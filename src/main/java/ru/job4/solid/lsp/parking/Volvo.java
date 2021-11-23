package ru.job4.solid.lsp.parking;

/**
 * Парковка машин
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class Volvo implements Auto {

    @Override
    public int getSize() {
        return 2;
    }
}
