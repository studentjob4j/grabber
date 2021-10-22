package ru.job4.grabber;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Shegai Evgenii
 * @since 21.10.2021
 * @version 1.0
 * Загрузка деталей одного поста
 */

public class SimpleParseOneLinkTest {

    @Test
    public void whenGetPost() {
        SimpleParseOneLink post = new SimpleParseOneLink();
        post.createPostAfterParse();
        assertThat(post.getPost().getName(),
                is("Лиды BE/FE/senior cистемные аналитики/QA и DevOps, Москва, до 200т. [new]"));
    }

}