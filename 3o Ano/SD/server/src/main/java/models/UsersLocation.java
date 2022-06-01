package models;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UsersLocation {
    private final Map<Location, List<User>> users_current_location;
    private final Map<Location, Set<User>> users_locations_history;

    private final Lock readLock;
    private final Lock writeLock;

    public UsersLocation() {
        this.users_current_location = new HashMap<>();
        this.users_locations_history = new HashMap<>();

        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
    }

    public List<User> getCurrentLocations(Location location) {
        List<User> users;

        readLock.lock();

        try {
            users = new ArrayList<>(users_current_location.get(location));
        } finally {
            readLock.unlock();
        }

        return users;
    }

    public Map<Location, Set<User>> getLocationsHistory() {
        Map<Location, Set<User>> locationsHistory = new HashMap<>();

        readLock.lock();

        try {
            users_locations_history.forEach((l, u) ->
                locationsHistory.put(l, new LinkedHashSet<>(u))
            );
        } finally {
            readLock.unlock();
        }

        return locationsHistory;
    }

    public int getNumberPeople(Location location) {
        int numberPeople = 0;

        readLock.lock();

        try {
            if (users_current_location.containsKey(location)) {
                numberPeople = users_current_location.get(location).size();
            }
        } finally {
            readLock.unlock();
        }

        return numberPeople;
    }

    public void addUserToLocation(User user, Location location) {
        writeLock.lock();

        try {
            if (users_current_location.containsKey(location)) {
                users_current_location.get(location).add(user);
                users_locations_history.get(location).add(user);

                user.setLocation(location);
            } else {
                initLocation(user, location);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public Location updateUserLocation(User user, Location location) {
        Location prev_location;

        writeLock.lock();

        try {
            prev_location = updateLocation(user, location);
        } finally {
            writeLock.unlock();
        }

        return prev_location;
    }

    private void initLocation(User user, Location location) {
        List<User> users_on_location = new ArrayList<>();
        Set<User> location_history = new LinkedHashSet<>();

        users_on_location.add(user);
        users_current_location.put(location, users_on_location);
        
        location_history.add(user);
        users_locations_history.put(location, location_history);

        user.setLocation(location);
    }

    private Location updateLocation(User user, Location location) {
        //save previous location
        Location prev_location = user.getLocation();
        List<User> user_at_previous_location = users_current_location.get(prev_location);

        //remove user from previous location if it has one
        user_at_previous_location.remove(user);

        //put user in locations map
        if (users_current_location.containsKey(location)) {
            users_current_location.get(location).add(user);
            users_locations_history.get(location).add(user);
            
            user.setLocation(location);
        } else {
            initLocation(user, location);
        }

        if (user_at_previous_location.size() > 0) {
            prev_location = null;
        }

        return prev_location;
    }
}
