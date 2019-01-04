package mmop.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {
    //TODO: Gracefully disconnecting when closed.
    //TODO: Handle 'exit' message.

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private static final int PORT = 6045;
    private static final String SERVER_ADDRESS = "localhost";

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField messageTextField;

    @FXML
    public void initialize() {
        try {
            chatTextArea.appendText("Connecting to " + SERVER_ADDRESS + " on port " + PORT + "...\n");
            socket = new Socket(SERVER_ADDRESS, PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            Netcode netcode = new Netcode(this, input);
            netcode.start();
        }
        catch(IOException exception) {
            printToTextArea("Client error: " + exception.getMessage());
        }
    }

    @FXML
    void focusOnMessageTextField() {
        messageTextField.requestFocus();
    }

    @FXML
    public void onSendMessageButtonClick() {
        if(!messageTextField.getText().isBlank())
            sendMessageToServer();

        messageTextField.clear();
    }

    @FXML
    public void onClearChatButtonClick() {
        chatTextArea.clear();
    }

    private void sendMessageToServer() {
        output.println(messageTextField.getText());
    }

    void printToTextArea(String message) {
        chatTextArea.appendText(message + "\n");
    }
}
