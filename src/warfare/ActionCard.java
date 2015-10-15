package warfare;

/****************************************************************
 * Class to simulate attributes specific to an Action Card
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class ActionCard extends Card{
	
	/* Array of integers representing action functions */
	private int[] actionFunction;
	

	/************************************************************
     * Constructor for objects of type ActionCard with attributes.
     * 
     * @param name of card, cost of card, description of card,
     * 			number of cards, number of actions, purchase amt,
     * 			amount of money
     ***********************************************************/
	public ActionCard(String name, int cost, String description, int cards, int actions, int purchase, int money, String type)
	{
		super(name, cost, description, type);
		actionFunction = new int[]{cards, actions, purchase, money};
	}
	
	/************************************************************
     * Constructor for objects of type ACtionCard.
     ***********************************************************/
	public ActionCard()
	{
		super();
		actionFunction = null;
	}
	
	/************************************************************
     * Clone an action card.
     * 
     * @param action card to be cloned
     * @return clone of action card (c) 
     ***********************************************************/
	@Override
	public ActionCard cardClone(Card c){
		ActionCard clone = new ActionCard();
		clone.setCost(c.getCost());
		clone.setDescription(c.getDescription());
		clone.setName(c.getName());
		clone.setType(c.getType());	
		clone.setActionFunction(((ActionCard)c).getActionFunction());
		
		return clone;
	}
	
	/************************************************************
     * Get function of card.
     * 
     * @return function of card
     ***********************************************************/
	public int[] getActionFunction() {
		return actionFunction;
	}

	/************************************************************
     * Set card function. 
     * 			Format: [cards, actions, purchase, money]
     * 
     * @param function of card
     ***********************************************************/
	public void setActionFunction(int[] actionFunction) {
		this.actionFunction = actionFunction;
	}
	
	/************************************************************
     * Get formatted string of card attributes for printing.
     * 
     * @return formatted string of card attributes
     ***********************************************************/
	public String toString(){
		String str = super.toString();
		str += "Action: ";
		return str;
	}
}
