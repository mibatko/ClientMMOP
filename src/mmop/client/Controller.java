package mmop.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {

    private Socket socket;
    private BufferedReader echoes;
    private PrintWriter stringToEcho;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField messageTextField;

    @FXML
    public void initialize() {
        try {
            socket = new Socket("localhost", 6045);
            echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stringToEcho = new PrintWriter(socket.getOutputStream(), true);
        }
        catch(IOException exception) {
            System.out.println("Client error: " + exception.getMessage());
        }
    }

    private String printDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void talkWithServer() {
        try {
            stringToEcho.println(messageTextField.getText());
            chatTextArea.appendText(echoes.readLine() + "\n");
        }
        catch (IOException exception) {
            System.out.println("Client error: " + exception.getMessage());
        }
    }

    @FXML
    void focusOnMessageTextField() {
        messageTextField.requestFocus();
    }

    @FXML
    public void onSendMessageButtonClick() {
        if(!messageTextField.getText().isBlank())
            //chatTextArea.appendText(printDate() + " Me: " + messageTextField.getText() + "\n");
            talkWithServer();

        messageTextField.clear();
    }

    @FXML
    public void onClearChatButtonClick() {
        chatTextArea.clear();
    }
}
