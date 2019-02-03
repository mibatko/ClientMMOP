package mmop.client;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private static final int PORT = 60321;
    private static final String SERVER_ADDRESS = "localhost"; //"18.197.197.247";

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField messageTextField;

    @FXML
    private Canvas drawAreaCanvas;
    private GraphicsContext drawGraphicsContext;
    private boolean isDrawing = false;

    @FXML
    public void initialize() {
        try {
            chatTextArea.appendText("Connecting to " + SERVER_ADDRESS + " on port " + PORT + "...\n");
            socket = new Socket(SERVER_ADDRESS, PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            Netcode netcode = new Netcode(this, input);
            netcode.start();

            prepareCanvas();
        }
        catch(IOException exception) {
            printToTextArea("Client error: " + exception.getMessage());
        }
    }

    private void prepareCanvas() {
        drawGraphicsContext = drawAreaCanvas.getGraphicsContext2D();
        drawGraphicsContext.setFill(Color.WHITE);
        drawGraphicsContext.fillRect(0, 0, drawAreaCanvas.getWidth(), drawAreaCanvas.getHeight());
    }

    public void startDrawing() {
        isDrawing = true;
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
    public void onClearButtonClick() {
        chatTextArea.clear();
        prepareCanvas();
    }

    @FXML
    public void onCanvasMousePressed(MouseEvent event) {
        if(isDrawing) {
            drawGraphicsContext.beginPath();
            drawGraphicsContext.moveTo(event.getX(), event.getY());
            drawGraphicsContext.stroke();
        }
    }

    @FXML
    public void onCanvasMouseDragged(MouseEvent event) {
        if(isDrawing) {
            drawGraphicsContext.lineTo(event.getX(), event.getY());
            drawGraphicsContext.stroke();
            drawGraphicsContext.closePath();
            drawGraphicsContext.beginPath();
            drawGraphicsContext.moveTo(event.getX(), event.getY());
        }
    }

    @FXML
    public void onCanvasMouseReleased(MouseEvent event) {
        if(isDrawing) {
            drawGraphicsContext.lineTo(event.getX(), event.getY());
            drawGraphicsContext.stroke();
            drawGraphicsContext.closePath();
        }
    }

    private void sendMessageToServer() {
        output.println(messageTextField.getText());
    }

    void printToTextArea(String message) {
        chatTextArea.appendText(message + "\n");
    }
}
