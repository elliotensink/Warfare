package warfare;

/****************************************************************
 * Class to simulate all cards
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class Card {

	/* Name of the card */
	private String name, type;
	
	/* Cost of the card */
	private int cost;
	
	/* Description of the card */
	private String description;
	
	/************************************************************
     * Constructor for objects of type Card with attributes.
     * 
     * @param name of card, cost of card, description of card
     ***********************************************************/
	public Card(String name, int cost, String description, String type)
	{
		this.name = name;
		this.cost = cost;
		this.type = type;
		this.description = description;
	}
	
	/************************************************************
     * Default constructor for objects of type Card.
     ***********************************************************/
	public Card()
	{
		name = "";
		cost = 0;
		description = "";
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	/************************************************************
     * Clone a card.
     * 
     * @param card to be cloned
     * @return cloned of card
     ***********************************************************/
	public Card cardClone(Card c)
	{
		Card newC = new Card();
		newC.setName(c.getName());
		newC.setCost(c.getCost());
		newC.setDescription(c.getDescription());
		newC.setType(c.getType());
		return newC;
	}
	
	/************************************************************
     * Get name of card.
     * 
     * @return name of card
     ***********************************************************/
	public String getName()
	{
		return name;
	}
	
	/************************************************************
     * Set name of card.
     * 
     * @param name of card
     ***********************************************************/
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/************************************************************
     * Get cost of card.
     * 
     * @return cost of card
     ***********************************************************/
	public int getCost()
	{
		return cost;
	}
	
	/************************************************************
     * Set cost of card.
     * 
     * @param cost of card
     ***********************************************************/
	public void setCost(int cost) 
	{
		this.cost = cost;
	}
	
	/************************************************************
     * Get card description.
     * 
     * @return card description
     ***********************************************************/
	public String getDescription() 
	{
		return description;
	}

	/************************************************************
     * Set card description.
     * 
     * @param card description
     ***********************************************************/
	public void setDescription(String description) 
	{
		this.description = description;
	}

	
	/************************************************************
     * Get formatted string of card attributes for printing.
     * 
     * @return formatted string of card attributes
     ***********************************************************/
	public String toString()
	{
		return name + ", " + type + ", ";
	}
	
	public boolean isActionable() {
			if (this.type.equals("attack") || this.type.equals("action")) {
				return true;
			}
			
			return false;
	}
}
