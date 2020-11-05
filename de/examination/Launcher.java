package de.examination;

public class Launcher {

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller();
        view.initialise(controller);
        controller.initalise(view);


    }
}
