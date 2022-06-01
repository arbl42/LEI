package SGA;

import SGA.Business.IArmazemLN;
import SGA.dataaccess.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class SistemaGestaoArmazemApplication extends Application {
    private static IArmazemLN iArmazemLN;
    @Autowired
    private Environment env;
    private ConfigurableApplicationContext context;
    private Parent rootNode;

    public static IArmazemLN getIArmazemLN() {
        return iArmazemLN;
    }

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SistemaGestaoArmazemApplication.class);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/welcome.fxml"));
        loader.setControllerFactory(context::getBean);
        rootNode = loader.load();
    }

    @PostConstruct
    public void initProperties() {
        DBConnection.startConnection(
                env.getProperty("spring.datasource.driverClassName"),
                env.getProperty("spring.datasource.url"),
                env.getProperty("spring.datasource.username"),
                env.getProperty("spring.datasource.password")
        );

        iArmazemLN = new IArmazemLN();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(rootNode, 600, 400));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @Override
    public void stop() {
        context.close();
    }
}
