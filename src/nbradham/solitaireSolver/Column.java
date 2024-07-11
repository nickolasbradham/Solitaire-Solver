package nbradham.solitaireSolver;

import java.util.ArrayList;

final class Column {

	private final ArrayList<Card> cards = new ArrayList<>();

	String getHash() {
		StringBuilder sb = new StringBuilder();
		cards.forEach(c -> sb.append(c.getHash()));
		return sb.toString();
	}

	private void add(Card card) {
		cards.add(card);
	}

	short getScore() {
		byte score = 0;
		int e = cards.size();
		Card last = cards.get(0);
		for (byte i = 1; i < e; ++i) {
			Card c = cards.get(i);
			score += (last.rank().val() + 1 == c.rank().val() ? 1 : 0) * (last.suit() == c.suit() ? 2 : 1);
			last = c;
		}
		return score;
	};

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