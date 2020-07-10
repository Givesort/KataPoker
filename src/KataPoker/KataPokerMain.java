package KataPoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import player.Player;
import player.PlayerComparer;
import result.Result;

public class KataPokerMain {
	public static void main(String[] args) {		
		// Add players
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("Black"));
		players.add(new Player("White"));
		
		// Give player instructions
		Scanner scanner = new Scanner(System.in);
		System.out.println("Lets compare poker hands!");
		System.out.println("Type exit and press enter to stop running");
		System.out.println("For each player, type all five cards separated by spaces then press enter. Case doesn't matter.");
		System.out.println("Example: ah kd 5c ts 2c\n");
		
		while(players.size() > 0) {
			for ( int i = 0; i < players.size(); i++ ) {
				System.out.print(players.get(i).getName() + ": ");
				String hand = scanner.nextLine();
				
				// Check if user wants to exit
				if ( hand.toLowerCase().contains("exit") ) {
					scanner.close();
					return;
				}
				
				players.get(i).setHand(hand);
			}
			
			Result result = PlayerComparer.compare(players);
			System.out.println(result.toString());
		}
		
		scanner.close();
			
		
	}
	

}
