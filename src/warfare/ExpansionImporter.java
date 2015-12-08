package warfare;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import java.util.*;


public class ExpansionImporter {
	private JFileChooser fc;
	
	public 	File dir, cl;
	
	public	File[] files;
	
	public ArrayList<ArrayList<Card>> cards;
	
	private Scanner s;
	public ExpansionImporter(Component sender) throws FileNotFoundException {
		cards = new ArrayList<ArrayList<Card>>();
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.showOpenDialog(sender);
		dir = fc.getSelectedFile();
		files = dir.listFiles();
		for (File f : files) {
			if (f.getName().equals("cardList.txt")) {
				cl = f;
			}
		}
		s = new Scanner(cl);
		while(s.hasNextLine()){
			makeCard(s.nextLine());
		}
		s.close();
		

	}
	
	public Card makeCard(String fmt) {
		String[] parts = fmt.split("/");
		File img = null;
		for (File f : files){
			if (parts[5].equals(f.getName()))
				img = f;
		}
		Card card = null;
		int a,b,c,d;
		switch(parts[0]) {
		case "action":
			a = Character.getNumericValue(parts[4].charAt(0));
			b = Character.getNumericValue(parts[4].charAt(1));
			c = Character.getNumericValue(parts[4].charAt(2));
			d = Character.getNumericValue(parts[4].charAt(3));
	
			card = new ActionCard(parts[1], Integer.parseInt(parts[2]), parts[3], a, b, c, d, "action", img);
			makeStack(card, Integer.parseInt(parts[6]));
			break;
		case "attack":
			char[] code = parts[4].toCharArray();
			int[] toSend = new int[code.length];
			
			for (int i = 0; i < code.length; i++) {
				toSend[i] = Character.getNumericValue(code[i]);
			}
			
			card = new AttackCard(parts[1], Integer.parseInt(parts[2]), parts[3], toSend, "attack", img);
			makeStack(card, Integer.parseInt(parts[6]));
			break;
		default:
			break;
			
		}
		return card;
	}
	
	public void makeStack(Card c,int n){
		ArrayList<Card> cardStack = new ArrayList<Card>();
		for(int i = 0;i<n;i++){
			cardStack.add(c.cardClone(c));
		}
		cards.add(cardStack);
	}
	
	
}
