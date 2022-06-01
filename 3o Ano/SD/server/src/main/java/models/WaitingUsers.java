package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitingUsers {
    private final Map<Location, List<User>> waiting_users;
    private final Lock lock;

    public WaitingUsers() {
        this.waiting_users = new HashMap<>();
        this.lock = new ReentrantLock();
    }

    public List<User> getWaitingUsers(Location location) {
        List<User> users = new ArrayList<>();

        lock.lock();

        try {
            if (waiting_users.containsKey(location)) {
                users = new ArrayList<>(waiting_users.get(location));
                waiting_users.get(location).clear();
            }
        } finally {
            lock.unlock();
        }

        return users;
    }

    public void putUserWaiting(Location location, User user) {
        lock.lock();

        try {
            if (waiting_users.containsKey(location)) {
                waiting_users.get(location).add(user);
            } else {
                List<User> users_at_location = new ArrayList<>();
                users_at_location.add(user);
                waiting_users.put(location, users_at_location);
            }
        } finally {
            lock.unlock();
        }
    }
}
