package ca.mcgill.ecse420.a1;

public class Counter {
    public double value;

    public Counter(double initial){
        this.value = initial;
    }

    public double getAndUpdate() {
        synchronized(this) {
            double temp = value;
            value = temp + 1;
            return temp;
        }
    }
}
