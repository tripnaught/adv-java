package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class csvDataLab {
	public static final String CSV_FILEPATH = "homework/lab3/data.csv";
	public static ArrayList<Sale> allSales = new ArrayList<>();

	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("\n============================================================");
		System.out.println("=W=E=L=C=O=M=E====T=O====R=E=C=O=R=D=S====P=R=O=C=E=S=S=O=R=");
		while (true) {

			System.out.println("============================================================");
			System.out.println("Please select an option. Type anything else to quit.");
			System.out.println("L: Load sale data");
			System.out.println("R: Retrieve latest sale");
			System.out.println("T: Total all revenue");
			System.out.println("D: Check for duplicate sale IDs");
			System.out.println("Any number: Search for sale by ID");
			System.out.print("> \u001b[36m");
			String input = scan.nextLine().toUpperCase();
			System.out.println("\u001b[39m");
			System.out.println("============================================================");

			long start, end;
			switch (input) {
				// load data
				case "L":
					System.out.println("Loading data...");
					start = System.nanoTime();
					fillAllSales();
					end = System.nanoTime();

					System.out.println("Sale data loaded successfully!");
					System.out.printf("Number of sales: %d\n", allSales.size());
					printDuration(start, end);
					break;

				// get latest sale
				case "R":
					if (allSales == null || allSales.size() == 0) {
						System.out.println("Error: No data loaded!");
						break;
					}
					System.out.println("Finding latest sale...");
					start = System.nanoTime();
					Sale latestSale = getLatestSale();
					end = System.nanoTime();

					System.out.println("Latest sale found:");
					latestSale.printData();
					printDuration(start, end);
					break;

				// get total revenue
				case "T":
					if (allSales == null || allSales.size() == 0) {
						System.out.println("Error: No data loaded!");
						break;
					}
					System.out.println("Calculating total revenue...");
					start = System.nanoTime();
					double revenue = getTotalRevenue();
					end = System.nanoTime();

					System.out.printf("Total revenue: %.2f\n", revenue);
					printDuration(start, end);
					break;

				// check for duplicate sale ids
				case "D":
					if (allSales == null || allSales.size() == 0) {
						System.out.println("Error: No data loaded!");
						break;
					}
					System.out.println("Looking for duplicate IDs...");
					start = System.nanoTime();
					ArrayList<Integer> duplicateIds = findDuplicateIds();
					end = System.nanoTime();

					System.err.println("Duplicate IDs found:");
					System.err.println(duplicateIds);
					break;

				default:
					try {
						int id = Integer.parseInt(input);
						// search for sale with id
						System.out.println(id);
					} catch (NumberFormatException e) {
						// input failed every check so user wants to quit. exit entirely
						System.out.println("Bye!");
						System.out.println("============================================================\n");
						return;
					}
			}
		}
	}

	public static void fillAllSales() {
		// clear `allSales` first, since we don't want
		// multiple copies of the data in this one ArrayList
		allSales.clear();

		Path path = Path.of(CSV_FILEPATH);

		try (BufferedReader br = Files.newBufferedReader(path)) {
			br.readLine(); // go past the header row

			String line;
			while ((line = br.readLine()) != null) {
				allSales.add(new Sale(line));
			}

		} catch (IOException e) {
			System.out.println("Error: no file found matching `CSV_FILEPATH` :(");
		}

	}

	public static Sale getLatestSale() {
		Sale latestSale = allSales.get(0);
		for (Sale sale : allSales) {
			if (sale.year > latestSale.year) {
				latestSale = sale;
			} else if (sale.year == latestSale.year && sale.month > latestSale.month) {
				latestSale = sale;
			} else if (sale.year == latestSale.year && sale.month == latestSale.month && sale.day > latestSale.day) {
				latestSale = sale;
			}
		}
		return latestSale;
	}

	public static double getTotalRevenue() {
		double total = 0.00;
		for (Sale sale : allSales) {
			total += sale.amount;
		}
		return total;
	}

	public static ArrayList<Integer> findDuplicateIds() {
		HashMap<Integer, Integer> idCounts = new HashMap<>();
		for (Sale sale : allSales) {
			idCounts.put(sale.id, idCounts.getOrDefault(sale.id, 0) + 1);
		}
		// System.out.println(idCounts.keySet());
		ArrayList<Integer> duplicateIds = new ArrayList<>();
		for (int id : idCounts.keySet()) {
			if (idCounts.get(id) > 1) { // this ID appeared more than once...
				duplicateIds.add(id); // so add it to duplicatedIds to get returned
			}
		}
		return duplicateIds;
	}

	public static void printDuration(long start, long end) {
		long durationNanoseconds = end - start;
		double durationMilliseconds = ((double) durationNanoseconds / 1_000_000L);
		System.out.printf("Time taken: \u001b[33m%f\u001b[39m ms.\n", durationMilliseconds);
	}
}

class Sale {
	int id;
	int year;
	int month;
	int day;
	double amount;
	String product;

	public Sale(int id, int year, int month, int day, double amount, String product) {
		this.id = id;
		this.year = year;
		this.month = month;
		this.day = day;
		this.amount = amount;
		this.product = product;
	}

	public Sale(String line) {
		String[] cells = line.split(",");

		// sale id
		int id = Integer.parseInt(cells[0]);

		// sale date
		String[] date = cells[1].split("-");
		int year = Integer.parseInt(date[0]);
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);

		// sale amount
		double amount = Double.parseDouble(cells[2]);

		// sale product
		String product = cells[3];

		this.id = id;
		this.year = year;
		this.month = month;
		this.day = day;
		this.amount = amount;
		this.product = product;
	}

	public void printData() {
		System.out.println("    id: " + this.id);
		System.out.println("    date: " + this.year + "-" + this.month + "-" + this.day);
		System.out.println("    amount: " + this.amount);
		System.out.println("    product: " + this.product);
	}
}
