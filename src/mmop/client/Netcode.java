package mmop.client;

import java.io.BufferedReader;
import java.io.IOException;

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
            //TODO: Check if there's a better way of handling infinite loop
            while(true) {
                String message = input.readLine();
                controller.printToTextArea(message);
            }
        }
        catch(IOException error) {
            controller.printToTextArea("Client error: " + error.getMessage());
        }
    }
}
