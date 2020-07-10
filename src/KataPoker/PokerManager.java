package KataPoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import result.Result;

public class PokerManager {
	public static void main(String[] args) {		
		// Add players
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("Black"));
		players.add(new Player("White"));
		
		// Give player instructions
		Scanner scanner = new Scanner(System.in);
		System.out.println("Lets compare poker hands!");
		System.out.println("Type exit and press enter to stop running");
		System.out.println("Type all 5 cards separated by spaces in a hand then press enter to enter a hand.");
		
		while(players.size() > 0) {
			for ( int i = 0; i < players.size(); i++ ) {
				System.out.print(players.get(i).getName() + ": ");
				String hand = scanner.nextLine().trim();
				
				// Check if user wants to exit
				if ( hand.contains("exit") ) {
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
