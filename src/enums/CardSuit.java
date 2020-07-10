package enums;

import java.util.HashMap;
import java.util.Map;

public enum CardSuit {
	CLUBS("c"),
	DIAMONDS("d"),
	HEARTS("h"),
	SPADES("s");
	
	private final String suit;
	private static Map<String, CardSuit> lookup = new HashMap<String, CardSuit>();
	
	static {
		for (CardSuit s : CardSuit.values()) {
			lookup.put(s.toString(), s);
		}
	}
	
	private CardSuit(String suit) { this.suit = suit; }
	public String toString() { return this.suit; }
	public static CardSuit get(String suitStr) { return lookup.get(suitStr); }
}
