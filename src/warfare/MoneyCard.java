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
     * Constructor for objects of type DefenseCard.
     ***********************************************************/
	public MoneyCard()
	{
		super();
		value = 0;
	}
	
	/************************************************************
     * Clone a money card.
     * 
     * @param money card to be cloned
     * @return cloned money card
     ***********************************************************/
	@Override
	public MoneyCard cardClone(Card c){
		MoneyCard clone = new MoneyCard();
		clone.setCost(c.getCost());
		clone.setDescription(c.getDescription());
		clone.setName(c.getName());
		clone.setType(c.getType());		
		clone.setValue(((MoneyCard)c).getValue());
		
		return clone;
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
	
	/************************************************************
     * Get formatted string of card attributes for printing.
     * 
     * @return formatted string of card attributes
     ***********************************************************/
	public String toString(){
		String str = super.toString();
		str += "$" +value + " million";
		return str;
	}
}
