package ru.job4.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Shegai Evgenii
 * @since 21.10.2021
 * @version 1.0
 * Загрузка деталей одного поста
 */

public class SimpleParseOneLink {

    private static final Logger LOG = LoggerFactory.getLogger(SqlRuDateTimeParser.class.getName());
    private Post post;
    private int id = 1;

    public Post getPost() {
        return post;
    }

    /**
     * получаем документ по ссылке - получаем ссылку, имя, описание, дату используя класс
     * SqlRuDateTimeParser создаем пост
     * @return post
     */
    public Post createPostAfterParse(String link) {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        try {
            Document document = Jsoup.connect(link).get();
            Elements url = document.select("link[rel=canonical]");
            Elements name = document.select("td[class=messageHeader]");
            Elements desc = document.select("td[class=msgBody]");
            Elements data = document.select("td[class=msgFooter]");
            //String date = data.get(0).text().substring(0, 16);
            String date = splitDate(data.get(0).text());
            LocalDateTime created = parser.parse(date);
            post = new Post(id++, splitName(name), desc.get(1).text(), url.attr("href"), created);

           } catch (IOException e) {
            LOG.error(e.getMessage());
           }
        return post;
    }

    /**
     * Если строка содержит слово сегодня или вчера то - из строки вырезаем слово + время
     * например - сегодня, 14:45 [22386878] Ответить | Цитировать Сообщить модератору
     * получаем "сегодня, 14:45"
     * @param text
     * @return
     */
    private String splitDate(String text) {
        StringBuilder builder = new StringBuilder();
        int limit = 3;
        String[] temp = text.split(" ");
        for (int i = 0; i <= limit; i++) {
            if (temp[i].contains("сегодня") || temp[i].contains("вчера")) {
                limit = 1;
            }
            builder.append(temp[i]);
            if (i != limit) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    // из Elements получаю element - потом из него имя типа string
    private String splitName(Elements el) {
        return el.get(0).text();
    }

}
