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
	public ArrayList<Card> cards;
	private Scanner s;
	public ExpansionImporter(Component sender) throws FileNotFoundException {
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
		Scanner s = new Scanner(cl);
		while(s.hasNextLine()){
			cards.add(makeCard(s.nextLine()));
		}
		s.close();
		

	}
	
	public Card makeCard(String fmt) {
		String[] parts = fmt.split("/");
		Card card = null;
		int a,b,c,d;
		switch(parts[0]) {
		case "action":
			a = Character.getNumericValue(parts[4].charAt(0));
			b = Character.getNumericValue(parts[4].charAt(1));
			c = Character.getNumericValue(parts[4].charAt(2));
			d = Character.getNumericValue(parts[4].charAt(3));
	
			card = new ActionCard(parts[1], Integer.parseInt(parts[2]), parts[3], a, b, c, d, "action", parts[5]);
			break;
		case "attack":
			a = Character.getNumericValue(parts[4].charAt(0));
			b = Character.getNumericValue(parts[4].charAt(1));
			c = Character.getNumericValue(parts[4].charAt(2));
			d = Character.getNumericValue(parts[4].charAt(3));
			card = new AttackCard(parts[1], Integer.parseInt(parts[2]), parts[3], new int[] {a,b,c,d}, "attack", parts[5]);
			break;
		default:
			break;
			
		}
		return card;
	}
	
	
}
