package mmop.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Klasa obsługująca GUI
public class Controller {
    //TODO: Gracefully disconnecting when closed.
    //TODO: Handle 'exit' message.

    //Dodatkowe elementy wymagane do nawiązania połączenia - gniazdo sieciowe i strumienie I/O
    //Prawdopodobnie lepiej byłoby je zawrzeń w innej klasie
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private static final int PORT = 60321;
    private static final String SERVER_ADDRESS = "18.197.197.247";

    //Zmienne poprzedzone @FXML reprezentują poszczególne elementy GUI. Nazwa zmiennej musi pokrywać się z nazwą elementu w pliku FXML
    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField messageTextField;


    //Pierwsza metoda kontrolera to przygotowanie wszytskich czynności któe muszą poprzedzić wyświetlenie GUI.
    //W naszym wypadku nawiązujemy połączenie sieciowe z serwerem.
    @FXML
    public void initialize() {
        //Zawarte w try-catch gdyż używane klasy i metody generują wyjątki
        try {
            chatTextArea.appendText("Connecting to " + SERVER_ADDRESS + " on port " + PORT + "...\n");
            socket = new Socket(SERVER_ADDRESS, PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            //Po nawiązaniu połączenia utwórz instancję klasy Netcode, która wielowątkowo obsługuje czynności związane z połączeniem z serwerem
            //Klasa dziedziczy po klasie Thread dlatego po utworzeniu instancji uruchamiamy nowy wątek metodą start()
            Netcode netcode = new Netcode(this, input);
            netcode.start();
        }
        catch(IOException exception) {
            printToTextArea("Client error: " + exception.getMessage());
        }
    }

    //Metoda suatwiająca pole tekstowe do wpisywania wiadomości jako aktywny element GUI. Aby można było pisać od razu po uruchomieniu aplikacji.
    @FXML
    void focusOnMessageTextField() {
        messageTextField.requestFocus();
    }

    //Kod wywoływany po naciśnieniu przycisku sendMessage
    @FXML
    public void onSendMessageButtonClick() {
        if(!messageTextField.getText().isBlank())
            sendMessageToServer();

        messageTextField.clear();
    }

    //Kod wywolywany po nacisnieniu przycisku clearChat
    @FXML
    public void onClearChatButtonClick() {
        chatTextArea.clear();
    }

    //Wysylanie wiadomosci do serwera przez strumien wyjscia
    private void sendMessageToServer() {
        output.println(messageTextField.getText());
    }

    //Drukowanie tektu w polu chactu
    void printToTextArea(String message) {
        chatTextArea.appendText(message + "\n");
    }
}
