package warfare;

import java.util.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

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
	ArrayList<ArrayList<Card>> allCards;

	/* Array list containing one copy of each unique card */
	ArrayList<Card> referenceDeck;

	/* Scanner for user input */
	private Scanner scan;

	/* Boolean value to determine when game is over */
	boolean gameFinished;
	
	int actions, purchases;

	/************************************************************
	 * Constructor for objects of type Game.
	 * 
	 * @param number of players
	 ***********************************************************/
	Game(int numPlayers)
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
		//showBoard();
		System.out.println("Setting up Players...");
		setIntialPlayerCards();
		System.out.println("Game Cards: ");
		showBoard();
		currentPlayer = 0;
		
		
	}

	/************************************************************
	 * Generate stack of cards to start game.
	 ***********************************************************/
	private void createDeck(){

			// Adding point cards
		PointCard pointCard1 = new PointCard("One VP",2,"Worth 1 Victory Point",1,"point", "OneVP.png");
		ArrayList<Card> pointCard1Stack = fillCardStack(pointCard1,25);
		allCards.add(pointCard1Stack);
		PointCard pointCard5 = new PointCard("Five VP",6,"Worth 5 Victory Point",5,"point", "TwoVP.png");
		ArrayList<Card> pointCard5Stack = fillCardStack(pointCard5,10);
		allCards.add(pointCard5Stack);
		PointCard pointCard10 = new PointCard("Ten VP",12,"Worth 10 Victory Point",10,"point", "ThreeVP.png");
		ArrayList<Card> pointCard10Stack = fillCardStack(pointCard10,5);
		allCards.add(pointCard10Stack);

			// Adding money cards
		MoneyCard moneyCard1 = new MoneyCard("One $",2,"Worth 1 $",1,"money", "money-md.png");
		ArrayList<Card> moneyCard1Stack = fillCardStack(moneyCard1,50);
		allCards.add(moneyCard1Stack);
		MoneyCard moneyCard2 = new MoneyCard("Two $",3,"Worth 2 $",3,"money", "money-md.png");
		ArrayList<Card> moneyCard2Stack = fillCardStack(moneyCard2,40);
		allCards.add(moneyCard2Stack);
		MoneyCard moneyCard3 = new MoneyCard("Three $",6,"Worth 3 $",6,"money", "money-md.png");
		ArrayList<Card> moneyCard3Stack = fillCardStack(moneyCard3,30);
		allCards.add(moneyCard3Stack);
		MoneyCard moneyCard4 = new MoneyCard("Four $",8,"Worth 4 $",8,"money", "money-md.png");
		ArrayList<Card> moneyCard4Stack = fillCardStack(moneyCard4,20);
		allCards.add(moneyCard4Stack);
		
			// Adding action cards
		ActionCard actionCard1 = new ActionCard("Action1", 2, "des", 2, 0, 2, 0, "action", "redCross.png");
		ArrayList<Card> actionCard1Stack = fillCardStack(actionCard1, 50);
		allCards.add(actionCard1Stack);
		ActionCard actionCard2 = new ActionCard("Action2", 4, "des", 2, 2, 0, 2, "action", "redCross.png");
		ArrayList<Card> actionCard2Stack = fillCardStack(actionCard2, 40);
		allCards.add(actionCard2Stack);
		ActionCard actionCard3 = new ActionCard("Action3", 6, "des", 2, 2, 2, 0, "action", "redCross.png");
		ArrayList<Card> actionCard3Stack = fillCardStack(actionCard3, 30);
		allCards.add(actionCard3Stack);
		ActionCard actionCard4 = new ActionCard("Action4", 10, "des", 2, 2, 2, 4, "action", "redCross.png");
		ArrayList<Card> actionCard4Stack = fillCardStack(actionCard4, 20);
		allCards.add(actionCard4Stack);
		ActionCard actionCard5 = new ActionCard("Action5", 12, "des", 4, 0, 4, 4, "action", "redCross.png");
		ArrayList<Card> actionCard5Stack = fillCardStack(actionCard5, 20);
		allCards.add(actionCard5Stack);
		
			// Adding attack cards
		AttackCard attackCard1 = new AttackCard("Attack1", 2, "des", new int[]{0, 0, 1, 0, 1}, "attack", "nuclear-md.png");
		ArrayList<Card> attackCard1Stack = fillCardStack(attackCard1, 50);
		allCards.add(attackCard1Stack);
		AttackCard attackCard2 = new AttackCard("Attack2", 4, "des", new int[]{0, 0, 1, 1, 1}, "attack", "nuclear-md.png");
		ArrayList<Card> attackCard2Stack = fillCardStack(attackCard2, 40);
		allCards.add(attackCard2Stack);
		AttackCard attackCard3 = new AttackCard("Attack3", 6, "des", new int[]{0, 0, 2, 1, 0, 1, 1}, "attack", "nuclear-md.png");
		ArrayList<Card> attackCard3Stack = fillCardStack(attackCard3, 30);
		allCards.add(attackCard3Stack);
		AttackCard attackCard4 = new AttackCard("Attack4", 10, "des", new int[]{0, 0, 1, 0, 2}, "attack", "nuclear-md.png");
		ArrayList<Card> attackCard4Stack = fillCardStack(attackCard4, 20);
		allCards.add(attackCard4Stack);
		System.out.println("\n\n\n"+pointCard1.getImg());
		
			// Adding defense cards
		DefenseCard defenseCard1 = new DefenseCard("Defense1", 2, "des", "defense", "shield.png");
		ArrayList<Card> defenseCard1Stack = fillCardStack(defenseCard1, 50);
		allCards.add(defenseCard1Stack);
		
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
		referenceDeck.add(actionCard5);
		referenceDeck.add(attackCard1);
		referenceDeck.add(attackCard2);
		referenceDeck.add(attackCard3);
		referenceDeck.add(attackCard4);
		referenceDeck.add(defenseCard1);
		
	}
	
	
	/************************************************************
	 * Run game.
	 ***********************************************************/
	private void run(){
		while(!gameFinished)
		{
			playerTurn();
			checkGameStatus();
		}
		endGame();
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
	public void playerTurn()
	{
		Player p = players[currentPlayer];
		System.out.println("******************************************************");
		System.out.println("Current Player: " + (currentPlayer+1));

		actions = 1; 
		purchases = 1; 
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
			choice = checkInput(1, 5);
			
			switch(choice)
			{
			case 1:
				// Play action
				int[] temp = actionChoice(p, actions, purchases);
				actions = temp[0];
				purchases = temp[1];
				break;
			case 2:
				// Purchase card
				purchases = purchaseChoice(p, purchases);;
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
	 * Carry out user action choice.
	 * 
	 * @param current player
	 * @param number of actions currently
	 * @param number of purchases currently
	 * @return new number of actions and purchases
	 ***********************************************************/
	private int[] actionChoice(Player p, int actions, int purchases){
		System.out.println("*PlayAction*");
		System.out.println("Choose which card to play: ");
		displayCards(p.getHand());
		System.out.println("(" + (p.getHand().size()+1) + ") Back to Menu");
		int c = checkInput(1, p.getHand().size()+1);
		
		if(c == p.getHand().size()+1){
			return new int[]{actions, purchases};
		}
		
		if(pAction(p, c)){
			if(p.getCard(c-1) instanceof ActionCard){
				int add[] = action(c);
				actions += add[0];
				purchases += add[1];
			}else{
				attack(c);
			}
			actions--;
		}
		
		return new int[]{actions, purchases};
	}
		
	/************************************************************
	 * Carry out user purchase choice.
	 * 
	 * @param current player
	 * @param number of purchases currently
	 * @return new number of purchases
	 ***********************************************************/
	private int purchaseChoice(Player p, int purchases){
		System.out.println("*PurchaseCard*");
		System.out.println("User money: " + p.getCurrentMoney());
		showBoard();
		System.out.println("Enter card choice: ");
		int purchaseChoice = checkInput(1, referenceDeck.size()+1);
		
		if(purchaseChoice == p.getHand().size()+1){
			return purchases;
		}
		
		if(purchaseChoice <= referenceDeck.size())
		{
			Card cardChoice = purchaseCard(purchaseChoice);
			while(cardChoice == null)
			{
				System.out.println("User money: " + p.getCurrentMoney());
				System.out.println("Enter card choice: ");
				purchaseChoice = checkInput(1, referenceDeck.size());
				if(purchaseChoice >= referenceDeck.size())
				{
					cardChoice = purchaseCard(purchaseChoice);
				}
				else
					return purchases;
			}
			players[currentPlayer].addPurchase(cardChoice);
			
		}
		return purchases-1;
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
	
	/************************************************************
	 * Check if card is playable.
	 * 
	 * @param current player
	 * @return true if playable, false if not
	 ***********************************************************/
	private boolean pAction(Player p, int c) {
		
		displayCards(p.getHand());
		if (p.getHand().get(c-1) instanceof ActionCard || p.getHand().get(c-1) instanceof AttackCard) {
			
			return true;
		}
		
		System.out.println("That is not a playabe card please select a playable card");
		return false;
	}

	/************************************************************
	 * Move to next player.
	 ***********************************************************/
	void nextPlayer()
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
		for(Card c : cards)
		{
			System.out.println("(" + count + ") " + c);
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
	 * Determine if game is over or not.
	 ***********************************************************/
	public void checkGameStatus()
	{
		if(allCards.get(2).size()==0)
			gameFinished = true;//Game over
	}
	
	/************************************************************
	 * Determine if game is over or not.
	 ***********************************************************/
	public int endGame()
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
	 * Check if player has defense card.
	 * 
	 * @param player to be checked
	 * @return true if player has defense card, false if not
	 ***********************************************************/
	private boolean defense(Player p){
		
		for(int i=0; i<p.getHand().size(); i++){
			if(p.getHand().get(i) instanceof DefenseCard){
				System.out.println("BLOCKED!");
				return true;
			}
		}
		
		return false;
	}
	
	/************************************************************
	 * Carry out attack card.
	 * 
	 * @param selected card index
	 ***********************************************************/
	private void attack(int selection){
		AttackCard ac = (AttackCard) players[currentPlayer].getCard(selection-1);
		for(int i=0; i < players.length; i++){
			if(i != currentPlayer){
				if(!defense(players[i])){
					attackEffect(players[i], ac);
				}
			}
		}
	}
	
	/************************************************************
	 * Carry out effects of attack card.
	 * 
	 * @param player attacked
	 * @param attack card
	 ***********************************************************/
	private void attackEffect(Player p, AttackCard c ){		
		for(int i=0; i<c.getEffect().size(); i++){
			Effect e = c.getEffect().get(i);
			switch(e){
				case DISCARD:
					for(int j=0; j<c.vals.get(i); j++){
						p.discardOne(rand.nextInt() % p.getHand().size());
					}
					break;
				case STEAL:
					if(p.getCurrentMoney() <= c.vals.get(i)){
						players[currentPlayer].addMoney(p.getCurrentMoney());
						p.setCurrentMoney(0);
					}else{
						players[currentPlayer].addMoney(c.vals.get(i));
						p.setCurrentMoney(p.getCurrentMoney()-c.vals.get(i));
					}
					break;
				default:
					break;
			}
		}
	}
	
	/************************************************************
	 * Validate user entry.
	 * 
	 * @param minimum integer value allowed
	 * @param maximum integer value allowed
	 * @return valid user entry
	 ***********************************************************/
	private int checkInput(int min, int max){
		int res = 0;
		
		try{
			res = scan.nextInt();
			if(res < min || res > max){
				System.out.println("Please enter a valid integer between " + min + " and " + max);
				checkInput(min, max);
			}
			
		}catch(Exception e){
			System.out.println("Please enter a valid integer between " + min + " and " + max);
			//checkInput(min, max);
		}
		return res;
	}
	
	/************************************************************
	 * Carry out an ActionCard.
	 * 
	 * @return number of actions and purchases to be added
	 ***********************************************************/
	public int[] action(int selection){
		ActionCard ac = (ActionCard) players[currentPlayer].getCard(selection-1);
		int action[] = ac.getActionFunction();
		players[currentPlayer].drawCards(action[0]);
		players[currentPlayer].addMoney(action[3]);
		int ret[] = {action[1], action[2]};
		
		return ret;		
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
	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}

	/************************************************************
	 * Set number of actions.
	 * 
	 * @param number of actions
	 ***********************************************************/
	public void setActions(int actions) {
		this.actions = actions;
	}

	/************************************************************
	 * Set number of purchases.
	 * 
	 * @param number of purchases
	 ***********************************************************/
	public void setPurchases(int purchases) {
		this.purchases = purchases;
	}

	/************************************************************
	 * Main function for running program.
	 ***********************************************************/
	public static void main(String[] args)
	{
						System.out.println("************************************** Running Tests **************************************");
						JUnitCore junit = new JUnitCore();
						Result result = junit.run(WarfareTest.class);
						for (Failure failure : result.getFailures()) 
						{
							System.out.println(failure.toString());
						}
						if(result.wasSuccessful())
						{
							System.out.println("********************************** All Tests Successful **********************************");
						}
		int num = 0;
		Scanner s = new Scanner(System.in);
		System.out.println("How many players in the game?");
		try{
			num = s.nextInt();
		}catch(Exception e){
			System.out.println("Invalid Entry");
			s.close();
			return;
		}
			

		Game g = new Game(num);
		g.run();
		s.close();
	}
}
