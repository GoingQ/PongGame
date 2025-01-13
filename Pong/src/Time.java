public class Time {
    //time measurement
    //Time the game engine started
    public static double timeStarted = System.nanoTime();

    public static double getTimeElapsed() {
        return (System.nanoTime() - timeStarted) * 1E-9; // System.nanoTime() returns in int value so 1E-3 converts to seconds
    }

}
