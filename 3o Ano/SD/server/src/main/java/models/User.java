package models;

import java.io.PrintWriter;
import java.util.Objects;

public class User {
    private final String username;
    private final String password;
    private Boolean isSick;
    private Location location;
    private PrintWriter printWriter;

    public User(String username, String password, PrintWriter printWriter) {
        this.username = username;
        this.password = password;
        this.isSick = false;
        this.location = null;
        this.printWriter = printWriter;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getSick() {
        return isSick;
    }

    public void setSick(Boolean sick) {
        isSick = sick;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "isSick;" + isSick +
                "; location;" + location;
    }
}
