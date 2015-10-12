package warfare;

import java.util.*;

/****************************************************************
 * Class for simulation of Warfare card game.
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class Game {

	Random rand = new Random(69);
	
	/* Current player (position in array) and total number of players */
	private int currentPlayer, numPlayers;

	/* Array to store players */
	private Player[] players;

	/* Array list containing all game cards */
	private ArrayList<ArrayList<Card>> allCards;

	/* Array list containing one copy of each unique card */
	ArrayList<Card> referenceDeck;

	/* Scanner for user input */
	private Scanner scan;

	/* Boolean value to determine when game is over */
	private boolean gameFinished;

	/************************************************************
	 * Constructor for objects of type Game.
	 * 
	 * @param number of players
	 ***********************************************************/
	private Game(int numPlayers)
	{
		scan = new Scanner(System.in);
		this.numPlayers = numPlayers;
		players = new Player[numPlayers];
		for(int i = 0;i<numPlayers;i++)
			players[i] = new Player();
		allCards = new ArrayList<ArrayList<Card>>();
		gameFinished = false;
		createDeck();
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
		endGame();

		scan.close();

	}

	/************************************************************
	 * Generate stack of cards to start game.
	 ***********************************************************/
	private void createDeck(){

			// Adding point cards
		PointCard pointCard1 = new PointCard("One VP",2,"Worth 1 Victory Point",1,"point");
		ArrayList<Card> pointCard1Stack = fillCardStack(pointCard1,25);
		allCards.add(pointCard1Stack);
		PointCard pointCard5 = new PointCard("Five VP",6,"Worth 5 Victory Point",5,"point");
		ArrayList<Card> pointCard5Stack = fillCardStack(pointCard5,10);
		allCards.add(pointCard5Stack);
		PointCard pointCard10 = new PointCard("Ten VP",12,"Worth 10 Victory Point",10,"point");
		ArrayList<Card> pointCard10Stack = fillCardStack(pointCard10,5);
		allCards.add(pointCard10Stack);

			// Adding money cards
		MoneyCard moneyCard1 = new MoneyCard("One $",2,"Worth 1 $",1,"money");
		ArrayList<Card> moneyCard1Stack = fillCardStack(moneyCard1,50);
		allCards.add(moneyCard1Stack);
		MoneyCard moneyCard2 = new MoneyCard("Two $",3,"Worth 2 $",3,"money");
		ArrayList<Card> moneyCard2Stack = fillCardStack(moneyCard2,40);
		allCards.add(moneyCard2Stack);
		MoneyCard moneyCard3 = new MoneyCard("Three $",6,"Worth 3 $",6,"money");
		ArrayList<Card> moneyCard3Stack = fillCardStack(moneyCard3,30);
		allCards.add(moneyCard3Stack);
		MoneyCard moneyCard4 = new MoneyCard("Four $",8,"Worth 4 $",8,"money");
		ArrayList<Card> moneyCard4Stack = fillCardStack(moneyCard4,20);
		allCards.add(moneyCard4Stack);
		
			// Adding action cards
		ActionCard actionCard1 = new ActionCard("A one", 2, "des", 2, 0, 2, 0, "action");
		ArrayList<Card> actionCard1Stack = fillCardStack(actionCard1, 50);
		allCards.add(actionCard1Stack);
		ActionCard actionCard2 = new ActionCard("A two", 4, "des", 2, 2, 0, 2, "action");
		ArrayList<Card> actionCard2Stack = fillCardStack(actionCard2, 40);
		allCards.add(actionCard2Stack);
		ActionCard actionCard3 = new ActionCard("A three", 6, "des", 2, 2, 2, 0, "action");
		ArrayList<Card> actionCard3Stack = fillCardStack(actionCard3, 30);
		allCards.add(actionCard3Stack);
		ActionCard actionCard4 = new ActionCard("A four", 10, "des", 2, 2, 2, 4, "action");
		ArrayList<Card> actionCard4Stack = fillCardStack(actionCard4, 20);
		allCards.add(actionCard4Stack);
		
		referenceDeck = new ArrayList<Card>();
		referenceDeck.add(pointCard1);
		referenceDeck.add(pointCard5);
		referenceDeck.add(pointCard10);
		referenceDeck.add(moneyCard1);
		referenceDeck.add(moneyCard2);
		referenceDeck.add(moneyCard3);
		referenceDeck.add(moneyCard4);
		referenceDeck.add(actionCard1);
		referenceDeck.add(actionCard2);
		referenceDeck.add(actionCard3);
		referenceDeck.add(actionCard4);
		
	}

	/************************************************************
	 * Adding cards to card stack.
	 * 
	 * @param card to add, number of cards to add
	 ***********************************************************/
	private ArrayList<Card> fillCardStack(Card c,int n)
	{
		ArrayList<Card> cardStack = new ArrayList<Card>();
		for(int i = 0;i<n;i++)
		{
			cardStack.add(c.cardClone(c));
		}

		return cardStack;
	}

	/************************************************************
	 * Print cards to user.
	 ***********************************************************/
	private void showBoard()
	{
		int count = 1;
		for(Card c: referenceDeck)
		{
			System.out.println("(" + count + ") " + "Name: " + c.getName() + " Cost: " + c.getCost() + 
					" Number of Cards: " + allCards.get(count-1).size());
			count++;
		}
		System.out.println("(" + count + ") " + "Back to Menu");
	}

	/************************************************************
	 * Deal initial cards to players.
	 ***********************************************************/

	private void setIntialPlayerCards()
	{
		for(Player p : players)
		{
			ArrayList<Card> pDeck = new ArrayList<Card>();
			for(int i = 0;i<3;i++)
				pDeck.add(allCards.get(0).remove(0));
			for(int i = 0;i<7;i++)
				pDeck.add(allCards.get(3).remove(0));
			Collections.shuffle(pDeck, new Random());
			p.setDeck(pDeck);
			p.drawCards(5);
		}
	}
	/************************************************************
	 * Simulate one turn for current player.
	 ***********************************************************/
	private void playerTurn()
	{
		Player p = players[currentPlayer];
		System.out.println("*******************Next Players Turn*******************");
		System.out.println("Current Player: " + (currentPlayer+1));

		int actions = 1; 
		int purchases = 1; 
		int choice = 0;

		ArrayList<Card> pDeck = p.getHand();
		while(actions > 0 || purchases > 0)
		{
			System.out.println("Player Cards: ");
			displayCards(pDeck);
			System.out.println("User money: " + p.getCurrentMoney());
			System.out.println("Actions: " + actions);
			System.out.println("Purchase: " + purchases);
			System.out.println("____________________");
			System.out.println("Player Options");
			displayOptions();
			choice = scan.nextInt();
			switch(choice)
			{
			case 1:
				// Play action
				System.out.println("*PlayAction*");
				System.out.println("Choose which card to play: ");
				displayCards(players[currentPlayer].getHand());
				int add[] = action();
				actions += add[0];
				purchases += add[1];
				actions--;
				break;
			case 2:
				// Purchase card
				System.out.println("*PurchaseCard*");
				System.out.println("User money: " + p.getCurrentMoney());
				showBoard();
				System.out.println("Enter card choice: ");
				int purchaseChoice = scan.nextInt();
				if(purchaseChoice <= referenceDeck.size())
				{
					Card cardChoice = purchaseCard(purchaseChoice);
					while(cardChoice == null)
					{
						System.out.println("User money: " + p.getCurrentMoney());
						System.out.println("Enter card choice: ");
						purchaseChoice = scan.nextInt();
						if(purchaseChoice >= referenceDeck.size())
						{
							cardChoice = purchaseCard(purchaseChoice);
						}
						else
							break;
					}
					players[currentPlayer].addPurchase(cardChoice);
					purchases--;
				}
				break;
			case 3:
				// End Turn
				System.out.println("*EndTurn*");
				actions = 0;
				purchases = 0;
				break;
			case 4:
				// help
				System.out.println("*Help*");
				break;
			default:
				// Show gameboard
				System.out.println("*ShowGameboard*");
				break;
			}
		}
		p.setCurrentMoney(0);
		p.discard();
		nextPlayer();
	}

	/************************************************************
	 * Simulate the purchase of a card.
	 * 
	 * @param card number
	 * @return card purchased
	 ***********************************************************/
	private Card purchaseCard(int cardNum){
		Player p = players[currentPlayer];
		int cost = allCards.get(cardNum-1).get(0).getCost();
		if(cost > p.getCurrentMoney())
		{
			System.out.println("Sorry, you're broke, choose another card.");
			return null;
		}
		p.setCurrentMoney(p.getCurrentMoney()-cost);
		return allCards.get(cardNum-1).remove(0);
	}
	
	private void pAction(Player p) {

		displayCards(p.getHand());
		int pChoice = scan.nextInt();
		if (!(checkIfAction(pChoice-1, p))) {
			System.out.println("That is not a playabe card please select a playable card");
			pAction(p);
			return;
		} else {
			Card toPlay = p.getHand().get(pChoice-1);
			if (toPlay instanceof AttackCard) {
				p.getDiscard().add(p.getHand().remove(pChoice-1));
				playAttack(toPlay);
			} else {
				//playAction(toPlay);
			}
		}
	}
	
	private void defenseActivate(ArrayList<DefenseCard> list, int pNum) {
		//if (list.size() == 1) {
			DefenseCard d = list.get(0);
			defTarget(d, pNum);
			ArrayList<Card> hand = players[pNum].getHand();
			for (Card c :hand)
				if (c instanceof DefenseCard) {
					if (c.getName().equals(d.getName()))
						players[pNum].getDiscard().add(d);
					    players[pNum].getHand().remove(c);
				}
			
		//} 
//		else {
//			System.out.println("Player " + pNum + " please select the Defense Card to use");
//		}
		
		
	}
	
	private void executeEffect(Effect e, int val, Player target, Player source) {
		ArrayList<Player> temp = new ArrayList<Player>();
		executeEffect(e, val, temp, source);
	}
	
	private void executeEffect(ArrayList<Effect> eList, int[] valList, ArrayList<Player> target, Player source) {
		for (int i = 0; i < eList.size(); i++) {
			executeEffect(eList.get(i), valList[i], target, source);
		}
	}
	
	private void executeEffect(ArrayList<Effect> eList, int[] valList, Player target, Player source) {
		ArrayList<Player> temp = new ArrayList<Player>();
		executeEffect(eList, valList, temp, source);
	}
	private void executeEffect(Effect e, int val, ArrayList<Player> target, Player source) {
		switch(e) {
		case DISCARD:
			for (Player p : target) {
				ArrayList<Card> newHand = p.getHand();
				int max = newHand.size();
				for (int i = 0; i<val; i++)
					newHand.remove(rand.nextInt() % max);//Random Value no bigger than max
				p.calcMoney();
			}
			
			break;
			
		case STEAL:
			source.setCurrentMoney(source.getCurrentMoney() + val);
			for (Player p: target)
				p.setCurrentMoney(p.getCurrentMoney() - val);
			break;
			
		case BHIGHER:
			//Raise all cost of cards on the board by val
			break;
		
		case BLOWER:
			//Raise all cost of cards on he board by val
			break;
		
		case BCHANGE:
			break;
		}
	}
	
	private void defTarget(DefenseCard card, int pNum) {
		Target t = card.getTar();
		//ArrayList<Player> tar = new ArrayList<Player>();
		switch (t){
		case SOURCE:
		//	tar.add(players[currentPlayer]);
			executeEffect(card.getEff(), card.getVal(), players[currentPlayer], players[pNum]);
			break;
		case SELF:
	//		tar.add(players[pNum]);
			executeEffect(card.getEff(), card.getVal(), players[pNum], players[pNum]);
			break;
		default:
			//Print error, shouldn't get here
			break;
			
		}
	}
	
	private boolean checkDef(Player p, int pNum) {
		ArrayList<Card> handToCheck = p.getHand();
		ArrayList<DefenseCard> dList = new ArrayList<DefenseCard>();
		for (Card c : handToCheck) {
			if (c instanceof DefenseCard) {
				DefenseCard d = (DefenseCard)c;
				dList.add(d);
			}
		}
		if (dList.size() > 0) {
			defenseActivate(dList, pNum);
			return true;
		}
		return false;
	}
	
	private void attkHand(AttackCard card) {
		Target t = card.getTar();
		ArrayList<Effect> eList = card.getEff();
		int[] valList = card.vals;
		Player p = players[currentPlayer];
		switch(t) {
		case ALL:
			ArrayList<Player> actualTar = new ArrayList<Player>();
			for (int i=0; i< players.length; i++) {
				if (i != currentPlayer)
					if (!checkDef(players[i], i))
							actualTar.add(players[i]);
			}
			executeEffect(eList, valList, actualTar, p);
//			ArrayList<Effect> attkEff = card.getEff();
//			int[] attkVal = card.vals;
//			for (int i = 0; i < attkEff.size(); i++) 
//				executeEffect(attkEff.get(i), attkVal[i], actualTar, players[currentPlayer]);
			break;
		case RANDOM:
			//randomly select player store to randP
			int randP = rand.nextInt() % players.length;
			if (!checkDef(players[randP], randP))
				executeEffect(eList, valList, players[randP], p);
			break;
			
		case HIGHEST:
			int tar = -1;
			int max = -1;
			for (int i = 0; i < players.length; i++) {
				if (players[i].getPoints() > max && i != currentPlayer) {
					tar = i;
					max = players[i].getPoints();
				}
			}
			if (!checkDef(players[tar], tar)) {
				executeEffect(eList, valList, players[tar], p);
			}
			break;
			
		default:
			break;
		}
	}
	
	private void playAttack(Card toPlay) {
		AttackCard c = (AttackCard)toPlay;
		Interaction intr = c.getInter();
		
		switch (intr) {
		case HAND:
			attkHand(c);
			break;
			
		case BOARD:
			break;
		}
	}
	
	private Boolean checkIfAction(int pChoice, Player p) {
		return p.getHand().get(pChoice).isActionable();
	}
	
	
	
	
	/************************************************************
     * Move to next player.
     ***********************************************************/
	private void nextPlayer()
	{
		currentPlayer++;
		currentPlayer = currentPlayer%numPlayers;//Make sure the currentPlayer stays within the the appropriate range (0 -> numPlayers)
	}

	/************************************************************
	 * Print cards to user.
	 * 
	 * @param cards to be printed
	 ***********************************************************/
	private void displayCards(ArrayList<Card> cards){
		int count = 1;
		for(Object c : cards)
		{
			
			System.out.println("(" + count + ") " + (Card)c);
			count++;
		}
	}

	/************************************************************
	 * Print game options to user.
	 ***********************************************************/
	private void displayOptions()
	{
		System.out.println("(1) Play Action");
		System.out.println("(2) Purchase Card");
		System.out.println("(3) End Turn");
		System.out.println("(4) Help");
		System.out.println("(5) Show Gameboard");
	}
	/************************************************************
	 * Elliot - moved this method to be handled internally within playerTurn()
	 * @param players choice
	 ***********************************************************/


	/************************************************************
	 * Determine if game is over or not.
	 ***********************************************************/

	private void checkGameStatus()
	{
		if(allCards.get(2).size()==0)
			gameFinished = true;//Game over
	}
	
	/************************************************************
	 * Determine if game is over or not.
	 ***********************************************************/
	private int endGame()
	{
		int maxScore = 0;
		int playerScore = 0;
		int winningPlayer = 0;
		int index = 1;
		for(Player p: players)
		{

			playerScore = p.calcPoints();
			System.out.println("Player: " + index + " Score: " + playerScore);
			if(playerScore > maxScore)
				winningPlayer = index;
			index++;
		}
		return winningPlayer;
	}
	
	/************************************************************
	 * 
	 ***********************************************************/
	public int[] action(){
		int c = scan.nextInt();
		ActionCard ac = (ActionCard) players[currentPlayer].getCard(c-1);
		int action[] = ac.getActionFunction();
		players[currentPlayer].drawCards(action[0]);
		players[currentPlayer].addMoney(action[3]);
		int ret[] = {action[1], action[2]};
		
		return ret;		
	}

	/************************************************************
	 * Run game play.
	 ***********************************************************/
	public static void run()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("How many players in the game?");
		int numPlayers = s.nextInt();
		new Game(numPlayers);
		s.close();
	}

	/************************************************************
	 * Get current player.
	 * 
	 * @return current player
	 ***********************************************************/
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	/************************************************************
	 * Set current player.
	 * 
	 * @param current player
	 ***********************************************************/
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/************************************************************
	 * Get number of players.
	 * 
	 * @return number of player
	 ***********************************************************/
	public int getNumPlayers() {
		return numPlayers;
	}

	/************************************************************
	 * Set number of players.
	 * 
	 * @param number of player
	 ***********************************************************/
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	/************************************************************
	 * Get players.
	 * 
	 * @return array of players
	 ***********************************************************/
	public Player[] getPlayers() {
		return players;
	}

	/************************************************************
	 * Set players.
	 * 
	 * @param array of players
	 ***********************************************************/
	public void setPlayers(Player[] players) {
		this.players = players;
	}

	/************************************************************
	 * Check if game is over.
	 * 
	 * @return true if finished, false if not
	 ***********************************************************/
	public boolean isGameFinished() {
		return gameFinished;
	}

	/************************************************************
	 * Set game as finished/not finished.
	 * 
	 * @param true if finished, false if not
	 ***********************************************************/
	public void setGameFinished(boolean gameFinished) 
	{
		this.gameFinished = gameFinished;
	}

	/************************************************************
	 * Main function for running program.
	 ***********************************************************/

	public static void main(String[] args)
	{
		//Game g = new Game();
		run();
	}
}
