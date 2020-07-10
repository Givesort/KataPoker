package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import enums.CardSuit;
import enums.CardValue;
import pokerComponents.Card;

class CardTest {

	@Test
	void invalidCardTest() {
		Card cards[] = new Card[] {
				new Card(null, null),
				new Card(null, CardSuit.HEARTS),
				new Card(null, CardSuit.CLUBS),
				new Card(null, CardSuit.DIAMONDS),
				new Card(null, CardSuit.SPADES),
				new Card(CardValue.ACE, null),
				new Card(CardValue.JACK, null),
				new Card(CardValue.NINE, null),
				new Card(CardValue.FOUR, null)};
		
		for ( Card card : cards ) {
			assertFalse( card.isValid() );
		}
	}
	
	@Test
	void validCardTest() {
		Card cards[] = new Card[] {
				new Card(CardValue.ACE, CardSuit.HEARTS),
				new Card(CardValue.SIX, CardSuit.CLUBS),
				new Card(CardValue.TWO, CardSuit.DIAMONDS),
				new Card(CardValue.SEVEN, CardSuit.SPADES),
				new Card(CardValue.ACE, CardSuit.SPADES),
				new Card(CardValue.JACK, CardSuit.DIAMONDS),
				new Card(CardValue.NINE, CardSuit.CLUBS),
				new Card(CardValue.FOUR, CardSuit.HEARTS)};
		
		for ( Card card : cards ) {
			assertTrue( card.isValid() );
		}
	}

}
