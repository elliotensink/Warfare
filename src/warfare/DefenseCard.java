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
	
	public DefenseCard(){
		super();
	}
	
	/************************************************************
     * Clone an action card.
     * 
     * @param action card to be cloned
     * @return clone of action card (c) 
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
}
