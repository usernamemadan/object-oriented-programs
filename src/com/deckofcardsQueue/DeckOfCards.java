package com.deckofcardsQueue;

import java.util.Iterator;

public class DeckOfCards {
	public static void main(String[] args) {
		Queue<Card> cardList = new Queue<>();
		
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		Player player4 = new Player();
		
		/*printing the cardlist after sorting in order
		*/
		cardList = sort(cardList);
		printCards(cardList);
		
		/*printing the cardlist after shuffling the cards
		*/
		System.out.println("\n\nafter shuffling the cards\n");
		cardList = shuffle(cardList);
		printCards(cardList);
		
		Iterator iterator = cardList.iterator();
        for(int i =0; i < 9; i++) {
			player1.putCard(iterator.next().toString());
			player2.putCard(iterator.next().toString());
			player3.putCard(iterator.next().toString());
			player4.putCard(iterator.next().toString());
		}
        
        System.out.println(player1 + "\n");
        System.out.println(player2 + "\n");
        System.out.println(player3 + "\n");
        System.out.println(player4 + "\n");
	}
	
	/*method to sort the cardlist and return the sorted cardlist
	*/
	public static Queue<Card> sort(Queue<Card> cardList) {
		String suits[] = {"Clubs","Daimonds","Heart","Spades"};
		String rank[] = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
		Queue<Card> newcardList = new Queue<>();
		
		for(int i=0;i<suits.length;i++) {
			for(int j=0;j<rank.length;j++) {
				newcardList.enqueue(new Card(suits[i],rank[j]));
			}
		}
		return newcardList;
	}
	
	/*method to shuffle the cardlist and return the shuffled cardlist
	*/
	public static Queue<Card> shuffle(Queue<Card> cardList) {
		String suits[] = {"Clubs","Daimonds","Heart","Spades"};
		String rank[] = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
		
		String temp;
		for(int i=0;i<suits.length;i++) {
			int randomValue = (int)(Math.random() * 10 % suits.length );
			temp = suits[i];
			suits[i] = suits[randomValue];
			suits[randomValue] = temp;	
			}
		
		for(int i=0;i<rank.length;i++) {
			int randomValue = (int)(Math.random() * 10 % rank.length);
			temp = rank[i];
			rank[i] = rank[randomValue];
			rank[randomValue] = temp;	
			}
        
		Queue<Card> newcardList = new Queue<>();
		
		for(int i=0;i<suits.length;i++) {
			for(int j=0;j<rank.length;j++) {
				newcardList.enqueue(new Card(suits[i],rank[j]));
			}
		}
		return newcardList;
	}
	
	/*method to print the cardlist passed as the parameter
	*/
	public static void printCards(Queue<Card> cardList) {
		Iterator iterator = cardList.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.toString());
			iterator.next();
		}
	}
		
	
	
}