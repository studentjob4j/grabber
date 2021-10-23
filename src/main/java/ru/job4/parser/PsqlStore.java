package ru.job4.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Shegai Evgenii
 * @since 23.10.2021
 * @version 1.0
 * Класс создает соединение к бд потом создает таблицу - помещает в таблицу 2 записи -
 * извлекает из бд по id запись и все записи - удаляет таблицу из бд и закрывает соединение
 *
 */

public class PsqlStore implements Store, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
            this.cnn = DriverManager.getConnection(cfg.getProperty("url"),
                    cfg.getProperty("username"), cfg.getProperty("password"));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cnn.prepareStatement(
                "insert into post(name, description, url, created_date)"
                        + "values (?, ?, ?, ?)")) {
            statement.setString(1, post.getName());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getUrl());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            statement.execute();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement = cnn.prepareStatement(
                "select * from Post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("url"),
                            resultSet.getTimestamp("created_date").toLocalDateTime());
                    posts.add(post);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post result = new Post();
        try (PreparedStatement statement = cnn.prepareStatement(
                "select * from Post as p where p.id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result = new Post(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("url"),
                            resultSet.getTimestamp("created_date").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    @Override
    public void close() {
        if (cnn != null) {
            try {
                cnn.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    private static Properties getProperties() {
        Properties pro = new Properties();
        ClassLoader loader = PsqlStore.class.getClassLoader();
        try (InputStream is = loader.getResourceAsStream("psql.properties")) {
            pro.load(is);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return pro;
    }

    public void createTable(String tablename) {
        try (Statement statement = cnn.createStatement()) {
            String temp = String.format("create table %s (%s, %s, %s, %s, %s);",
                    tablename,
                    "id serial primary key", "name varchar(250)",
                    "description text", "url varchar(250) unique", "created_date TIMESTAMP");
            statement.execute(temp);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void dropTable(String tableName) {
        try (Statement statement = cnn.createStatement()) {
            String temp = String.format("drop table %s ", tableName);
            statement.execute(temp);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        PsqlStore store = new PsqlStore(getProperties());
        store.createTable("post");
        LocalDateTime time = LocalDateTime.now();
        store.save(new Post(1, "shop1", "desc1", "www.google.com", time));
        store.save(new Post(2, "shop2", "desc2", "www.yandex.ru", time));
        Post result = store.findById(2);
        System.out.println(result.getName());
        List<Post> res = store.getAll();
        for (Post temp : res) {
            System.out.println(temp);
        }
        store.dropTable("post");
        store.close();
    }
}
