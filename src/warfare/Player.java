package warfare;

import java.util.*;

public class Player {
	
	private ArrayList<Card> deck, discard;
	private int points;
	
	public Player()
	{
		deck = new ArrayList<Card>();
		discard = new ArrayList<Card>();
		points = 0;
	}
	
	public ArrayList<Card> getDeck()
	{
		return deck;
	}
	
	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}

	public Card draw()
	{
		return deck.remove(0);
	}
	
	public ArrayList<Card> getDiscard()
	{
		return discard;
	}
}
