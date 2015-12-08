package warfare;

import java.io.File;
import java.util.*;

/****************************************************************
 * Class for simulation of Warfare card game.
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class Game {

	/* Random variable */
	Random rand = new Random(69);

	/* Current player (position in array) and total number of players */
	private int currentPlayer, numPlayers;

	/* Array to store players */
	private Player[] players;

	/* Array list containing all game cards */
	ArrayList<ArrayList<Card>> allCards;
	
	/* Factions for each player */
	private ArrayList<String> factions;

	/* Array list containing one copy of each unique card */
	ArrayList<Card> referenceDeck;
	
	ArrayList<ArrayList<Card>> expCards = new ArrayList<ArrayList<Card>>();
	
	/* Boolean value to determine when game is over */
	boolean gameFinished;

	/* Number of actions and purchases current player has */
	int actions, purchases;

	/************************************************************
	 * Constructor for objects of type Game.
	 ***********************************************************/
	public Game(){
		this.numPlayers = 2;
		players = new Player[numPlayers];
		for(int i = 0;i<numPlayers;i++)
			players[i] = new Player();
		allCards = new ArrayList<ArrayList<Card>>();
		gameFinished = false;
		currentPlayer = 0;
		actions = 1;
		purchases = 1;
	}

	/************************************************************
	 * Constructor for objects of type Game.
	 * 
	 * @param number of players
	 ***********************************************************/
	public Game(int numPlayers, ArrayList<ArrayList<Card>> exp, ArrayList<String> factions){
		this.numPlayers = numPlayers;
		if (exp != null)
			this.expCards = exp;
		players = new Player[numPlayers];
		for(int i = 0;i<numPlayers;i++)
			players[i] = new Player();
		this.factions = factions;
		if(factions != null)
			setUpFactions();
		allCards = new ArrayList<ArrayList<Card>>();
		gameFinished = false;
		createDeck();
		setInitialPlayerCards();
		currentPlayer = 0;
		actions = 1;
		purchases = 1;


	}

	/************************************************************
	 * Set up factions for the game
	 ***********************************************************/
	public void setUpFactions()
	{
		//int decision = JOptionPane.showConfirmDialog(factionFrame,"Faction Choice", "Would you like to play with factions?",JOptionPane.YES_NO_OPTION);
			//int playerCount = 1;
		for(int i=0; i<numPlayers; i++){
			//Player p = game.getPlayers()[i];
//			String[] factionOpts = {"Red", "Green", "Blue", "Orange", "Yellow"};
//			String s = (String)JOptionPane.showInputDialog(factionFrame, "Player " + playerCount,
//					"Choose a Faction:", JOptionPane.PLAIN_MESSAGE, null, factionOpts, "1");
			//System.out.println((String)factionchoice.get(i).getSelectedItem());
			switch(factions.get(i)){
			case "Red":
				players[i].setFaction(Faction.RED);
				break;
			case "Blue":
				players[i].setFaction(Faction.BLUE);
				break;
			case "Orange":
				players[i].setFaction(Faction.ORANGE);
				break;
			case "Yellow":
				players[i].setFaction(Faction.YELLOW);
				break;
			case "Green":
				players[i].setFaction(Faction.GREEN);
				break;
			default:
				break;
			}
				//playerCount++;
		}		
	}
	
	/************************************************************
	 * Generate stack of cards to start game.
	 ***********************************************************/
	public void createDeck(){
		
		// Adding point cards
		PointCard pointCard1 = new PointCard("One VP",2,"Worth 1 Victory Point",1,"point", new File("OneVP.png"));
		ArrayList<Card> pointCard1Stack = fillCardStack(pointCard1,25);
		allCards.add(pointCard1Stack);
		PointCard pointCard5 = new PointCard("Five VP",6,"Worth 5 Victory Point",5,"point", new File("FiveVP.png"));
		ArrayList<Card> pointCard5Stack = fillCardStack(pointCard5,10);
		allCards.add(pointCard5Stack);
		PointCard pointCard10 = new PointCard("Ten VP",12,"Worth 10 Victory Point",10,"point", new File("TenVP.png"));
		ArrayList<Card> pointCard10Stack = fillCardStack(pointCard10,5);
		allCards.add(pointCard10Stack);

		// Adding money cards
		MoneyCard moneyCard1 = new MoneyCard("$1 mill",2,"Worth $1 mill",1,"money", new File("Money.png"));
		ArrayList<Card> moneyCard1Stack = fillCardStack(moneyCard1,50);
		allCards.add(moneyCard1Stack);
		MoneyCard moneyCard2 = new MoneyCard("$2 mill",3,"Worth $2 mill",2,"money", new File("MoneyTwo.png"));
		ArrayList<Card> moneyCard2Stack = fillCardStack(moneyCard2,40);
		allCards.add(moneyCard2Stack);
		MoneyCard moneyCard3 = new MoneyCard("$3 mill",6,"Worth $3 mill",3,"money", new File("MoneyThree.png"));
		ArrayList<Card> moneyCard3Stack = fillCardStack(moneyCard3,30);
		allCards.add(moneyCard3Stack);
		MoneyCard moneyCard4 = new MoneyCard("$4 mill",8,"Worth $4 mill",4,"money", new File("MoneyFour.png"));
		ArrayList<Card> moneyCard4Stack = fillCardStack(moneyCard4,20);
		allCards.add(moneyCard4Stack);
		
		referenceDeck = new ArrayList<Card>();
		referenceDeck.add(pointCard1);
		referenceDeck.add(pointCard5);
		referenceDeck.add(pointCard10);
		referenceDeck.add(moneyCard1);
		referenceDeck.add(moneyCard2);
		referenceDeck.add(moneyCard3);
		referenceDeck.add(moneyCard4);
		
		if (expCards.isEmpty()) {
			// Adding action cards
			ActionCard actionCard1 = new ActionCard("Target Practice", 3, "+1 Cards;+1 Action", 1, 1, 0, 0, "action", new File("TargetPractice.png"));
			ArrayList<Card> actionCard1Stack = fillCardStack(actionCard1, 50);
			allCards.add(actionCard1Stack);
			ActionCard actionCard2 = new ActionCard("Medic", 4, "+3 Cards", 3, 0, 0, 0, "action", new File("redCross.png"));
			ArrayList<Card> actionCard2Stack = fillCardStack(actionCard2, 40);
			allCards.add(actionCard2Stack);
			ActionCard actionCard3 = new ActionCard("Refuel", 5, "+1 Card;+1 Action;+1 Purchase;+$1 mill", 1, 1, 1, 1, "action", new File("Refuel.jpg"));
			ArrayList<Card> actionCard3Stack = fillCardStack(actionCard3, 30);
			allCards.add(actionCard3Stack);
			ActionCard actionCard4 = new ActionCard("BaseCamp", 8, "+2 Cards;+2 Purchases;+$4 mill", 2, 0, 2, 4, "action", new File("BaseCamp.jpg"));
			ArrayList<Card> actionCard4Stack = fillCardStack(actionCard4, 20);
			allCards.add(actionCard4Stack);
			ActionCard actionCard5 = new ActionCard("AirDrop", 10, "+3 Cards;+2 Actions;+2 Purchases;+$4 mill", 3, 2, 2, 4, "action", new File("AirDrop.jpg"));
			ArrayList<Card> actionCard5Stack = fillCardStack(actionCard5, 20);
			allCards.add(actionCard5Stack);
			
			
			ActionCard actionCard6 = new ActionCard("Factory", 4, "+1Card;+2Actions", 1, 2, 0, 0, "action", new File("Factory.jpg"));
			ArrayList<Card> actionCard6Stack = fillCardStack(actionCard6, 20);
//			allCards.add(actionCard6Stack);
			ActionCard actionCard7 = new ActionCard("MRE", 4, "+1 Purchase;+$3 mill", 0, 0, 1, 3, "action", new File("MRE.jpg"));
			ArrayList<Card> actionCard7Stack = fillCardStack(actionCard7, 20);
//			allCards.add(actionCard7Stack);
	
			// Adding attack cards
			//Need to write descriptions for attack cards, I'm not sure how they work yet...-EE
			AttackCard attackCard1 = new AttackCard("Recon", 2, "ALL Enem. Discard 1", new int[]{0, 0, 1, 0, 1}, "attack", new File("Recon.jpg"));
			ArrayList<Card> attackCard1Stack = fillCardStack(attackCard1, 50);
			allCards.add(attackCard1Stack);
			AttackCard attackCard2 = new AttackCard("Air Strike", 4, "ALL Enem. Steal $1 Mill", new int[]{0, 0, 1, 1, 1}, "attack", new File("AirStrike.jpg"));
			ArrayList<Card> attackCard2Stack = fillCardStack(attackCard2, 40);
			allCards.add(attackCard2Stack);
			AttackCard attackCard3 = new AttackCard("Naval Fleet", 6, "ALL Enem. Discard 1, Steal $1 Mill", new int[]{0, 0, 2, 1, 0, 1, 1}, "attack", new File("Navy-Fleet.jpg"));
			ArrayList<Card> attackCard3Stack = fillCardStack(attackCard3, 30);
			allCards.add(attackCard3Stack);
			AttackCard attackCard4 = new AttackCard("Tank", 10, "ALL Enem. Discard 2", new int[]{0, 0, 1, 0, 2}, "attack", new File("Tank.jpg"));
			ArrayList<Card> attackCard4Stack = fillCardStack(attackCard4, 20);
			allCards.add(attackCard4Stack);
	
			// Adding defense cards
			DefenseCard defenseCard1 = new DefenseCard("Bunker", 2, "Blocks oncoming attacks.", "defense", new File("Bunker.png"));
			ArrayList<Card> defenseCard1Stack = fillCardStack(defenseCard1, 50);
			allCards.add(defenseCard1Stack);
	
			
			referenceDeck.add(actionCard1);
			referenceDeck.add(actionCard2);
			referenceDeck.add(actionCard3);
			referenceDeck.add(actionCard4);
			referenceDeck.add(actionCard5);
//			referenceDeck.add(actionCard6);
//			referenceDeck.add(actionCard7);
			referenceDeck.add(attackCard1);
			referenceDeck.add(attackCard2);
			referenceDeck.add(attackCard3);
			referenceDeck.add(attackCard4);
			referenceDeck.add(defenseCard1);
		} else {
			allCards.addAll(expCards);
			for (ArrayList<Card> stack : expCards) {
				referenceDeck.add(stack.get(0));
			}
			// Adding defense cards
			DefenseCard defenseCard1 = new DefenseCard("Bunker", 2, "Blocks oncoming attacks.", "defense", new File("Bunker.png"));
			ArrayList<Card> defenseCard1Stack = fillCardStack(defenseCard1, 50);
			allCards.add(defenseCard1Stack);
			referenceDeck.add(defenseCard1);
		}
	}

	/************************************************************
	 * Adding cards to card stack.
	 * 
	 * @param card to add, number of cards to add
	 ***********************************************************/
	public ArrayList<Card> fillCardStack(Card c,int n){
		ArrayList<Card> cardStack = new ArrayList<Card>();
		for(int i = 0;i<n;i++){
			cardStack.add(c.cardClone(c));
		}
		return cardStack;
	}

	/************************************************************
	 * Deal initial cards to players.
	 ***********************************************************/
	public void setInitialPlayerCards(){
		for(Player p : players){
			ArrayList<Card> pDeck = new ArrayList<Card>();
			Faction fac = p.getFaction();
			if(fac != null){
				switch(fac){
				case RED:
					for(int i = 0;i<2;i++)
						pDeck.add(allCards.get(8).remove(0));//Get 2 'Recon'
					for(int i = 0;i<3;i++)
						pDeck.add(allCards.get(0).remove(0));//Get 3 '1VP'
					for(int i = 0;i<5;i++)
						pDeck.add(allCards.get(3).remove(0));//Get 5 '$1 mill'
					break;
				case BLUE:
					for(int i = 0;i<2;i++)
						pDeck.add(allCards.get(21).remove(0));//Get 2 'Bunkers'
					for(int i = 0;i<3;i++)
						pDeck.add(allCards.get(0).remove(0));//Get 3 '1VP'
					for(int i = 0;i<5;i++)
						pDeck.add(allCards.get(3).remove(0));//Get 5 '$1 mill'
					break;
				case GREEN:
					for(int i = 0;i<2;i++)
						pDeck.add(allCards.get(1).remove(0));//Get 2 '5VP'
					for(int i = 0;i<3;i++)
						pDeck.add(allCards.get(0).remove(0));//Get 3 '1VP'
					for(int i = 0;i<5;i++)
						pDeck.add(allCards.get(3).remove(0));//Get 5 '$1 mill'
					break;
				case ORANGE:
					for(int i = 0;i<2;i++)
						pDeck.add(allCards.get(7).remove(0));//Get 2 'Target Practice'
					for(int i = 0;i<3;i++)
						pDeck.add(allCards.get(0).remove(0));//Get 3 '1VP'
					for(int i = 0;i<5;i++)
						pDeck.add(allCards.get(3).remove(0));//Get 5 '$1 mill'
				case YELLOW:
					for(int i = 0;i<2;i++)
						pDeck.add(allCards.get(4).remove(0));//Get 2 '$2 mill'
					for(int i = 0;i<3;i++)
						pDeck.add(allCards.get(0).remove(0));//Get 3 '1VP'
					for(int i = 0;i<5;i++)
						pDeck.add(allCards.get(3).remove(0));//Get 5 '$1 mill'
					break;
				default:
					break;
				}
			}
			else{
				for(int i = 0;i<3;i++)
					pDeck.add(allCards.get(0).remove(0));//Get 3 '1VP'
				for(int i = 0;i<7;i++)
					pDeck.add(allCards.get(3).remove(0));//Get 7 '$1 mill'
			}
			Collections.shuffle(pDeck, new Random());
			p.setDeck(pDeck);
			p.drawCards(5);
		}
	}

	/************************************************************
	 * Carry out user action choice.
	 * 
	 * @param current player
	 * @param number of actions currently
	 * @param number of purchases currently
	 * @return new number of actions and purchases
	 ***********************************************************/
	public void actionChoice(Player p, int c){
		if(pAction(p, c)){
			if(p.getCard(c) instanceof ActionCard){
				int add[] = action(c);
				actions += add[0];
				purchases += add[1];
			}else
				attack(c);
			actions--;
			System.out.println(p.getCard(c) + "Card Played!");
		}
	}

	/************************************************************
	 * Simulate the purchase of a card.
	 * 
	 * @param card index
	 ***********************************************************/
	public void purchaseCard(int cardNum){
		Player p = players[currentPlayer];
		int cost = allCards.get(cardNum).get(0).getCost();
		p.setCurrentMoney(p.getCurrentMoney()-cost);
		players[currentPlayer].addPurchase(allCards.get(cardNum).remove(0));
		purchases--;
		System.out.println(allCards.get(cardNum).get(0)+" Card Purchased!");
	}

	/************************************************************
	 * Checks if card is purchaseable
	 * 
	 * @param card index
	 * @return true if purchaseable, false otherwise
	 ***********************************************************/
	public boolean checkPurchasable(int cardNum){
		Player p = players[currentPlayer];
		int cost = allCards.get(cardNum).get(0).getCost();
		if(cost > p.getCurrentMoney() || purchases == 0){
			return false;
		}
		return true;
	}

	/************************************************************
	 * Check if card is playable.
	 * 
	 * @param current player
	 * @return true if playable, false if not
	 ***********************************************************/
	public boolean pAction(Player p, int c) {
		if (p.getHand().get(c) instanceof ActionCard || p.getHand().get(c) instanceof AttackCard) {
			return true;
		}
		return false;
	}

	/************************************************************
	 * Move to next player.
	 ***********************************************************/
	void nextPlayer(){
		currentPlayer++;
		currentPlayer = currentPlayer%numPlayers;//Make sure the currentPlayer stays within the the appropriate range (0 -> numPlayers)
	}

	/************************************************************
	 * Determine if game is over or not.
	 ***********************************************************/

	public void checkGameStatus()
	{
		//		if(allCards.get(2).size()==0)
		//			gameFinished = true;//Game over
		for(int i = 0;i<players.length;i++)
		{
			if(players[i].getPoints() > 30)
			{
				gameFinished = true;
				break;
			}
		}
	}

	/************************************************************
	 * Determine if game is over or not.
	 ***********************************************************/
	public int endGame(){
		int maxScore = 0;
		int playerScore = 0;
		int winningPlayer = 0;
		int index = 1;
		for(Player p: players){
			playerScore = p.calcPoints();
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
	public boolean defense(Player p){
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
		AttackCard ac = (AttackCard) players[currentPlayer].getCard(selection);
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
					p.discardOne(rand.nextInt(p.getHand().size()));
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
	 * Carry out an ActionCard.
	 * 
	 * @return number of actions and purchases to be added
	 ***********************************************************/
	public int[] action(int selection){
		ActionCard ac = (ActionCard) players[currentPlayer].getCard(selection);
		int action[] = ac.getActionFunction();
		players[currentPlayer].drawCards(action[0]);
		players[currentPlayer].addMoney(action[3]);
		int ret[] = {action[1], action[2]};

		return ret;		
	}

	/************************************************************
	 * Get specific card.
	 * 
	 * @return desired card
	 ***********************************************************/
	public Card getCard(int index){
		return allCards.get(index).get(0);
	}

	/************************************************************
	 * Get current player.
	 * 
	 * @return current player
	 ***********************************************************/
	public int getCurrentPlayer(){
		return currentPlayer;
	}

	/************************************************************
	 * Set current player.
	 * 
	 * @param current player
	 ***********************************************************/
	public void setCurrentPlayer(int currentPlayer){
		this.currentPlayer = currentPlayer;
	}

	/************************************************************
	 * Get number of players.
	 * 
	 * @return number of player
	 ***********************************************************/
	public int getNumPlayers(){
		return numPlayers;
	}

	/************************************************************
	 * Set number of players.
	 * 
	 * @param number of player
	 ***********************************************************/
	public void setNumPlayers(int numPlayers){
		this.numPlayers = numPlayers;
	}

	/************************************************************
	 * Get players.
	 * 
	 * @return array of players
	 ***********************************************************/
	public Player[] getPlayers(){
		return players;
	}

	/************************************************************
	 * Set players.
	 * 
	 * @param array of players
	 ***********************************************************/
	public void setPlayers(Player[] players){
		this.players = players;
	}

	/************************************************************
	 * Check if game is over.
	 * 
	 * @return true if finished, false if not
	 ***********************************************************/
	public boolean isGameFinished(){
		return gameFinished;
	}

	/************************************************************
	 * Set game as finished/not finished.
	 * 
	 * @param true if finished, false if not
	 ***********************************************************/
	public void setGameFinished(boolean gameFinished){
		this.gameFinished = gameFinished;
	}

	/************************************************************
	 * Set number of actions.
	 * 
	 * @param number of actions
	 ***********************************************************/
	public void setActions(int actions){
		this.actions = actions;
	}

	/************************************************************
	 * Set number of purchases.
	 * 
	 * @param number of purchases
	 ***********************************************************/
	public void setPurchases(int purchases){
		this.purchases = purchases;
	}
	
	public void setExpCards(ArrayList<ArrayList<Card>> exp) {
		this.expCards = exp;
	}
}
