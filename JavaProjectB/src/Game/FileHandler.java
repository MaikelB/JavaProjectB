package Game;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class FileHandler {
	/**Creates the arraylist with the decks
	 * 3 String arrays which have the deck and the path to the deck data file
	 * adding strings for Deck name, deck description, card name and card description.
	 * adding ints for Card_special and card_rarity
	 * adding int array for card_stats, [0] is attack and [1] is defence
	 * booleans to turn the while loops on/off
	 * String to dump the br.readline in for scanner.*/
	static private List<String[]> decks = new ArrayList<String[]>();
	static private String[] deck1 = {
			"deck1",
			"Decks/Deck1.data" };
	static private String[] deck2 = {
			"deck2",
			"Decks/Deck2.data"};
	static private String[] deck3 = {
			"deck3",
			"Decks/Deck3.data"};
	static private String dn, dd, cn, cd;
	static private int spec, rar;
	static private int[] st = new int[2];
	static private boolean searchd = true, searchc = false, reader = true;
	static private String read = "test";

	/**add deck1, 2 and 3 to decks arraylist*/
	public FileHandler() {
		decks.add(deck1);
		decks.add(deck2);
		decks.add(deck3);
	}

	public void loadDeck(String deckName, boolean player) throws IOException {
		/**if statement to check if its loading the player, or enemy deck, true = player, else enemy deck. */
		if (player) {
			for (int i = 0; i < decks.size(); i++) {
				String[] s = decks.get(i);
				if (s[0].equals(deckName)) {
					InputStream file = getClass().getResourceAsStream(s[1]);
					BufferedReader br = new BufferedReader(new InputStreamReader(file));
					/**start of the file reading while */
					while (reader) {
						while (searchd) {
							/**Dumps the read line into "read"
							 * sets up scanner to scan "read"
							 * uses delimiters = and : */
							read = br.readLine();
							Scanner sc = new Scanner(read);
							sc.useDelimiter("\\s=\\s|\\s:\\s");
							/**if the scanner gets a String, it continues*/
							if (sc.hasNext()) {
								/** if it then contains "Deck_name" the string after it gets placed into "dn" and printed for testing
								* if it contains "Deck_description" the string after it gets placed into "dd" and printed for testing
								* if it contains { and doesn't contain ;{(deck starts with ;{ and cards with {) it turns on the while loop to search the cards, 
								* and turns off the one to search the deck*/
								String scanned = sc.next();
								if (scanned.contains("Deck_name")) {
									dn = sc.next();
									System.out.println(dn);
								} else if (scanned.contains("Deck_description")) {
									dd = sc.next();
									System.out.println(dd);
								} else if (scanned.contains("{")&& !scanned.contains(";{")) {
									searchc = true;
									searchd = false;
								}
							}
							sc.close();
						}
						/**Creates a deck with deck_name and deck_description, then sets it as playerdeck in Main */
						Deck b = new Deck(dn, dd);
						Main.setPlayerDeck(b);
						
						while (searchc) {
							/**Dumps the br.readline into string "read"
							 * starts scanner on "read"
							 * gives the scanner delimiters = and : */
							read = br.readLine();
							Scanner sc = new Scanner(read);
							sc.useDelimiter("\\s=\\s|\\s:\\s");
							
							/**if "read" has something in it*/
							if (sc.hasNext()) {
								/**Puts the output of sc.next() into the string Scanned
								 * if scanned has Card_name it puts the string after it in cn
								 * if scanned has Card_Description it puts the string after it in cd
								 * if scanned has Card_stats it puts the first int in [0] and the 2nd one in [1]
								 * if scanned has Card_special it puts the next int in spec
								 * if scanned has Card_rarity it puts the next int in rar
								 * if scanned has } and not };, it makes a new card with all the info from the scanned lines 
								 * and adds it to the Playerdeck with Main.playerDeck.setCard(a);
								 * if it contains }; (at the end of the file) it stops the while loops */
								String scanned = sc.next();
								if (scanned.contains("Card_name")) {
									cn = sc.next();
								} else if (scanned.contains("Card_description")) {
									cd = sc.next();
								} else if (scanned.contains("Card_stats")) {
									st[0] = sc.nextInt();
									st[1] = sc.nextInt();
								} else if (scanned.contains("Card_special")) {
									spec = sc.nextInt();
								} else if (scanned.contains("Card_rarity")) {
									rar = sc.nextInt();
								} else if (scanned.contains("}") && !scanned.contains("};")) {
									Card a = new Card(cn, cd, spec, st, rar);
									Main.playerDeck.setCard(a);
								}
								if (scanned.contains("};")){
									reader = false;
									searchc = false;
								}
							}
							sc.close();
						}
					}
				}
			}
		}
		else{
			/**Turns on Reader and searchd that were turned off in the other part of the if*/
			reader = true;
			searchd = true;
			for (int i = 0; i < decks.size(); i++) {
				String[] s = decks.get(i);
				if (s[0].equals(deckName)) {
					
					InputStream file = getClass().getResourceAsStream(s[1]);
					BufferedReader br = new BufferedReader(new InputStreamReader(file));
					/**start of the file reading while */
					while (reader) {
						while (searchd) {
							/**Dumps the read line into "read"
							 * sets up scanner to scan "read"
							 * uses delimiters = and : */
							read = br.readLine();
							Scanner sc = new Scanner(read);
							sc.useDelimiter("\\s=\\s|\\s:\\s");
							/**if the scanner gets a String, it continues*/
							if (sc.hasNext()) {
								/** if it then contains "Deck_name" the string after it gets placed into "dn" and printed for testing
								* if it contains "Deck_description" the string after it gets placed into "dd" and printed for testing
								* if it contains { and doesn't contain ;{(deck starts with ;{ and cards with {) it turns on the while loop to search the cards, 
								* and turns off the one to search the deck*/

								String scanned = sc.next();
								if (scanned.contains("Deck_name")) {
									dn = sc.next();
									System.out.println(dn);
								} else if (scanned.contains("Deck_description")) {
									dd = sc.next();
									System.out.println(dd);
								} else if (scanned.contains("{")&& !scanned.contains(";{")) {
									searchc = true;
									searchd = false;
								}
							}
							sc.close();
						}
						/**Creates a deck with deck_name and deck_description, then sets it as Enemydeck in Main */
						Deck b = new Deck(dn, dd);
						Main.setEnemyDeck(b);
						
						while (searchc) {
							/**Dumps the br.readline into string "read"
							 * starts scanner on "read"
							 * gives the scanner delimiters = and : */
							read = br.readLine();
							Scanner sc = new Scanner(read);
							sc.useDelimiter("\\s=\\s|\\s:\\s");
							/**if scanner has a String*/
							if (sc.hasNext()) {
								/**Puts the output of sc.next() into the string Scanned
								 * if scanned has Card_name it puts the string after it in cn
								 * if scanned has Card_Description it puts the string after it in cd
								 * if scanned has Card_stats it puts the first int in [0] and the 2nd one in [1]
								 * if scanned has Card_special it puts the next int in spec
								 * if scanned has Card_rarity it puts the next int in rar
								 * if scanned has } and not };, it makes a new card with all the info from the scanned lines 
								 * and adds it to the Playerdeck with Main.playerDeck.setCard(a);
								 * if it contains }; (at the end of the file) it stops the while loops */
								String scanned = sc.next();
								if (scanned.contains("Card_name")) {
									cn = sc.next();
								} else if (scanned.contains("Card_description")) {
									cd = sc.next();
								} else if (scanned.contains("Card_stats")) {
									st[0] = sc.nextInt();
									st[1] = sc.nextInt();
								} else if (scanned.contains("Card_special")) {
									spec = sc.nextInt();
								} else if (scanned.contains("Card_rarity")) {
									rar = sc.nextInt();
								} else if (scanned.contains("}") && !scanned.contains("};")) {
									Card a = new Card(cn, cd, spec, st, rar);
									Main.enemyDeck.setCard(a);
								}
								if (scanned.contains("};")){
									reader = false;
									searchc = false;
								}
							}
							sc.close();
						}
					}
				}
			}
		}
	}
}
