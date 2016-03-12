
/**
 *Interface that defines methods of DAO
 */
public interface PizzaShopDAO {
	boolean createUser(User user);
	User authenticateUser(String username, String password);
	
	Topping createTopping(Topping topping);
	Topping getToppingByName(String name);
	
	Boolean createOrder(PizzaOrder order);
	Boolean createDiscountedOrder(DiscountedPizzaOrder discountedOrder);

	void closeSessionFactory();
}
