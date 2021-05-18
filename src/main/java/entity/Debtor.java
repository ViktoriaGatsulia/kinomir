package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Debtor {
    private static int idx = 1;
    private final int id;
    private final Person person;
    private final Date date_deb;
    private final Film film;

    public Debtor(Film film, Person person, Date date_deb) {
        id = idx;
        this.film = film;
        this.person = person;
        this.date_deb = date_deb;
        idx += 1;
    }
    public Debtor(int id, Film film, Person person, Date date_deb) {
        this.id = id;
        this.film = film;
        this.person = person;
        this.date_deb = date_deb;
    }

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Film getFilm() {
        return film;
    }

    public Date getDate_deb() {
        return date_deb;
    }

    @Override
    public String toString() {
        return id + ") " + person.getFio() + "\t" + film.getTitle() + "\t" + date_deb;
    }

}
