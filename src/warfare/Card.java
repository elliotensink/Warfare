package warfare;

public class Card {

	private String name;
	private int cost;
	private String description;
	
	public Card(String name, int cost, String description){
		this.name = name;
		this.cost = cost;
		this.description = description;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCost(){
		return cost;
	}
	
	public String toString(){
		return name;
	}
}
