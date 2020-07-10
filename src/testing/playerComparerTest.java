package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import player.Player;
import player.PlayerComparer;
import result.InvalidResult;
import result.Result;
import result.Tie;
import result.Win;

class playerComparerTest {
	// define a list of 2 players
	List<Player> players = Arrays.asList( new Player[] {new Player("one"), new Player("two")} );
	
	String straightFlushHands[] = new String[] {"ah kh qh jh th", "ad kd qd jd td", "2d 3d 4d 5d 6d"}; // Can be a tie
	
	String fourKindHands[] = new String[] {"ah ad as ac ks", "7d 7s 7c 7h 8c"}; // Can't be a tie
	
	String fullHouseHands[] = new String[] {"ah ad as kc ks", "5d 5s 5c 8h 8d"}; // Can't be a tie
	
	String flushHands[] = new String[] {"ah kh qh jh 9h", "ad kd qd jd 9d", "5d 7d 3d 2d 6d"}; // Can be a tie
	
	String straightHands[] = new String[] {"ah kh qh jh td", "ad kd qd jd ts", "2d 3d 4d 5d 6s"}; // Can be a tie
	
	String threeKindHands[] = new String[] {"ah ad as qc ks", "5d 5s 5h 3h 2h"}; // Can't be a tie
	
	String twoPairHands[] = new String[] {"ah ad td kh kd", "as ac ks kc ts", "5d 5s 2c 2h 4h"}; // Can be a tie
	
	String pairHands[] = new String[] {"ah ad qh jh kh", "as ac qs js ks", "6d 6s 2c 4h 3h"}; // Can be a tie
	
	String highCardHands[] = new String[] {"ah kh qh jh 9s", "ac kc qc jc 9d", "6d 8s 2c 4h 3h"}; // Can be a tie

	@Test
	void duplicateCardTest() {
		players.get(0).setHand(straightFlushHands[0]);
		players.get(1).setHand(fourKindHands[0]);
		assertTrue(PlayerComparer.compare(players) instanceof InvalidResult);
		players.get(1).setHand(fullHouseHands[0]);
		assertTrue(PlayerComparer.compare(players) instanceof InvalidResult);
		players.get(1).setHand(flushHands[0]);
		assertTrue(PlayerComparer.compare(players) instanceof InvalidResult);
		players.get(1).setHand(straightHands[0]);
		assertTrue(PlayerComparer.compare(players) instanceof InvalidResult);
		players.get(1).setHand(pairHands[0]);
		assertTrue(PlayerComparer.compare(players) instanceof InvalidResult);
		
		players.get(0).setHand(straightFlushHands[2]);
		players.get(1).setHand(fullHouseHands[1]);
		assertTrue(PlayerComparer.compare(players) instanceof InvalidResult);
		players.get(1).setHand(straightHands[2]);
		assertTrue(PlayerComparer.compare(players) instanceof InvalidResult);
	}
	
	@Test
	void straightFlushTest() {
		checkTie(straightFlushHands);
		chechWinWithin(straightFlushHands);
		checkWinBetween(straightFlushHands, fourKindHands);
		checkWinBetween(straightFlushHands, fullHouseHands);
		checkWinBetween(straightFlushHands, flushHands);
		checkWinBetween(straightFlushHands, straightHands);
		checkWinBetween(straightFlushHands, threeKindHands);
		checkWinBetween(straightFlushHands, twoPairHands);
		checkWinBetween(straightFlushHands, pairHands);
		checkWinBetween(straightFlushHands, highCardHands);
	}

	@Test
	void fourKindTest() {
		// Player one wins greater fk over lesser fk
		players.get(0).setHand(fourKindHands[0]);
		players.get(1).setHand(fourKindHands[1]);
		Result result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals( result.getWinner(), players.get(0));
		
		// Player one wins lesser fk over fh
		players.get(0).setHand(fourKindHands[1]);
		players.get(1).setHand(fullHouseHands[0]);
		result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals( result.getWinner(), players.get(0));
		
		// Player one wins lesser fk over fl
		players.get(0).setHand(fourKindHands[1]);
		players.get(1).setHand(flushHands[0]);
		result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals( result.getWinner(), players.get(0));

		// Player one wins lesser fk over str
		players.get(0).setHand(fourKindHands[1]);
		players.get(1).setHand(straightHands[0]);
		result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals( result.getWinner(), players.get(0));

		// Player one wins lesser fk over tk
		players.get(0).setHand(fourKindHands[1]);
		players.get(1).setHand(threeKindHands[0]);
		result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals( result.getWinner(), players.get(0));
		
		// Player one wins lesser fk over tp
		players.get(0).setHand(fourKindHands[1]);
		players.get(1).setHand(twoPairHands[0]);
		result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals( result.getWinner(), players.get(0));
		
		// Player one wins lesser fk over p
		players.get(0).setHand(fourKindHands[1]);
		players.get(1).setHand(pairHands[0]);
		result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals( result.getWinner(), players.get(0));
		
		// Player one wins lesser fk over hc
		players.get(0).setHand(fourKindHands[1]);
		players.get(1).setHand(highCardHands[0]);
		result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals( result.getWinner(), players.get(0));
	}

	@Test
	void fullHouseTest() {
		checkWinBetween(fullHouseHands, flushHands);
		checkWinBetween(fullHouseHands, straightHands);
		checkWinBetween(fullHouseHands, threeKindHands);
		checkWinBetween(fullHouseHands, twoPairHands);
		checkWinBetween(fullHouseHands, pairHands);
		checkWinBetween(fullHouseHands, highCardHands);
	}
	
	@Test
	void flushTest() {
		checkTie(flushHands);
		chechWinWithin(flushHands);
		checkWinBetween(flushHands, straightHands);
		checkWinBetween(flushHands, threeKindHands);
		checkWinBetween(flushHands, twoPairHands);
		checkWinBetween(flushHands, pairHands);
		checkWinBetween(flushHands, highCardHands);
	}

	@Test
	void straightTest() {
		checkTie(straightHands);
		chechWinWithin(straightHands);
		checkWinBetween(straightHands, threeKindHands);
		checkWinBetween(straightHands, twoPairHands);
		checkWinBetween(straightHands, pairHands);
		checkWinBetween(straightHands, highCardHands);
	}

	@Test
	void threeKindTest() {
		chechWinWithin(threeKindHands);
		checkWinBetween(threeKindHands, twoPairHands);
		checkWinBetween(threeKindHands, pairHands);
		checkWinBetween(threeKindHands, highCardHands);
	}

	@Test
	void twoPairTest() {
		checkTie(twoPairHands);
		chechWinWithin(twoPairHands);
		checkWinBetween(twoPairHands, pairHands);
		checkWinBetween(twoPairHands, highCardHands);
	}
	
	@Test
	void pairTest() {
		checkTie(pairHands);
		chechWinWithin(pairHands);
		checkWinBetween(pairHands, highCardHands);
		
	}

	@Test
	void highCardTest() {
		checkTie(highCardHands);
		chechWinWithin(highCardHands);
	}
	
	void checkTie(String handType[]) {
		// Tied hands
		players.get(0).setHand(handType[0]);
		players.get(1).setHand(handType[1]);
		assertTrue(PlayerComparer.compare(players) instanceof Tie);
	}
	
	void chechWinWithin(String handType[]) {
		// Better version of the same hand
		players.get(0).setHand(handType[0]);
		players.get(1).setHand(handType[ handType.length - 1]);
		Result result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals(result.getWinner(), players.get(0));
	}
	
	void checkWinBetween( String handTypeOne[], String handTypeTwo[]) {
		// Player one wins greater p over lesser p
		players.get(0).setHand(handTypeOne[handTypeOne.length - 1]);
		players.get(1).setHand(handTypeTwo[0]);
		Result result = PlayerComparer.compare(players);
		assertTrue( result instanceof Win);
		assertEquals( result.getWinner(), players.get(0));
	}
	
	
}
