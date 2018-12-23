package mmop.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);

        Parent root = loader.load();

        primaryStage.setTitle("Massive Multiplayer Online Pictionary");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.getIcons().add(new Image("file:assets/brushIcon.png"));

        controller.focusOnMessageTextField();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
