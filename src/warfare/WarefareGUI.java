package warfare;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/****************************************************************
 * GUI for simulation of Warfare card game.
 * 
 * @author Cameron Novotny, Elliot Ensink, Curtis Holden
 * @version 
 ***************************************************************/
public class WarefareGUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	Random rand = new Random();

	/* Game variable to run game */
	private Game game;

	/* Names of all players */
	private ArrayList<String> names;

	/* Number of players and index of selected card */
	private int numPlayers, selectedCardIndex;

	/* Is there a computer player */
	private boolean comPlayer, faction;

	/* Current player */
	private Player current;
	
	private JCheckBox expandCHECK, factionCHECK;
	
	private ArrayList <JComboBox<String>> factionchoice;
	private ArrayList <JPanel> factionPANs;

	/* Buttons for user choices */
	private JButton playBUT;
	private JButton purchaseBUT;
	private JButton endTurnBUT;
	private JButton helpBUT;
	private JButton continueBUT;
	private JButton continueBUT2;

	/* Canvas for displaying/interacting purchasable cards */
	private gameCanvas gameBoard;

	/* Canvas for displaying/interacting player cards */
	private playerBoardCanvas playerBoard;

	/* Listener for events within canvases */
	private CanvasListener cl;

	/* Labels for displaying player information */
	private JLabel nameLAB;
	private JLabel infoName[];
	private JLabel infoPlayer[];
	private JLabel cardInfo;
	private JLabel gameMessage;

	/* Panels to add components to build GUI */
	private JPanel gameBoardPan;
	private JPanel playerPAN;
	private JPanel infoPAN;
	private JPanel framePAN;
	private JPanel menuPAN;
	private JPanel playInfo[];
	private JPanel factionPAN;
	//private JPanel factionPANs [];

	/* Scroll pane to hold user cards */
	private JScrollPane playerBoardPan;

	/* Fields for entry of player names */
	private JTextField[] nameFLDs;

	/* Frame to hold game components */
	private JFrame frame;

	/* Frame to hold player name entry components */
	private JFrame nameFrame;
	
	/* Frame to hold faction/expansion choices */
	private JFrame factionFrame;

	/* Color variables for different types of cards */
	private Color actionColor, moneyColor, pointColor, attackColor, defenseColor;
	
	/*Game Deck for all players to interact with */
	private ArrayList<ArrayList<Card>> gameDeck;
	
	/*Reference Deck of all cards in the current game */
	private ArrayList<Card> gameRefDeck;


	/************************************************************
	 * Constructor for objects of type WarefareGUI.
	 ***********************************************************/
	public WarefareGUI(){
		comPlayer = false;
		playBUT = new JButton("Play Action");
		purchaseBUT = new JButton("Purchase Card");
		endTurnBUT = new JButton("End Turn");
		helpBUT = new JButton("Help");
		gameBoardPan = new JPanel();
		playerPAN = new JPanel();
		infoPAN = new JPanel();
		framePAN = new JPanel();
		menuPAN = new JPanel();
		nameLAB = new JLabel();
		actionColor = new Color(255,194,97);
		moneyColor = new Color(231,237,55);
		pointColor = new Color(154,245,157);
		attackColor = new Color(237,69,69);
		defenseColor = new Color(173,221,237);

		names = new ArrayList<String>();
		getNames();


		playInfo = new JPanel[numPlayers];
		infoName = new JLabel[numPlayers];
		infoPlayer = new JLabel[numPlayers];
		game = new Game(numPlayers);
		frame = new JFrame("Warefare");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1100, 650);

		cl = new CanvasListener();
		playerPAN.setLayout(new BoxLayout(playerPAN, BoxLayout.Y_AXIS));
		framePAN.setLayout(new BorderLayout());
		infoPAN.setLayout(new BoxLayout(infoPAN, BoxLayout.Y_AXIS));
		menuPAN.setLayout(new BoxLayout(menuPAN, BoxLayout.Y_AXIS));
		gameBoard = new gameCanvas(setUpGameDeck());
		gameBoard.setPreferredSize(new Dimension(850,410));
		gameBoard.addMouseListener(cl);
		gameBoard.addMouseMotionListener(cl);

		playerBoard = new playerBoardCanvas();
		playerBoard.addMouseListener(cl);
		playerBoard.addMouseMotionListener(cl);

		playerBoardPan = new JScrollPane(playerBoard);
		playerBoardPan.setPreferredSize(new Dimension(850,150));
		playerBoardPan.setBackground(new Color(237,227,183));

		gameBoardPan.add(gameBoard);
		gameBoardPan.add(new JLabel("Current Player: "));
		gameBoardPan.add(nameLAB);
		gameBoardPan.add(playerBoardPan);
		gameBoardPan.setBackground(new Color(237,227,183));
		gameBoardPan.setBorder(BorderFactory.createBevelBorder(NORMAL, Color.BLACK, Color.GRAY));

		selectedCardIndex = 0;
		//		playingAction = false;
		cardInfo = new JLabel("");

		gameMessage = new JLabel("");

		playBUT.addActionListener(this);
		purchaseBUT.addActionListener(this);
		endTurnBUT.addActionListener(this);
		helpBUT.addActionListener(this);

		menuPAN.add(playBUT);;
		menuPAN.add(purchaseBUT);
		menuPAN.add(endTurnBUT);
		menuPAN.add(helpBUT);
		menuPAN.add(cardInfo);
		menuPAN.add(gameMessage);

		framePAN.add(gameBoardPan, BorderLayout.CENTER);
		framePAN.add(infoPAN, BorderLayout.WEST);
		framePAN.add(menuPAN, BorderLayout.EAST);

		frame.add(framePAN);
		frame.setResizable(false);
		playBUT.setEnabled(false);
		purchaseBUT.setEnabled(false);
	}

	/************************************************************
	 * Respond to button clicks.
	 * 
	 * @param e the button that was selected
	 ***********************************************************/
	@Override
	public void actionPerformed(ActionEvent e){

		// 'Continue' button on name panel
		if(e.getSource() == continueBUT){			
			for(int i=0; i<numPlayers; i++){
				names.add(nameFLDs[i].getText());
			}
			nameFrame.setVisible(false);
			setUpFactions();
		}
		
		// 'Continue' button on faction panel
		if(e.getSource() == continueBUT2){
			setUpInfo();
			if(expandCHECK.isSelected()){
				try {
					ExpansionImporter ei = new ExpansionImporter(this);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			factionFrame.setVisible(false);
			frame.setVisible(true);
		}
		
		// 'Use factions?' checkbox
		if(e.getSource() == factionCHECK){
			addFactions();
			factionFrame.repaint();
			factionFrame.pack();			
		}

		// 'Play Card' button
		if(e.getSource() == playBUT){
			if(game.pAction(current, selectedCardIndex)){
				game.actionChoice(current, selectedCardIndex);
				current.discardOne(selectedCardIndex);
				gameMessage.setText("");
				setUpInfo();
			}else{
				gameMessage.setText("Not Playable");
			}

		}

		//'Purchase Card' button
		if(e.getSource() == purchaseBUT){			
			if(!game.checkPurchasable(gameBoard.getSelectedIndex())){
				gameMessage.setText("Not Enough $$");
			}else{
				game.purchaseCard(selectedCardIndex);
				checkTurn();
				gameMessage.setText("");
				setUpInfo();
			}
		}

		//'End Turn' button
		if(e.getSource() == endTurnBUT){
			game.purchases = 0;
			game.actions = 0;
			checkTurn();
			if(comPlayer){
				compTurn();
			}
		}

		//'Help' button
		if(e.getSource() == helpBUT){
			helpScreen();
		}
	}

	// As of now the computer plays like this (not sure if it works perfectly):
	//		1. Plays all playable cards it can starting with the action cards so it can get more actions
	//		   and play more cards if possible. If there are more than one action or attack cards it picks
	//		   a random one of that type.
	//		2. Randomly picks a card type out of the 5 possibilities (numbered 0-4) and purchases the 
	//		   most expensive card of that type that it can and continues in that manner until it either
	//		   runs out of money or purchases.
	//		3. Ends the turn.

	/************************************************************
	 * Carry out computer players turn.
	 ***********************************************************/
	private void compTurn(){
		String message = "";
		Boolean playable = false;

		// Find all playable cards
		for(int i=0; i<current.getHand().size(); i++){
			Card c = current.getCard(i);
			if(c instanceof ActionCard || c instanceof AttackCard){
				playable = true;
			}
		}

		// Play playable cards
		if(playable){
			message += compPlay();
		}

		// Purchase cards
		if(current.getCurrentMoney() > 1){
			message += compPurchase();
		}

		JOptionPane.showConfirmDialog(this, message, "Computers Turn", numPlayers);

		// Next player
		game.purchases = 0;
		game.actions = 0;
		checkTurn();
	}

	/************************************************************
	 * Carry out computer purchases.
	 ***********************************************************/
	private String compPurchase(){
		String message = "Purchases:\n";
		// Purchase cards until purchase not possible
		while(game.purchases != 0 && current.getCurrentMoney() > 1){

			// Random card type
			int type = rand.nextInt(5);
			int min, max;

			// Set range of chosen card type
			switch(type){
			// Point card
			case 0: 
				min = 0;
				max = 2;
				break;
				// Money card
			case 1:
				min = 3;
				max = 6;
				break;
				// Action card
			case 2:
				min = 7;
				max = 11;
				break;
				// Attack card
			case 3:
				min = 12;
				max = 15;
				break;
				// Defense card
			case 4:
				min = 16;
				max = 16;
				break;
			default:
				min = max = 0;
			}

			// Find most expensive card that can be bought
			while(max >= min && !game.checkPurchasable(max)){
				max--;
			}

			// Purchase card
			if(game.checkPurchasable(max)){
				game.purchaseCard(max);
				message += "  - " + game.getCard(max) + " card.\n";
			}
		}
		return message;
	}

	/************************************************************
	 * Play computers playable cards.
	 * 
	 * @param players current playable cards
	 * @param index of each playable card
	 ***********************************************************/
	private String compPlay(){
		ArrayList <Integer> actionIndex = new ArrayList <Integer>();
		ArrayList <Integer> attackIndex = new ArrayList <Integer>();
		String message = "Plays:\n";
		actionIndex.add(0);
		attackIndex.add(0);

		// Play all action and attack cards in hand (action first)
		// that you can
		while(game.actions != 0 && actionIndex.size() + attackIndex.size() != 0){
			actionIndex.clear();
			attackIndex.clear();

			for(int i=0; i<current.getHand().size(); i++){
				Card c = current.getCard(i);
				if(c instanceof ActionCard){
					actionIndex.add(i);
				}else if(c instanceof AttackCard){
					attackIndex.add(i);
				}
			}

			if(actionIndex.size() != 0){
				int index = rand.nextInt(actionIndex.size());
				// If there are more than one it picks a random one to play
				selectedCardIndex = actionIndex.get(index);
				game.actionChoice(current, selectedCardIndex);
				message += "  - " + current.getCard(selectedCardIndex) + " card.\n";
				current.discardOne(selectedCardIndex);
			}else if(attackIndex.size() != 0){
				int index = rand.nextInt(attackIndex.size());
				selectedCardIndex = attackIndex.get(index);
				game.actionChoice(current, selectedCardIndex);
				message += "  - " + current.getCard(selectedCardIndex) + " card.\n";
				current.discardOne(selectedCardIndex);
			}
		}
		return message;
	}

	/************************************************************
	 * Generate and display help screen. 
	 ***********************************************************/
	private void helpScreen(){
		String help;
		help = "Click cards to select them\n"
				+ "Use buttons to purchase or play cards\n"
				+ "Game is over when all high value point cards\n"
				+ "have been purchased";
		JOptionPane.showMessageDialog(nameFrame, help);
	}


	/************************************************************
	 * Run the game. 
	 ***********************************************************/
	public void runGame(){
		current = game.getPlayers()[game.getCurrentPlayer()];
		game.purchases = 1;
		game.actions = 1;

		while(!game.gameFinished){
			game.checkGameStatus();
		}

		game.endGame();
		String results = "GAME OVER!\nPoints:\n";
		for(int i = 0;i < game.getPlayers().length; i++){
			results = results + names.get(i) + ": " + game.getPlayers()[i].getPoints() + "\n";
		}
		JFrame endGameFrame = new JFrame();
		endGameFrame.setVisible(false);
		JOptionPane.showMessageDialog(endGameFrame, results);
	}

	/************************************************************
	 * Check if player turn is over.
	 ***********************************************************/
	private void checkTurn(){
		if(game.purchases == 0 && game.actions == 0){
			current.setCurrentMoney(0);
			current.discard();
			game.nextPlayer();
			current = game.getPlayers()[game.getCurrentPlayer()];
			game.purchases = 1;
			game.actions = 1;
			gameBoard.setSelectedCard(null);
			playBUT.setEnabled(false);
			purchaseBUT.setEnabled(false);
			gameMessage.setText("");
			setUpInfo();
		}
	}

	/************************************************************
	 * Setup and display player info and cards.
	 ***********************************************************/
	private void setUpInfo(){
		infoPAN.removeAll();

		for(int i=0; i<numPlayers; i++){
			playInfo[i] = new JPanel();
			infoName[i] = new JLabel();
			infoPlayer[i] = new JLabel();
			infoName[i].setText(names.get(i));
			Faction fac = game.getPlayers()[i].getFaction();
			if(fac != null){
				switch(fac){
					case RED:
						infoName[i].setForeground(attackColor);
						break;
					case GREEN:
						infoName[i].setForeground(pointColor);
						break;
					case BLUE:
						infoName[i].setForeground(defenseColor);
						break;
					case ORANGE:
						infoName[i].setForeground(actionColor);
						break;
					case YELLOW:
						infoName[i].setForeground(moneyColor);
						break;
					default:
						break;
				}
			}

			infoPlayer[i].setText("<html>$"+game.getPlayers()[i].getCurrentMoney()+ "<BR>"+
					game.getPlayers()[i].calcPoints()+" points<html>");
			playInfo[i].add(infoName[i]);
			playInfo[i].add(infoPlayer[i]);
			infoPAN.add(playInfo[i]);
		}

		nameLAB.setText(names.get(game.getCurrentPlayer()) + ": " + game.actions + " actions, " + game.purchases + " purchases.");

		playerBoard.setPlayerHand(current.getHand());
		playerBoard.paint(playerBoard.getGraphics());
	}
	/************************************************************
	 * Set up game deck to be used throughout the game
	 ***********************************************************/
	public ArrayList<ArrayList<Card>> setUpGameDeck(){
		gameDeck = new ArrayList<ArrayList<Card>>();
		gameRefDeck = new ArrayList<Card>();
		for(int i = 0; i < 7; i++){
			gameDeck.add(game.allCards.get(i));
			gameRefDeck.add(game.referenceDeck.get(i));
		}
		int numActions = 0;
		Random rand = new Random();
		ArrayList<Integer> usedNums = new ArrayList<Integer>();
		while(numActions < 5)
		{
			boolean newRand = true;
			int randAction = rand.nextInt(9)+8;
			for(int num:usedNums){
				if(num == randAction){
					newRand = false;
					break;
				}
			}
			if(newRand){
				gameDeck.add(game.allCards.get(randAction));
				gameRefDeck.add(game.referenceDeck.get(randAction));
				usedNums.add(randAction);
				numActions++;
			}
		}
		for(int i = 17; i < 22; i++)
		{
			gameDeck.add(game.allCards.get(i));
			gameRefDeck.add(game.referenceDeck.get(i));
		}
		return gameDeck;

	}


	/************************************************************
	 * Get player names from user.
	 ***********************************************************/
	private void getNames(){
		nameFrame = new JFrame();
		JLabel nameLAB = new JLabel("Player Names:");
		JPanel namePAN = new JPanel();
		continueBUT = new JButton("Continue");

		String[] numOpts = {"1", "2", "3", "4"};
		String s = (String)JOptionPane.showInputDialog(nameFrame, "How many Players?",
				"Number of players", JOptionPane.PLAIN_MESSAGE, null, numOpts, "1");
		numPlayers = Integer.parseInt(s);
		if(numPlayers == 1){
			numPlayers = 2;
			comPlayer = true;
		}
		namePAN.setLayout(new BoxLayout(namePAN, BoxLayout.Y_AXIS));
		namePAN.add(nameLAB);

		if(comPlayer){
			nameFLDs = new JTextField[numPlayers];
			nameFLDs[0] = new JTextField("Player 1", 30);
			nameFLDs[1] = new JTextField("Computer", 30);
			namePAN.add(nameFLDs[0]);
		}else{
			nameFLDs = new JTextField[numPlayers];

			for(int i=0; i<numPlayers; i++){
				nameFLDs[i] = new JTextField("Player "+(i+1), 30);
				namePAN.add(nameFLDs[i]);
			}
		}

		namePAN.add(continueBUT);

		continueBUT.addActionListener(this);
		nameFrame.add(namePAN);
		nameFrame.setVisible(true);
		nameFrame.pack();
	}
	
	/************************************************************
	 * Get faction and expansion pack decisions from user.
	 ***********************************************************/
	private void getDecision(){
		factionCHECK = new JCheckBox("Use Factions?");
		expandCHECK = new JCheckBox("Use expansion pack?");
		continueBUT2 = new JButton("Continue");
		continueBUT2.addActionListener(this);
		factionCHECK.addActionListener(this);
		factionPAN = new JPanel();
		factionPAN.setLayout(new BoxLayout(factionPAN, BoxLayout.Y_AXIS));
		
		factionPAN.add(expandCHECK);
		factionPAN.add(factionCHECK);
		factionPAN.add(continueBUT2);
		factionFrame.add(factionPAN);
		
		factionFrame.setVisible(true);
		factionFrame.pack();
	}
	
	/************************************************************
	 * Add faction choices for each player.
	 ***********************************************************/
	private void addFactions(){
		String [] factions = {"Red", "Green", "Blue", "Orange", "Yellow"};
		factionchoice = new ArrayList <JComboBox<String>>();
		factionPANs = new ArrayList <JPanel>();
		factionPAN.removeAll();
		factionPAN.add(expandCHECK);
		
		for(int i=0; i<numPlayers; i++){
			factionchoice.add(new JComboBox<String>(factions));
			factionPANs.add(new JPanel());
			factionPANs.get(i).setLayout(new FlowLayout());
			factionPANs.get(i).add(new JLabel(names.get(i)));
			factionPANs.get(i).add(factionchoice.get(i));
			factionPAN.add(factionPANs.get(i));
		}
		factionPAN.add(continueBUT2);		
	}
	
	/************************************************************
	 * Set up factions for the game
	 ***********************************************************/
	public void setUpFactions()
	{
		factionFrame = new JFrame();
		//int decision = JOptionPane.showConfirmDialog(factionFrame,"Faction Choice", "Would you like to play with factions?",JOptionPane.YES_NO_OPTION);
		getDecision();
		if(faction){
			//int playerCount = 1;
			for(int i=0; i<numPlayers; i++){
				Player p = game.getPlayers()[i];
//				String[] factionOpts = {"Red", "Green", "Blue", "Orange", "Yellow"};
//				String s = (String)JOptionPane.showInputDialog(factionFrame, "Player " + playerCount,
//						"Choose a Faction:", JOptionPane.PLAIN_MESSAGE, null, factionOpts, "1");
				switch((String)factionchoice.get(i).getSelectedItem()){
				case "Red":
					p.setFaction(Faction.RED);
					break;
				case "Blue":
					p.setFaction(Faction.BLUE);
					break;
				case "Orange":
					p.setFaction(Faction.ORANGE);
					break;
				case "Yellow":
					p.setFaction(Faction.YELLOW);
					break;
				case "Green":
					p.setFaction(Faction.GREEN);
					break;
				default:
					break;
				}
				//playerCount++;
			}
		}
		game.setIntialPlayerCards();
	}

	/************************************************************
	 * Class to display and handle selection of game board.
	 ***********************************************************/
	private class gameCanvas extends JPanel{
		private static final long serialVersionUID = 1L;
		private ArrayList<ArrayList<Card>> crds;
		private ArrayList<int[]> cardXCoords = new ArrayList<int[]>();
		private ArrayList<int[]> cardYCoords = new ArrayList<int[]>();
		private int selectedIndex;

		private final int cardWd = 100;
		private final int cardHt = 120;
		private final int cardSp = 20;
		private Card selectedCard;

		/************************************************************
		 * Constructor of objects of type gameCanvas.
		 * 
		 * @param cards to be displayed
		 ***********************************************************/
		public gameCanvas(ArrayList<ArrayList<Card>> crds){
			this.crds = crds;
			selectedCard = null;
		}

		/************************************************************
		 * Setting background color of cards.
		 * 
		 * @param card type
		 * @return background color
		 ***********************************************************/
		private Color cardColor(String type){
			switch(type){
			case "point":
				return pointColor;
			case "money":
				return moneyColor;
			case "action":
				return actionColor;
			case "attack":
				return attackColor;
			case "defense":
				return defenseColor;
			default:
				return Color.WHITE;
			}
		}

		/************************************************************
		 * Get X coordinates.
		 * 
		 * @return X coordinates of cards
		 ***********************************************************/
		public ArrayList<int[]> getCardXCoords()
		{
			return cardXCoords;
		}

		/************************************************************
		 * Get Y coordinates.
		 * 
		 * @return Y coordinates of cards
		 ***********************************************************/
		public ArrayList<int[]> getCardYCoords()
		{
			return cardYCoords;
		}

		/************************************************************
		 * Set selected card.
		 * 
		 * @param selected card
		 ***********************************************************/
		public void setSelectedCard(Card c)
		{
			selectedCard = c;
			paintSelectedCard(this.getGraphics());
		}

		/************************************************************
		 * Get index of selected card.
		 * 
		 * @return index of selected card
		 ***********************************************************/
		public int getSelectedIndex(){
			return selectedIndex;
		}

		/************************************************************
		 * Set index of selected card.
		 * 
		 * @param index of selected card
		 ***********************************************************/
		public void setSelectedIndex(int i){
			selectedIndex = i;
		}

		/************************************************************
		 * Setting up and displaying cards.
		 * 
		 * @param container in which cards are to be displayed
		 ***********************************************************/
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			int cardIndex = 0, XLeft = 0, XRight = 0, YTop = 0, YBot = 0, row = 0, xMult = 0;

			// Setting up card edge coordinates
			for(ArrayList<Card> a: crds){
				g.setColor(Color.BLACK);

				if(cardIndex <= 6){
					row = 0;
					xMult = cardIndex;
				}else if(cardIndex > 11){
					row = 2;
					xMult = cardIndex - 12;
				}else{
					row = 1;
					xMult = cardIndex - 7;
				}

				XLeft = cardWd*(xMult) + cardSp*(xMult);
				XRight = cardWd*(xMult+1) + cardSp*(xMult);
				YTop = cardHt*row+cardSp*row;
				YBot = cardHt*(row+1)+cardSp*row;
				addCard(XLeft, XRight, YTop, YBot, cardIndex, g);
				cardIndex++;
			}

			paintSelectedCard(g);
		}

		/************************************************************
		 * Painting cards to canvas.
		 * 
		 * @param container in which cards are to be displayed
		 ***********************************************************/
		public void paintSelectedCard(Graphics g){
			g.setFont(new Font("arial", Font.BOLD, 15));
			g.setColor(Color.BLACK);
			g.drawString("Selected Card: ", cardWd*6 + cardSp, cardHt+cardSp+10);
			g.drawPolygon(new int[]{(cardWd*6+cardSp),(cardWd*8+cardSp),(cardWd*8+cardSp),(cardWd*6+cardSp)},
					new int[]{(cardHt+cardSp*2),(cardHt+cardSp*2),(cardHt*3+cardSp*2),(cardHt*3+cardSp*2)}, 4);
			if(selectedCard != null){
				g.setColor(cardColor(selectedCard.getType()));
				g.fillPolygon(new int[]{(cardWd*6+cardSp)+1,(cardWd*8+cardSp)-1,(cardWd*8+cardSp)-1,(cardWd*6+cardSp+1)},
						new int[]{(cardHt+cardSp*2)+1,(cardHt+cardSp*2)+1,(cardHt*3+cardSp*2)-1,(cardHt*3+cardSp*2)-1}, 4);
				g.setColor(Color.BLACK);
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(2));
				g2.drawLine(cardWd*6+cardSp,cardHt+cardSp*2+10,cardWd*8+cardSp,cardHt+cardSp*2+10);
				g2.drawLine(cardWd*6+cardSp,cardHt+cardSp*2+40,cardWd*8+cardSp,cardHt+cardSp*2+40);
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString(selectedCard.getName(),(cardWd*6+cardSp*2),(cardHt+cardSp*2+35));
				g.setFont(new Font("arial", Font.PLAIN, 15));
				g.drawString("Cost: $"+selectedCard.getCost()+" mill",(cardWd*6+cardSp*2),(cardHt+cardSp*2+55));
				String[] des =  selectedCard.getDescription().split(";");
				int HtCount = 10;
				g.setFont(new Font("arial", Font.PLAIN, 10));

				for(String s:des){
					g.drawString(s,(cardWd*6+cardSp*2),(cardHt+cardSp*5+HtCount));
					HtCount += 10;
				}

				try{
					g.drawImage(ImageIO.read(selectedCard.getImg()), cardWd*6+cardSp+40, 
							cardHt+cardSp*5+50, 120, 120, null);
				}catch (Exception e){
					e.printStackTrace();
				}
			}

			this.validate();
			this.repaint();
		}

		/************************************************************
		 * Add card to container.
		 * 
		 * @param X coordinate of left edge of card
		 * @param X coordinate of right edge of card
		 * @param Y coordinate of top edge of card
		 * @param Y coordinate of bottom edge of card
		 * @param index of card
		 * @param container in which card is displayed
		 ***********************************************************/
		private void addCard(int XLeft, int XRight, int YTop, int YBot, int cardIndex, Graphics g){
			g.drawPolygon(new int[]{XLeft,XRight,XRight,XLeft},new int[]{YTop,YTop,YBot,YBot},4);
			g.setColor(Color.WHITE);
			g.drawPolygon(new int[]{XLeft+1,XRight+1,XRight+1,XLeft+1},new int[]{YTop+1,YTop+1,YBot+1,YBot+1},4);
			g.setColor(Color.BLACK);
			g.drawPolygon(new int[]{XLeft+2,XRight+2,XRight+2,XLeft+2},new int[]{YTop+2,YTop+2,YBot+2,YBot+2},4);
			g.setColor(Color.WHITE);
			g.drawPolygon(new int[]{XLeft+3,XRight+3,XRight+3,XLeft+3},new int[]{YTop+3,YTop+3,YBot+3,YBot+3},4);
			g.setColor(Color.BLACK);
			g.drawPolygon(new int[]{XLeft+4,XRight+4,XRight+4,XLeft+4},new int[]{YTop+4,YTop+4,YBot+4,YBot+4},4);
			g.setColor(cardColor(crds.get(cardIndex).get(cardIndex).getType()));
			g.fillPolygon(new int[]{XLeft+5,XRight+4,XRight+4,XLeft+5},new int[]{YTop+5,YTop+5,YBot+4,YBot+4},4);
			g.setColor(Color.BLACK);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(XLeft+5,YTop+10,XRight+3,YTop+10);
			g2.drawLine(XLeft+5,YTop+35,XRight+3,YTop+35);
			String nm = crds.get(cardIndex).get(cardIndex).getName();

			if(nm.length() <= 8)
				g.setFont(new Font("arial", Font.BOLD, 15));
			else
				g.setFont(new Font("arial", Font.BOLD, 10));

			g.drawString(nm, XLeft+20,YTop+30);

			try{
				g.drawImage(ImageIO.read(crds.get(cardIndex).get(0).getImg()), XLeft+25, YTop+50, 60, 60, null);
			}catch (Exception e){
				e.printStackTrace();
			}
			cardXCoords.add(new int[]{XLeft+5,XRight+4});
			cardYCoords.add(new int[]{YTop+5,YBot+4});
		}

	}

	/************************************************************
	 * Class to display and handle selection of player cards.
	 ***********************************************************/
	private class playerBoardCanvas extends JPanel{
		private static final long serialVersionUID = 1L;
		private ArrayList<Card> playerHand;
		private ArrayList<int[]> playerCardXCoords = new ArrayList<int[]>();
		private ArrayList<int[]> playerCardYCoords = new ArrayList<int[]>();
		private final int cardWd = 100;
		private final int cardHt = 120;
		private final int cardSp = 20;

		/************************************************************
		 * Constructor for objects of type playerBoardCanvas.
		 ***********************************************************/

		public playerBoardCanvas(){
		}

		/************************************************************
		 * Set player hand.
		 * 
		 * @param players hand
		 ***********************************************************/
		public void setPlayerHand(ArrayList<Card> playerHand){
			this.playerHand = playerHand;
		}

		/************************************************************
		 * Setting background color of cards.
		 * 
		 * @param card type
		 * @return background color
		 ***********************************************************/
		private Color cardColor(String type){
			switch(type){
			case "point":
				return pointColor;
			case "money":
				return moneyColor;
			case "action":
				return actionColor;
			case "attack":
				return attackColor;
			case "defense":
				return defenseColor;
			default:
				return Color.WHITE;
			}
		}

		/************************************************************
		 * Setting up and displaying cards.
		 * 
		 * @param container in which cards are to be displayed
		 ***********************************************************/
		public void paintComponent(Graphics g){
			int cardIndex = 0, XLeft = 0, XRight = 0, YTop = 0, YBot = 0, row = 0, xMult = 0;

			// Setting up card edge coordinates
			for(Card c: playerHand){
				row = cardIndex / 7;
				g.setColor(Color.BLACK);
				xMult = cardIndex % 7;
				//row = Math.floorDiv(cardIndex,8);
				XLeft = cardWd*(xMult) + cardSp*(xMult);
				XRight = cardWd*(xMult+1) + cardSp*(xMult);
				YTop = cardHt*row+cardSp*row;
				YBot = cardHt*(row+1)+cardSp*row;
				addCard(XLeft, XRight, YTop, YBot, cardIndex, g);
				cardIndex++;
			}

			playerBoard.setPreferredSize(new Dimension(600,cardHt*(row+1)+cardSp*row));
			playerBoardPan.repaint();
			playerBoardPan.revalidate();

		}

		/************************************************************
		 * Add card to container.
		 * 
		 * @param X coordinate of left edge of card
		 * @param X coordinate of right edge of card
		 * @param Y coordinate of top edge of card
		 * @param Y coordinate of bottom edge of card
		 * @param index of card
		 * @param container in which card is displayed
		 ***********************************************************/
		private void addCard(int XLeft, int XRight, int YTop, int YBot, int cardIndex, Graphics g){
			g.drawPolygon(new int[]{XLeft,XRight,XRight,XLeft},new int[]{YTop,YTop,YBot,YBot},4);
			g.setColor(cardColor(playerHand.get(cardIndex).getType()));
			g.fillPolygon(new int[]{XLeft+1,XRight-1,XRight-1,XLeft+1},new int[]{YTop+1,YTop+1,YBot-1,YBot-1},4);
			g.setColor(Color.BLACK);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(XLeft,YTop+10,XRight,YTop+10);
			g2.drawLine(XLeft,YTop+35,XRight,YTop+35);
			String nm = playerHand.get(cardIndex).getName();

			if(nm.length() <= 8)
				g.setFont(new Font("arial", Font.BOLD, 15));
			else
				g.setFont(new Font("arial", Font.BOLD, 10));

			g.drawString(nm, XLeft+20,YTop+30);

			try{
				g.drawImage(ImageIO.read(playerHand.get(cardIndex).getImg()), XLeft+25, YTop+50, 60, 60, null);
			}catch (Exception e){
				e.printStackTrace();
			}

			playerCardXCoords.add(new int[]{XLeft+5,XRight+4});
			playerCardYCoords.add(new int[]{YTop+5,YBot+4});

		}

	}

	/************************************************************
	 * Check if user clicked on a card.
	 * 
	 * @param arraylist of cards in container
	 * @param mouse click event
	 * @return true if card was clicked on
	 ***********************************************************/
	public Boolean checkCanvas(ArrayList<Card> c, MouseEvent event){
		Point point = event.getPoint();
		int clickX = point.x;
		int clickY = point.y;
		int x = 0;
		int y = 0;
		gameBoard.setSelectedCard(null);

		for(int i = 0; i < c.size(); i++){
			x = gameBoard.getCardXCoords().get(i)[0];
			y = gameBoard.getCardYCoords().get(i)[0];

			if((clickX >= x && clickX <= x + 120) && (clickY >= y && clickY <= y + 160)){
				selectedCardIndex = i;
				gameBoard.setSelectedCard(c.get(i));
				gameBoard.setSelectedIndex(selectedCardIndex);
				return true;
			}
		}
		return false;
	}

	/************************************************************
	 * Class to handle user interaction within canvas.
	 ***********************************************************/
	private class CanvasListener implements MouseListener, MouseMotionListener{

		/************************************************************
		 * Respond to user interaction within canvas.
		 * 
		 * @param mouse event (moved or clicked)
		 ***********************************************************/
		public void mouseClicked(MouseEvent event){

			// User clicked within the game board
			if(event.getSource() == gameBoard){
				if(checkCanvas(gameRefDeck, event)){
					playBUT.setEnabled(false);
					purchaseBUT.setEnabled(true);
				}
				gameBoardPan.repaint();
			}

			// User clicked within player board
			if(event.getSource() == playerBoard){
				if(checkCanvas(current.getHand(), event)){
					playBUT.setEnabled(true);
					purchaseBUT.setEnabled(false);
				}
			}
		}

		public void mouseEntered(MouseEvent event){

		}
		public void mouseExited(MouseEvent event){

		}
		public void mousePressed(MouseEvent event){

		}
		public void mouseReleased(MouseEvent event){

		}
		public void mouseDragged(MouseEvent event){

		}

		// User moved mouse
		public void mouseMoved(MouseEvent event){

		}
	}

	/************************************************************
	 * Main function to initiate game.
	 ***********************************************************/
	public static void main(String[] args)
	{
		WarefareGUI GUI = new WarefareGUI();
		GUI.runGame();
	}
}
