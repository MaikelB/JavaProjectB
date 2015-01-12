package Game;

import java.util.ArrayList;

public class Deck {
	public ArrayList<Card> cards = new ArrayList<Card>();
	
	public Deck(){
		
	}
	
	public boolean setCard( Card c ){
		cards.add(c);
		return false;
	}
	public Card getCard( int i ){
		Card cardReturn = cards.get(i);
		return cardReturn;
	}
}
