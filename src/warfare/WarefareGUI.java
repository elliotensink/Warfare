package warfare;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.*;
import java.util.*;

public class WarefareGUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private Game game;

	private ArrayList<String> names;

	private int numPlayers;
	
	private Player current;

	private JButton playBUT;
	private JButton purchaseBUT;
	private JButton endTurnBUT;
	private JButton helpBUT;
	private JButton showBoardBUT;
	private JButton continueBUT;
	private JButton[] board;
	private JButton[] hand;

	private gameCanvas gameBoard;
	private CanvasListener cl;

	private JLabel nameLAB;
	private JLabel infoName[];
	private JLabel infoPlayer[];
	private JLabel cardInfo;

	private JPanel gameBoardPan;
	private JPanel playerPAN;
	private JPanel infoPAN;
	private JPanel handPAN;
	private JPanel framePAN;
	private JPanel menuPAN;
	private JPanel playInfo[];

	private JTextField[] nameFLDs;

	private JFrame frame;
	private JFrame nameFrame;;


	public WarefareGUI(){
		playBUT = new JButton("Play Action");
		purchaseBUT = new JButton("Purchase Card");
		endTurnBUT = new JButton("End Turn");
		helpBUT = new JButton("Help");
		showBoardBUT = new JButton("Show Gameboard");
		gameBoardPan = new JPanel();
		playerPAN = new JPanel();
		infoPAN = new JPanel();
		handPAN = new JPanel();
		framePAN = new JPanel();
		menuPAN = new JPanel();
		nameLAB = new JLabel();

		names = new ArrayList<String>();
		getNames();


		playInfo = new JPanel[numPlayers];
		infoName = new JLabel[numPlayers];
		infoPlayer = new JLabel[numPlayers];

		game = new Game(numPlayers);
		frame = new JFrame("Warefare");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1100, 500);


		cl = new CanvasListener();

		playerPAN.setLayout(new BoxLayout(playerPAN, BoxLayout.Y_AXIS));
		framePAN.setLayout(new BorderLayout());
		infoPAN.setLayout(new BoxLayout(infoPAN, BoxLayout.Y_AXIS));
		menuPAN.setLayout(new BoxLayout(menuPAN, BoxLayout.Y_AXIS));



		gameBoard = new gameCanvas(game.allCards);
		gameBoard.setPreferredSize(new Dimension(850,410));
		gameBoard.addMouseListener(cl);
		gameBoard.addMouseMotionListener(cl);
		gameBoardPan.add(gameBoard);
		gameBoardPan.setBackground(new Color(237,227,183));
		gameBoardPan.setBorder(BorderFactory.createBevelBorder(NORMAL, Color.BLACK, Color.GRAY));


		cardInfo = new JLabel("");


		//nameLAB.setText("Player Cards:");

		/*hand = new JButton[current.getHand().size()];
		for(int i=0; i<current.getHand().size(); i++){
			hand[i] = new JButton(current.getHand().get(i).getName());
			handPAN.add(hand[i]);
			hand[i].addActionListener(this);
		}*/

		playBUT.addActionListener(this);
		purchaseBUT.addActionListener(this);
		endTurnBUT.addActionListener(this);
		helpBUT.addActionListener(this);

		/* Don't think we'll actually need the play button or the purchase button
		 *  since the player can just select a card to buy/play it, but I threw 
		 *  them in just in case...
		 */
		menuPAN.add(playBUT);;
		menuPAN.add(purchaseBUT);
		menuPAN.add(endTurnBUT);
		menuPAN.add(helpBUT);
		menuPAN.add(cardInfo);
		playerPAN.add(nameLAB);
		playerPAN.add(handPAN);
		framePAN.add(gameBoardPan, BorderLayout.CENTER);
		framePAN.add(playerPAN, BorderLayout.SOUTH);
		framePAN.add(infoPAN, BorderLayout.WEST);
		framePAN.add(menuPAN, BorderLayout.EAST);

		frame.add(framePAN);
		frame.setResizable(false);
		//frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);

		/* This method will have to be edited a lot, as of now it
		 * basically just runs the text based version from here.
		 */
		runGame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == continueBUT){
			for(int i=0; i<numPlayers; i++){
				names.add(nameFLDs[i].getText());
			}
			setupInfo();
			nameFrame.setVisible(false);
			frame.setVisible(true);
		}

		if(e.getSource() == playBUT){
			// Play action
		}

		if(e.getSource() == purchaseBUT){
			if(gameBoard.isSelected()){
				System.out.println(current.getCurrentMoney());
				if(!game.purchaseCard(gameBoard.getSelectedIndex())){
					JOptionPane.showMessageDialog(frame, "Sorry, your'e broke! :(", "Error", JOptionPane.ERROR_MESSAGE);
				}
				checkTurn();
				setupInfo();
				System.out.println("\n\n\nDiscard:" + current.getDiscard());
			}else
				JOptionPane.showMessageDialog(frame, "Please select a card.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		if(e.getSource() == endTurnBUT){
			// End turn
		}

		if(e.getSource() == helpBUT){
			// Display help
		}

		//		for(JButton b: board){
		//			if(e.getSource() == b){
		//				// Purchasing chosen card
		//			}
		//		}

		for(JButton b: hand){
			if(e.getSource() == b){
				// Play Card
			}
		}
	}

	private void runGame(){
		while(!game.gameFinished){
			//game.playerTurn();
			game.checkGameStatus();
		}
		game.endGame();
	}
	
	private void checkTurn(){
		if(game.purchases == 0 & game.actions == 0){
			game.nextPlayer();
			current = game.getPlayers()[game.getCurrentPlayer()];
		}
	}

	private void setupInfo(){
		current = game.getPlayers()[game.getCurrentPlayer()];
		infoPAN.removeAll();
		handPAN.removeAll();
		
		for(int i=0; i<numPlayers; i++){
			playInfo[i] = new JPanel();
			infoName[i] = new JLabel();
			infoPlayer[i] = new JLabel();

			infoName[i].setText(names.get(i));
			infoPlayer[i].setText("<html>$"+game.getPlayers()[i].getCurrentMoney()+ "<BR>"+
					game.getPlayers()[i].getPoints()+" points<html>");
			playInfo[i].add(infoName[i]);
			playInfo[i].add(infoPlayer[i]);
			infoPAN.add(playInfo[i]);
		}
		
		nameLAB.setText(names.get(game.getCurrentPlayer()) + ": " + game.actions + " actions, " + game.purchases + " purchases.");
		
		hand = new JButton[current.getHand().size()];
		for(int i=0; i<current.getHand().size(); i++){
			hand[i] = new JButton(current.getHand().get(i).getName());
			handPAN.add(hand[i]);
			hand[i].addActionListener(this);
		}
	}

	private void getNames(){
		nameFrame = new JFrame();
		JLabel nameLAB = new JLabel("Player Names:");
		JPanel namePAN = new JPanel();
		continueBUT = new JButton("Continue");

		String[] numOpts = {"2", "3", "4"};
		String s = (String)JOptionPane.showInputDialog(nameFrame, "How many Players?",
				"Number of players", JOptionPane.PLAIN_MESSAGE, null, numOpts, "2");
		numPlayers = Integer.parseInt(s);

		namePAN.setLayout(new BoxLayout(namePAN, BoxLayout.Y_AXIS));

		namePAN.add(nameLAB);

		nameFLDs = new JTextField[numPlayers];
		for(int i=0; i<numPlayers; i++){
			nameFLDs[i] = new JTextField("Player "+(i+1), 30);
			namePAN.add(nameFLDs[i]);
		}

		namePAN.add(continueBUT);

		continueBUT.addActionListener(this);
		nameFrame.add(namePAN);
		nameFrame.setVisible(true);
		nameFrame.pack();
	}

	private class gameCanvas extends JPanel 
	{
		private static final long serialVersionUID = 1L;
		private Color actionColor = new Color(255,194,97);
		private	Color moneyColor = new Color(231,237,55);
		private Color pointColor = new Color(154,245,157);
		private Color attackColor = new Color(237,69,69);
		private Color defenseColor = new Color(173,221,237);
		private ArrayList<ArrayList<Card>> crds;
		private ArrayList<int[]> cardXCoords = new ArrayList<int[]>();
		private ArrayList<int[]> cardYCoords = new ArrayList<int[]>();
		private int selectedIndex;

		private final int cardWd = 100;
		private final int cardHt = 120;
		private final int cardSp = 20;

		private Boolean selected;
		private Card selectedCard;

		public gameCanvas(ArrayList<ArrayList<Card>> crds)
		{
			this.crds = crds;
			selected = false;
			selectedCard = null;
		}

		private Color cardColor(String type)
		{
			switch(type)
			{
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

		public ArrayList<int[]> getCardXCoords()
		{
			return cardXCoords;
		}

		public ArrayList<int[]> getCardYCoords()
		{
			return cardYCoords;
		}

		public void setSelectedCard(Card c)
		{
			selectedCard = c;
		}
		
		public int getSelectedIndex(){
			return selectedIndex;
		}
		
		public void setSelectedIndex(int i){
			selectedIndex = i;
		}
		
		public Boolean isSelected(){
			return selected;
		}
		
		public void setSelected(Boolean b){
			selected = b;
		}

		public void paintComponent(Graphics g)
		{
			int cardIndex = 0, XLeft = 0, XRight = 0, YTop = 0, YBot = 0, row = 0, xMult = 0;
			
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

			//Selected Card Window
			g.setFont(new Font("arial", Font.BOLD, 15));
			g.setColor(Color.BLACK);
			g.drawString("Selected Card: ", cardWd*6 + cardSp, cardHt+cardSp+10);
			g.drawPolygon(new int[]{(cardWd*6+cardSp),(cardWd*8+cardSp),(cardWd*8+cardSp),(cardWd*6+cardSp)},
					new int[]{(cardHt+cardSp*2),(cardHt+cardSp*2),(cardHt*3+cardSp*2),(cardHt*3+cardSp*2)}, 4);
			if(selectedCard != null)		
			{
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
				g.drawString("Cost: "+selectedCard.getCost(),(cardWd*6+cardSp*2),(cardHt+cardSp*2+55));
				String[] des =  selectedCard.getDescription().split(";");
				int HtCount = 10;
				g.setFont(new Font("arial", Font.PLAIN, 10));
				for(String s:des)
				{
					g.drawString(s,(cardWd*6+cardSp*2),(cardHt+cardSp*5+HtCount));
					HtCount += 10;
				}
				try 
				{
					g.drawImage(ImageIO.read(new File(selectedCard.getImg())), cardWd*6+cardSp+40, 
							cardHt+cardSp*5+50, 120, 120, null);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}


		}
		
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
			
			try {
				g.drawImage(ImageIO.read(new File(crds.get(cardIndex).get(0).getImg())), XLeft+25, YTop+50, 60, 60, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			cardXCoords.add(new int[]{XLeft+5,XRight+4});
			cardYCoords.add(new int[]{YTop+5,YBot+4});
			
		}

	}

	private class CanvasListener implements MouseListener, MouseMotionListener
	{

		public void mouseClicked(MouseEvent event) 
		{
			Point point = event.getPoint();
			int clickX = point.x;
			int clickY = point.y;
			int x = 0;
			int y = 0;
			gameBoard.setSelectedCard(null);
			for(int i = 0; i < game.referenceDeck.size(); i++)
			{
				x = gameBoard.getCardXCoords().get(i)[0];
				y = gameBoard.getCardYCoords().get(i)[0];
				if((clickX >= x && clickX <= x + 120) && (clickY >= y && clickY <= y + 160))
				{
					gameBoard.setSelectedCard(game.referenceDeck.get(i));
					gameBoard.setSelectedIndex(i);
					gameBoard.setSelected(true);
					break;
				}
			}
			gameBoardPan.repaint();
		}
		public void mouseEntered(MouseEvent event)
		{

		}
		public void mouseExited(MouseEvent event) 
		{

		}
		public void mousePressed(MouseEvent event) 
		{

		}
		public void mouseReleased(MouseEvent event) 
		{

		}
		public void mouseDragged(MouseEvent event) 
		{

		}
		public void mouseMoved(MouseEvent event) 
		{
			Point point = event.getPoint();
			int clickX = point.x;
			int clickY = point.y;
			int x = 0;
			int y = 0;
			for(int i = 0; i < game.referenceDeck.size(); i++)
			{
				x = gameBoard.getCardXCoords().get(i)[0];
				y = gameBoard.getCardYCoords().get(i)[0];
				if((clickX >= x && clickX <= x + 120) && (clickY >= y && clickY <= y + 160))
				{
					cardInfo.setText(game.referenceDeck.get(i).getName());
					break;
				}
			}
		}
	}



	public static void main(String[] args){
		new WarefareGUI();
	}
}
