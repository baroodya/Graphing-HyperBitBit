package main.java;

public class HelperMethods {
    public static boolean withinFivePercent(double guess, double target) {
        double fivePercent = target * 0.05;
        return (guess >= target - fivePercent && guess <= target + fivePercent);
    }
}
