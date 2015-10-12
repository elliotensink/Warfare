package warfare;

/****************************************************************
 * Class to simulate attributes specific to a Defense Card
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class DefenseCard extends Card
{

	Target tar;
	Effect eff;
	int val;
	
	/************************************************************
     * Constructor for objects of type DefenseCard.
     * 
     * @param name of card, cost of card, description of card
     ***********************************************************/
	public DefenseCard(String name, int cost, String description, String type, int[] code){
		super(name, cost, description, type);
		decode(code);
		val = code[2];
	}
	


	public DefenseCard(){
		super();
	}
	
	/************************************************************
     * Get formatted string of card attributes for printing.
     * 
     * @return formatted string of card attributes
     ***********************************************************/
	public String toString(){
		String str = super.toString();
		return str;
	}
	
	/************************************************************
     * Clone an action card.
     * 
     * @param action card to be cloned
     * @return clone of action card (c) 
     ***********************************************************/
	@Override
	public DefenseCard cardClone(Card c){
		DefenseCard clone = new DefenseCard();
		clone.setCost(c.getCost());
		clone.setDescription(c.getDescription());
		clone.setName(c.getName());
		clone.setType(c.getType());		
		return clone;
	}
	
	private void decode(int[] code) {
		switch(code[0]) {
		case 0:
			tar = Target.SOURCE;
			break;
		case 1:
			tar = Target.SELF;
			break;
		}
		decodeEffect(code[1]);
	}
	
	private void decodeEffect(int i) {
		switch(i) {
		case 0:
			eff = Effect.DISCARD;
			break;
		case 1:
			eff = Effect.BLOWER;
			break;
		}
		
	}
	
	public Target getTar() {
		return tar;
	}

	public Effect getEff() {
		return eff;
	}

	public int getVal() {
		return val;
	}
	
	
}
