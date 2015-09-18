package warfare;

import java.util.*;

public class Player {
	
	private ArrayList<Card> deck, discard;
	private int points;
	
	private Player(){
		deck = new ArrayList<Card>();
		discard = new ArrayList<Card>();
		points = 0;
	}
	
	private void fillCards(){
		// Add initial cards
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	public Card draw(){
		return deck.remove(0);
	}
	
	public ArrayList<Card> getDiscard(){
		return discard;
	}
}
