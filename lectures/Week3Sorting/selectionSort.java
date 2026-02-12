package Week3Sorting;

import java.util.Arrays;

public class selectionSort {
	public static void main(String[] args) {
		int[] data = { 51, 2, 88, 74, 73, 5, 18 };

		System.out.println(Arrays.toString(data));
		System.out.println();

		sSort(data);

		System.out.println(Arrays.toString(data));
	}
	
	public static void sSort(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			int minIndex = i;
			
			// look thru the unsorted section for the smallest index
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}

			// swap minIndex with whatever the first item in the unsorted array is (index i)
			int temp = arr[i];
			arr[i] = arr[minIndex];
			arr[minIndex] = temp;
		}
	}
}
