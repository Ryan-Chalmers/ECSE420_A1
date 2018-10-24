package ca.mcgill.ecse420.a1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
    //Create two locks to protect a critical section
    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();

    public static void main(String args[]) {
        Thread t1 = new Thread(new Deadlock().new FirstTask());
        Thread t2 = new Thread(new Deadlock().new SecondTask());

        t1.start();
        t2.start();

    }

    /*First task will lock lock1 first then wait to lock lock2. This will give SecondTask enough time to acquire lock2.
    * Once FirstTask is done sleeping it will attempt to acquire lock2 however SecondTask will be waiting to acquire
    * lock1. DEADLOCK*/
    private class FirstTask implements Runnable {

        @Override
        public void run(){
            lock1.lock();
            try{
                Thread.sleep(3000);
                lock2.lock();
                System.out.println("We are in the critical section");
                lock2.unlock();

            } catch(Exception e){
                e.printStackTrace();
            }
            lock1.unlock();
        }
    }
    private class SecondTask implements Runnable {

        @Override
        public void run(){
            lock2.lock();
            try{
                Thread.sleep(3000);
                lock1.lock();
                System.out.println("We are in the critical section");
                lock1.unlock();

            } catch(Exception e){
                e.printStackTrace();
            }
            lock2.unlock();
        }
    }
}
