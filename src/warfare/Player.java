package warfare;

import java.util.*;

/****************************************************************
 * Class for storage of player information
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class Player {
	
	/* Cards in hand and discard pile */
	private ArrayList<Card> deck, discard;
	
	/* Current points */
	private int points;
	
	/************************************************************
     * Constructor for objects of type Player.
     ***********************************************************/
	public Player()
	{
		deck = new ArrayList<Card>();
		discard = new ArrayList<Card>();
		points = 0;
	}
	
	/************************************************************
     * Get current deck.
     * 
     * @return players deck
     ***********************************************************/
	public ArrayList<Card> getDeck()
	{
		return deck;
	}
	
	/************************************************************
     * Set current deck.
     * 
     * @param players deck
     ***********************************************************/
	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}

	/************************************************************
     * Draw a card.
     * 
     * @return drawn card
     ***********************************************************/
	public Card draw()
	{
		return deck.remove(0);
	}
	
	/************************************************************
     * Get discard pile.
     * 
     * @return discard pile
     ***********************************************************/
	public ArrayList<Card> getDiscard()
	{
		return discard;
	}
}
