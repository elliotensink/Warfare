package warfare;

public class MoneyCard extends Card{

	private int value;
	
	public MoneyCard(String name, int cost, String description, int value)
	{
		super(name, cost, description);
		this.value = value;
	}
	
	public MoneyCard()
	{
		super();
		value = 0;
	}
	
	public MoneyCard cardClone(MoneyCard c)
	{
		MoneyCard newC = new MoneyCard();
		newC.setName(new String(c.getName()));
		newC.setCost(new Integer(c.getCost()));
		newC.setDescription(new String(c.getDescription()));
		newC.setValue(new Integer(c.getValue()));
		return newC;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
