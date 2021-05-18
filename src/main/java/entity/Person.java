package entity;


public class Person {
    private static int idx = 1;

    private final int id;
    private final String fio;
    private final String address;

    public Person(int id, String fio, String address) {
        this.id = id;
        this.fio = fio;
        this.address = address;
    }

    public Person(String fio, String address) {
        this.id = idx;
        this.fio = fio;
        this.address = address;
        idx++;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return id + ") " + fio.trim() + " (" + address.trim() + ")";
    }
}
