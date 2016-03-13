import java.util.List;

import javax.persistence.*;

/**
 * An order consists of pizza size, toppings of choice, 
 * delivery date and time (use TimeStamp type), 
 * payment method and total price.
 * A customer can add upto 3 different or same toppings to the order.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class PizzaOrder {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int orderID;
	
	@Enumerated(EnumType.STRING)
	private PizzaSize size;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Order_Topping",
	joinColumns 		= {@JoinColumn(name = "orderID")}, 
	inverseJoinColumns 	= {@JoinColumn(name = "toppingID")})
	private List<Topping> toppingList;
	
	private double totalPrice;
	
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	
	private java.util.Calendar deliveryDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userID")
	private User user;

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public PizzaSize getSize() {
		return size;
	}

	public void setSize(PizzaSize size) {
		this.size = size;
	}

	public List<Topping> getToppingList() {
		return toppingList;
	}

	public void setToppingList(List<Topping> toppingList) {
		this.toppingList = toppingList;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public java.util.Calendar getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(java.util.Calendar deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}//PizzaOrder
