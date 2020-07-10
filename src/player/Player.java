package player;

import pokerComponents.Hand;

public class Player {
	private Hand hand = null;
	private String name = "";
	
	public Player(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setHand(String h) {
		hand = new Hand(h);
	}
	
	public void setHand(Hand h) {
		hand = h;
	}
	
	public Hand getHand() {
		return hand;
	}
}
