package sd.client.shell;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import sd.client.Client;

@Component
public class Commands implements CommandMarker {
    @CliCommand(value = {"register"}, help = "Allows a new user to sign in")
    public void register(@CliOption(key = "username") String username, @CliOption(key = "password") String password,
                         @CliOption(key = "latitude") String latitude, @CliOption(key = "longitude") String longitude) throws InterruptedException {

        String out = "register;" + username + ";" + password + ";" + latitude + ";" + longitude;
        Client.getSocketWriter().println(out);

        Client.reentrantLock.lock();
        Client.reentrantLock.unlock();
    }

    @CliCommand(value = {"login"}, help = "Allows a user to login")
    public void login(@CliOption(key = "username") String username, @CliOption(key = "password") String password,
                      @CliOption(key = "latitude") String latitude, @CliOption(key = "longitude") String longitude) {

        String out = "login;" + username + ";" + password + ";" + latitude + ";" + longitude;
        Client.getSocketWriter().println(out);

        Client.reentrantLock.lock();
        Client.reentrantLock.unlock();
    }

    @CliCommand(value = {"location"}, help = "Allows a user to send his current location")
    public void sendLocation(@CliOption(key = "latitude") String latitude,
                             @CliOption(key = "longitude") String longitude) {

        String out = "location;" + latitude + ";" + longitude;
        Client.getSocketWriter().println(out);

        Client.reentrantLock.lock();
        Client.reentrantLock.unlock();
    }

    @CliCommand(value = {"number-people"}, help = "Displays the number of people currently at the location requested")
    public void numberOfPeople(@CliOption(key = "latitude") String latitude,
                               @CliOption(key = "longitude") String longitude) {

        String out = "number-people?;" + latitude + ";" + longitude;
        Client.getSocketWriter().println(out);

        Client.reentrantLock.lock();
        Client.reentrantLock.unlock();
    }

    @CliCommand(value = {"empty"}, help = "Notifies the user as soon as the location requested is empty")
    public void emptyLocation(@CliOption(key = "latitude") String latitude,
                              @CliOption(key = "longitude") String longitude) {

        String out = "empty?;" + latitude + ";" + longitude;
        Client.getSocketWriter().println(out);

        Client.reentrantLock.lock();
        Client.reentrantLock.unlock();
    }

    @CliCommand(value = {"sick"}, help = "Warns the system that the user is currently infected")
    public void infected(@CliOption(key = "latitude") String latitude,
                         @CliOption(key = "longitude") String longitude) {

        String out = "sick";
        Client.getSocketWriter().println(out);

        Client.reentrantLock.lock();
        Client.reentrantLock.unlock();
    }

    @CliCommand(value = {"info"}, help = "Displays the information of the logged in user")
    public void userInfo() {

        String out = "info";
        Client.getSocketWriter().println(out);

        Client.reentrantLock.lock();
        Client.reentrantLock.unlock();
    }

    @CliCommand(value = {"vip-info"}, help = "Special access to location logs")
    public void vipInfo() {

        String out = "locationsHistory";
        Client.getSocketWriter().println(out);
        Client.reentrantLock.lock();
        Client.reentrantLock.unlock();
    }

    @CliCommand(value = {"logout"}, help = "Logs the user out of the account")
    public void logOut() {

        String out = "logout";
        Client.getSocketWriter().println(out);
        Client.reentrantLock.lock();
        Client.reentrantLock.unlock();
    }
}
