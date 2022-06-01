package sd.client.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Banner extends DefaultBannerProvider {
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public String getBanner() {
        return "";
    }

    @Override
    public String getWelcomeMessage() {
        clearScreen();
        return "Welcome to 'Alarme COVID'. Type 'help' for command menu.";
    }
}
