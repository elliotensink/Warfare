package warfare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

public class WarefareGUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private ArrayList<String> names;
	
	private int numPlayers;
	
	private JButton playBUT;
	private JButton purchaseBUT;
	private JButton endTurnBUT;
	private JButton helpBUT;
	private JButton showBoardBUT;
	private JButton continueBUT;
	private JButton[] board;
	private JButton[] hand;
	
	private JLabel nameLAB;
	private JLabel infoName[];
	private JLabel infoPlayer[];
	
	private JPanel boardPAN;
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
		boardPAN = new JPanel();
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
		frame.setBounds(100, 100, 1000, 1000);
		
		boardPAN.setLayout(new GridLayout(4,4));
		playerPAN.setLayout(new BoxLayout(playerPAN, BoxLayout.Y_AXIS));
		framePAN.setLayout(new BorderLayout());
		infoPAN.setLayout(new BoxLayout(infoPAN, BoxLayout.Y_AXIS));
		menuPAN.setLayout(new BoxLayout(menuPAN, BoxLayout.Y_AXIS));
		
		board = new JButton[game.referenceDeck.size()];
		for(int i=0; i<game.referenceDeck.size(); i++){
			board[i] = new JButton(game.referenceDeck.get(i).getName());
			boardPAN.add(board[i]);
			board[i].addActionListener(this);
		}
		
		//nameLAB.setText("Player Cards:");
		
		hand = new JButton[game.getPlayers()[game.getCurrentPlayer()].getHand().size()];
		for(int i=0; i<game.getPlayers()[game.getCurrentPlayer()].getHand().size(); i++){
			hand[i] = new JButton(game.getPlayers()[game.getCurrentPlayer()].getHand().get(i).getName());
			handPAN.add(hand[i]);
			hand[i].addActionListener(this);
		}
		
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
		playerPAN.add(nameLAB);
		playerPAN.add(handPAN);
		framePAN.add(boardPAN, BorderLayout.CENTER);
		framePAN.add(playerPAN, BorderLayout.SOUTH);
		framePAN.add(infoPAN, BorderLayout.WEST);
		framePAN.add(menuPAN, BorderLayout.EAST);
		
		frame.add(framePAN);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		
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
			nameLAB.setText(names.get(game.getCurrentPlayer()) + ": " + game.actions + " actions, " + game.purchases + " purchases.");
			nameFrame.setVisible(false);
			frame.setVisible(true);
		}
		
		if(e.getSource() == playBUT){
			// Play action
		}
		
		if(e.getSource() == purchaseBUT){
			// Purchase a card
		}
		
		if(e.getSource() == endTurnBUT){
			// End turn
		}
		
		if(e.getSource() == helpBUT){
			// Display help
		}
		
		for(JButton b: board){
			if(e.getSource() == b){
				// Purchasing chosen card
			}
		}
		
		for(JButton b: hand){
			if(e.getSource() == b){
				// Play Card
			}
		}
	}
	
	private void runGame(){
		while(!game.gameFinished){
			game.playerTurn();
			game.checkGameStatus();
		}
		game.endGame();
	}
	
	private void setupInfo(){
		for(int i=0; i<numPlayers; i++){
			playInfo[i] = new JPanel();
			infoName[i] = new JLabel();
			infoPlayer[i] = new JLabel();
			
			infoName[i].setText(names.get(i));
			infoPlayer[i].setText("<html>$"+game.getPlayers()[i].getCurrentMoney()+" million<BR>"+
									game.getPlayers()[i].getPoints()+" points<html>");
			playInfo[i].add(infoName[i]);
			playInfo[i].add(infoPlayer[i]);
			infoPAN.add(playInfo[i]);
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

	public static void main(String[] args){
		new WarefareGUI();
	}
}
