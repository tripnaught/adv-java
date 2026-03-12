package week7;

public class hashtables {
	public static void main(String[] args) {
		BasicHashTable abcs = new BasicHashTable(10);
		abcs.put("a", "alpha");
		abcs.put("b", "bravo");
		abcs.put("c", "charlie");
		abcs.put("d", "delta");
		abcs.put("e", 2.718281828);
		abcs.put("f", null);

		System.out.println(abcs.lookup("e"));
		System.out.println(abcs.lookup("f"));
		System.out.println(abcs.lookup("b"));
	}
}

class BasicHashTable {
	private HashTableEntry[] table;
	private int size; // currently filled size (NOT total size i.e. capacity)
	private double loadFactor; // size / table.length

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

	private int hash2(String key) {
		// lets be lazy for our 2nd hash function >:)
		return Math.abs(key.hashCode()) % table.length;
	}

	// #region resize
	// resize when loadFactor > .7
	// create the new array
	// rehash every item for our old array
	public BasicHashTable resize(int newCapacity) {
		BasicHashTable newHT = new BasicHashTable(newCapacity);
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null && table[i] instanceof HashTableEntry) {
				newHT.put(table[i].key, table[i].value);
			}
		}
		return newHT;
	}

	// #region add
	public void put(String key, Object value) {
		// check for room to add
		if (size == table.length) {
			System.err.println("Error: Hash table full !!!1!1!!!!11!11");
			return;
		}
		if (value instanceof String && value.equals("DELETED")) {
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

	// # double probing
	public void putDoubleProbing(String key, Object value) {
		// check for room to add
		if (size == table.length) {
			System.err.println("Error: Hash table full !!!1!1!!!!11!11");
			return;
		}
		if (value instanceof String && value.equals("DELETED")) {
			System.err.println("Error: please don't enter the value 'DELETED'!!!");
		}

		// get the hash index based on key
		int index1 = hash(key);
		int index2 = hash2(key);
		int startIndex1 = index1;
		int startIndex2 = index2;
		int i = 0;

		// check if that index is occupied:
		// if not yet occupied, insert and we're done
		// else if already occupied, probe to next valid location and repeat
		while (table[index1] != null && !table[index1].equals("DELETED")) {
			// if we find key again, overwrite value at that position
			if (table[index1].key.equals(key)) {
				table[index1].value = value;
				return;
			}

			// THIS IS WHERE THE MAGIC HAPPENS
			index1 = (startIndex1 + i * index2) % table.length;
			i++;

			// once we get back to the original index, we found no free spots
			// (this will not happen bc we checked at beginning)
			if (index1 == startIndex1) {
				System.err.println("No empty slot");
				return;
			}

		}

		table[index1] = new HashTableEntry(key, value);
		size++;

	}

	// #region lookup aka get
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

			index = (index + 1) % table.length; // use % to loop around

		} while (table[index] != null && index != startIndex);

		// if null, return failed (return null)
		return null;

	}

	// #region get (prof's implementation of lookup)

	// lookup
	// get hash index based key
	// loop: while key not found and not null -› probe to next location //if key
	// found return value, if not, return null
	public Object get(String key) {
		int index = hash(key);
		int startIndex = index;
		while (table[index] != null) {
			// handle key being found
			if (!table[index].value.equals("DELETED") && table[index].key.equals(key)) {
				return table[index].value;
			}

			// iterate
			index = (index + 1) % table.length;

			// exit if you've looked through everything
			if (index == startIndex) {
				break;
			}
		}
		return null;
	}

	// #region remove
	// we don't actually remove the HTEntry object, we just change its value to "DELETED"
	// search for the item you want to remove. if you find it:
	// change value to DELETED, decrement size, and break the loop.
	public void remove(String key) {
		int index = hash(key);
		int startIndex = index;
		while (table[index] != null) {
			// handle key being found
			if (!table[index].value.equals("DELETED") && table[index].key.equals(key)) {
				table[index].value = "DELETED";
				size--;
				return;
			}

			// iterate
			index = (index + 1) % table.length;

			// exit if you've looked through everything
			if (index == startIndex) {
				break;
			}
		}
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