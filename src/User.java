import java.util.List;

import javax.persistence.*;

/**
 * A customer signs up for the system
 * The customer chooses user name and password and enters address information
 * (street name, city, state and zip code) to sign up for the system
 * @author ericpham
 *
 */
@Entity
public class User {
	
	@Id
	@GeneratedValue
	private int userID;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	private Address address;
	
	@OneToMany(mappedBy="user", targetEntity = PizzaOrder.class,
				fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PizzaOrder> orderList;
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public List<PizzaOrder> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<PizzaOrder> orderList) {
		this.orderList = orderList;
	}
	@Override
	public String toString() {
		return userID + " " + username + " " + password;
	}
}
