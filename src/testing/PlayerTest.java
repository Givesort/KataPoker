package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import player.Player;
import pokerComponents.Hand;

class PlayerTest {

	@Test
	void nameTest() {
		Player player = new Player("Test player");
		assertEquals(player.getName(), "Test player");
	}
	
	@Test
	void handSetTest() {
		Player player = new Player("Test player");
		assertNull(player.getHand());
		
		Hand handOne = new Hand("ah kh qh jh th");
		player.setHand(handOne);
		
		assertEquals(player.getHand(), handOne);
		
		Hand handTwo = new Hand("2d 3h 7h 8d ts");
		assertFalse(player.getHand().equals(handTwo));
		
		player.setHand(handTwo);
		assertEquals(player.getHand(), handTwo);
		assertFalse(player.getHand().equals(handOne));
	}

}
