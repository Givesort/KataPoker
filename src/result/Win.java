package result;

import player.Player;
import pokerComponents.Card;

public class Win extends Result{

	private Player winner = null;
	private Card highCard = null;
	
	public Win (Player player) {
		winner = player;
	}
	
	public Win (Player player, Card card) {
		winner = player;
		highCard = card;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if ( winner == null )
			return "";
		
		StringBuilder winString = new StringBuilder();
		winString.append(winner.getName());
		winString.append(" wins - ");
		winString.append(winner.getHand().toString());
		
		if ( highCard != null ) {
			winString.append(", high card : " + highCard.getCardValue().toString());		
		}
		
		return winString.toString();
	}
	
	@Override
	public Player getWinner() {
		return winner;
	}

}
