package mmop.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//Glowna klasa uruchamiająca aplikację
//Dziedziczy po klasie Application gdyz jest to aplikacja z GUI stworzonym w JavaFX
public class Main extends Application {

    //Struktura wygenerowana podczas tworzenia nowej aplikaacji JavaFX
    //Main wywoluje metodę launch() lecz kod naprawdę piszemy w metodzie start()
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Odczytaj informacje o elementach GUI z pliku FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        //Utworz w wczytaj kontroler elementow GUI
        Controller controller = new Controller();
        loader.setController(controller);

        //Baza na ktorej budujemy elementy GUI
        Parent root = loader.load();

        //Podstawowa scena GUI
        primaryStage.setTitle("Massive Multiplayer Online Pictionary");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.getIcons().add(new Image("file:assets/brushIcon.png"));

        //Dodatkowa metoda napisana ręcznie tylko po to by przy starcie aplikacji aktywne było pole tekstowe do wpisywania wiaodmości.
        //Kod metody w klasie Controller
        controller.focusOnMessageTextField();

        //Wyswietl GUI po zakończeniu przygotowań
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
