package KataPoker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import enums.CardSuit;
import enums.CardValue;
import enums.HandArrangement;

public class Hand {
	private int handSize = 5;
	
	private List<Card> cards;
	
	private boolean isStraight = false;
	private boolean isFlush = false;
	private List<Card> pairCards = null; // Contains each card in all pairs in the hand. 1 pair -> size = 2, 2 pairs -> size = 4
	private Card threeKindCard = null;
	private Card fourKindCard = null;
	
	public Hand(String hand) {
		cards = stringToCards(hand);
		setHandProperties();
	}
	
	public static List<Card> stringToCards(String hand) {
		List<Card> cards = new ArrayList<Card>();
		
		// Iterate through string and convert value suit pairs and convert to cards
		for (String card : hand.toLowerCase().split(" ")) {
			if ( card.length() == 2 ) {
				CardValue value = CardValue.get(card.substring(0, 1));
				CardSuit suit = CardSuit.get(card.substring(1));
				cards.add(new Card(value, suit));
			}
		}
		
		return cards;
	}
	
	private void setHandProperties() {
		// If hand isn't valid then don't calculate hand properties
		if ( ! isValid() )
			return;
		
		isStraight = isStraight();
		isFlush = isFlush();
		setKinds();
	}
	
	public boolean isValid() {
		if ( cards != null && cards.size() != handSize )
			return false;
		
		// Go through all the cards and if there are duplicates then return false
		for ( Card card : cards ) {
			if ( cards.stream().filter(c -> c.equals(card)).count() != 1)
				return false;
		}
		
		return true;
	}

	private void setKinds() {
		pairCards = new ArrayList<Card>();
		for ( Card card : cards ) {
			long kindCount = cards.stream().filter( a -> a.sameValueAs(card) ).count();
			
			if ( kindCount == 2 ) {
				pairCards.add(card);
			} else if ( kindCount == 3) {
				threeKindCard = card;
			} else if ( kindCount == 4 ) {
				fourKindCard = card;
			}
		}
	}
	
	public boolean isFlush() {
		return cards.stream().filter(card -> card.getCardSuit() == cards.get(0).getCardSuit()).count() == handSize;
	}
	
	public boolean isStraight() {
		cards.sort(Card::compareTo);
		
		for (int i = 0; i < handSize - 1; i++) {
			if ( cards.get(i).compareTo(cards.get(i + 1)) != -1 )
				return false;
		}
		
		return true;
	}
	
	public boolean isFullHouse() {
		return threeKindCard != null && pairCards.size() == 2;
	}

	public boolean isStraightFlush() {
		return isStraight && isFlush;
	}
	
	public List<Card> getPairs() {
		return pairCards;
	}
	
	public Card getThreeKind() {
		return threeKindCard;
	}
	
	public Card getFourKind() {
		return fourKindCard;
	}
	
	public List<Card> getNonKinds() {
		List<Card> nonKindCards = cards.stream().filter(
				card -> !card.sameValueAs(fourKindCard) && !card.sameValueAs(threeKindCard) && !pairCards.contains(card)
				).collect(Collectors.toList());
		
		nonKindCards.sort(Card::compareTo);
		return nonKindCards;
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public Card getHighCard() {
		return cards.stream().max( (a, b) -> a.compareTo(b)).get();
	}

	public HandArrangement getArrangement() {
		if (isStraightFlush())
			return HandArrangement.STRAIGHTFLUSH;
		if ( fourKindCard != null )
			return HandArrangement.FOUROFAKIND;
		if ( isFullHouse() )
			return HandArrangement.FULLHOUSE;
		if ( isFlush() )
			return HandArrangement.FLUSH;
		if ( isStraight() )
			return HandArrangement.STRAIGHT;
		if ( threeKindCard != null )
			return HandArrangement.THREEOFAKIND;
		if ( pairCards.size() == 4 )
			return HandArrangement.TWOPAIR;
		if ( pairCards.size() == 2 )
			return HandArrangement.PAIR;
		
		return HandArrangement.HIGHCARD;
	}
	
	public String toString() {
		HandArrangement arrangement = getArrangement();
		
		switch (arrangement) {
		case STRAIGHTFLUSH:
		case FLUSH:
		case STRAIGHT:
			return arrangement.toString() + ": " + cards.get(0).getCardValue().toString()
					+ " to " + cards.get(cards.size() - 1).getCardValue().toString();
		case FOUROFAKIND:
			return arrangement.toString() + ": " + fourKindCard.getCardValue().toString();
		case FULLHOUSE:
			return arrangement.toString() + ": " + threeKindCard.getCardValue().toString()
					+ " over " + pairCards.get(0).getCardValue().toString();
		case THREEOFAKIND:
			return arrangement.toString() + ": " + threeKindCard.getCardValue().toString();
		case TWOPAIR:
			return arrangement.toString() + ": " + pairCards.get(3).getCardValue().toString()
					+ " and " + pairCards.get(0).getCardValue().toString();
		case PAIR:
			return arrangement.toString() + ": " + pairCards.get(0).getCardValue().toString();
		case HIGHCARD:
			return arrangement.toString() + ": " + cards.get(cards.size() - 1).getCardValue().toString();
		}
		return "";
	}
}
