package mmop.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Netcode extends Thread {

    private BufferedReader input;
    private Controller controller;

    Netcode(Controller controller, BufferedReader input) {
        this.controller = controller;
        this.input = input;
    }

    @Override
    public void run() {
        try {
            while(true) {
                String message = input.readLine();
                if(message.equals("SERVER INFO: isDrawing=true")) {
                    controller.startDrawing();
                }
                else if(message.equals("SERVER INFO: clearCanvas=true")) {
                    controller.prepareCanvas();
                }
                else if(message.startsWith("SERVER INFO: DRAW_PRESS:")) {
                    String[] coordinates = message.substring(24).split("\\;");
                    controller.drawPress(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
                }
                else if(message.startsWith("SERVER INFO: DRAW_DRAG:")) {
                    String[] coordinates = message.substring(23).split("\\;");
                    controller.drawDrag(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
                }
                else if(message.startsWith("SERVER INFO: DRAW_RELEASE:")) {
                    String[] coordinates = message.substring(26).split("\\;");
                    controller.drawRelease(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
                }
                else {
                    controller.printToTextArea(message);
                }
            }
        }
        catch(IOException error) {
            controller.printToTextArea("Client error: " + error.getMessage());
        }
    }
}
