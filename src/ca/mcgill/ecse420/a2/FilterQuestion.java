package ca.mcgill.ecse420.a2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilterQuestion{

    private static int INCREMENT = 0;

    public static void main(String args[]) {
        // Get the thread ids, increment int, and filter
        int n = 5;
        Filter filter = new Filter(n);
        ExecutorService executor = Executors.newFixedThreadPool(n);
        for(int i = 0; i < n; i++){
            executor.execute(new Incrementer(i, filter));
        }

    }

    /*
    * This filter class will act as the filter lock
    * The point is to provide mutual exclusion to multiple threads
    * */
    private static class Filter {

        // Keep the two thread arbiter arrays
        private int[] level;
        private int[] victim;
        private int n;

        public Filter(int n){
            this.n = n;
            level = new int[n];
            victim = new int[n];
            for(int i = 0; i < n; i++){
                level[i] = 0;
            }
        }

        private boolean isAnotherLeverGreaterOrEqual(int id, int l){
            // For all k's not equal to i
            for(int k = 0; k < n; k++){
                if(k==id) break;
                // Return true is level[k] >= l
                if( (level[k] >= l) && (victim[l] == id)){ // this may be wrong too
                    return true;
                }
            }
            return false;
        }

        /*
         * To lock, starting at 1, announce to enter level L and announce yourself as victim to wait
         * @input int i
         * @output void
         * */
        public void lock(int id){
            int n = level.length;
            for(int l = 1; l < n; l++){
                level[id] = l;
                victim[id] = id;
                while( isAnotherLeverGreaterOrEqual(id, l) ){}
            }
        }

        /*
        * To unlock, set level at i to zero
        * @input int i
        * @output void
        * */
        public void unlock(int id) {
            level[id] = 0;
        }

    }

    /*
    * This incrementer will use the filter lock to increment a common variable
    * */
    public static class Incrementer implements Runnable{
        int id;
        int inc;
        Filter filter;

        public Incrementer(int id, Filter filter){
            this.id = id;
            this.filter = filter;
        }

        @Override
        public void run() {
            for(int i = 0; i < 100; i++){
                // Set the lock
                filter.lock(id);

                // Increment and print the number
                INCREMENT++;

                // Unlock
                filter.unlock(id);
            }
            System.out.println(INCREMENT);
        }
    }
}
