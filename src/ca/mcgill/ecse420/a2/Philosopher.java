package ca.mcgill.ecse420.a1;

public class Philosopher implements Runnable {


    //each philosopher has a designated left and right chopstick according to their position at the table
    private Object leftChopstick;
    private Object rightChopstick;

    public Philosopher(Object leftChopstick, Object rightChopstick){
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    @Override
    public void run(){
        while(true){
            try {
                //The philosopher is thinking
                Thread.sleep((int) (Math.random() * 100));
                //When done thinking retrieve a left chopstick, if the chopstick is being held wait for it to be released
                synchronized (leftChopstick) {
                    //same with the right chopstick
                    synchronized (rightChopstick) {
                        //Once both chopsticks have been acquired we are free to eat
                        Thread.sleep((int) (Math.random() * 100));
                        System.out.println("Philospher " + Thread.currentThread().getName() + " is eating");
                    }
                }
                //done eating, go back to thinking
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
