package ru.job4.solid.lsp.parking;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Парковка машин
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class CarParkingTest {

    private Volvo volvo;
    private Volvo volvo2;
    private Tesla one;
    private Tesla two;

    @Before
    public void createCar() {
        this.volvo = new Volvo();
        this.volvo2 = new Volvo();
        this.one = new Tesla();
        this.two = new Tesla();
    }

    @Test
    public void whenAddVolvoInParking() {
        CarParking parking = new CarParking(2, 10);
        boolean res = parking.add(new Volvo());
        assertTrue(res);
    }

    @Test
    public void whenAddTwoTrucksInCarParking() {
        CarParking parking = new CarParking(0, 10);
        parking.add(volvo);
        parking.add(volvo2);
        Auto[] result = parking.getSimpleCar();
        assertThat(result[0], is(volvo));
        assertThat(result[1], is(volvo));
        assertThat(result[2], is(volvo2));
        assertThat(result[3], is(volvo2));
    }

    @Test
    public void whenAddTeslaInParking() {
        CarParking parking = new CarParking(2, 10);
        boolean res = parking.add(new Tesla());
        assertTrue(res);
    }

    @Test
    public void whenAddTwoTrucksThenRemove() {
        CarParking parking = new CarParking(0, 4);
        parking.add(volvo);
        parking.add(volvo2);
        parking.remove(volvo2);
        parking.remove(volvo);
        int free = parking.getCar();
        assertThat(free, is(4));
    }

    @Test
    public void whenAddTwoTrucksThenRemoveFirstAndAddTwoCar() {
        CarParking parking = new CarParking(0, 4);
        parking.add(volvo);
        parking.add(volvo2);
        parking.remove(volvo);
        parking.add(one);
        parking.add(two);
        assertThat(parking.getCar(), is(0));
    }
}