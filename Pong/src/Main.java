public class Main {
    public static int state = 0;
    public static Thread mainThread;
    public static MainMenu mainMenu;
    public static Window window;

    public static void main(String[] args) {
        mainMenu = new MainMenu();

        mainThread = new Thread(mainMenu);
        mainThread.start();
    }

    public static void changeState(int newState) {
        if (newState == 1 && state == 0) {
            mainMenu.stop();
            window = new Window();
            mainThread = new Thread(window);
            mainThread.start();
        } else if (newState == 0 && state == 1) {
            window.stop();
            mainMenu = new MainMenu();
            mainThread = new Thread(mainMenu);
            mainThread.start();
        } else if (newState == 2) {
            if (window != null) {
                window.stop();
            }
            if (mainMenu != null) {
                mainMenu.stop();
            }
        }
        state = newState;
    }
}