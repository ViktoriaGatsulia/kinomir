package db_work;

import entity.Film;
import entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScriptFilms {


    public static List<Film> allSearch(Statement stmt) {
        try {
            String query = "SELECT * FROM films";
            ResultSet resultSet = stmt.executeQuery(query);
            List<Film> f = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    f.add(new Film(new Film.Builder(resultSet.getString("title").trim())
                            .actors(resultSet.getString("actors").trim()).genre(
                                    resultSet.getString("genre").trim())
                            .content(resultSet.getString("content").trim())
                            .producer(resultSet.getString("producer").trim())
                            .studio(resultSet.getString("studio").trim()),
                            resultSet.getInt("Id")));
                }
            }
            return f;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Film> filmsByTitle(Statement stmt, String title) {
        try {
            String query = "SELECT * FROM films WHERE LOWER(title) LIKE LOWER('%" + title + "%')";
            ResultSet resultSet = stmt.executeQuery(query);
            List<Film> f = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    f.add(new Film(new Film.Builder(resultSet.getString("title").trim())
                            .actors(resultSet.getString("actors").trim()).genre(
                                    resultSet.getString("genre").trim())
                            .content(resultSet.getString("content").trim())
                            .producer(resultSet.getString("producer").trim())
                            .studio(resultSet.getString("studio").trim()),
                            resultSet.getInt("Id")));
                }
            }
            return f;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Film> filmsByGenreOrActor(Statement stmt, String s) {
        try {
            String query = "SELECT * FROM films WHERE LOWER(concat(genre, actors)) LIKE LOWER('%" + s + "%')";
            ResultSet resultSet = stmt.executeQuery(query);
            List<Film> f = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    f.add(new Film(new Film.Builder(resultSet.getString("title").trim())
                            .actors(resultSet.getString("actors").trim()).genre(
                                    resultSet.getString("genre").trim())
                            .content(resultSet.getString("content").trim())
                            .producer(resultSet.getString("producer").trim())
                            .studio(resultSet.getString("studio").trim()),
                            resultSet.getInt("Id")));
                }
            }
            return f;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insertFilm(Statement stmt, String title, String actors, String content, String genre, String producer, String studio) {
        try {
            Film forInsert = new Film(new Film.Builder(title).actors(actors).content(content).genre(genre).producer(producer).studio(studio));
            String query = "INSERT INTO films (id, title, genre, producer, studio, actors, content) values (" +
                    forInsert.getId() + ", '" + forInsert.getTitle() + "', '" + forInsert.getGenre() +
                    ", '" + forInsert.getProducer() + "', '" +
                    ", '" + forInsert.getStudio() + "', '" +
                    ", '" + forInsert.getActors() + "', '" +
                    ", '" + forInsert.getContent() +
                    "')";
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Film filmById(Statement stmt, int id) throws SQLException {
        String query = "SELECT * FROM films WHERE id = " + id;
        ResultSet resultSet = stmt.executeQuery(query);
        if (resultSet != null) {
            resultSet.next();
            return new Film(new Film.Builder(resultSet.getString("title").trim())
                    .actors(resultSet.getString("actors").trim()).genre(
                            resultSet.getString("genre").trim())
                    .content(resultSet.getString("content").trim())
                    .producer(resultSet.getString("producer").trim())
                    .studio(resultSet.getString("studio").trim()),
                    resultSet.getInt("Id"));
        }
        return null;
    }

}
