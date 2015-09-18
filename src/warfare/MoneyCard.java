package warfare;

public class MoneyCard extends Card{

	private int value;
	
	public MoneyCard(String name, int cost, String description, int value){
		super(name, cost, description);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
