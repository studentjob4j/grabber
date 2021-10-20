package ru.job4.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shegai Evgenii
 * @since 20.10.2021
 * @version 1.0
 * Простой парсер страницы
 */

public class SqlRuParse {

    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        // Получаю элементы по указанным селектам
        Elements row = doc.select(".postslisttopic");
        Elements row2 = doc.select(".altCol");
        // получаю по атрибуту - текст и помещаю его в лист
        for (Element element : row2) {
            list.add(element.getElementsByAttribute("style").text());
        }
        int count = 0;
        // тут получаю элемент  - у элемента получаю -
        // первого ребенка под индексом ноль из массива нодов -
        // по атрибуту href получаю ссылку , а по text получаю сам текст
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(list.get(count++) + System.lineSeparator());
        }
    }
}
