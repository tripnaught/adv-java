public class verticalNumbers {
    public static void main(String[] args) {
        writeVertical(-1234);
    }

    public static void writeVertical(int number) {
        if (number < 0) {
			System.out.println("-");
			number *= -1;
		}
		if (number >= 10) {
            writeVertical(number / 10);
        }
        System.out.println(number % 10);
    }
}