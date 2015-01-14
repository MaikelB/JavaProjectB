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
	static private String dn, dc, cn, cd;
	static private int spec, rar;
	static private int[] st = new int[2];
	static private boolean searchd = true, searchc = false, reader = true;
	static private String read = "test";

	public FileHandler() {
		decks.add(deck1);
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
							System.out.println("test1");
							read = br.readLine();
							Scanner sc = new Scanner(read);
							sc.useDelimiter("\\s=\\s|\\s:\\s");
							if (sc.hasNext()) {

								System.out.println("test 2");
								String scanned = sc.next();
								if (scanned.contains("Deck_name")) {
									System.out.println("test  3");
									dn = sc.next();
									System.out.println(dn);
								} else if (scanned.contains("Deck_description")) {
									System.out.println("test    4");
									dc = sc.next();
									System.out.println(dc);
								} else if (scanned.contains("{")) {
									System.out.println("test       5");
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
		/*
		 * else{ for (String[] s : decks){ if (s[0].equals(deckName)){ String
		 * read = null; FileReader f = new FileReader(s[1]); BufferedReader br =
		 * new BufferedReader(f); Scanner sc = new Scanner(read);
		 * sc.useDelimiter("\\s = \\s|\\s : \\s"); while(reader){
		 * while(searchd){ read = br.readLine(); if (sc.next() =="Deck_name"){
		 * dn = sc.next(); } else if (sc.next() == "Deck_description"){ dc =
		 * sc.next(); } else if (br.readLine()== "\t{"){ searchc = true; break;
		 * } } while (searchc){ read = br.readLine(); if (sc.next() ==
		 * "Card_name"){ cn = sc.next(); } else if (sc.next() ==
		 * "Card_description"){ cd = sc.next(); } else if(sc.next() ==
		 * "Card_stats"){ st[0] = sc.nextInt(); st[1] = sc.nextInt(); } else
		 * if(sc.next() == "Card_special"){ spec = sc.nextInt(); } else if
		 * (sc.next() == "Card_rarity"){ rar = sc.nextInt();}
		 * 
		 * else if (br.readLine() == "\t}"){ Card a = new Card(cn, cd, spec, st,
		 * rar); Deck.setCard(a); } else if (br.readLine() == "}"){ Deck a = new
		 * Deck(dn, dc); Main.setEnemyDeck(a); reader = false; sc.close();
		 * br.close(); f.close(); break; } } } } } }
		 */
	}
}
