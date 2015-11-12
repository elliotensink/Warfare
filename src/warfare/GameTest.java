package warfare;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import java.lang.reflect.*;

public class GameTest {
	Game g = new Game();
	Player[] p = g.getPlayers();

	@Test
	public void fillCardStackTest1() {
		MoneyCard c = new MoneyCard();
		ArrayList<Card> d  = g.fillCardStack(c, 5);		
		assertTrue(d.size() == 5);
	}
	
	@Test
	public void fillCardStackTest2(){
		PointCard c = new PointCard();
		ArrayList<Card> d = g.fillCardStack(c, 3);
		assertTrue(d.get(0) instanceof PointCard);
	}
	
	@Test
	public void setInitialPlayerCardTest() {
		g.createDeck();
		g.setIntialPlayerCards();
		Player[] p = g.getPlayers();
		for (Player k : p) {
			assertTrue(k.getDeck().size() == 5);
			assertTrue(k.getHand().size() == 5);
		}
		
	}
	
	@Test
	public void pActionTest1() {
		ActionCard c = new ActionCard();
		Player cp = p[g.getCurrentPlayer()];
		cp.getHand().clear();
		cp.getHand().add(c);
		assertTrue(g.pAction(cp, 0));
		cp.getHand().clear();
	}
	
	@Test
	public void pActionTest2() {
		AttackCard c = new AttackCard();
		Player cp = p[g.getCurrentPlayer()];
		cp.getHand().clear();
		cp.getHand().add(c);
		assertTrue(g.pAction(cp, 0));
		cp.getHand().clear();
	}
	
	@Test
	public void nextPlayerTest1() {
		g.setCurrentPlayer(0);
		g.nextPlayer();
		assertTrue(g.getCurrentPlayer() == 1);
	}
	
	@Test
	public void nextPlayerTest2() {
		g.setCurrentPlayer(1);
		g.nextPlayer();
		assertTrue(g.getCurrentPlayer() == 0);
	}
	
	@Test
	public void defenseTest() {
		DefenseCard c = new DefenseCard();
		Player cp = p[g.getCurrentPlayer()];
		cp.getHand().add(c);
		assertTrue(g.defense(cp));
	}

}
