package Game;

import java.util.ArrayList;

public class Deck {
	
	/** String deck_name = holds the name of the deck */
	private String deck_name;
	
	/**String deck_desc = holds the description of the deck */
	private String deck_desc;
	
	/** ArrayList<Card> deck = holds the cards still in your deck */
	static public ArrayList<Card> deck = new ArrayList<Card>();
	
	/** ArrayList<Card> graveyard = holds the cards that died */
	static public ArrayList<Card> graveyard = new ArrayList<Card>();
	
	/** ArrayList<Card> onTable = holds the cards on the table */
	static public ArrayList<Card> onTable = new ArrayList<Card>();
	
	/** ArrayList<Card> onSpells = holds the spells on the table */
	static public ArrayList<Card> onSpells = new ArrayList<Card>();
	
	/** ArrayList<Card> inHand = holds the cards/spells currently in your hand */
	static public ArrayList<Card> inHand = new ArrayList<Card>();
	
	/** Deck( name of the deck, description of the deck ) */
	public Deck(String nm, String desc){
		deck_name 	= nm;
		deck_desc 	= desc;		
	}
	
	/** void cardPlay( index of the card to play )
	 * removes the card in hand
	 * and adds it onTable or OnSpells depending on it's special
	 */
	public void cardPlay( int i ) {
		Card rm = inHand.get(i);
		inHand.remove(rm);
		if(rm.getRarity() == 4) {
			onSpells.add(rm);
		}else {		
		onTable.add(rm);	
		}
	}
	
	/** void spellDie( index of the spell to die )
	 * removes the spell from onSpells and adds it to the graveyard
	 */
	public void spellDie( int i ) {
		Card rm = onSpells.get(i);
		onSpells.remove(rm);
		graveyard.add(rm);
	}
	
	/** void cardDie( index of the card to die )
	 * removes the card from onTable and adds it to the graveyard
	 */
	public void cardDie( int i ) {
		Card rm = onTable.get(i);
		onTable.remove(rm);
		rm.setShowBack(true);
		graveyard.add(rm);
		
	}
	
	/** boolean setCard( Card to add to the deck )
	 * adds #c card to the deck
	 */
	public void setCard( Card c ){
		deck.add(c);
	}
	public void drawCard(){
		int randomNum = 1 + (int)(Math.random()*deck.size());
		System.out.println(deck.size());
		Card draw = deck.get(randomNum - 1);
		if(draw.equals(Main.playerDeck.getCard(randomNum-1))){
			draw.setShowBack(false);
		}
		deck.remove(draw);
		inHand.add(draw);
	}
	
	/** Card getCard( index of the card to get )
	 * returns the card defined by #i
	 */
	public Card getCard( int i ){
		Card cardReturn = deck.get(i);
		return cardReturn;
	}
}
