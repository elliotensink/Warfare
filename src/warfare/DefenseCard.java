package warfare;

/****************************************************************
 * Class to simulate attributes specific to a Defense Card
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class DefenseCard extends Card{
	
	/************************************************************
     * Constructor for objects of type DefenseCard.
     * 
     * @param name of card, cost of card, description of card
     ***********************************************************/
	public DefenseCard(String name, int cost, String description, String type){
		super(name, cost, description, type);
	}
	
	/************************************************************
     * Constructor for objects of type DefenseCard.
     ***********************************************************/
	public DefenseCard(){
		super();
	}
	
	/************************************************************
     * Clone a defense card.
     * 
     * @param defense card to be cloned
     * @return clone of defense card (c) 
     ***********************************************************/
	@Override
	public DefenseCard cardClone(Card c){
		DefenseCard clone = new DefenseCard();
		clone.setCost(c.getCost());
		clone.setDescription(c.getDescription());
		clone.setName(c.getName());
		clone.setType(c.getType());	
		
		return clone;
	}
	
	/************************************************************
     * Get formatted string of card attributes for printing.
     * 
     * @return formatted string of card attributes
     ***********************************************************/
	public String toString(){
		String str = super.toString();
		return str;
	}
}
