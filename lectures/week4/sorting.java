package week4;

import java.util.Arrays;

public class sorting {
	public static void main(String[] args) {
		int[] numbers = { 8, 2, 4, 1, 5 };

		System.out.println(Arrays.toString(numbers));
		// quickSort(numbers, 0, numbers.length - 1);
		mergeSort(numbers);
		System.out.println(Arrays.toString(numbers));

	}

	public static void quickSort(int[] arr, int low, int high) {
		// base case: arr is 1 or smaller
		// if low = high, array length is exactly 1
		// if low > high, array length is 0
		if (low >= high) {
			return;
		}

		int pivot = arr[high];
		int i = low - 1;

		for (int j = low; j < high; j++) {
			if (arr[j] <= pivot) {
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		int temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;

		int newPivotIndex = i + 1;
		quickSort(arr, low, newPivotIndex - 1);
		quickSort(arr, newPivotIndex + 1, high);
	}

	public static void mergeSort(int[] arr) {
		// Base case: 1- or 0-element arrays
		if (arr.length <= 1) {
			return;
		}

		// split step
		// get the middle index with integer division (round down)
		int midIndex = arr.length / 2;
		// create array left from 0 to midIndex
		// create array right from midIndex + 1 to length - 1

		// Recursive case
		// call merge sort on array left
		mergeSort(left);
		// call merge sort on array right
		mergeSort(right);

		// iterate through left and right using a leftCounter and a rightCounter
		int i = 0;
		int j = 0;
		// merge step
		while (i <= left.length && j <= right.length) {
			// weave them into arr
			// each time we add from left or right, increment its counter
			// if leftCounter = size of left && rightCounter = size of right: break;
		}
	}
}
