import db_work.ScriptDebtor;
import db_work.ScriptFilms;
import db_work.ScriptPerson;
import entity.Debtor;
import entity.Film;
import entity.Person;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final String URL = "jdbc:postgresql://localhost:5432/kinomir";
    private static final String USER = "postgres";
    private static final String PASSWD = "postgres";


    public static void main(String[] argv) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD)) {
            Statement stmt = conn.createStatement();
            ScriptPerson.allSearch(stmt);

            while (true) {
                System.out.println("Меню: ");
                System.out.println("1. Ввод данных о новом пользователе");
                System.out.println("2. Ввод данных о новом фильме");
                System.out.println("3. Отдать фильм");
                System.out.println("4. Получение сведений о том, какие фильмы брал данный пользователь с распечаткой");
                System.out.println("5. Получение сведений о том, кто и когда брал данный фильм с распечаткой");
                System.out.println("6. Получение списка должников с распечаткой");
                System.out.println("7. Получение списка отданных фильмов с распечаткой");
                System.out.println("8. Поиск фильма по названию (неточный поиск)");
                System.out.println("9. Поиск пользователя по ФИО (неточный поиск)");
                System.out.println("10. Поиск фильма по жанру и по актерам");
                System.out.println("11. Составление письма должнику");
                System.out.println("12. Составление письма давно не посещавшему видеотеку пользователю");

                Scanner scanner = new Scanner(System.in);
                int num = scanner.nextInt();

                switch (num) {
                    case 1:
                        insertPersonMenu(stmt);
                        break;
                    case 2:
                        insertFilmMenu(stmt);
                        break;
                    case 3:
                        insertDebtorMenu(stmt);
                        break;
                    case 4:
                        getDebtorByFio(stmt);
                        break;
                    case 5:
                        searchAllDebtors(stmt);
                        break;
                    case 6:
                        printAllPerson(stmt);
                        break;
                    case 7:
                        printAllFilm(stmt);
                        break;
                    case 8:
                        searchFilmByTitle(stmt);
                        break;
                    case 9:
                        searchPersonByFio(stmt);
                        break;
                    case 10:
                        searchFilmByGenreAndActors(stmt);
                        break;
                    case 11:
                        letter1(stmt);
                        break;
                    case 12:
                        letter2(stmt);
                        break;
                    default:
                        return;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(1);
        }
    }

    public static void letter2(Statement stmt) {

    }
    public static void letter1(Statement stmt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите фамилию должника:");
        String personFio = scanner.nextLine();
        while (personFio.trim().equals("") || personFio.trim().equals("\n")) {
            personFio = scanner.nextLine();
        }
        System.out.println("Введите дату возврата:");
        String date_return = scanner.nextLine();
        String fio = "";
        String address = "";
        List<Person> p = ScriptPerson.personByFio(stmt, personFio);
        if (p != null) {
            if (!p.isEmpty()) {
                fio = p.get(0).getFio();
                fio = p.get(0).getAddress();
            } else {
                fio = personFio;
            }
        }
        String[] fio_full = fio.split(" ");
        String l_name = "";
        String f_name = "";
        String m_name = "";
        if (fio_full.length >= 3) {
            l_name = fio_full[0];
            f_name = fio_full[1];
            m_name = fio_full[2];
        }
        String film_title = "";
        String date_deb = "";
        List<Debtor> d = ScriptDebtor.selectDebtorForFio(stmt, fio);
        if (d != null) {
            if (!d.isEmpty()) {
                d.get(0).getFilm().getTitle();
                d.get(0).getDate_deb();
            }
        }
        try (FileWriter writer = new FileWriter("letter_1_" + Math.random() + ".txt", false)) {
            String text = l_name + " " + f_name.substring(0, 1) + ". " + m_name.substring(0, 1) + ".\n" ;
            text += address + "\n";
            text += "Уважаемый " + f_name + " " + m_name + "!\n";
            text += "Убедительно прошу Вас вернуть фильм «" + film_title + "»,\n";
            text += "который Вы взяли " + date_deb + " до " + date_return + ".\n";
            text += "\t\t\t\t\t\tЗаранее спасибо\n";
            text += "\t\t\t\t\t\t\t\tПодпись\n";
            text += "\t\t\t\t\t\t\t\tДата\n";
            System.out.println(text);
            writer.write(text);
            writer.append('\n');
            writer.append('E');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void searchFilmByGenreAndActors(Statement stmt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите жанр или актёра из фильма:");
        String s = scanner.nextLine();
        while (s.trim().equals("") || s.trim().equals("\n")) {
            s = scanner.nextLine();
        }
        List<Film> f = ScriptFilms.filmsByGenreOrActor(stmt, s);
        if (f != null) {
            if (!f.isEmpty()) {
                for (Film film : f) {
                    System.out.println(film.toString());
                }
            } else {
                System.out.println("Подходящих фильмов нет.");
            }
        }
    }

    public static void searchFilmByTitle(Statement stmt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название фильма:");
        String title = scanner.nextLine();
        while (title.trim().equals("") || title.trim().equals("\n")) {
            title = scanner.nextLine();
        }
        List<Film> f = ScriptFilms.filmsByTitle(stmt, title);
        if (f != null) {
            if (!f.isEmpty()) {
                for (Film film : f) {
                    System.out.println(film.toString());
                }
            } else {
                System.out.println("Фильмов с таким названием нет.");
            }
        }
    }

    public static void searchAllDebtors(Statement stmt) {
        List<Debtor> d = ScriptDebtor.allSearch(stmt);
        if (d != null) {
            if (!d.isEmpty()) {
                for (Debtor debtor : d) {
                    System.out.println(debtor.toString());
                }
            } else {
                System.out.println("Вы ещё никому не давлали фильмов");
            }
        }
    }

    public static void getDebtorByFio(Statement stmt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите фамилию персоны, которой отдавали фильмы:");
        String personFio = scanner.nextLine();
        while (personFio.trim().equals("") || personFio.trim().equals("\n")) {
            personFio = scanner.nextLine();
        }
        List<Debtor> d = ScriptDebtor.selectDebtorForFio(stmt, personFio);
        if (d != null) {
            if (!d.isEmpty()) {
                for (Debtor debtor : d) {
                    System.out.println(debtor.toString());
                }
            } else {
                System.out.println("Этому человеку вы ещё фильмов не давали, введите номер 4 ещё раз, для повторного поиска");
            }
        }

    }

    public static void insertDebtorMenu(Statement stmt) {
        System.out.println("Введите имя или фамилию персоны, которой собираетесь отдать фильм:");
        Scanner scanner1 = new Scanner(System.in);
        String fio = scanner1.nextLine();
        List<Person> p = ScriptPerson.personByFio(stmt, fio);
        if (p == null) {
        } else if (p.size() > 1) {
            for (Person person : p) {
                System.out.println(person.toString());
            }
            System.out.println("Персон, подходящих по описанию несколько, введите цифру 3 ещё раз и уточните, пожалуйста, запрос");
        } else if (p.isEmpty()) {
            System.out.println("Ещё не было добавлено ниодного должника с такой фамилией, добавить? (y \\ n)");
            String reshenie = scanner1.nextLine();
            if (reshenie.trim().equals("y")) {
                insertPersonMenu(stmt);
            }
        } else {
            System.out.println("Введите название фильма, который хотите отдать:");
            String title = scanner1.nextLine();
            List<Film> f = ScriptFilms.filmsByTitle(stmt, title.trim());
            if (f != null) {
                if (!f.isEmpty()) {
                    if (f.size() > 1) {
                        for (Film film : f) {
                            System.out.println(film.toString());
                        }
                        System.out.println("Фильмов, подходящих по описанию несколько, введите цифру 3 ещё раз и уточните, пожалуйста, запрос");
                    } else {
                        if (ScriptDebtor.insertDebtor(stmt, f.get(0), p.get(0))) {
                            System.out.println("Должник и фильм успешно добавлены");
                        } else {
                            System.out.println("Должника и фильм добавить не удалось");
                        }
                    }
                } else {
                    System.out.println("Ещё не было добавлено ниодного фильма с таким названием, добавить? (y \\ n)");
                    String reshenie = scanner1.nextLine();
                    if (reshenie.trim().equals("y")) {
                        insertFilmMenu(stmt);
                    }
                }
            }
        }
    }

    public static void searchPersonByFio(Statement stmt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите фамилию персоны, которую хотите найти:");
        String personFio = scanner.nextLine();
        while (personFio.trim().equals("") || personFio.trim().equals("\n")) {
            personFio = scanner.nextLine();
        }
        List<Person> p = ScriptPerson.personByFio(stmt, personFio);
        if (p != null) {
            if (!p.isEmpty()) {
                for (Person person : p) {
                    System.out.println(person.toString());
                }
            } else {
                System.out.println("Персоны с такой фамилией нет, введите номер 9 ещё раз, для повторного поиска");
            }
        }
    }

    public static void insertPersonMenu(Statement stmt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ФИО новой персоны:");
        String fio = scanner.nextLine();
        while (fio.trim().equals("") || fio.trim().equals("\n")) {
            fio = scanner.nextLine();
        }
        System.out.println("Введите адрес новой персоны:");
        String address = scanner.nextLine();
        boolean result = ScriptPerson.insertPerson(stmt, fio, address);
        if (result) {
            System.out.println("Персона успешно добавлена.");
        } else {
            System.out.println("Персону не удалось добавить.");
        }
    }

    public static void insertFilmMenu(Statement stmt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название нового фильма:");
        String title = scanner.nextLine();
        while (title.trim().equals("") || title.trim().equals("\n")) {
            title = scanner.nextLine();
        }
        System.out.println("Введите жанр:");
        String genre = scanner.nextLine();
        System.out.println("Введите режиссёра:");
        String producer = scanner.nextLine();
        System.out.println("Введите киностудию:");
        String studio = scanner.nextLine();
        System.out.println("Введите актёров:");
        String actors = scanner.nextLine();
        System.out.println("Введите краткое содержание:");
        String content = scanner.nextLine();

        if (ScriptFilms.insertFilm(stmt, title, actors, content, genre, producer, studio)) {
            System.out.println("Фильм успешно добавлен.");
        } else {
            System.out.println("Фильм не удалось добавить.");
        }
    }

    public static void printAllPerson(Statement stmt) {
        List<Person> p = ScriptPerson.allSearch(stmt);
        if (p != null) {
            if (!p.isEmpty()) {
                for (Person person : p) {
                    System.out.println(person.toString());
                }
            } else {
                System.out.println("Ещё не добавлено ни одной персоны.");
            }
        }
    }

    public static void printAllFilm(Statement stmt) {
        List<Film> f = ScriptFilms.allSearch(stmt);
        if (f != null) {
            if (!f.isEmpty()) {
                for (Film film : f) {
                    System.out.println(film.toString());
                }
            } else {
                System.out.println("Ещё не добавлено ни одного фильма.");
            }
        }
    }
}
