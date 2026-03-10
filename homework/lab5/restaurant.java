package lab5;

import java.util.ArrayList;
import java.util.PriorityQueue; // <- TODO replace this !! has to be own implementation 
import java.util.Random;
import java.util.Scanner;

public class restaurant {
	public static PriorityQueue<Order> pq = new PriorityQueue<Order>(); // <- the queue all the orders go in
	public static int currentOrderNumber = 0;

	public static Random rand = new Random();
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		// ===============================================================================================================
		// #region user order loop
		System.out.println("\nHi, welcome to McDonald's.\n");
		while (true) {
			System.out.println("===================================================");
			System.out.println("\nWhat would you like to do?");
			System.out.println("D: Dine-in order");
			System.out.println("T: Take-out order");
			// System.out.println("M: See the menu");
			System.out.println("V: View order queue");
			System.out.println("X: Close this and process orders");
			System.out.print(">  ");

			String input = scan.nextLine().toUpperCase();

			// DINE IN
			if (input.charAt(0) == 'D') {
				DineInOrder dineIn = new DineInOrder();
				pq.add(dineIn);
			}

			// TAKE OUT
			else if (input.charAt(0) == 'T') {
				TakeOutOrder takeOut = new TakeOutOrder();
				pq.add(takeOut);
			}

			// MENU
			/* else if (input.charAt(0) == 'M') {
				System.out.println("\nBig Mac");
				System.out.println("Quarter Pounder");
				System.out.println("Quarter Pounder with Cheese\n");

				System.out.println("McCrispy");
				System.out.println("Filet-O-Fish");
				System.out.println("Egg McMuffin\n");

				System.out.println("Fries");
				System.out.println("10 pc. Chicken McNuggets\n");

				System.out.println("Coca-Cola");
				System.out.println("Diet Coke");
				System.out.println("Sprite");
				System.out.println("Dr. Pepper\n");

				System.out.println("Lemonade");
				System.out.println("Sweet Tea");
				System.out.println("McCafé");
				System.out.println("Water\n");

				System.out.println("Vanilla Cone\n");
			} */

			// VIEW QUEUE
			else if (input.charAt(0) == 'V') {
				System.out.println();
				for (Object o : pq.toArray()) {
					if (!(o instanceof Order)) {
						continue;
					}
					((Order) o).print(false);
				}
			}

			// CLOSE QUEUE AND PROCESS ORDERS
			else if (input.charAt(0) == 'X') {
				System.out.println("\nNo more orders! Processing current orders now.");
				processOrders();
				return;
			} else {
				System.out.println("Error: Invalid input! Please try again.");
			}
		}
	}



	// ===================================================================================================================
	// #region enqueueNewOrder
	public static void enqueueNewRandomOrder() {
		final int MAX_MEALS_PER_ORDER = 5;
		int numberOfMeals = rand.nextInt(1, MAX_MEALS_PER_ORDER + 1);

		Order newOrder = new Order();
		for (int i = 0; i < numberOfMeals; i++) {
			// add anywhere from 1 to MAX_MEALS_PER_ORDER meals to this order
			Meal newMeal = new Meal(Integer.toString(rand.nextInt(0, 10))); 
			newOrder.meals.add(newMeal);
		}
		newOrder.price = newOrder.calculateAPrice(numberOfMeals);
	}

	// ===================================================================================================================
	// #region processOrders()
	public static void processOrders() {
		

		LLStack<Order> compeletedOrders = new LLStack<Order>();
		int dineInOrdersProcessed = 0;
		int takeOutOrdersProcessed = 0;
		double totalRevenue = 0;

		try {
			for (Object element : pq.toArray()) {
				if (!(element instanceof Order)) {
					continue;
				}
				Order o = (Order) element;
				System.out.println();
				o.print(false);

				while (!o.meals.isEmpty()) { // loop thru meals in order
					Thread.sleep(rand.nextInt(1000, 1800));
					Meal currentMeal = o.meals.removeFirst();
					System.out.printf("Now making %s:\n", currentMeal.mealName);
					while (!currentMeal.ingredients.isEmpty()) { // loop thru ingredients in meal
						System.out.printf("\t• %s...\n", currentMeal.ingredients.pop());
						Thread.sleep(rand.nextInt(200, 1000));
					}
					System.out.printf("\t%s completed.\n", currentMeal.mealName);
					Thread.sleep(rand.nextInt(100, 200));
				}

				System.out.printf("\u001b[32mOrder [%d] is ready!\u001b[0m\n", o.orderNumber);
				compeletedOrders.push(o);
				if (o instanceof DineInOrder) {
					dineInOrdersProcessed++;
				} else if (o instanceof TakeOutOrder) {
					takeOutOrdersProcessed++;
				}
				totalRevenue += o.price;

				System.out.println("================ END OF DAY SUMMARY ===============");
				System.out.printf("Customers/orders processed: %d\n",
						dineInOrdersProcessed + takeOutOrdersProcessed);
				System.out.printf("Dine-in orders processed:   %d\n", dineInOrdersProcessed);
				System.out.printf("Take-out orders processed:  %d\n", takeOutOrdersProcessed);
				System.out.printf("Total revenue:              $%.2f\n", totalRevenue);

				System.out.println("\nThanks you for choosing McDonald's! Please come back soon!");
				System.out.println("===================================================");

			}
		} catch (InterruptedException e) {
			System.out.println("Error: sleep interrupted");
			System.out.println("(this REALLY shouldn't ever happen)");
		}
	}
}

// =======================================================================================================================
// #region Station
class Station {
	public int stationNumber;
	public Order currentOrder;

	public Station(int number) {
		this.stationNumber = number;
		this.currentOrder = null;
	}

	public boolean isBusy() {
		return (currentOrder == null) ? false : true;
	}

	public void doAStep() {
		if (!isBusy()) {
			System.out.printf("\u001b[31mStation %d has nothing to do!\u001b[0m");
			return;
		}
		
	}
	// TODO TODO TODO
}

// =======================================================================================================================
// #region Order
class Order implements Comparable<Order> {
	ArrayList<Meal> meals;
	int orderNumber;
	double price;

	public Order() {
		restaurant.currentOrderNumber++;
		this.orderNumber = restaurant.currentOrderNumber;

		meals = new ArrayList<Meal>();

		// while (true) {
		// 	System.out.println("\nType what you'd like to order,");
		// 	System.out.println("or type \"END\" to end your order.");
		// 	System.out.print(">  ");
		// 	String input = restaurant.scan.nextLine();
		// 	if (input.toUpperCase().equals("END")) {
		// 		break;
		// 	} else {
		// 		Meal meal = new Meal(input);
		// 		if (!meal.ingredients.isEmpty()) {
		// 			meals.add(meal);
		// 		}
		// 	}
		// }
		// System.out.println("You ordered...");
		// this.price = this.print(true);
	}

	@Override
	public int compareTo(Order o) {
		// if (o instanceof DineInOrder && this instanceof TakeOutOrder) {
		// return (100);
		// } else if (o instanceof TakeOutOrder && this instanceof DineInOrder) {
		// return (-100);
		// } else if (o instanceof TakeOutOrder && this instanceof TakeOutOrder ||
		// o instanceof DineInOrder && this instanceof DineInOrder) {
		// return (this.orderNumber - o.orderNumber);
		// }
		return 0;
	}

	public double calculateAPrice(int numberOfMeals) {
		Random rand = new Random();
		int dollars = rand.nextInt(numberOfMeals * 3, numberOfMeals * 5);
		int cents = rand.nextInt(10, 99);
		return dollars + ((double) cents / 100);
	}

	public double print(boolean shouldShowPrice) {
		for (Meal meal : meals) {
			System.out.println("\t" + meal.mealName);
		}
		Random rand = new Random();
		int dollars = rand.nextInt(meals.size() * 3, meals.size() * 5);
		int cents = rand.nextInt(10, 99);
		if (shouldShowPrice) {
			System.out.printf("Your total is \u001b[33m[$%d.%d]\u001b[0m, and\n", dollars, cents);
			System.out.printf("your order number is \u001b[36m[%d]\u001b[0m.\n", orderNumber);
		}
		return dollars + ((double) cents / 100);
	}
}

// =======================================================================================================================
// #region DineInOrder
class DineInOrder extends Order {
	public DineInOrder() {
		System.out.println("\nNew Dine-In Order...");
		super();
	}

	public double print(boolean shouldShowPrice) {
		System.out.printf("Dine-In Order \u001b[36m[%d]\u001b[0m.\n", orderNumber);
		return super.print(shouldShowPrice);
	}
}

// =======================================================================================================================
// #region TakeOutOrder
class TakeOutOrder extends Order {
	public TakeOutOrder() {
		System.out.println("\nNew Take-Out Order...");
		super();
	}

	public double print(boolean shouldShowPrice) {
		System.out.printf("Take-Out Order \u001b[36m[%d]\u001b[0m.\n", orderNumber);
		return super.print(shouldShowPrice);
	}
}

// =======================================================================================================================
// #region Meal
class Meal {
	String mealName;
	LLStack<String> ingredients;

	public Meal(String meal) {
		ingredients = new LLStack<String>();
		switch (meal.toUpperCase()) { // <- all the ingredient lists are in here
			case "BIG MAC":
			case "0":
				ingredients.push("top bun");
				ingredients.push("burger patty");
				ingredients.push("pickles");
				ingredients.push("lettuce");
				ingredients.push("onions");
				ingredients.push("sauce");
				ingredients.push("middle bun");
				ingredients.push("burger patty");
				ingredients.push("cheese");
				ingredients.push("lettuce");
				ingredients.push("onions");
				ingredients.push("sauce");
				ingredients.push("bottom bun");
				ingredients.push("package");

				this.mealName = "Big Mac®";
				break;

			case "QUARTER POUNDER":
			case "HAMBURGER":
			case "1":
				ingredients.push("top bun");
				ingredients.push("pickles");
				ingredients.push("mustard");
				ingredients.push("ketchup");
				ingredients.push("onions");
				ingredients.push("burger patty");
				ingredients.push("bottom bun");
				ingredients.push("package");

				this.mealName = "Quarter Pounder®";
				break;

			case "QUARTER POUNDER WITH CHEESE":
			case "CHEESEBURGER":
			case "2":
				ingredients.push("top bun");
				ingredients.push("pickles");
				ingredients.push("mustard");
				ingredients.push("ketchup");
				ingredients.push("onions");
				ingredients.push("cheese");
				ingredients.push("burger patty");
				ingredients.push("cheese");
				ingredients.push("bottom bun");
				ingredients.push("package");

				this.mealName = "Quarter Pounder® With Cheese";
				break;

			case "MCCRISPY":
			case "CHICKEN SANDWICH":
			case "3":
				ingredients.push("top bun");
				ingredients.push("mayonnaise");
				ingredients.push("pickles");
				ingredients.push("chicken patty");
				ingredients.push("bottom bun");
				ingredients.push("package");

				this.mealName = "McCrispy®";
				break;

			case "NUGGETS":
			case "CHICKEN NUGGETS":
			case "10 PC CHICKEN NUGGETS":
			case "10 PC. CHICKEN NUGGETS":
			case "4":
				ingredients.push("nuggets");
				ingredients.push("package");

				this.mealName = "10 piece Chicken McNuggets®";
				break;

			case "FILET-O-FISH":
			case "FILET O FISH":
			case "5":
				ingredients.push("top bun");
				ingredients.push("tartar sauce");
				ingredients.push("fish patty");
				ingredients.push("cheese");
				ingredients.push("bottom bun");
				ingredients.push("package");

				this.mealName = "Filet-O-Fish®";
				break;

			case "MCMUFFIN":
			case "EGG MCMUFFIN":
			case "6":
				ingredients.push("top english muffin");
				ingredients.push("ham");
				ingredients.push("egg");
				ingredients.push("cheese");
				ingredients.push("bottom english muffin");
				ingredients.push("package");

				this.mealName = "Egg McMuffin®";
				break;

			case "FRIES":
			case "FRENCH FRIES":
			case "7":
				ingredients.push("fries");
				ingredients.push("package");

				this.mealName = "McFries®";
				break;

			case "COCA-COLA":
			case "COCA COLA":
			case "COKE":
			case "COLA":
			case "DIET COKE":
			case "SPRITE":
			case "DR PEPPER":
			case "DR. PEPPER":
			case "SODA":
			case "LEMONADE":
			case "SWEET TEA":
			case "DRINK":
			case "8":
				ingredients.push("straw");
				ingredients.push("lid");
				ingredients.push("ice");
				ingredients.push("soda");
				ingredients.push("cup");

				this.mealName = "Fountain Drink";
				break;

			case "COFFEE":
			case "MCCAFE":
			case "9":
				ingredients.push("lid");
				ingredients.push("sugar");
				ingredients.push("cream");
				ingredients.push("coffee");
				ingredients.push("cup");

				this.mealName = "McCafé®";
				break;

			case "CONE":
			case "ICE CREAM":
			case "ICE CREAM CONE":
			case "VANILLA CONE":
			case "VANILLA ICE CREAM CONE":
			case "-1":
				System.err.println("Oops, our ice cream machine is broken right now :("); // easter egg
				return;

			default:
				System.err.println("Error: Invalid menu item!");
				return;
		}
		System.out.println("One " + mealName + ", coming up!");

	}
}

// =======================================================================================================================
// #region LLStack
class LLStack<T> {
	public int length;
	public Node<T> head;

	public LLStack() {
		this.length = 0;
		this.head = null;
	}

	public void push(T item) {
		Node<T> newNode = new Node<T>(item);
		newNode.next = head;
		this.head = newNode;
		length++;
	}

	public T pop() {
		T result = null;
		if (length > 0) {
			result = this.head.data;
			this.head = this.head.next;
			length--;
		} else {
			System.err.println("error: attempting to remove from empty stack");
		}
		return result;
	}

	public T peek() {
		T result = null;
		if (length > 0) {
			result = this.head.data;
		} else {
			System.err.println("error: attempting to remove from empty stack");
		}
		return result;
	}

	public boolean isEmpty() {
		return (length == 0) ? true : false;
	}
}

// =======================================================================================================================
// #region LLQueue
class LLQueue<T> {

}

// =======================================================================================================================
// #region Node
class Node<T> {
	// data
	public T data;

	// link
	public Node<T> next;

	public Node(T data) {
		this.data = data;
		this.next = null;
	}
}