package lab3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class generateData {
	// vs code won't let me use paths relative to java files for some reason,
	// they have to be relative to the root of the workspace.

	// when testing this on a different machine, you may need to change this path.
	public static final String CSV_FILEPATH = "homework/lab3/data.csv";

	public static Path path = Path.of(CSV_FILEPATH);

	public static void main(String[] args) {
		System.out.println("program started...");
		clearCSV();
		fillCSV(100000);
	}

	public static void clearCSV() {
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write("");
			System.out.println("csv cleared!");
		} catch (IOException e) {
			System.err.format("error clearing data.csv");
		}
	}

	public static void fillCSV(int rows) {
		try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
			// header row
			bw.write("sale_id,sale_date,amount,product");
			bw.newLine();

			for (int i = 0; i < rows; i++) {
				// sale ID
				bw.write(Integer.toString(i));
				bw.write(",");
				
				// sale date
				bw.write(Integer.toString(randIntRange(1995, 2027)));
				bw.write("-");
				bw.write(Integer.toString(randIntRange(1, 13)));
				bw.write("-");
				bw.write(Integer.toString(randIntRange(1, 31)));
				bw.write(",");
				
				// amount
				bw.write(Integer.toString(randIntRange(0, 10000)));
				bw.write(".");
				bw.write(Integer.toString(randIntRange(0, 100)));
				bw.write(",");

				// product
				String[] products = {
					"gadget",
					"gizmo",
					"whoozit",
					"whatsit",
					"thingamabob",
					"doohickey",
					"whatchamacalit",
					"thingy",
					"book",
					"car",
					"toy",
					"phone",
					"pet",
					"subscription",
				};
				bw.write(products[randIntRange(0, products.length)]);
				
				// no extra new line with no data at the end, please
				if (i != rows - 1) {
					bw.newLine();
				}
			}

			System.out.println("csv filled!");
		} catch (IOException e) {
			System.err.format("error writing to data.csv");
		}
	}

	// this function comes from baeldung.com, though it's similar to
	// one used in last semester's pokemon lab to generate random ints.
	public static int randIntRange(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
