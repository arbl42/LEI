package models;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Users {
    private final Map<String, User> users;
    private final Lock readLock;
    private final Lock writeLock;

    public Users() {
        this.users = new HashMap<>();
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
    }

    public User get(String username) {
        User user;

        readLock.lock();

        try {
            user = users.get(username);
        } finally {
            readLock.unlock();
        }

        return user;
    }

    public boolean containsKey(String username) {
        boolean containsKey;

        readLock.lock();

        try {
            containsKey = users.containsKey(username);
        } finally {
            readLock.unlock();
        }

        return containsKey;
    }

    public User createUser(User user) {
        writeLock.lock();

        try {
            if (!users.containsKey(user.getUsername())) {
                users.put(user.getUsername(), user);
            } else {
                user = null;
            }
        } finally {
            writeLock.unlock();
        }

        return user;
    }

    public User createUser(String username, String password, PrintWriter printWriter) {
        User user = new User(username, password, printWriter);
        return createUser(user);
    }
}
