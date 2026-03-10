package lab5;

import java.util.Random;
import java.util.Scanner;

public class restaurant {
	public static Queue<Order> orderQueue = new Queue<Order>(100); // <- the queue all the orders go in
	public static Station[] stations = new Station[6];
	public static int currentOrderNumber = 0;

	public static LLStack<Order> compeletedOrders = new LLStack<Order>();
	public static int dineInOrdersProcessed = 0;
	public static int takeOutOrdersProcessed = 0;
	public static double totalRevenue = 0;

	public static Random rand = new Random();
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		// ===============================================================================================================
		// #region main
		System.out.println("\n\n\nHi, welcome to McDonald's.");
		System.out.println("It's \u001b[33m7:00\u001b[0m... time to start your shift.\n");

		for (int i = 0; i < stations.length; i++) {
			stations[i] = new Station(i + 1);
		}

		int minutes = 0;

		enqueueNewRandomOrder();
		enqueueNewRandomOrder();
		enqueueNewRandomOrder();
		enqueueNewRandomOrder();

		do {
			enqueueNewRandomOrder();
			System.out.println("\u001b[31m");
			System.out.printf("%02d:%02d - %d customers waiting\n", 7 + (minutes / 60), minutes % 60, orderQueue.size);
			System.out.print("\u001b[0m");

			for (Station station : stations) {
				if (!station.isBusy && orderQueue.size > 0) {
					station.takeAnOrder(orderQueue.dequeue());
				} else {
					station.doAStep();
				}
			}

			minutes++;
			scan.nextLine(); // wait for user before next timestep 
		} while (orderQueue.size > 0 || isAnyStationBusy());

		endOfDayStats();
	}

	// ===================================================================================================================
	// #region isAnyStationBusy
	public static boolean isAnyStationBusy() {
		for (Station s : stations) {
			if (s.isBusy)
				return true;
		}
		return false;
	}

	// ===================================================================================================================
	// #region enqueueNewOrder
	public static void enqueueNewRandomOrder() {
		char[] orderTypes = { 'D', 'T', 'x', 'x' };
		char orderType = orderTypes[rand.nextInt(orderTypes.length)];

		if (orderType == 'D') { // dine in
			orderQueue.enqueue(new DineInOrder());
		} else if (orderType == 'T') { // take out
			orderQueue.enqueue(new TakeOutOrder());
		} else { // no customer this step
			;
		}
	}

	// ===================================================================================================================
	// #region endOfDayStats
	public static void endOfDayStats() {
		System.out.println("\u001b[33m================ END OF DAY SUMMARY ===============\u001b[0m");
		System.out.printf("Customers/orders processed: %d\n",
				dineInOrdersProcessed + takeOutOrdersProcessed);
		System.out.printf("Dine-in orders processed:   %d\n", dineInOrdersProcessed);
		System.out.printf("Take-out orders processed:  %d\n",
				takeOutOrdersProcessed);
		System.out.printf("Total revenue:              $%.2f\n", totalRevenue);

		System.out.println("\nThanks you for choosing McDonald's! Please come back soon!");
		System.out.println("===================================================");
	}
}






// =======================================================================================================================
// #region Station
class Station {
	public int stationNumber;
	public Order currentOrder;
	public boolean isBusy;

	public Station(int number) {
		this.stationNumber = number;
		this.currentOrder = null;
		this.isBusy = false;
	}

	public void takeAnOrder(Order o) {
		currentOrder = o;
		isBusy = true;
		System.out.printf("Station %d took Order %d.\n", stationNumber, o.orderNumber);
	}

	public void doAStep() {
		if (!isBusy) {
			System.out.printf("\u001b[36mStation %d has nothing to do!\u001b[0m\n", stationNumber);
			return;
		}
		String nextIngredient = currentOrder.meal.ingredients.pop();
		if (nextIngredient == null) {
			System.out.printf("Station %d finished Customer %d's %s. \u001b[32mOrder up!\u001b[0m\n", stationNumber,
					currentOrder.orderNumber, currentOrder.meal.mealName);

			restaurant.compeletedOrders.push(currentOrder);
			if (currentOrder instanceof DineInOrder) {
				restaurant.dineInOrdersProcessed++;
			} else if (currentOrder instanceof TakeOutOrder) {
				restaurant.takeOutOrdersProcessed++;
			}
			restaurant.totalRevenue += currentOrder.price;
			currentOrder = null;
			isBusy = false;
			return;
		}
		System.out.printf("Station %d prepped the %s for #%d's %s.\n", stationNumber, nextIngredient,
				currentOrder.orderNumber, currentOrder.meal.mealName);

	}
}

// =======================================================================================================================
// #region Order
class Order {
	Meal meal;
	int orderNumber;
	double price;

	public Order() {
		restaurant.currentOrderNumber++;
		this.orderNumber = restaurant.currentOrderNumber;

		this.meal = new Meal(Integer.toString(restaurant.rand.nextInt(0, 10)));
		this.price = calculateAPrice();

	}

	public double calculateAPrice() {
		Random rand = new Random();
		int dollars = rand.nextInt(1, 10);
		int cents = rand.nextInt(10, 99);
		return dollars + ((double) cents / 100);
	}
}

// =======================================================================================================================
// #region DineInOrder
class DineInOrder extends Order {
	public DineInOrder() {
		super();
		System.out.printf("Customer %d placed a dine-in order for... %s!\n", this.orderNumber, this.meal.mealName);
	}
}

// =======================================================================================================================
// #region TakeOutOrder
class TakeOutOrder extends Order {
	public TakeOutOrder() {
		super();
		System.out.printf("Customer %d placed a take-out order for... %s!\n", this.orderNumber, this.meal.mealName);
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
			// System.err.println("error: attempting to remove from empty stack");
			// this warning was getting annoying
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
// #region Queue
class Queue<T> {
	private Object[] data;
	public int size;
	private int head;
	private int tail;

	public Queue(int capacity) {
		data = new Object[capacity];
		size = 0;
		head = 0;
		tail = 0;
	}

	public void enqueue(T item) {
		if (size == data.length) {
			Object[] newData = new Object[data.length * 2];

			for (int i = 0; i < size; i++) {
				newData[i] = data[(head + i) % data.length];
			}

			data = newData;
			head = 0;
			tail = size;
		}
		this.data[this.tail] = item;
		this.tail = (this.tail + 1) % data.length;
		this.size++;
	}

	public T dequeue() {
		T result = null;
		if (size == 0) {
			System.err.println("error: removing from empty queue!");
		} else {
			result = (T) this.data[this.head];
			this.data[this.head] = null;
			this.head = (this.head + 1) % data.length;
			this.size--;
		}
		return result;
	}
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