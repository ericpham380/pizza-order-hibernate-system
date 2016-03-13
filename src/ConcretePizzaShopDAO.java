import java.util.List;

import org.hibernate.*;


/**
 * Concrete implementation of PizzaShopDAO
 */
public class ConcretePizzaShopDAO implements PizzaShopDAO{

	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static Transaction transaction = null;
	private static Session session = null;

	/**================================ createUser ================================
	 * Add the user object to Hibernate session and persist the object to database
	 * return user object when it's done.
	 */
	@Override
	public boolean createUser(User user) {
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
		} catch(HibernateException he){
			transaction.rollback();
			return false;
		}finally{
			session.close();
		}
		return true;
	}//createUser

	/**============================= authenticateUser =============================
	 * First retrieve the user data using unique username, then check the password
	 * return user object when it sastifies both condition and null otherwise.
	 */	
	@Override
	public User authenticateUser(String name, String pass) {
		User user = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			String queryString = "from User where username = :name";
			Query query = session.createQuery(queryString);
			query.setString("name", name);
			user = (User)query.uniqueResult();
			transaction.commit();
		} catch(HibernateException he){
			transaction.rollback();
		}finally{
			session.close();
		}
		if(user != null && pass.equals(user.getPassword()))
			return user;
		else
			return null;
	}//authenticateUser
	
	/**================================ createTopping ================================
	 * Add a Topping object to Hibernate session and persist the object to database
	 * return Topping object when it's done.
	 */
	@Override
	public Topping createTopping(Topping topping){
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(topping);
			transaction.commit();
		} catch(HibernateException he){
			transaction.rollback();
			System.out.println("ERROR: cannot add new topping. Transaction is rolled back.");
		}finally{
			session.close();
		}
		return topping;
	}//createTopping

	public Topping getToppingByName(String name){
		Topping topping = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String queryString = "from Topping where name = :toppingName";
			Query query = session.createQuery(queryString);
			query.setString("toppingName", name);
			topping = (Topping)query.uniqueResult();
			transaction.commit();
		} catch(HibernateException he) {
			transaction.rollback();
		} finally {
			session.close();
		}
		
		if(topping != null & topping.getName().equals(name)){

			return topping;
		}else{
			return null;
		}
			
	}//Topping
	
	/**================================ createOrder ================================
	 * Add a PizzaOrder object to Hibernate session and persist the object to database
	 * return PizzaOrder object when it's done.
	 */	
	public boolean createOrder(PizzaOrder order){		
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(order);
			transaction.commit();
		} catch(HibernateException he){
			transaction.rollback();
			return false;
		} finally{
			session.close();
		}
		return true;
	}//PizzaOrder

	/**================================ createDiscountedOrder ================================
	 * Add a DiscountedPizzaOrder object to Hibernate session and persist the object to database
	 * return DiscountedPizzaOrder object when it's done.
	 */	
	public boolean createDiscountedOrder(DiscountedPizzaOrder discountedOrder){		
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(discountedOrder);
			transaction.commit();
		} catch(HibernateException he){
			transaction.rollback();
			return false;
		}finally{
			session.close();
		}
		return true;
	}//PizzaOrder

	/**================================ getOrdersByUser ================================
	 * Given a user object, find all the orders, retrieve them and return to service
	 */	
	public List<PizzaOrder> getOrdersByUser(int userID){
		List<PizzaOrder> orders = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			String queryString = "from PizzaOrder as p where userID = :id order by p.orderID DESC";
			Query query = session.createQuery(queryString);
			query.setInteger("id", userID);
			orders = query.list();
			
			transaction.commit();
		} catch(HibernateException he){
			transaction.rollback();
		}finally{
			session.close();
		}
		return orders;
	}//getOrdersByUser

	/**================================ getToppingsByOrder ================================
	 * Given orderID, find all the toppings associated with it.
	 */		
	public List<Topping> getToppingsByOrder(int orderID){
		List<Topping> toppings = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			String queryStr = "from Topping where orderID = :id";
			Query query = session.createQuery(queryStr);
			query.setInteger("id", orderID);
			toppings = query.list();
			
			transaction.commit();
		} catch(HibernateException he){
			transaction.rollback();
			System.out.println("ERROR: cannot find toppings by order. Transaction is rolled back.");
		} finally {
			System.out.println("List of toppings by order is successfully created!");
			session.close();
		}
		return toppings;
	}//getToppingsByOrder
	
	/**================================ deleteOrder ================================
	 * Given orderID, delete the pizza order
	 */			
	public boolean deleteOrder(int orderID){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			String queryStr = "from PizzaOrder where orderID = :id";
			Query query = session.createQuery(queryStr);
			query.setInteger("id", orderID);
			
			PizzaOrder order = (PizzaOrder) query.uniqueResult();
			session.delete(order);
			
			transaction.commit();
		} catch(HibernateException he){
			transaction.rollback();
			return false;
		} finally {
			session.close();
		}
		
		return true;
	}
	
	public void closeSessionFactory(){
		sessionFactory.close();
	}
}
