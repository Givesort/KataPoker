package KataPoker;

import enums.CardSuit;
import enums.CardValue;

public class Card {
	private CardValue value;
	private CardSuit suit;
	
	public Card(CardValue val, CardSuit s) {
		value = val;
		suit = s;
	}
	
	public CardValue getCardValue() {
		return value;
	}
	
	public CardSuit getCardSuit() {
		return suit;
	}
	
	public int getValue() {
		return value.getValue();
	}
	
	public boolean isValid() {
		return value != null && suit != null;
	}
	
	public boolean equals(Card card) {
		return value == card.getCardValue() && suit == card.getCardSuit();
	}
	
	public int compareTo(Card card) {
		if ( card == null )
			return 1;
		
		return value.getValue() - card.getCardValue().getValue();
	}
	
	public boolean sameValueAs(Card card) {
		return compareTo(card) == 0;
	}
	
	public String toString() {
		return value.toString() + " of " + suit.name();
	}
		
}
