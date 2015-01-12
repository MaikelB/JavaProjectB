package Game;

import java.util.ArrayList;
import java.util.List;


public class FileHandler {
	private List<String[]> decks = new ArrayList<String[]>();
	private String 
	String[] deck1 = { "deck1", "Decks\\Deck1.data"};
	
	public FileHandler(){
		decks.add(deck1);
	}
	public void loadDeck( String deckName ){
		for (String[] s : decks){
			if (s[0].equals(deckName)){
				
			}
		}
	}

}
