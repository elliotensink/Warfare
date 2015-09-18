package warfare;

public class ActionCard extends Card{
	
	private int[] actionFunction;
	
	public ActionCard(String name, int cost, String description, int cards, int actions, int purchase, int money){
		super(name, cost, description);
		actionFunction = new int[]{cards, actions, purchase, money};
	}
	
	public int[] getFunction(){
		return actionFunction;
	}
}
