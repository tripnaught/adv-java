package Week3Time;

public class time {
	@SuppressWarnings("unused")
	public static void main(String[] args) {	
		long sum = 0;
		int iterations = 2000000000;
		
		for (int j = 0; j < 5; j++) {
			long start = System.nanoTime();
			int x = 0;
			for (int i = 0; i < iterations; i++) {
				x = i;
			}
			
			long end = System.nanoTime();
			long duration = (end - start) / 1000000;
			sum += duration;
			
			System.out.printf("%d iterations took %d ms!\n", iterations, duration);
		}

		System.out.println("average is..." + sum / 5);
	}

	
}
