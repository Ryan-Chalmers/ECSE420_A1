package ca.mcgill.ecse420.a2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BakeryQuestion {
    private static int numThreads = 5;
    private static int INCREMENT = 0;

    public static void main(String args[]) {
        // Get the thread ids, increment int
        BakeryLock lock = new BakeryLock(numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for(int i = 0; i < numThreads; i++){
            executor.execute(new Incrementer(i, lock));
        }

    }


    public static class BakeryLock {
        private volatile boolean[] flag;
        private volatile int[] ticket;
        private int threads;

        /*initialize the bakery lock setting a flag and a ticket for each available thread. Set flag to false initially
         then ticket number to 0*/
        public BakeryLock(int threads){
            this.threads=threads;
            flag = new boolean[threads];
            ticket = new int[threads];
            for(int i = 0; i < threads; i++){
                flag[i] = false;
                ticket[i] = 0;
            }

        }
        /*To lock the bakery lock we first want to give the incoming thread a ticket number, we do this by finding the
        * max ticket number and incrementing it by one. Next we iterate through all the threads, if the current thread
        * has a larger ticket number than the current thread and the ticket number is not zero then we wait.*/
        public void lock(int id){
            flag[id] = true;
            ticket[id] = findMax()+1;
            flag[id] = false;

            for (int j = 0; j < threads; j++) {
                if (j == id)
                    continue;
                while (flag[j]) {}
                while (ticket[j] != 0 && (ticket[id] > ticket[j] || (ticket[id] == ticket[j] && id > j))) { }

            }

        }

        private void unlock(int id) {
            ticket[id] = 0;
        }

        //Helper method to find the highest ticket number amongst competing threads
        private int findMax(){
            int max = ticket[0];
            for (int i = 0; i < threads; i++){
                if(ticket[i]>max){
                    max = ticket[i];
                }
            }
            return max;
        }
    }

    /*
     * This incrementer will use the bakery lock to increment a common variable
     * */
    public static class Incrementer implements Runnable{
        int id;
        BakeryLock lock;
        public Incrementer(int id, BakeryLock lock){
            this.id = id;
            this.lock = lock;
        }

        @Override
        public void run() {
            for(int i = 0; i < 100; i++){
                // Set the lock
                lock.lock(id);

                // Increment and print the number

                INCREMENT++;
                System.out.println(INCREMENT);

                // Unlock
                lock.unlock(id);
            }
        }
    }

}
