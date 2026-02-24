
public class quiz1 {
    public static int count = 0;

    public static void main(String[] args) {
        String s = "strawberry";
        char c = 'r';

        countOccurrences(s, c);
        System.out.printf("There are %d %c's in \"%s\".\n", count, c, s);
    }

    // #region q1
    public static void reverseString(String s) {
        int len = s.length();
        if (len == 0) {
            return;
        }
        System.out.print(s.charAt(len - 1));
        String shorterS = s.substring(0, len - 1);
        reverseString(shorterS);
    }

    // Base case: len = 0. Return before calling reverseString again
    // Recursive case: len > 0. Print out the last char of the string.
    // Then, call reverseString on a string one char shorter than before.
    // The order of the stack means that the very last char will print first.

    // #endregion




    // #region q4
    public static void countOccurrences(String s, char c) {
        if (s.length() == 0) {
            return;
        }

        if (s.length() == 1) {
            if (s.charAt(0) == c) {
                count++;
            }
            return;
        }

        // split step
        int mid = s.length() / 2;
        String left = s.substring(0, mid);
        String right = s.substring(mid, s.length());

        countOccurrences(left, c);
        countOccurrences(right, c);

    }

    // Base case: s is length 0. Return, since it definitely doesn't match any c's.
    // Base case: s is length 1. Return, but if that one character matches c, increase count by 1.
    // Recursive case: s is length 2 or more. Split s in half, then run countOccurences on each half.
    // This is similar to binary search.

}
