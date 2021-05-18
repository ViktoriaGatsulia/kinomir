package db_work;

import entity.Film;
import entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScriptPerson {


    public static List<Person> allSearch(Statement stmt) {
        try {
            String query = "SELECT * FROM person";
            ResultSet resultSet = stmt.executeQuery(query);
            List<Person> p = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    p.add(new Person(
                            resultSet.getInt("id"),
                            resultSet.getString("fio").trim(),
                            resultSet.getString("address").trim()
                    ));
                }
            }
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Person> personByFio(Statement stmt, String fio) {
        try {
            String query = "SELECT * FROM person WHERE LOWER(fio) LIKE LOWER('%" + fio + "%')";
            ResultSet resultSet = stmt.executeQuery(query);
            List<Person> p = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    p.add(new Person(
                            resultSet.getInt("id"),
                            resultSet.getString("fio").trim(),
                            resultSet.getString("address").trim()
                    ));
                }
            }
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insertPerson(Statement stmt, String fio, String address) {
        try {
            Person forInsert = new Person(fio, address);
            String query = "INSERT INTO person (id, fio, address) values (" +
                    + forInsert.getId() + ", '" + forInsert.getFio() + "', '" + forInsert.getAddress() +
                    "')";
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Person personById(Statement stmt, int id) throws SQLException {
        String query = "SELECT * FROM person WHERE id = " + id;
        ResultSet resultSet = stmt.executeQuery(query);
        if (resultSet != null) {
            resultSet.next();
            return new Person(
                    resultSet.getInt("id"),
                    resultSet.getString("fio").trim(),
                    resultSet.getString("address").trim());
        }
        return null;
    }

}
