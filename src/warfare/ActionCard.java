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
	public ActionCard(String name, int cost, String description, int cards, int actions, int purchase, int money)
	{
		super(name, cost, description);
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
	
	/************************************************************
     * Clone an action card.
     * 
     * @param action card to be cloned
     * @return clone of action card (c) 
     ***********************************************************/
	public ActionCard cardClone(ActionCard c)
	{
		ActionCard newC = new ActionCard();
		newC.setName(new String(c.getName()));
		newC.setCost(new Integer(c.getCost()));
		newC.setDescription(new String(c.getDescription()));
		int[] actionFunction = new int[4];
		actionFunction[0] = new Integer(c.getFunction()[0]);
		actionFunction[1] = new Integer(c.getFunction()[1]);
		actionFunction[2] = new Integer(c.getFunction()[2]);
		actionFunction[3] = new Integer(c.getFunction()[3]);
		newC.setFunction(actionFunction);
		return newC;
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
