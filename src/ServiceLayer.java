import java.util.List;

/**
 * Program that implements the business logic of this medical system. 
 * This ServiceLayer program will use functions defined in the DAO.
 * @author ericpham
 */
public class ServiceLayer {
	
	private static PizzaShopDAO pizzaShopDAO = new ConcretePizzaShopDAO();
	private static User user = null;
	private static Topping topping = null;
	private static PizzaOrder pizzaOrder = null;
	private static DiscountedPizzaOrder discountedPizzaOrder = null;
	private static String[] toppingList = {"Pepperoni", "Mushrooms", "Onions", "Sausage", "Bacon",
			"Extra cheese", "Black olives", "Green peppers", "Pineapple", "Spinach"};
	
	private static boolean discountApplied = false;
	
	/**================================ signupUser ================================
	 * Setup user object and pass it to DAO
	 */
	public boolean signupUser(String username, String password){
		User user = new User();
		user.setUsername(username.toLowerCase());
		user.setPassword(password);
		if(pizzaShopDAO.createUser(user)) 
			return true;
		else 
			return false;
	}//signupUser
	
	/**================================== login ===================================
	 * Prompt the user to get information to login. If login is successful, return
	 * the user object, null otherwise.
	 */
	public User login(String username, String password){
		user = pizzaShopDAO.authenticateUser(username.toLowerCase(), password);
		return user;
	}//login
	
	/**=============================== setupTopping ===============================
	 * Set up the topping objects and pass them to DAO 
	 */	
	public void setupTopping(){
		double toppingPrice;
		for(String item : toppingList){
			item = item.toLowerCase();
			if(item.equals("pepperoni") || item.equals("sausage") || item.equals("bacon")){
				toppingPrice = 1.00;
			}else
				toppingPrice = 0.75;
			
			Topping topping = new Topping();
			topping.setName(item);
			topping.setPrice(toppingPrice);
			pizzaShopDAO.createTopping(topping);
		}
	}//setupTopping
	
	public Topping findToppingByName(String name){
		topping = pizzaShopDAO.getToppingByName(name);
		return topping;
	}//findToppingByName
	
	/**============================= setupPizzaOrder ==============================
	 * Set up the setupPizzaOrder objects with size, list of toppings, and price
	 */	
	public PizzaOrder setupPizzaOrder(PizzaSize size, List<Topping> toppingList){
		double totalPrice = 0;
		double totalToppingPrice = 0;
		
		for(Topping t : toppingList){
			totalToppingPrice += t.getPrice();
		}
		totalPrice = size.getPrice() + totalToppingPrice;
		
		if(discountApplied){
			discountedPizzaOrder = new DiscountedPizzaOrder();
			discountedPizzaOrder.setSize(size);
			discountedPizzaOrder.setToppingList(toppingList);
			discountedPizzaOrder.setTotalPrice(totalPrice);
			discountedPizzaOrder.setDiscountPrice(totalPrice);
			discountedPizzaOrder.setUser(user);
			
			return discountedPizzaOrder;
		}else {
			pizzaOrder = new PizzaOrder();
			pizzaOrder.setSize(size);
			pizzaOrder.setToppingList(toppingList);
			pizzaOrder.setTotalPrice(totalPrice);
			pizzaOrder.setUser(user);
			
			return pizzaOrder;
		}
	}

	/**============================= finalizePizzaOrder ==============================
	 * Complete the PizzaOrder object with payment type and pass them to DAO
	 */	
	public boolean finalizePizzaOrder (PizzaOrder order, PaymentType type){
		
		order.setPaymentType(type);
		
		if(order.getClass() == DiscountedPizzaOrder.class){
			discountedPizzaOrder = (DiscountedPizzaOrder) order;
			if(pizzaShopDAO.createDiscountedOrder(discountedPizzaOrder)) return true;
			else return false;
		} else {
			if(pizzaShopDAO.createOrder(order)) return true;
			else return false;
		}
		
	}//finalizePizzaOrder

	
	public void quit(){
		pizzaShopDAO.closeSessionFactory();
	}

	public boolean isDiscountApplied() {
		return discountApplied;
	}

}//ServiceLayer
