package controllers;

import models.AlarmeCovid;
import models.User;

import java.io.PrintWriter;

public class Router {
    private final AlarmeCovid alarmeCovid;
    private User loggedInUser;
    private boolean adminLoggedIn;

    public Router(AlarmeCovid alarmeCovid) {
        this.alarmeCovid = alarmeCovid;
    }

    public String processInput(String inputLine, PrintWriter printWriter) {
        String[] parts = inputLine.split(";");
        String outputLine;

        if (loggedInUser == null) {
            outputLine = handleNotLoggedIn(parts, printWriter);
        } else {
            try {
                if (adminLoggedIn) {
                    outputLine = handleAdmin(parts);
                } else {
                    outputLine = handleLoggedIn(parts);
                }
            } catch (NumberFormatException e) {
                return "err;invalid-input";
            }
        }

        return outputLine;
    }

    private String handleAdmin(String[] parts) {
        String outputLine;

        switch (parts[0]) {
            case "logout":
                outputLine = "bye";
                break;
            case "locationsHistory":
                outputLine = alarmeCovid.getLocationHistory();
                break;
            default:
                outputLine = "err;invalid_option";
                break;
        }

        return outputLine;
    }

    private String handleLoggedIn(String[] parts) {
        String outputLine;

        if (parts[0].equals("logout")) {
            return "bye";
        }

        if (loggedInUser.getSick()) {
            return "err;quarantined";
        }

        switch (parts[0]) {
            case "location":
                outputLine = alarmeCovid.setClientLocation(loggedInUser, parts);
                break;
            case "number-people?":
                outputLine = alarmeCovid.getNumberPeople(parts);
                break;
            case "empty?":
                outputLine = alarmeCovid.locationIsEmpty(loggedInUser, parts);
                break;
            case "sick":
                outputLine = alarmeCovid.sickClient(loggedInUser);
                break;
            case "info":
                outputLine = alarmeCovid.getUserInfo(loggedInUser.getUsername());
                break;
            default:
                outputLine = "err;invalid_option";
                break;
        }

        return outputLine;
    }

    private String handleNotLoggedIn(String[] parts, PrintWriter printWriter) {
        String outputLine;

        switch (parts[0]) {
            case "register":
                outputLine = alarmeCovid.register(parts, printWriter);
                logIn(outputLine);
                break;
            case "login":
                outputLine = alarmeCovid.login(parts, printWriter);
                logIn(outputLine);
                break;
            default:
                outputLine = "err;user_not_logged_in";
        }

        return outputLine;
    }

    private void logIn(String outputLine) {
        String[] parts = outputLine.split(";");

        if (parts[0].equals("ok")) {
            loggedInUser = alarmeCovid.getUser(parts[1]);
            adminLoggedIn = loggedInUser.getUsername().equals("admin");
        }
    }
}
