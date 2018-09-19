package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
	
	public static void main(String[] args) {

		int numberOfPhilosophers = 5;
                Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
                Object[] chopsticks = new Object[numberOfPhilosophers];
	}

	public static class Philosopher implements Runnable {


		@Override
		public void run() {
			while(true){
				//Initially start thinking
				think();

				pickup_left_fork();
				pickup_right_fork();
				eat();
				place_left_fork();
				place_right_fork();
			}

			
		}

		public void think(){
			try {
				Thread.sleep(500);
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		public void pickup_left_fork(){

		}

		public void pickup_right_fork(){

		}

		public void place_left_fork(){

		}

		public void place_right_fork(){

		}

		public void eat(){

		}


	}

}
