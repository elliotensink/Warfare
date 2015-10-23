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
	public PointCard(String name, int cost, String description, int point, String type, String img)
	{
		super(name, cost, description, type, img);
		this.point = point;
	}
	
	/************************************************************
     * Constructor for objects of type DefenseCard.
     ***********************************************************/
	public PointCard()
	{
		super();
		point = 0;
	}
	
	/************************************************************
     * Create clone of a defense card.
     * 
     * @param defense card to be cloned
     * @return cloned defense card
     ***********************************************************/
	@Override
	public PointCard cardClone(Card c){
		PointCard clone = new PointCard();
		clone.setCost(c.getCost());
		clone.setDescription(c.getDescription());
		clone.setName(c.getName());
		clone.setType(c.getType());		
		clone.setPoint(((PointCard)c).getPoint());
		clone.setImg(c.getImg());
		
		return clone;
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
	
	/************************************************************
     * Get formatted string of card attributes for printing.
     * 
     * @return formatted string of card attributes
     ***********************************************************/
	public String toString(){
		String str = super.toString();
		str += point + " Point(s), File: " + this.getImg();
		return str;
	}
}
