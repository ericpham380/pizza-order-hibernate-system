import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.criteria.Order;

import antlr.build.Tool;

/**
 * main() using console interface
 * @author ericpham
 */
public class PresentationLayer {
	private static Scanner reader = new Scanner(System.in);
	private static User user = null;
	private static ServiceLayer service = new ServiceLayer();
	private static boolean loggedin = false;
	private static String[] toppingList = {"Pepperoni", "Mushrooms", "Onions", "Sausage", "Bacon",
			"Extra cheese", "Black olives", "Green peppers", "Pineapple", "Spinach"};


	public static void main(String[] args) {

		//service.setupTopping();

		int validChoice = 1;

		String choice;
		System.out.println("================================");
		System.out.println("==== Welcome to 157B Pizza  ====");
		System.out.println("================================");

		do{
			System.out.println("================================");
			if(!loggedin){
				System.out.println("====  [1] Login	            ====");
				System.out.println("====  [2] Sign Up           ====");
				System.out.println("====  [Q] Quit              ====");

			} else {
				System.out.println("====  [3] Make an order     ====");
				System.out.println("====  [4] View all orders   ====");
				System.out.println("====  [5] Change an order   ====");
				System.out.println("====  [6] Cancel an order   ====");
				System.out.println("====  [Q] Quit              ====");
			}
			System.out.println("================================");

			System.out.print("Choose an option: ");
			choice = reader.nextLine();
			choice = choice.toUpperCase();

			switch (choice) {
			case "1": 	loginView();
			break;
			case "2": 	signupView();
			break;
			case "3":	pizzaOrderView();
			break;		
			case "4":	allOrdersView();
			break;
			case "5":	
				break;
			case "6":	cancelOrderView();
			break;
			case "Q": 
				break;			

			default : 	System.out.println("Invalid Choice");
			validChoice = 0;
			}//switch


		} while(!choice.equals("Q") || validChoice == 0);




		System.out.println("Thank you!");
		service.quit();

	}//main

	/**================================ signupView ================================
	 * Prompt the user to get information to sign up/create an account
	 */	
	public static void signupView(){
		System.out.print("Username: ");
		String username = reader.nextLine();
		System.out.print("Password: ");
		String password = reader.nextLine();
		if(service.signupUser(username, password))
			System.out.println("User is successfully created!");
		else
			System.out.println("ERROR: cannot create new user.");
	}//signupView

	/**================================== login ===================================
	 * Prompt the user to get information to login. If login is successful, print
	 * success message, else error message
	 */	
	public static void loginView(){
		System.out.print("Username: ");
		String username = reader.nextLine();
		System.out.print("Password: ");
		String password = reader.nextLine();

		user = service.login(username, password);
		if(user != null){
			System.out.println("================================");
			System.out.println("Welcome, " + user.getUsername());
			loggedin = true;
		}else{
			System.out.println("================================");
			System.out.println("ERROR: Incorrect username or password. \n"
					+ "Try again or sign up for new user");
			loggedin = false;
		}	
	}//loginView

	/**============================== pizzaOrderView ===============================
	 * Prompt the user to get information to create a pizza order
	 */		
	public static void pizzaOrderView(){
		PizzaSize size = null;
		PaymentType type = null;
		int numToppings = 0;
		int toppingNum;
		double totalPrice = 0;
		double discountedPrice = 0;
		List<Topping> toppings = new ArrayList<Topping>();
		String input;
		boolean validChoice = true;
		do {
			validChoice = true;
			System.out.println("================================");
			System.out.println("====  [S] Small	            ====");
			System.out.println("====  [M] Medium            ====");
			System.out.println("====  [L] Large             ====");
			System.out.println("================================");
			System.out.print("What size would you like? ");
			input = reader.nextLine().toUpperCase();
			if(input.equals("S")) size = PizzaSize.SMALL;
			else if(input.equals("M")) size = PizzaSize.MEDIUM;
			else if(input.equals("L")) size = PizzaSize.LARGE;
			else {
				validChoice = false;
				System.out.println("================================");
				System.out.println("ERROR: Invalid choice");
			}
		} while(validChoice == false);

		System.out.println("================================");
		System.out.println("Available Toppings");
		System.out.println("================================");
		int i = 1;
		for(String t : toppingList){
			System.out.println("====  [" + (i++) + "] " + t);
		}

		do {
			validChoice = true;
			System.out.println("================================");
			System.out.print("Number of toppings (3 maximum): ");
			numToppings = reader.nextInt();
			if(numToppings <= 0 || numToppings > 3){
				System.out.println("Invalid number of toppings");
				validChoice = false;
			}
		} while(validChoice == false);

		for(i = 1; i <= numToppings; i++){
			do {
				validChoice = true;
				System.out.println("================================");
				System.out.printf("====  Choose topping #" + i + ": ");
				toppingNum = reader.nextInt();
				if(toppingNum < 1 || toppingNum > toppingList.length){
					System.out.println("Invalid selected topping");
					validChoice = false;
				} else{
					Topping t = service.findToppingByName(toppingList[toppingNum-1].toLowerCase());
					toppings.add(t);
				}
			} while(validChoice == false);
		}
		reader.nextLine();
		PizzaOrder order = service.setupPizzaOrder(size, toppings);
		totalPrice = order.getTotalPrice();
		System.out.println("================================");
		if(order.getClass() == DiscountedPizzaOrder.class){
			DiscountedPizzaOrder discountedOrder = (DiscountedPizzaOrder)order;
			discountedPrice = discountedOrder.getDiscountPrice();
			System.out.printf("                           $%.2f\n", totalPrice);
			System.out.println("                 Discount: " + discountedOrder.getDiscountedrate()*100 + "%");
			System.out.printf("                    Total: $%.2f\n", discountedPrice);
		} else{
			System.out.printf("                    Total: $%.2f\n", totalPrice);
		}

		do {
			validChoice = true;
			System.out.println("================================");
			System.out.println("====  [C] Cash	            ====");
			System.out.println("====  [V] Visa              ====");
			System.out.println("====  [M] Master            ====");
			System.out.println("================================");
			System.out.print("Pay with: ");
			input = reader.nextLine().toUpperCase();
			if(input.equals("C")) type = PaymentType.CASH;
			else if(input.equals("V")) type = PaymentType.VISA;
			else if(input.equals("M")) type = PaymentType.MASTER;
			else {
				validChoice = false;
				System.out.println("================================");
				System.out.println("ERROR: Invalid choice");
			}
		} while(validChoice == false);
		System.out.println("================================");
		if(service.finalizePizzaOrder(order, type)) {
			System.out.println("Your order is completed.\n");
		} else {
			System.out.println("ERROR: cannot complete your order.");
			System.out.println("Please try again.\n");
		}
		System.out.println("********************************");
	}//pizzaOrderView

	/**============================== allOrdersView ================================
	 * Show all the orders of a user.
	 */	
	public static void allOrdersView(){
		System.out.println("================================");
		System.out.println("Your Orders");
		System.out.println("============================================"
				+ "=====================================");
		List<PizzaOrder> orders = service.findOrdersByUser();
		List<Topping> toppings = null;
		if(orders == null || orders.isEmpty()){
			System.out.println("You dont have any orders");
		}else if(orders != null){
			System.out.printf("%-7s", "ORDER");
			System.out.printf("%-7s", "SIZE");
			System.out.printf("%-14s", "TOPPING 1");
			System.out.printf("%-14s", "TOPPING 2");
			System.out.printf("%-14s", "TOPPING 3");
			System.out.printf("%-10s", "DISCOUNT");
			System.out.printf("%-8s", "TOTAL");

			System.out.printf("%-7s", "PAYMENT");
			System.out.println();
			for(PizzaOrder order : orders){
				if(order != null){
					System.out.printf("%-7d", order.getOrderID());
					System.out.printf("%-7s", order.getSize());
					toppings = order.getToppingList();
					if(toppings != null){
						for(Topping topping : toppings){
							if(topping != null)
								System.out.printf("%-14s", topping.getName());
						}
						for(int i = toppings.size(); i < 3; i++){
							System.out.printf("%-14s", "null");
						}
					}
					if(order.getClass() == DiscountedPizzaOrder.class){
						DiscountedPizzaOrder temp = (DiscountedPizzaOrder)order;
						System.out.printf("%-2.0f", temp.getDiscountedrate() * 100);
						System.out.printf("%-8s", "% OFF");
						System.out.printf("$%-7.2f", temp.getDiscountPrice());

					} else{
						System.out.printf("%-10s", "  NO");
						System.out.printf("$%-7.2f", order.getTotalPrice());
					}

					System.out.printf("%-7s", order.getPaymentType());
					System.out.println();
				}
			}//for-loop
		}//else-statement
		System.out.println("============================================"
				+ "=====================================");
	}//allOrdersView

	
	/**============================= cancelOrderView ===============================
	 * Prompt user for orderID and cancel that order
	 */	
	public static void cancelOrderView(){
		System.out.println("================================");
		System.out.print("Choose an orderID to cancel: ");
		int orderNum = reader.nextInt();
		reader.nextLine();
		if(service.cancelOrder(orderNum))
			System.out.println("Order is sucessfully cancelled");
		else
			System.out.println("Invalid selected order number");
	}
}//PresentationLayer
