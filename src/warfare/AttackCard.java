package warfare;

public class AttackCard extends Card{
    private enum Interaction {
        HAND, BOARD;
    }
    
    private enum Target {
        ALL, HIGHEST, RANDOM;
    }
    
    private enum Effect {
        DISCARD, STEAL, BHIGHER, BLOWER, BCHANGE;
    }
    
    

    private Interaction inter;
    private Target tar;
    private int efcNum;
    private ArrayList<Effect> eff = new ArrayList<Effect>();
    private ArrayList<int> amount = new ArrayList<int>();
    
	public AttackCard(String name, int cost, String description, int[] effect){
		super(name, cost, description);
        cardEffect = effect;
	}
    
    private decode(int[] code) {
        /*
         'Code' is an int array with the layout of 
         ABCXX...XYY...Y
         A - Type of interaction with the game, other players hands or the board of cards
         B - Target, used for the actual target of the effects only really works with hand interaction at this point have room to easily add things for the board
         C - The number of effects on the card, e.g. 1 if it only makes players discard, 2 if players discard and you steal from them, used for easier implementation of helper methods
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
                inter = HAND;
                tar = decodeHand(code[1]);
                break;
            case 1:
                inter = BOARD;
                //may use code[1] in later implementation
                break;
            default:
                //catch if not 0 or 1 and print some error
                break;
        }
        decodeEffect(code);
        decodeValue(code);
        
        
        
    }
    
    private Target decodeHand(int in) {
        switch(in) {
            case 0:
                return ALL;
                break;
            case 1:
                return HIGHEST;
                break;
            case 2:
                return RANDOM;
                break;
            default:
                //print error if get here
                break;
        }
    }
    
    private void decodeEffect(int[] arr) {
        int count = arr[2];
        for (int i = 0; i < count; i++) {
            switch (code[3+i]) {
                case 0:
                    eff.add(DISCARD);
                    break;
                case 1:
                    eff.add(STEAL);
                    break;
                case 2:
                    eff.add(BHIGHER);
                    break;
                case 3:
                    eff.add(BLOWER);
                    break;
                case 4:
                    eff.add(BCHANGE);
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
            values.add(arr[o+i]);
        }
    }


}
