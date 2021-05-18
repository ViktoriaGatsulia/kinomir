package db_work;

import entity.Debtor;
import entity.Film;
import entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScriptDebtor {


    public static boolean insertDebtor(Statement stmt, Film film, Person person) {
        try {
            Debtor forInsert = new Debtor(film, person, new Date());
            String query = "INSERT INTO debtors (id, id_film, id_person, data_deb) values (" +
                    + forInsert.getId() + ", '" + forInsert.getFilm().getId() + "', '" + forInsert.getPerson().getId() + "', '" + new Date() +
            "')";
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<Debtor> selectDebtorForFio(Statement stmt, String fio) {
        String query = "SELECT * FROM debtors WHERE id_person IN (SELECT id FROM person WHERE LOWER(fio) LIKE LOWER('%" + fio + "%'))";
        ResultSet resultSet = null;
        try {
            resultSet = stmt.executeQuery(query);
            List<Debtor> d = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Film film = ScriptFilms.filmById(stmt, resultSet.getInt("id_film"));
                    Person person = ScriptPerson.personById(stmt, resultSet.getInt("id_person"));
                    d.add(new Debtor(
                            resultSet.getInt("id"),
                            film,
                            person,
                            resultSet.getDate("data_deb")
                    ));
                }
            }
            return d;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    public static List<Debtor> selectDebtorForFilmTitle(Statement stmt, String title) throws SQLException{
        String query = "SELECT * FROM debtors WHERE id_film IN (SELECT id FROM films WHERE LOWER(title) LIKE LOWER('%" + title + "%'))";
        ResultSet resultSet = stmt.executeQuery(query);
        List<Debtor> d = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                Film film = ScriptFilms.filmById(stmt, resultSet.getInt("id_film"));
                Person person = ScriptPerson.personById(stmt, resultSet.getInt("id_person"));
                d.add(new Debtor(
                        resultSet.getInt("id"),
                        film,
                        person,
                        resultSet.getDate("data_deb")
                ));
            }
        }
        return d;
    }

    public static List<Debtor> allSearch(Statement stmt) {
        try {
            String query = "SELECT * FROM debtors";
            ResultSet resultSet = stmt.executeQuery(query);
            List<Debtor> d = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Film film = ScriptFilms.filmById(stmt, resultSet.getInt("id_film"));
                    Person person = ScriptPerson.personById(stmt, resultSet.getInt("id_person"));
                    d.add(new Debtor(
                            resultSet.getInt("id"),
                            film,
                            person,
                            resultSet.getDate("data_deb")
                    ));
                }
            }
            return d;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
