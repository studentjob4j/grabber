package ru.job4.solid.srp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.job4.parser.Post;
import java.io.IOException;

/**
 * Не рпавильное исользование принципа srp через класс проходит более одной
 * оси изменений
 * @author Shegai Evgenii
 * @since 15.11.2021
 * @version 1.0
 */

public class Srp {

    public int square(int a, int b) {
        WrongTwoSrpDemo twoSrpDemo = new WrongTwoSrpDemo();
        return (int) Math.pow(twoSrpDemo.summ(a, b), 2);
    }

    public Post simpleParse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            //  some logic
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Post();
    }
}
