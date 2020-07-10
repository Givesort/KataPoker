package enums;

public enum HandArrangement {
	STRAIGHTFLUSH(8, "straight flush"),
	FOUROFAKIND(7, "four of a kind"),
	FULLHOUSE(6, "full house"),
	FLUSH(5, "flush"),
	STRAIGHT(4, "straight"),
	THREEOFAKIND(3, "three of a kind"),
	TWOPAIR(2, "two pair"),
	PAIR(1, "pair"),
	HIGHCARD(0, "high card");
	
	private int value;
	private String description;
	private HandArrangement(int val, String desc) { this.value = val; this.description = desc; }
	public int getValue() { return this.value; }
	public String toString() { return this.description; }
}
