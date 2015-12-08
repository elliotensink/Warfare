package warfare;

import java.io.File;

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
     * @param name of card
     * @param cost of card
     * @param description of card
     * @param type of card
     * @param image file
     ***********************************************************/
	public DefenseCard(String name, int cost, String description, String type, File img){
		super(name, cost, description, type, img);
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
		clone.setImg(c.getImg());
		
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
