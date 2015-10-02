package warfare;

/****************************************************************
 * Class to simulate attributes specific to an Action Card
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class ActionCard extends Card{
	
	/*  */
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
     * Default constructor for objects of type ACtionCard.
     ***********************************************************/
	public ActionCard()
	{
		super();
		actionFunction = null;
	}
	
	public int[] getActionFunction() {
		return actionFunction;
	}

	public void setActionFunction(int[] actionFunction) {
		this.actionFunction = actionFunction;
	}
	
	/************************************************************
     * Clone an action card.
     * 
     * @param action card to be cloned
     * @return clone of action card (c) 
     ***********************************************************/
	@Override
	public ActionCard cardClone(Card c)
	{
		ActionCard clone = new ActionCard();
		clone.setCost(c.getCost());
		clone.setDescription(c.getDescription());
		clone.setName(c.getName());
		clone.setType(c.getType());	
		clone.setFunction(((ActionCard)c).getActionFunction());
		
		return clone;
	}
	
	/************************************************************
     * Get function of card.
     * 
     * @return function of card
     ***********************************************************/
	public int[] getFunction()
	{
		return actionFunction;
	}
	
	/************************************************************
     * Set card function. 
     * 			Format: [cards, actions, purchase, money]
     * 
     * @param function of card
     ***********************************************************/
	public void setFunction(int[] actionFunction)
	{
		this.actionFunction = actionFunction;
	}
}
