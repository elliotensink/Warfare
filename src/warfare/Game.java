package warfare;

import java.util.*;

public class Game {
	
	private int currentPlayer;
	private int numPlayers;
	private Player[] players;
	private ArrayList<ArrayList<Card>> allCards;
	private Scanner scan;
	private boolean gameFinished = false;
	
	private Game(int numPlayers)
	{
		scan = new Scanner(System.in);
		this.numPlayers = numPlayers;
		players = new Player[numPlayers];
		for(int i = 0;i<numPlayers;i++)
			players[i] = new Player();
		allCards = new ArrayList<ArrayList<Card>>();
		
		PointCard pointCard1 = new PointCard("One VP",2,"Worth 1 Victory Point",1);
		ArrayList<Card> pointCard1Stack = fillCardStack(pointCard1,30);
		allCards.add(pointCard1Stack);
		PointCard pointCard5 = new PointCard("Five VP",6,"Worth 5 Victory Point",5);
		ArrayList<Card> pointCard5Stack = fillCardStack(pointCard5,20);
		allCards.add(pointCard5Stack);
		PointCard pointCard10 = new PointCard("Ten VP",12,"Worth 10 Victory Point",10);
		ArrayList<Card> pointCard10Stack = fillCardStack(pointCard10,10);
		allCards.add(pointCard10Stack);
		
		MoneyCard moneyCard1 = new MoneyCard("One $",0,"Worth 1 $",1);
		ArrayList<Card> moneyCard1Stack = fillCardStack(moneyCard1,50);
		allCards.add(moneyCard1Stack);
		MoneyCard moneyCard2 = new MoneyCard("Two $",0,"Worth 2 $",3);
		ArrayList<Card> moneyCard2Stack = fillCardStack(moneyCard2,40);
		allCards.add(moneyCard2Stack);
		MoneyCard moneyCard3 = new MoneyCard("Three $",0,"Worth 3 $",6);
		ArrayList<Card> moneyCard3Stack = fillCardStack(moneyCard3,30);
		allCards.add(moneyCard3Stack);
		MoneyCard moneyCard4 = new MoneyCard("Four $",0,"Worth 4 $",8);
		ArrayList<Card> moneyCard4Stack = fillCardStack(moneyCard4,20);
		allCards.add(moneyCard4Stack);
		
		System.out.println("Intial Cards: ");
		showBoard();
		System.out.println("Setting up Players...");
		setIntialPlayerCards();
		System.out.println("Game Cards: ");
		showBoard();
		
		currentPlayer = 0;
		
		while(!gameFinished)
		{
			playerTurn();
			checkGameStatus();
		}
		
		scan.close();

	}
	
	//Create stack of card for beginning of the game
	private ArrayList<Card> fillCardStack(Card c,int n)
	{
		ArrayList<Card> cardStack = new ArrayList<Card>();
		for(int i = 0;i<n;i++)
		{
			cardStack.add(c.cardClone(c));
		}
		
		return cardStack;
	}
	
	private void showBoard()
	{
		int count = 1;
		for(ArrayList<Card> stack:allCards)
		{
			System.out.println("(" + count + ") " + "Name: " + stack.get(0) + "  Number of Cards: " + stack.size());
			count++;
		}
	}
	
	private void setIntialPlayerCards()
	{
		for(Player p : players)
		{
			ArrayList<Card> pDeck = new ArrayList<Card>();
			for(int i = 0;i<3;i++)
				pDeck.add(allCards.get(0).remove(0));
			for(int i = 0;i<7;i++)
				pDeck.add(allCards.get(3).remove(0));
			p.setDeck(pDeck);
		}
	}
	
	public ArrayList<Card> getCards(int n){
		ArrayList<Card> c = new ArrayList<Card>();
		
		for(int i=0; i<n; i++){
			c.add(players[currentPlayer].draw());
		}
		 return c;
	}
	
	private void playerTurn()
	{
		Player p = players[currentPlayer];
		System.out.println("Current Player: " + currentPlayer+1);
		
		int actions = 1; 
		int purchases = 1; 
		int choice;
		
		ArrayList<Card> pDeck = getCards(5);
		System.out.println("Player Cards: ");
		displayCards(pDeck);
		System.out.println("____________________");
		choice = Integer.parseInt(scan.nextLine());
		displayOptions();
		playerOption(choice);
		p.getDiscard().addAll(pDeck);
		nextPlayer();
	}
	
	private void nextPlayer()
	{
		currentPlayer++;
		currentPlayer = currentPlayer%numPlayers;//Make sure the currentPlayer stays within the the appropriate range (0 -> numPlayers)
	}
	
	private void displayCards(ArrayList<Card> cards){
		int count = 1;
		for(Card c : cards)
		{
			System.out.println("(" + count + ") " + c);
			count++;
		}
	}
	
	private void displayOptions()
	{
		System.out.println("(1) Play Action");
		System.out.println("(2) Purchase Card");
		System.out.println("(3) End Turn");
		System.out.println("(4) Help");
		System.out.println("(5) Show Gameboard");
	}
	
	private void playerOption(int choice){
		
		switch(choice)
		{
			case 1:
				// Play action
				System.out.println("PlayAction");
				break;
			case 2:
				// Purchase card
				System.out.println("PurchaseCard");
				break;
			case 3:
				// End Turn
				System.out.println("EndTurn");
				break;
			case 4:
				// help
				System.out.println("Help");
				break;
			default:
				// Show gameboard
				System.out.println("ShowGameboard");
				break;
		}
	}
	
	//Checks if game is over or not
	private void checkGameStatus()
	{
		if(allCards.get(2).size()==0)
			gameFinished = true;//Game over
	}
	
	public static void run()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("How many players in the game?");
		int numPlayers = Integer.parseInt(s.nextLine());
		s.close();
		new Game(numPlayers);
	}
	
	public static void main(String[] args)
	{
		run();
	}
}
