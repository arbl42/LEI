package models;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

// ZONA CRITICA ACEDIDA POR MUITA GENTE EM SIMULTANEO
public class AlarmeCovid {
    private final Users users = new Users();
    private final UsersLocation usersLocation = new UsersLocation();
    private final WaitingUsers waitingUsers = new WaitingUsers();

    public String register(String[] parts, PrintWriter printWriter) {
        String username = parts[1];
        String password = parts[2];
        double latitude = Double.parseDouble(parts[3]);
        double longitude = Double.parseDouble(parts[4]);
        Location location = new Location(latitude, longitude);

        User user = users.createUser(username, password, printWriter);

        if (user == null) {
            return "err;username_already_exists";
        } else {
            usersLocation.addUserToLocation(user, location);
        }

        return "ok;" + username;
    }

    public String login(String[] parts, PrintWriter printWriter) {
        String username = parts[1];
        String password = parts[2];

        if (username.equals("admin")) {
            return adminLogin(username, password, printWriter);
        }

        return userLogin(username, password, parts, printWriter);
    }

    public String getUserInfo(String loggedInUser) {
        User user = users.get(loggedInUser);
        return loggedInUser + ";" + user.toString();
    }

    public String setClientLocation(User loggedInUser, String[] parts) {
        //save client's current location
        double latitude = Double.parseDouble(parts[1]);
        double longitude = Double.parseDouble(parts[2]);
        Location location = new Location(latitude, longitude);

        updateUserLocation(loggedInUser, location);

        return "ok;location";
    }

    public String getNumberPeople(String[] parts) {
        //get number of people gathered at one location
        double latitude = Double.parseDouble(parts[1]);
        double longitude = Double.parseDouble(parts[2]);
        Location location = new Location(latitude, longitude);

        return "number-people;" + usersLocation.getNumberPeople(location);
    }

    public String locationIsEmpty(User loggedInUser, String[] parts) {
        double latitude = Double.parseDouble(parts[1]);
        double longitude = Double.parseDouble(parts[2]);
        Location location = new Location(latitude, longitude);

        //verify if location is currently empty
        if (usersLocation.getNumberPeople(location) == 0) {
            return "location;empty";
        } else {
            //verify if there is already a waiting list for the location
            waitingUsers.putUserWaiting(location, loggedInUser);

            return "location;not-empty;waiting";
        }
    }

    public String sickClient(User loggedInUser) {
        //notify everyone in the last known location of the user they might be sick
        String loggedInUsername = loggedInUser.getUsername();
        String last_location = loggedInUser.getLocation().toString();
        List<User> users = usersLocation.getCurrentLocations(loggedInUser.getLocation());

        users.forEach(user -> {
            if (!user.getUsername().equals(loggedInUsername)) {
                user.getPrintWriter().println("warning;possibly-infected;at;" + last_location);
            }
        });

        //disable services for client
        loggedInUser.setSick(true);

        return "ok;quarantine";
    }

    public String getLocationHistory() {
        Map<Location, Set<User>> locationsHistory = usersLocation.getLocationsHistory();
        StringBuilder result = new StringBuilder();
        locationsHistory.forEach((l, us) -> {
            result.append("------ Location: ").append(l).append(" ------!");
            us.forEach(user ->
                    result.append("Username: ").
                            append(user.getUsername()).
                            append("!Infected: ").
                            append(user.getSick()).
                            append("!----!"));
        });
        return result.toString();
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void createAdmin(User user) {
        users.createUser(user);
    }

    // PRIVATE

    private void updateUserLocation(User user, Location location) {
        Location prev_location = usersLocation.updateUserLocation(user, location);

        //verify if it was the only user at that location and notify waiting users
        if (prev_location != null) {
            notifyWaitingUsers(prev_location);
        }
    }

    private void notifyWaitingUsers(Location location) {
        List<User> waiting = waitingUsers.getWaitingUsers(location);
        waiting.forEach(user -> user.getPrintWriter().println("notification;location;" + location.toString() + ";empty"));
    }

    private String userLogin(
            String username, String password,
            String[] parts, PrintWriter printWriter
    ) {

        double latitude = Double.parseDouble(parts[3]);
        double longitude = Double.parseDouble(parts[4]);
        Location location = new Location(latitude, longitude);

        if (users.containsKey(username)) {
            User user = users.get(username);
            user.setPrintWriter(printWriter);

            if (user.getPassword().equals(password)) {
                updateUserLocation(user, location);
                return "ok;" + username;
            }
        }

        return "err;invalid_credentials";
    }

    private String adminLogin(String username, String password, PrintWriter printWriter) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            user.setPrintWriter(printWriter);

            if (user.getPassword().equals(password)) {
                return "ok;" + username;
            }
        }

        return "err;invalid_credentials";
    }
}
