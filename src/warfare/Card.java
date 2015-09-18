package warfare;

public class Card {

	private String name;
	private int cost;
	private String description;
	
	public Card(String name, int cost, String description)
	{
		this.name = name;
		this.cost = cost;
		this.description = description;
	}
	
	public Card()
	{
		name = "";
		cost = 0;
		description = "";
	}
	
	public Card cardClone(Card c)
	{
		Card newC = new Card();
		newC.setName(new String(c.getName()));
		newC.setCost(new Integer(c.getCost()));
		newC.setDescription(new String(c.getDescription()));
		return newC;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public void setCost(int cost) 
	{
		this.cost = cost;
	}
	
	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}



	public String toString()
	{
		return name;
	}
}
