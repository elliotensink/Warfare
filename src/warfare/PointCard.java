package warfare;

public class PointCard extends Card{
	
	private int point;
	
	public PointCard(String name, int cost, String description, int point)
	{
		super(name, cost, description);
		this.point = point;
	}
	
	public PointCard()
	{
		super();
		point = 0;
	}
	
	public PointCard cardClone(PointCard c)
	{
		PointCard newC = new PointCard();
		newC.setName(new String(c.getName()));
		newC.setCost(new Integer(c.getCost()));
		newC.setDescription(new String(c.getDescription()));
		newC.setPoint(new Integer(c.getPoint()));
		return newC;
	}

	public int getPoint() 
	{
		return point;
	}

	public void setPoint(int point) 
	{
		this.point = point;
	}
	
	
}
