package KataPoker;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import enums.HandArrangement;
import result.*;

public class PlayerComparer {

	public static Result compare(List<Player> players) {
		if ( !situationValid(players) ) {
			return new InvalidResult();
		}
		HandArrangement bestHand = players.stream().filter(player -> player.getHand().isValid()).map(player -> player.getHand().getArrangement())
				.max(Comparator.comparing(HandArrangement::getValue)).orElse(HandArrangement.HIGHCARD);
		
		List<Player> bestPlayers = players.stream().filter(player -> player.getHand().getArrangement() == bestHand).collect(Collectors.toList());
		
		if ( bestHand == HandArrangement.STRAIGHTFLUSH || bestHand == HandArrangement.STRAIGHT || bestHand == HandArrangement.FLUSH ) {
			Card highCard = bestPlayers.stream().map(player -> player.getHand().getHighCard())
					.max(Comparator.comparing(Card::getValue)).orElse(null);
			bestPlayers = bestPlayers.stream().filter(player -> player.getHand().getHighCard().sameValueAs(highCard))
					.collect(Collectors.toList());
			if ( bestPlayers.size() == 1 )
				return new Win(bestPlayers.get(0));
			else
				return new Tie();
		}
		
		if ( bestHand == HandArrangement.FOUROFAKIND ) {
			Card highCard = bestPlayers.stream().map(player -> player.getHand().getFourKind())
					.max(Comparator.comparing(Card::getValue)).orElse(null);
			bestPlayers = bestPlayers.stream().filter(player -> player.getHand().getFourKind().sameValueAs(highCard))
					.collect(Collectors.toList());
			if ( bestPlayers.size() == 1 )
				return new Win(bestPlayers.get(0));
			else
				return new Tie();
		}
		
		if ( bestHand == HandArrangement.FULLHOUSE || bestHand == HandArrangement.THREEOFAKIND ) {
			Card threeKind = bestPlayers.stream().map(player -> player.getHand().getThreeKind())
					.max(Comparator.comparing(Card::getValue)).orElse(null);
			bestPlayers = bestPlayers.stream().filter(player -> player.getHand().getThreeKind().sameValueAs(threeKind))
					.collect(Collectors.toList());
			if ( bestPlayers.size() == 1 )
				return new Win(bestPlayers.get(0));
			return getHighestCard(bestPlayers, true);
		}
		
		if ( bestHand == HandArrangement.TWOPAIR || bestHand == HandArrangement.PAIR ) {
			// Set number of pairs
			int pairs = bestHand == HandArrangement.TWOPAIR ? 2 : 1;
			// Find player with highest pair
			for (int i = pairs; i > 0; i--) {
				final int index = i;
				Card pairCard = bestPlayers.stream().map(player -> player.getHand().getPairs().get(index))
						.max(Comparator.comparing(Card::getValue)).orElse(null);
				bestPlayers = bestPlayers.stream().filter(player -> player.getHand().getPairs().get(index).sameValueAs(pairCard))
						.collect(Collectors.toList());
				if ( bestPlayers.size() == 1)
					return new Win(bestPlayers.get(0));				
			}
			
			// All players have equal pairs so determine winner based on non pair card
			return getHighestCard(bestPlayers, true);
		}
		if ( bestHand == HandArrangement.HIGHCARD )
			return getHighestCard(bestPlayers, false);

		return new Tie();
	}
	
	public static Result getHighestCard(List<Player> players, boolean withHighCardString) {
		// All players have the same type of hand => the same number of non kind cards
		int numberOfNonKinds = players.get(0).getHand().getNonKinds().size(); 
		for ( int i = numberOfNonKinds; i > 0; i-- ) {
			final int index = i - 1;
			Card highCard = players.stream().map(player -> player.getHand().getNonKinds().get(index)).max(Comparator.comparing(Card::getValue)).orElse(null);
			players = players.stream().filter(player -> player.getHand().getNonKinds().get(index).sameValueAs(highCard)).collect(Collectors.toList());
			if ( players.size() == 1 ) {
				// Result is being checked after hand eval or cards were iterated through then add the high card string
				if ( withHighCardString || i != numberOfNonKinds)
					return new Win(players.get(0), highCard);
				
				return new Win(players.get(0));
			}
		}
		return new Tie();
	}
	
	public static boolean situationValid(List<Player> players) {
		if ( players.stream().filter(player -> ! player.getHand().isValid()).count() != 0 )
			return false;
		
		// Go through all the cards and if there are duplicates then return false
		List<Card> allCards = players.stream().map(player -> player.getHand().getCards()).flatMap(x -> x.stream()).collect(Collectors.toList());
		for ( Card card : allCards ) {
			if ( allCards.stream().filter(c -> c.equals(card)).count() != 1)
				return false;
		}
		
		return true;
	}
	
}
