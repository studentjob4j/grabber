package ru.job4.parser;

import java.util.List;

/**
 * Связь с базой будет через этот интерфейс
 */

public interface Store {
    void save(Post post);

    List<Post> getAll();

    Post findById(int id);
}
