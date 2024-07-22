package nbradham.solitaireSolver;

import java.util.ArrayList;

import nbradham.solitaireSolver.Card.Suit;

final class Column {

	private final ArrayList<Card> cards = new ArrayList<>();

	String getHash() {
		StringBuilder sb = new StringBuilder();
		cards.forEach(c -> sb.append(c.getHash()));
		return sb.toString();
	}

	void add(Card card) {
		cards.add(card);
	}

	short getScore() {
		byte score = 0;
		int e = cards.size();
		if (e > 0) {
			Card last = cards.get(0);
			for (byte i = 1; i < e; ++i) {
				Card c = cards.get(i);
				score += (last.rank().val() + 1 == c.rank().val() ? 1 : 0) * (last.suit() == c.suit() ? 2 : 1);
				last = c;
			}
		}
		return score;
	};

	int size() {
		return cards.size();
	}

	Card get(int ind) {
		return cards.get(ind);
	}

	Column copy() {
		Column t = new Column();
		cards.forEach(c -> t.add(c));
		return t;
	}

	void move(int startCard, Column destCol) {
		while (cards.size() > startCard)
			destCol.add(cards.remove(startCard));
	}

	Suit removeRun() {
		int size = cards.size();
		if (size < 13)
			return null;
		Suit st = cards.get(size - 1).suit();
		for (byte i = 1; i < 14; ++i) {
			Card c = cards.get(size - i);
			if (c.suit() != st || c.rank().val() != i)
				return null;
		}
		int e = size - 14;
		for (int i = size - 1; i > e; --i)
			cards.remove(i);
		return st;
	}

	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder("[");
		int s = cards.size();
		if (s > 0) {
			sb.append(cards.get(0));
			for (byte i = 1; i < s; ++i)
				sb.append(", ").append(cards.get(i));
		}
		return sb.append(']').toString();
	}

	static Column parse(String str) {
		Column c = new Column();
		for (String s : str.split(" "))
			c.add(Card.parse(s));
		return c;
	}
}