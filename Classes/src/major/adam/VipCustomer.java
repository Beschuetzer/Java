package major.adam;

public class VipCustomer {
  private String name;
  private double creditLimit;
  private String email;

	public VipCustomer() {
		this("default", 1000, "no email");
	}

	public VipCustomer(String name, double creditLimit) {
		this(name, creditLimit, "no email");
	}

	public VipCustomer(String name, double creditLimit, String email) {
    this.name = name;
    this.creditLimit = creditLimit;
    		this.email = email;
  }



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCreditLimit() {
		return this.creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
