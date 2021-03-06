package ru.job4.parser;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 22.10.2021
 * модель данных Post
 */

public class Post {

    private int id;
    private String name;
    private String description;
    private String url;
    private LocalDateTime created;

    public Post() {
    }

    public Post(int id, String name, String description, String url, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id && Objects.equals(name, post.name)
                && Objects.equals(description, post.description)
                && Objects.equals(url, post.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, url);
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", name='" + name + '\''
                + ", description='" + description + '\'' + ", url='"
                + url + '\''
                + ", created=" + created + '}';
    }
}
