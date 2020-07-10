package enums;

import java.util.HashMap;
import java.util.Map;

public enum CardValue {
	TWO(2, "2", "2"),
	THREE(3, "3", "3"),
	FOUR(4, "4", "4"),
	FIVE(5, "5", "5"),
	SIX(6, "6", "6"),
	SEVEN(7, "7", "7"),
	EIGHT(8, "8", "8"),
	NINE(9, "9", "9"),
	TEN(10, "t", "10"),
	JACK(11, "j", "Jack"),
	QUEEN(12, "q", "Queen"),
	KING(13, "k", "King"),
	ACE(14, "a", "Ace");
	
	private int value;
	private final String abbreviation;
	private final String description;
	private static final Map<String, CardValue> lookup = new HashMap<String, CardValue>();
	
	static {
		for (CardValue val : CardValue.values()) {
			lookup.put(val.abbreviation, val);
		}
	}
	
	private CardValue(int val, String abbr, String desc) { 
		this.value = val;
		this.abbreviation = abbr;
		this.description = desc;
	}
	
	public int getValue() { return value; }
	public String toString() { return description; }
	
	public static CardValue get(String valStr) { return lookup.get(valStr);	}
	
}
