package Week3Sorting;

import java.util.Arrays;

public class insertionSort {
	public static void main(String[] args) {
		int[] data = { 51, 10, 88, 74, 73, 25, 18, 99 };

		System.out.println(Arrays.toString(data));
		System.out.println();

		iSort(data);

		System.out.println(Arrays.toString(data));
	}

	public static void iSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(" ".repeat(4 * i));
			System.out.println("< v");
			System.out.println(Arrays.toString(arr));

			int item = arr[i]; // store the item we're looking at
			int j = i - 1;

			while (j >= 0) {
				if (arr[j] > item) { // if the number before item is too big, that's the wrong order so swap
					System.out.print(" ".repeat(4 * j));
					System.out.println("< v--'");
					arr[j + 1] = arr[j];
					// setAndPrint(arr, j + 1, arr[j]);
					j = j - 1;
				} else {
					break;
				}
			}

			setAndPrint(arr, j + 1, item);
			System.out.println();
		}
	}

	public static void setAndPrint(int[] arr, int index, int value) {
		arr[index] = value;
		System.out.println(Arrays.toString(arr));

	}
}
