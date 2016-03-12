import javax.persistence.Entity;

/**
 * A discounted order is a special order with a discounted rate of 10%
 * The total price of a discounted order should reflect the discount rate.
 */
@Entity
public class DiscountedPizzaOrder extends PizzaOrder{
	public static final double discountedRate = 0.10; //discountedRate = 10%
	
	private double discountPrice;
	
	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double totalPrice) {
		this.discountPrice = totalPrice - totalPrice*discountedRate;
	}

	public double getDiscountedrate() {
		return discountedRate;
	}
	
	
}//DiscountedPizzaOrder
