package warfare;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

public class WarfareTest 
{
	
	@Test
	public void testInitialGameSetup() 
	{
		Game g = new Game();
		assertTrue(g.getNumPlayers()==2);
		//Check initial cards for Player 1
		Player p = g.getPlayers()[g.getCurrentPlayer()];
		int pointCount = 0;
		int moneyCount = 0;
		for(Card c : p.getDeck())
		{
			if(c instanceof PointCard)
				pointCount++;
			if(c instanceof MoneyCard)
				moneyCount++;
		}
		for(Card c : p.getHand())
		{
			if(c instanceof PointCard)
				pointCount++;
			if(c instanceof MoneyCard)
				moneyCount++;
		}
		System.out.println(pointCount);
		assertTrue(pointCount == 3);
		assertTrue(moneyCount == 7);
		g.nextPlayer();
		//Check initial cards for Player 2
		p = g.getPlayers()[g.getCurrentPlayer()];
		pointCount = 0;
		moneyCount = 0;
		for(Card c : p.getDeck())
		{
			if(c instanceof PointCard)
				pointCount++;
			if(c instanceof MoneyCard)
				moneyCount++;
		}
		for(Card c : p.getHand())
		{
			if(c instanceof PointCard)
				pointCount++;
			if(c instanceof MoneyCard)
				moneyCount++;
		}
		assertTrue(pointCount == 3);
		assertTrue(moneyCount == 7);
	}
	
	@Test
	public void PlayerTest() 
	{
		Game g = new Game();
		Player p = g.getPlayers()[g.getCurrentPlayer()];
		assertTrue(p.getDeck().size()==5);
		assertTrue(p.getHand().size()==5);
		p.getHand().add(p.draw());
		assertTrue(p.getDeck().size()==4);
		assertTrue(p.getHand().size()==6);
		p.setCurrentMoney(0);
		p.discard();
		assertTrue(p.getDeck().size()==5);
		assertTrue(p.getHand().size()==5);
		assertTrue(p.getDiscard().size()==0);
		int moneyCount = 0;
		int pointCount = 0;
		for(Card c : p.getHand())
		{
			if(c instanceof PointCard)
				pointCount++;
			if(c instanceof MoneyCard)
				moneyCount++;
		}
		for(Card c : p.getDeck())
		{
			if(c instanceof PointCard)
				pointCount++;
		}
		assertTrue(p.getCurrentMoney()==moneyCount);
		p.addMoney(3);
		assertTrue(p.getCurrentMoney()==moneyCount+3);
		assertTrue(p.calcPoints()==pointCount);
	}
	
	@Test
	public void testCard()
	{
		Card c = new Card("Test",0,"Description","Test");
		Card c2 = c.cardClone(c);
		assertTrue(c2 instanceof Card);
		assertTrue(c.toString().equals("Test, Test, "));
		assertTrue(c2.toString().equals("Test, Test, "));
	}
	
	@Test
	public void testMoneyCard()
	{
		MoneyCard c = new MoneyCard("Money",0,"Description",1,"money");
		MoneyCard c2 = c.cardClone(c);
		assertTrue(c2 instanceof MoneyCard);
		assertTrue(c.toString().equals("Money, money, $1 million"));
		assertTrue(c2.toString().equals("Money, money, $1 million"));
	}
	
	@Test
	public void testPointCard()
	{
		PointCard c = new PointCard("Point",0,"Description",1,"point");
		PointCard c2 = c.cardClone(c);
		assertTrue(c2 instanceof PointCard);
		assertTrue(c.toString().equals("Point, point, 1 Point(s)"));
		assertTrue(c2.toString().equals("Point, point, 1 Point(s)"));
	}
	
	@Test
	public void testActionCard()
	{
		PointCard c = new PointCard("Point",0,"Description",1,"point");
		PointCard c2 = c.cardClone(c);
		assertTrue(c2 instanceof PointCard);
		assertTrue(c.toString().equals("Point, point, 1 Point(s)"));
		assertTrue(c2.toString().equals("Point, point, 1 Point(s)"));
	}

}
