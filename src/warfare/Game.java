package warfare;

import java.util.*;

public class Game {
	
	private int currentPlayer;
	private Player[] players;
	private ArrayList<Card> cards;
	private Scanner scan;
	
	private Game(int numPlayers){
		
		players = new Player[numPlayers];
		cards = new ArrayList<Card>();
		currentPlayer = 0;
		scan = new Scanner(System.in);
	}
	
	private void fillBoard(){
		
	}
	
	public ArrayList<Card> getCards(int n){
		ArrayList<Card> c = new ArrayList<Card>();
		
		for(int i=0; i<n; i++){
			c.add(players[currentPlayer].draw());
		}
		 return c;
	}
	
	private void playerTurn(){
		Player p = players[currentPlayer];
		int actions = 1, purchases = 1, choice;
		
		ArrayList<Card> pDeck = getCards(5);
		displayCards(pDeck);
		choice = Integer.parseInt(scan.nextLine());
		action(choice);
		p.getDiscard().addAll(pDeck);
		
	}
	
	private void displayCards(ArrayList<Card> c){
		for(Card i: c){
			System.out.println(i);
		}
	}
	
	private void action(int choice){
		
		switch(choice){
			case 1:
				// Play action
				break;
			case 2:
				// Purchase card
				break;
			case 3:
				// End Turn
				break;
			case 4:
				// help
				break;
			default:
				// Show gameboard
				break;
		}
	}
}
