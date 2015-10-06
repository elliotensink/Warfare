package warfare;

/****************************************************************
 * Class to simulate attributes specific to a Money Card
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class MoneyCard extends Card{

	/* Amount of money card is worth */
	private int value;
	
	/************************************************************
     * Constructor for objects of type MoneyCard with input.
     * 
     * @param name of card, cost of card, description of card,
     * 			value of card
     ***********************************************************/
	public MoneyCard(String name, int cost, String description, int value, String type)
	{
		super(name, cost, description, type);
		this.value = value;
	}
	
	/************************************************************
     * Default constructor for objects of type DefenseCard.
     ***********************************************************/
	public MoneyCard()
	{
		super();
		value = 0;
	}
	
	/************************************************************
     * Get formatted string of card attributes for printing.
     * 
     * @return formatted string of card attributes
     ***********************************************************/
	public String toString()
	{
		String str = super.toString() + "," + value;
		return str;
	}
	
	/************************************************************
     * Create clone of a card.
     * 
     * @param card to be cloned
     * @return cloned card
     ***********************************************************/
	public MoneyCard cardClone(MoneyCard c)
	{
		MoneyCard newC = new MoneyCard();
		newC.setName(new String(c.getName()));
		newC.setCost(new Integer(c.getCost()));
		newC.setType(c.getType());
		newC.setDescription(new String(c.getDescription()));
		newC.setValue(new Integer(c.getValue()));
		return newC;
	}

	/************************************************************
     * Get value of a card.
     * 
     * @return value of the card
     ***********************************************************/
	public int getValue() {
		return value;
	}

	/************************************************************
     * Set value of a card.
     * 
     * @param value of the card
     ***********************************************************/
	public void setValue(int value) {
		this.value = value;
	}
}
