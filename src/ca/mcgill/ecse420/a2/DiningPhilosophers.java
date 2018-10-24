package ca.mcgill.ecse420.a1;


public class DiningPhilosophers {
	
	public static void main(String[] args) {

		int numberOfPhilosophers = 5;
                Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
                Object[] chopsticks = new Object[numberOfPhilosophers];

		//initialize an object for each chopstick on the table
		for (int i = 0; i < chopsticks.length; i++) {
			chopsticks[i] = new Object();
		}


		//For each philosopher assign that philosopher a left and right chopstick
		for (int i = 0; i < philosophers.length; i++) {

			Object leftChopstick = chopsticks[i];
			Object rightChopstick  = chopsticks[(i + 1) % chopsticks.length];

			/*clause to remove a circular wait condition, the last philosopher always tries to pick up the right chopstick
			while all the others pick up the left chopstick first*/
			if (i == philosophers.length - 1) {
				philosophers[i] = new Philosopher(rightChopstick, leftChopstick);
			} else {
				philosophers[i] = new Philosopher(leftChopstick, rightChopstick);
			}

			Thread t = new Thread(philosophers[i], "" + i );
			t.start();
		}
	}



}
