
public enum PizzaSize {
	SMALL(3), MEDIUM(5), LARGE(7);
	private int price;
	private PizzaSize(int price) {
		// TODO Auto-generated constructor stub
		this.price = price;
	}
	public int getPrice() {
		return price;
	}	
}
