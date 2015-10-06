package warfare;

import java.util.*;

/****************************************************************
 * Class for storage of player information
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class Player {
	
	/* Cards in deck, discard pile, and in hand */
	private ArrayList<Card> deck, discard, hand;
	
	/* Current points */
	private int points, currentMoney;
	
	/************************************************************
     * Constructor for objects of type Player.
     ***********************************************************/
	public Player()
	{
		deck = new ArrayList<Card>();
		discard = new ArrayList<Card>();
		hand = new ArrayList<Card>();
		points = 0;
		currentMoney = 0;
	}
	
	/************************************************************
     * Draw cards.
     * 
     * @param number of cards to draw
     ***********************************************************/
	public void drawCards(int num){
		if(deck.size() < num){
			Collections.shuffle(discard, new Random());
			deck.addAll(discard);
			discard.clear();
		}
		
		for(int i=0; i<num; i++){
			hand.add(deck.remove(0));
		}
		System.out.println(hand);
		calcMoney();
	}
	

	public void calcMoney(){
		int sum = currentMoney;
		for(Card crd: hand){
			if(crd instanceof MoneyCard)
			{
				sum += ((MoneyCard)crd).getValue();
				//System.out.println(sum);
			}
		}
		currentMoney = sum;
	}
	
	/************************************************************
     * Discard current hand and draw new cards.
     ***********************************************************/
	public void discard(){
		discard.addAll(hand);
		hand.clear();
		drawCards(5);
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
     * Get money in current hand.
     * 
     * @return amount of money
     ***********************************************************/
	public int getCurrentMoney() {
		return currentMoney;
	}

	/************************************************************
     * Set money in current hand.
     * 
     * @param amount of money
     ***********************************************************/
	public void setCurrentMoney(int currentMoney) {
		this.currentMoney = currentMoney;
	}

	/************************************************************
     * Get current cards.
     * 
     * @return current cards
     ***********************************************************/
	public ArrayList<Card> getHand() {
		return hand;
	}

	/************************************************************
     * Set current cards.
     * 
     * @param current cards
     ***********************************************************/
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	/************************************************************
     * Get points in current hand.
     * 
     * @return number of points
     ***********************************************************/
	public int getPoints() {
		return points;
	}

	/************************************************************
     * Set points in current hand.
     * 
     * @param number of points
     ***********************************************************/
	public void setPoints(int points) {
		this.points = points;
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
     * Get current discard pile.
     * 
     * @return discard pile
     ***********************************************************/
	public ArrayList<Card> getDiscard()
	{
		return discard;
	}
	
	/************************************************************
     * Get current discard pile.
     * 
     * @return discard pile
     ***********************************************************/
	public void setDiscard(ArrayList<Card> discard) {
		this.discard = discard;
	}
}
