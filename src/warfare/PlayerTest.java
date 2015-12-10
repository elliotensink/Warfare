package warfare;

import static org.junit.Assert.*;


import org.junit.Test;

public class PlayerTest {
	Player p = new Player();
	
	@Test
	public void drawCardsTest1() {

		for (int i = 0; i < 5; i++){
			p.getDeck().add(new Card());
		}
		p.getDiscard().add(new Card());
		p.drawCards(5);
		assertTrue(p.getHand().size() == 5);
		assertTrue(p.getDeck().size() == 0);
		p.getHand().clear();
		
	}
	
	@Test
	public void drawCardsTest2() {
		for (int i = 0; i < 3; i++) {
			p.getDiscard().add(new Card());
		}
		p.drawCards(3);
		assertTrue(p.getHand().size() == 3);
		assertTrue(p.getDiscard().size() == 0);
		p.getHand().clear();
		p.getDiscard().clear();
		
	}
	
	@Test
	public void addMoneyTest() {
		p.addMoney(5);
		assertTrue(p.getCurrentMoney() == 5);
		p.setCurrentMoney(0);
	}
	
	@Test
	public void calcMoneyTest() {
		MoneyCard one = new MoneyCard("one", 0, "", 1, "money", null);
		for (int i = 0; i<5; i++) {
			p.getHand().add(one);
		}
		p.calcMoney();
		assertTrue(p.getCurrentMoney() == 5);
		p.getHand().clear();
	}
	
	@Test
	public void calcPointsTest1() {
		PointCard point = new PointCard("one", 0, "", 1, "point", null);
		for (int i = 0; i < 5; i++) {
			p.getDeck().add(point);
		}
		assertTrue(p.calcPoints() == 5);
		p.getDeck().clear();
	}
	
	@Test
	public void calcPointsTest2() {
		PointCard one = new PointCard("one", 0, "", 1, "point", null);
		PointCard two = new PointCard("one", 0, "", 2, "point", null);
		p.getDeck().add(one);
		p.getDeck().add(two);
		assertTrue(p.calcPoints() == 3);
		p.getDeck().clear();
	}
	
	@Test
	public void discardTest() {
		for (int i = 0; i < 3; i++) {
			p.getHand().add(new Card());
		}
		for (int i = 0; i < 10; i++) {
			p.getDeck().add(new Card());
		}
		
		p.discard();
		assertTrue(p.getDiscard().size() == 3);
		assertTrue(p.getDeck().size() == 5);
		assertTrue(p.getHand().size() == 5);
	}
	
	@Test
	public void discardOneTest() {
		Card k = new Card();
		p.getHand().add(k);
		p.discardOne(0);
		assertTrue(p.getDiscard().size() == 1);
		assertTrue(p.getDiscard().get(0) == k);
		assertTrue(p.getHand().size() == 0);
		p.getHand().clear();
		p.getDiscard().clear();
	}
	
	@Test
	public void getCardTest1() {
		p.getHand().add(new MoneyCard());
		assertTrue(p.getCard(0) instanceof MoneyCard);
		p.getHand().clear();
	}
	
	@Test
	public void getCardTest2() {
		p.getHand().add(new PointCard());
		assertTrue(p.getCard(0) == p.getHand().get(0));
		p.getHand().clear();
	}
	
	@Test
	public void getCardTest3() {
		p.getHand().add(new MoneyCard());
		p.getHand().add(new PointCard());
		assertTrue(p.getCard(0) == p.getHand().get(0));
		assertTrue(p.getCard(1) == p.getHand().get(1));
		p.getHand().clear();
	}
	
	@Test
	public void setFactionTest(){
		p.setFaction(Faction.BLUE);
		assertTrue(p.getFaction().equals(Faction.BLUE));
	}
	

}

