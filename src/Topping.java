import javax.persistence.*;

/**
 * 10 toppings available to the system
 * Pepperoni, Mushrooms, Onions, Sausage, Bacon Extra cheese, 
 * Black olives, Green peppers, Pineapple, Spinach
 * 
 * The Topping class defines an instance variable price for this Topping instance. 
 * Assign some reasonable price to it
 * @author ericpham
 */
@Entity
public class Topping {
	@Id
	@GeneratedValue
	private int toppingID;
	
	private String name;
	private double price;
	
	public int getToppingID() {
		return toppingID;
	}
	public void setToppingID(int toppingID) {
		this.toppingID = toppingID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}//Topping
