package com.deckofcardsQueue;

/*This class stores each card's suit and rank
*/
public class Card {
	String rank;
	String suit;
	
	public Card(String suit, String rank) {
		this.suit = suit;
		this.rank = rank;
	}
	public String getRank() {
		return rank;
	}
	public String getSuit() {
		return suit;
	}
	@Override
	public String toString() {
		return "Card [rank=" + rank + ", suit=" + suit + "]";
	}
	
}
