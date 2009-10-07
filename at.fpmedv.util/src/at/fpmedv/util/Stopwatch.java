package at.fpmedv.util;

public class Stopwatch {
    private long start;
    private long stop;
    
    public void start() {
        start = System.currentTimeMillis(); // start timing
    }
    
    public void stop() {
        stop = System.currentTimeMillis(); // stop timing
    }
    
    public long getElapsedTimeMillis() {
        return stop - start;
    }
    
    public String toString() {
        return "elapsedTimeMillis: " + Long.toString(getElapsedTimeMillis()); // print execution time
    }
}
