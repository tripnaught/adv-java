package midterm;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;

public class acnhMidterm {
	public static final String CSV_FILENAME = "homework/midterm/acnh.csv";
	public static final int CSV_ROWS = 6219;

	public static Object[][] acnhData = new Object[CSV_ROWS][4];

	public static void main(String[] args) {
		System.out.println("\u001b[36m");

		loadData();

		Storage myStorage = new Storage(80);
		myStorage.add(0);
		myStorage.add(5234);
		myStorage.add(729);
		myStorage.add(2785);
		myStorage.add(1890);


		myStorage.printGrid();

		System.out.println("\u001b[0m");
	}

	public static void loadData() {
		Path path = Path.of(CSV_FILENAME);

		try (BufferedReader br = Files.newBufferedReader(path)) {
			br.readLine(); // skip header row

			for (int i = 0; i < CSV_ROWS; i++) {
				String line = br.readLine();
				String[] column = line.split(",");
				acnhData[i] = column;
			}

		} catch (IOException e) {
			System.out.println("Error: no file found matching path :(");
		}
	}

	public static int idToItemName(String name) {
		String strippedName = name.toUpperCase().replaceAll("[^A-Z0-9]", "");
		for (int i = 0; i < CSV_ROWS; i++) {
			String strippedItem = ((String) acnhData[i][1]).toUpperCase().replaceAll("[^A-Z0-9]", "");
			if (strippedItem.equals(strippedName)) {
				return i;
			}
		}
		System.err.println("There's no item called \"" + name + "\".");
		return -1;
	}

}

class Storage {
	public Item[] items;
	public int maxSize;
	public int currentSize; // stacks (e.g. oranges x 10) are counted as 1 here.

	public Storage(int size) {
		items = new Item[size];
		maxSize = size;
		currentSize = 0;
	}

	public void add(String name) {
		int id = acnhMidterm.idToItemName(name);
		if (id != -1) {
			add(id);
		} else {
			System.out.println("There's no item with the name ");
		}
	}

	public void add(int id) {
		if (currentSize == maxSize) {
			System.out.println("Your storage is full!");
			System.out.println("Move items to your pockets to make more space!");
			return;
		}
		items[currentSize] = new Item(id);
		currentSize++;
	}

	public void print() {
		for (Item item : items) {
			if (item != null) {
				System.out.println(item.name);
			}
		}
	}

	public void printGrid() {
		// ┌────────────┬────────────┬────────────┬────────────┬────────────┬────────────┐
		// │000000000000│111111111111│222222222222│333333333333│444444444444│555555555555│
		// │000000000000│111111111111│222222222222│333333333333│444444444444│555555555555│
		// │000000000000│111111111111│222222222222│333333333333│444444444444│555555555555│
		// │000000000000│111111111111│222222222222│333333333333│444444444444│555555555555│
		// └────────────┴────────────┴────────────┴────────────┴────────────┴────────────┘

		// System.out.println(Arrays.toString(cellText));

		final String FILLER_CHAR = " ";
		final int DISPLAY_WIDTH = 5;

		final int CELL_LINES = 5;
		final int CELL_COLUMNS = 15;

		String fillerCellString = FILLER_CHAR.repeat(CELL_COLUMNS / 2 - (CELL_COLUMNS - 1) % 2)
				+ "•"
				+ FILLER_CHAR.repeat(CELL_COLUMNS / 2);

		System.out.print(
				"┌" + ("─".repeat(CELL_COLUMNS) + "┬").repeat(DISPLAY_WIDTH - 1) + "─".repeat(CELL_COLUMNS) + "┐\n");

		int totalNumberOfCells = maxSize + (maxSize % DISPLAY_WIDTH);
		if (maxSize % DISPLAY_WIDTH != 0) {
			// for whatever reason, if the number of columns doesn't cleanly divide
			// the max storage size, i have to add one to totalNumberOfCells.
			// this is the only way i could get this to work ;-;
			totalNumberOfCells++;
		}

		for (int i = 0; i < totalNumberOfCells; i++) {
			String name = "";
			if (i >= maxSize) {
				name += "█".repeat(CELL_COLUMNS); // extra slots to complete the grid are blacked out
			} else if (items[i] == null) {
				name = fillerCellString; // slots which can be filled
			} else {
				name = items[i].name;
				if (name.length() < CELL_COLUMNS) {
					name += FILLER_CHAR.repeat(CELL_COLUMNS - name.length()); // name too short; add spaces on the end
				} else if (name.length() > CELL_COLUMNS) {
					name = name.substring(0, CELL_COLUMNS - 1) + "…"; // name too long; restrict length and add …
				}
			}

			System.out.print("│" + name);

			// closing right border and new line
			if (i % DISPLAY_WIDTH == DISPLAY_WIDTH - 1) {
				System.out.print("│\n");
			}

			// only add the horizontal line if this is NOT the very last one !!!
			if (i % DISPLAY_WIDTH == DISPLAY_WIDTH - 1 && i != totalNumberOfCells - 1) {
				System.out.print("├" + ("─".repeat(CELL_COLUMNS) + "┼").repeat(DISPLAY_WIDTH - 1)
						+ "─".repeat(CELL_COLUMNS) + "┤\n");
			}
		}

		// closing horizontal line
		System.out.print(
				"└" + ("─".repeat(CELL_COLUMNS) + "┴").repeat(DISPLAY_WIDTH - 1) + "─".repeat(CELL_COLUMNS) + "┘\n");

		String currentSizeNotif = Integer.toString(currentSize) + "/" + Integer.toString(maxSize);
		currentSizeNotif += FILLER_CHAR.repeat(CELL_COLUMNS - currentSizeNotif.length() - 1);


		if (currentSize == maxSize) {
			System.out.print("\u001b[31m");
		} else {
			System.out.print("\u001b[33m");
		}


		System.out.print(
			"│ " + currentSizeNotif + "│\n" +
			"└" + "─".repeat(CELL_COLUMNS) + "┘\n" +
			"\u001b[30m"
		);
	}
}

class Item {
	public int id;
	public String name;
	public int categoryId;
	public int quantity;
	public int maxQuantity;
	public long timeStoredNs;

	public Item(int id) {
		if (id < 0 || id > acnhMidterm.CSV_ROWS - 1) {
			System.out.printf("error: no row with an id of %d\n", id);
			return;
		}
		if (acnhMidterm.acnhData == null) {
			System.err.println("error: acnhData was not properly loaded!");
			return;
		}

		Object[] row = acnhMidterm.acnhData[id];
		this.id = id;
		this.name = (String) row[1];
		this.categoryId = Integer.parseInt((String) row[2]);
		this.quantity = 1;
		this.maxQuantity = Integer.parseInt((String) row[3]);
		this.timeStoredNs = System.nanoTime();
	}

	public void printItem() {
		if (quantity == 1) {
			System.out.println(name);
		} else if (quantity > 1) {
			System.out.println(name + " × " + quantity);
		}
	}
}