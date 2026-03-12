package midterm;

import java.util.Scanner;

public class calcuator {
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		ExpressionTree exp = ExpressionTree.parse("123+456");
		System.out.print(((Number) exp.root).value);
	}
}

class ExpressionTree {
	public final char[] VALID_SYMBOLS = {'+', '-', '*', '/', '^'};
	public Node root;

	private ExpressionTree(Node root) {
		this.root = root;
	}

	public static ExpressionTree parse(String expression) {
		Node root = null;
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			System.out.println(c);
			if (Character.isDigit(c)) {
				// the next node is going to be a number
				double newValue = 0; 
				while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
					newValue *= 10;
					newValue += Character.getNumericValue(expression.charAt(i));
					i++; // this advances the while-loop, and also
					// prevents the for-loop from looking at digits twice.
				}
				i--;
				root = new Number(newValue);
			} else if (c == '+') { // symbols = Arrays.asList(strings).contains("Two")
				Operation newOperation = new Operation(c);
				newOperation.left = root;
				root = newOperation;
			}
		}
		return new ExpressionTree(root);
	}
}

class Node {
	public Node left;
	public Node right;
}

class Number extends Node {
	public double value;

	public Number(double value) {
		this.value = value;
	}
}

class Operation extends Node {
	char symbol;

	public Operation(char symbol) {
		this.symbol = symbol;
	}

	public double eval(Number a, Number b) {
		switch (symbol) {
			case '+':
				return add(a, b);
			case '-':
				return subtract(a, b);
			case '*':
				return multiply(a, b);
			case '/':
				return divide(a, b);
			case '^':
				return exponent(a, b);
			default:
				System.out.printf("error: can't calculate %f %c %f\n", a.value, symbol, b.value);
				return Double.NaN;
		}
	}

	public double add(Number a, Number b) {
		return a.value + b.value;
	}

	public double subtract(Number a, Number b) {
		return a.value - b.value;
	}

	public double multiply(Number a, Number b) {
		return a.value * b.value;
	}

	public double divide(Number a, Number b) {
		if (b.value == 0) {
			System.err.println("Error: ÷0");
		}
		return a.value / b.value;
	}

	public double exponent(Number a, Number b) {
		return Math.pow(a.value, b.value);
	}
}
