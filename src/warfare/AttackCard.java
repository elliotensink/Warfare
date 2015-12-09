package warfare;

import java.io.File;
import java.util.*;

/****************************************************************
 * Class to simulate attributes specific to an Attack Card
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version Friday, December 11, 2015
 ***************************************************************/
public class AttackCard extends Card{
	
	/* How card interacts with game */
    private Interaction inter;
    
    /* Target of card */
    private Target tar;
        
    /* All effects of card */
    private ArrayList<Effect> eff = new ArrayList<Effect>();
    
    /* Values associated with each effect */    
    ArrayList<Integer> vals = new ArrayList<Integer>();
        
    /************************************************************
     * Constructor for objects of type AttackCard.
     * 
     * @param name of card
     * @param cost of card
     * @param description of card
     * @param effect of card
     * @param type of card
     * @param image file
     ***********************************************************/
	public AttackCard(String name, int cost, String description, int[] effect, String type, File img){
		super(name, cost, description, type, img);
	    decode(effect);
	}

	/************************************************************
     * Constructor for objects of type AttackCard.
     ***********************************************************/
	public AttackCard(){
		super();
	}
	
	/************************************************************
     * Clone an attack card.
     * 
     * @param attack card to be cloned
     * @return cloned attack card
     ***********************************************************/
	@Override
	public AttackCard cardClone(Card c){
		AttackCard clone = new AttackCard();
		clone.setCost(c.getCost());
		clone.setDescription(c.getDescription());
		clone.setName(c.getName());
		clone.setType(c.getType());		
		clone.setEffect(((AttackCard)c).getEffect());
		clone.setVals(((AttackCard)c).getVals());
		clone.setImg(c.getImg());
		
		return clone;
	}

	/************************************************************
     * Decode effects array.
     * 
     * @param array of effects
     ***********************************************************/
	private void decode(int[] code) {
        /*
         'Code' is an int array with the layout of 
         ABCXX...XYY...Y
         A - Type of interaction with the game, other players hands or the board of cards
         B - Target, used for the actual target of the effects only really works with hand interaction at this 
         	point have room to easily add things for the board
         C - The number of effects on the card, e.g. 1 if it only makes players discard, 2 if players discard and 
         	you steal from them, used for easier implementation of helper methods
         X - Up to C amount of effects most likely not greater than 3 for our cases
         Y - Up to C amount of values associated with each effect, the first Y is associated with the first X
         
         e.g.
         0020111
         Would be All enemy players discard 1 card and you steal 1 money
         
         10122
         Would make everything on the board cost 2 more for other players
         
         */
        
        switch(code[0]){
            case 0:
                inter = Interaction.HAND;
                decodeHand(code[1]);
                break;
            case 1:
                inter = Interaction.BOARD;
                //may use code[1] in later implementation
                break;
            default:
                //catch if not 0 or 1 and print some error
                break;
        }
        decodeEffect(code);
        decodeValue(code);
    }
    
	/************************************************************
     * Decode hand.
     * 
     * @param type
     ***********************************************************/
    private void decodeHand(int in) {
        switch(in) {
            case 0:
            	tar = Target.ALL;
                break;
            case 1:
                tar = Target.HIGHEST;
                break;
            case 2:
            	tar = Target.RANDOM;
            	break;
            default:
                //print error if get here
            	break;            	
        }
    }
    
    /************************************************************
     * Determine what to do with effect.
     * 
     * @param array of effects
     ***********************************************************/
    private void decodeEffect(int[] arr) {
        int count = arr[2];
        for (int i = 0; i < count; i++) {
            switch (arr[3+i]) {
                case 0:
                    eff.add(Effect.DISCARD);
                    break;
                case 1:
                    eff.add(Effect.STEAL);
                    break;
                case 2:
                    eff.add(Effect.BHIGHER);
                    break;
                case 3:
                    eff.add(Effect.BLOWER);
                    break;
                case 4:
                    eff.add(Effect.BCHANGE);
                    break;
                default:
                    //print error
                    break;
            }
        }
    }

    /************************************************************
     * Determine what to do with value.
     * 
     * @param array of effects
     ***********************************************************/
    private void decodeValue(int[] arr) {
        int count = arr[2];
        int o = 3 + count; //Offset for start of values for each effect
        for (int i = o; i<arr.length; i++) {
            vals.add(arr[i]);
        }
    }
    
    /************************************************************
     * Get values associated with effects.
     * 
     * @return values
     ***********************************************************/
	public ArrayList<Integer> getVals() {
		return vals;
	}

	/************************************************************
     * Set values associated with effects.
     * 
     * @param values
     ***********************************************************/
	public void setVals(ArrayList<Integer> vals) {
		this.vals = vals;
	}

	/************************************************************
     * Get interaction of attack card.
     * 
     * @return interaction
     ***********************************************************/
	public Interaction getInter() {
		return inter;
	}

	/************************************************************
     * Set interaction of attack card.
     * 
     * @param interaction
     ***********************************************************/
	public void setInter(Interaction inter) {
		this.inter = inter;
	}

	/************************************************************
     * Get target of attack card.
     * 
     * @return target
     ***********************************************************/
	public Target getTar() {
		return tar;
	}

	/************************************************************
     * Set target of attack card.
     * 
     * @param target
     ***********************************************************/
	public void setTar(Target tar) {
		this.tar = tar;
	}

	/************************************************************
     * Get effects of attack card.
     * 
     * @return ArrayList of effects
     ***********************************************************/
	public ArrayList<Effect> getEffect() {
		return eff;
	}

	/************************************************************
     * Set effects of attack card.
     * 
     * @param ArrayList of effects
     ***********************************************************/
	public void setEffect(ArrayList<Effect> eff) {
		this.eff = eff;
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
}
