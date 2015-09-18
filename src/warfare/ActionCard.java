package warfare;

public class ActionCard extends Card{
	
	private int[] actionFunction;
	
	public ActionCard(String name, int cost, String description, int cards, int actions, int purchase, int money)
	{
		super(name, cost, description);
		actionFunction = new int[]{cards, actions, purchase, money};
	}
	
	public ActionCard()
	{
		super();
		actionFunction = null;
	}
	
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
	
	public int[] getFunction()
	{
		return actionFunction;
	}
	
	public void setFunction(int[] actionFunction)
	{
		this.actionFunction = actionFunction;
	}
}
