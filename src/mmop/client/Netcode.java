package mmop.client;

import java.io.BufferedReader;
import java.io.IOException;

//Klasa obsługująca połączenie z serwerem
public class Netcode extends Thread {

    private BufferedReader input;
    private Controller controller;

    //Konstruktor
    Netcode(Controller controller, BufferedReader input) {
        this.controller = controller;
        this.input = input;
    }

    //Glowne dzialanie klasy obslugujacej połączenie
    @Override
    public void run() {
        //W nieskonczonej petli sluchaj nadawanych wiadomości przez serwer i drukuj je w polu tektowych chatu.
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
