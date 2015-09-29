package warfare;

import java.util.*;

/****************************************************************
 * Class to simulate attributes specific to an Attack Card
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class AttackCard extends Card{
    
	public enum Target {
		ALL, HIGHEST, RANDOM;
	}

	public enum Interaction {
		HAND, BOARD;
	}
	
	public enum Effect {
		DISCARD, STEAL, BHIGHER, BLOWER, BCHANGE;
	}
	
    private Interaction inter;
    private Target tar;
    private ArrayList<Effect> eff = new ArrayList<Effect>();
    private ArrayList<Integer> amount = new ArrayList<Integer>();
    
    /************************************************************
     * Constructor for objects of type AttackCard.
     * 
     * @param name of card, cost of card, description of card
     ***********************************************************/
	public AttackCard(String name, int cost, String description, int[] effect, String type){
		super(name, cost, description, type);
	        decode(effect);
	}
    
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
         Y - Up to C amount of values assosiated with each effect, the first Y is associated with the first X
         
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

    private void decodeValue(int[] arr) {
        int count = arr[2];
        int o = 3 + count; //Offset for start of values for each effect
        for (int i = 0; i<count; i++) {
            amount.add(arr[o+i]);
        }
    }


}
