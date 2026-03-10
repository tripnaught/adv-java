package week7;

public class hashtables {
	public static void main(String[] args) {
		System.out.println("guess we never tested this yet huh");
	}
}

class BasicHashTable {
	private HashTableEntry[] table;
	private int size;

	public BasicHashTable(int capacity) {
		table = new HashTableEntry[capacity];
		size = 0;
	}

	private int hash(String key) {
		// return key.hashCode() % table.length; // do this if you're lazy lolll
		int hashValue = 0;
		for (int i = 0; i < key.length(); i++) {
			hashValue += key.charAt(i);
		}
		return Math.abs(hashValue) % table.length;
	}

	// #region add
	public void put(String key, Object value) {
		// check for room to add
		if (size == table.length) {
			System.err.println("Error: Hash table full !!!1!1!!!!11!11");
			return;
		}
		if (value.equals("DELETED")) {
			System.err.println("Error: please don't enter the value 'DELETED'!!!");
		}

		// get the hash index based on key
		int index = hash(key);
		int startIndex = index;

		// check if that index is occupied:
		// if not yet occupied, insert and we're done
		// else if already occupied, probe to next valid location and repeat
		while (table[index] != null && !table[index].equals("DELETED")) {
			// if we find key again, overwrite value at that position
			if (table[index].key.equals(key)) {
				table[index].value = value;
				return;
			}

			// if we get to the end,
			// wrap around (using %) to check for any open spots BEFORE index
			index = (index + 1) % table.length;

			// once we get back to the original index, we found no free spots
			// (this will not happen bc we checked at beginning)
			if (index == startIndex) {
				System.err.println("No empty slot");
				return;
			}

		}

		table[index] = new HashTableEntry(key, value);
		size++;

	}

	// #region lookup
	public Object lookup(String key) {
		// get hash index based on key
		int index = hash(key);
		int startIndex = index;

		// loop: while key not found and not null -> probe to next location
		do {
			if (table[index].key.equals(key)) {
				// if key found return value.
				return table[index].value;
			}

			index = (index + 1) % table.length;

		} while (table[index] != null && index != startIndex);

		// if null, return failed (return null)
		return null;

	}
}

class HashTableEntry {
	String key;
	Object value;

	public HashTableEntry(String key, Object value) {
		this.key = key;
		this.value = value;
	}
}