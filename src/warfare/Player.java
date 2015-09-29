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
	
	public void drawCards(int num){
		if(deck.size() < num){
			Collections.shuffle(discard, new Random());
			deck.addAll(discard);
			discard.clear();
		}
		
		for(int i=0; i<num; i++){
			hand.add(deck.remove(0));
		}
		calcMoney();
	}
	
	public void calcMoney(){
		int sum = currentMoney;
		for(Object crd: hand){
			if(crd instanceof MoneyCard){
				sum += ((MoneyCard)crd).getValue();
				System.out.println(sum);
			}
		}
		currentMoney = sum;
	}
	
	public int getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(int currentMoney) {
		this.currentMoney = currentMoney;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setDiscard(ArrayList<Card> discard) {
		this.discard = discard;
	}

	public void discard(){
		discard.addAll(hand);
		hand.clear();
		drawCards(5);
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
