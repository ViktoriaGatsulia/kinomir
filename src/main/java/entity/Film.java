package entity;

import java.util.ArrayList;
import java.util.List;

public class Film {
    private static int idx = 10;
    private final int id;
    private final String title;
    private final String genre; // жанр
    private final String producer; // режиссёр
    private final String studio; // киностудия
    private final String actors;
    private final String content;

    public Film(Builder builder) {
        id = idx;
        title = builder.title;
        genre = builder.genre;
        producer = builder.producer;
        studio = builder.studio;
        actors = builder.actors;
        content = builder.content;
        idx += 1;
    }

    public Film(Builder builder, int id) {
        this.id = id;
        title = builder.title;
        genre = builder.genre;
        producer = builder.producer;
        studio = builder.studio;
        actors = builder.actors;
        content = builder.content;
    }

    public String getStudio() {
        return studio;
    }

    public String getActors() {
        return actors;
    }

    public String getContent() {
        return content;
    }

    public String getProducer() {
        return producer;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public static class Builder {
        private final String title; // обязательный параметр
        private String genre; // жанр
        private String producer; // режиссёр
        private String studio; // киностудия
        private String actors;
        private String content;

        public Builder(String title) {
            this.title = title;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder producer(String producer) {
            this.producer = producer;
            return this;
        }

        public Builder studio(String studio) {
            this.studio = studio;
            return this;
        }

        public Builder actors(String actors) {
            this.actors = actors;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }
    }

    @Override
    public String toString() {
        return id + ") '" + title
                + "'\nЖанр: " + genre
                + "\nРежиссёр: " + producer
                + "\nКиностудия: " + studio
                + "\nАктёры: " + actors
                + "\nСодержание: " + content;
    }
}
