package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pokerComponents.Hand;

class HandTest {

	@Test
	void invalidHandTest() {
		Hand hands[] = new Hand[] {
				new Hand(""), // empty string
				new Hand("asdfva"), // invalid string
				new Hand("ah kh qh jh dd"), // invalid card value
				new Hand("ah kh qh jh t7"), // invalid card suit
				new Hand("ah kh qh jh th 9h"), // too many cards
				new Hand("ah kh qh jh"), // too few cards
		};
		
		for ( Hand hand : hands ) {
			assertFalse(hand.isValid());
		}
	}
	
	@Test
	void validHandTest() {
		Hand hands[] = new Hand[] {
				new Hand("ah kh qh jh th"), // straight flush
				new Hand("2h 2d 2s 2c 3c"), // four of a kind
				new Hand("5h 5d 5s 2c 2d"), // full house
				new Hand("3d 7d td qd jd"), // flush
				new Hand("6c 8h 7s 5h 4d"), // straight
				new Hand("jh jd js th 9d"), // three of a kind
				new Hand("th td 7s kc kd"), // two pair
				new Hand("9h 9d ts qc 2d"), // pair
				new Hand("9h 3d ts qc 2d"), // high card
				new Hand("AH KH QH JH TH"), // Capital letters
				new Hand("   9h 3d ts qc 2d   "), // non trimmed string
		};
		
		for ( Hand hand : hands ) {
			assertTrue(hand.isValid());
		}
	}

}
