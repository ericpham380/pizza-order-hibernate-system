import java.util.List;

/**
 *Interface that defines methods of DAO
 */
public interface PizzaShopDAO {
	boolean createUser(User user);
	User authenticateUser(String username, String password);
	
	Topping createTopping(Topping topping);
	Topping getToppingByName(String name);
	List<Topping> getToppingsByOrder(int orderID);
	
	boolean createOrder(PizzaOrder order);
	boolean createDiscountedOrder(DiscountedPizzaOrder discountedOrder);
	boolean deleteOrder(int orderID);
	PizzaOrder getOrderByID(int orderID);
	List<PizzaOrder> getOrdersByUser(int userID);
	
	boolean updateOrder(PizzaOrder order);
	boolean updateDiscountedOrder(DiscountedPizzaOrder order);

	void closeSessionFactory();
}
