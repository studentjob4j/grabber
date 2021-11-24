package ru.job4.solid.lsp;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Хранилище продуктов использую шаблон проектирования - стратегия
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class ControlQualityClientTest {

    private List<Store> list;

    @Before
    public void createStorage() {
        Warehouse warehouse = new Warehouse();
        Shop shop = new Shop();
        Trash trash = new Trash();
        list = List.of(warehouse, shop, trash);
    }

    @Ignore
    @Test
    public void whenSetFoodInShop() {
        LocalDate created = LocalDate.of(2021, 11, 10);
        LocalDate expire = LocalDate.of(2021, 11, 30);
        Milk milk = new Milk("Milk", expire, created, 100, 0);
        ControlQualityClient client = new ControlQualityClient();
        client.setStore(list);
        client.action(milk);
        Shop shop = (Shop) client.getStore().get(1);
        assertThat(shop.getStorage().get(0).getName(), is("Milk"));
    }

    @Ignore
    @Test
    public void whenSetFoodInShopWithDiscount() {
        LocalDate created = LocalDate.of(2021, 11, 10);
        LocalDate expire = LocalDate.of(2021, 11, 27);
        Strawberry strawberry = new Strawberry("Strawberry", expire, created, 300, 12);
        ControlQualityClient client = new ControlQualityClient();
        client.setStore(list);
        client.action(strawberry);
        Shop shop = (Shop) client.getStore().get(1);
        assertThat(shop.getStorage().get(0).getPrice(), is(288.0));
    }

    @Ignore
    @Test
    public void whenSetFoodInWarehouse() {
        LocalDate created = LocalDate.of(2021, 11, 22);
        LocalDate expire = LocalDate.of(2021, 11, 30);
        Meat meat = new Meat("Meat", expire, created, 400, 0);
        ControlQualityClient client = new ControlQualityClient();
        client.setStore(list);
        client.action(meat);
        Warehouse warehouse = (Warehouse) client.getStore().get(0);
        assertThat(warehouse.getStorage().get(0).getName(), is("Meat"));
    }

    @Ignore
    @Test
    public void whenSetFoodInTrash() {
        LocalDate created = LocalDate.of(2021, 11, 10);
        LocalDate expire = LocalDate.of(2021, 11, 23);
        Onion onion = new Onion("Onion", expire, created, 40, 0);
        ControlQualityClient client = new ControlQualityClient();
        client.setStore(list);
        client.action(onion);
        Trash trash = (Trash) client.getStore().get(2);
        assertThat(trash.getStorage().get(0).getName(), is("Onion"));
    }

}