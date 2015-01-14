package Game;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class FileHandler {
	static private List<String[]> decks = new ArrayList<String[]>();
	static private String[] deck1 = {
			"deck1",
			"src\\Game\\Decks\\Deck1.data" };
	static private String[] deck2 = {
			"deck2",
			"src\\Game\\Decks\\Deck2.data"};
	static private String dn, dc, cn, cd;
	static private int spec, rar;
	static private int[] st = new int[2];
	static private boolean searchd = true, searchc = false, reader = true;
	static private String read = "test";

	public FileHandler() {
		decks.add(deck1);
		decks.add(deck2);
	}

	public static void loadDeck(String deckName, boolean player) throws IOException {
		if (player) {
			for (int i = 0; i < decks.size(); i++) {
				String[] s = decks.get(i);
				if (s[0].equals(deckName)) {

					FileReader f = new FileReader(s[1]);
					BufferedReader br = new BufferedReader(f);

					while (reader) {
						while (searchd) {
							read = br.readLine();
							Scanner sc = new Scanner(read);
							sc.useDelimiter("\\s=\\s|\\s:\\s");
							if (sc.hasNext()) {

								String scanned = sc.next();
								if (scanned.contains("Deck_name")) {
									dn = sc.next();
									System.out.println(dn);
								} else if (scanned.contains("Deck_description")) {
									dc = sc.next();
									System.out.println(dc);
								} else if (scanned.contains("{")&& !scanned.contains(";{")) {
									searchc = true;
									searchd = false;
								}
							}
							sc.close();
						}
						Deck b = new Deck(dn, dc);
						
						Main.setPlayerDeck(b);
						
						while (searchc) {
							read = br.readLine();
							Scanner sc = new Scanner(read);
							sc.useDelimiter("\\s=\\s|\\s:\\s");
							if (sc.hasNext()) {
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
			reader = true;
			searchd = true;
			for (int i = 0; i < decks.size(); i++) {
				String[] s = decks.get(i);
				if (s[0].equals(deckName)) {
					
					FileReader f = new FileReader(s[1]);
					BufferedReader br = new BufferedReader(f);

					while (reader) {
						while (searchd) {
							read = br.readLine();
							Scanner sc = new Scanner(read);
							sc.useDelimiter("\\s=\\s|\\s:\\s");
							if (sc.hasNext()) {

								String scanned = sc.next();
								if (scanned.contains("Deck_name")) {
									dn = sc.next();
									System.out.println(dn);
								} else if (scanned.contains("Deck_description")) {
									dc = sc.next();
									System.out.println(dc);
								} else if (scanned.contains("{")&& !scanned.contains(";{")) {
									searchc = true;
									searchd = false;
								}
							}
							sc.close();
						}
						System.out.println("testing2: " + dn + " - " + dc);
						Deck b = new Deck(dn, dc);
						Main.setEnemyDeck(b);
						
						while (searchc) {
							read = br.readLine();
							Scanner sc = new Scanner(read);
							sc.useDelimiter("\\s=\\s|\\s:\\s");
							if (sc.hasNext()) {
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
