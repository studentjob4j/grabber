package ru.job4.solid.isp;

import java.util.List;
import java.util.Optional;

/**
 * Меню
 * @author Shegai Evgenii
 * @since 24.11.2021
 * @version 1.0
 */

public class Menu {

    private Item item;

    public Menu(Item item) {
        this.item = item;
    }

    /**
     * добавляет родительский элемент меню
     */

    public boolean addParent(String name) {
        return this.item.getList().add(new Item(name));
    }

    /**
     * ищет в родительском списке родителя и добавляем в его список новый элемент меню
     * если не находим то кидаем исключение
     */

    public boolean addChild(String parentName, Item child) {
        boolean check = false;
        for (Item tmp : this.item.getList()) {
            if (tmp.getName().equals(parentName)) {
                check = tmp.getList().add(child);
                break;
            }
        }
        if (!check) {
            throw new IllegalArgumentException("Please check parent name");
        }
        return check;
    }

    /**
     * получаем по имени родительский элмент меню. Если у корневого элемента список пустой ,
     * кидаем исключение. Если нет совпадения в родительском списке то ищем в дочерних
     * списках. Если и там нет то создаем новый итем с именем - пункт меню не обнаружен
     */

    public Item get(String name) {
        Optional<Item> rsl = Optional.empty();
        if (this.item.getList().size() == 0) {
            throw new UnsupportedOperationException("Not data");
        }
        for (Item temp : this.item.getList()) {
            if (temp.getName().equals(name)) {
                rsl = Optional.of(temp);
                break;
            }
        }
        if (rsl.isEmpty()) {
            rsl = find(name);
        }
        return rsl.orElse(new Item("item not found"));
    }

    /**
     * ищет элемент меню в дочерних пунктах .
     */

    private Optional<Item> find(String name) {
        Item res = null;
        int index = 0;
        while (index != this.item.getList().size()) {
            Item temp = this.item.getList().get(index);
            List<Item> child = temp.getList();
            for (int i = 0; i < child.size(); i++) {
                Item tmp = child.get(i);
                if (tmp.getName().equals(name)) {
                    res = tmp;
                    index = child.size();
                }
            }
            if (res == null) {
                index++;
            }
        }
        return Optional.ofNullable(res);
    }

    /**
     * печатает меню на экран
     */

    public void print() {
        int count = 0;
        while (count != this.item.getList().size()) {
            Item temp = this.item.getList().get(count);
            List<Item> tmp = temp.getList();
            System.out.println(temp.getName());
            for (Item menu : tmp) {
                System.out.println(menu.getName());
            }
            count++;
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(new Item("Basic"));
        menu.addParent("Задача 1");
        menu.addParent("Задача 2");
        menu.addChild("Задача 1", new Item("Задача 1.1"));
        menu.addChild("Задача 1", new Item("Задача 1.2"));
        menu.addChild("Задача 1", new Item("Задача 1.3"));
        menu.addChild("Задача 2", new Item("Задача 2.1"));
        menu.addChild("Задача 2", new Item("Задача 2.2"));
        menu.print();
    }
}
