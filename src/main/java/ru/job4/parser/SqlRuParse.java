package ru.job4.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shegai Evgenii
 * @since 20.10.2021
 * @version 2.0
 * Программа получает ссылку на общую страницу с вакансиями далее парсит ее и
 * получает ссылки на отдельные посты  далее по ссылкам получает инф для создания
 * поста по каждой вакансии имя, описание, дата создания, ссылка на вакансию
 * и сохраняет ее в лист постов
 */

public class SqlRuParse implements Parse {

    private static List<Post> list = new ArrayList<>();
    private static final Logger LOG = LoggerFactory.getLogger(SqlRuParse.class.getName());
    private SimpleParseOneLink parsePost = new SimpleParseOneLink();

    @Override
    public List<Post> list(String link) {
        int count = 3;
        try {
            Document document = Jsoup.connect(link).get();
            //получаем все ссылки по селекту
            Elements url = document.select("td[class=postslisttopic]");
            //начинаем с 3 т.к вначале идет просто инф не ссылки
            while (count < url.size()) {
                // получаем 3 элемент
                Element element = url.get(count);
                // по селекту а получаем  ссылки
                Elements temp = element.select("a");
                // получаем ссылку по атрибуту href
                String value = temp.attr("href");
                list.add(parsePost.createPostAfterParse(value));
                count++;
            }
        } catch (IOException e) {
          LOG.error(e.getMessage());
        }
        return list;
    }

    @Override
    public Post detail(String link) {
        return parsePost.createPostAfterParse(link);
    }

    /*private static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        // Получаю элементы по указанным селектам
        Elements row = doc.select(".postslisttopic");
        Elements row2 = doc.select(".altCol");
        // получаю по атрибуту - текст и помещаю его в лист
        for (int i = 0; i < row2.size(); i++) {
            String text = row2.get(i).getElementsByAttribute("style").text();
            if (i % 2 == 1) {
                list.add(text);
            }
        }
        // тут получаю элемент  - у элемента получаю -
        // первого ребенка под индексом ноль из массива нодов -
        // по атрибуту href получаю ссылку , а по text получаю сам текст
        int count = 0;
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(list.get(count++) + System.lineSeparator());
        }
    }*/
}
