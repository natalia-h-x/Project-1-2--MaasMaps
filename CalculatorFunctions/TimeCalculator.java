package CalculatorFunctions;


abstract class TimeCalculator {
    double avgSpeed;
    double estimatedTimeOfArival;

    public void calculateTime(int distance) {
        estimatedTimeOfArival =  distance / avgSpeed;
    }

    public double getAverageSpeed(){
        return avgSpeed;
    }

    public double getETA() {
        return estimatedTimeOfArival;
    }
}
