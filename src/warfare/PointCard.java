package warfare;

/****************************************************************
 * Class to simulate attributes specific to a Point Card
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class PointCard extends Card{
	
	/* Number of points */
	private int point;
	
	/************************************************************
     * Constructor for objects of type PointCard with input.
     * 
     * @param name of card, cost of card, description of card,
     * 			point value
     ***********************************************************/
	public PointCard(String name, int cost, String description, int point)
	{
		super(name, cost, description);
		this.point = point;
	}
	
	/************************************************************
     * Default constructor for objects of type DefenseCard.
     ***********************************************************/
	public PointCard()
	{
		super();
		point = 0;
	}
	
	/************************************************************
     * Create a clone of a card.
     * 
     * @param card to be cloned
     * @return cloned cards
     ***********************************************************/
	public PointCard cardClone(PointCard c)
	{
		PointCard newC = new PointCard();
		newC.setName(new String(c.getName()));
		newC.setCost(new Integer(c.getCost()));
		newC.setDescription(new String(c.getDescription()));
		newC.setPoint(new Integer(c.getPoint()));
		return newC;
	}

	/************************************************************
     * Get cards point value.
     * 
     * @return point value
     ***********************************************************/
	public int getPoint() 
	{
		return point;
	}

	/************************************************************
     * Set cards point value.
     * 
     * @param point value
     ***********************************************************/
	public void setPoint(int point) 
	{
		this.point = point;
	}
	
	
}
