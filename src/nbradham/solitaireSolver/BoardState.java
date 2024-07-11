package nbradham.solitaireSolver;

import java.util.ArrayList;
import java.util.Queue;

import nbradham.solitaireSolver.Card.Suit;

record BoardState(Column[] cols, Queue<Card> draw, ArrayList<Suit> foundations) {

	BoardState(Column[] cols, Queue<Card> draw) {
		this(cols, draw, new ArrayList<>());
	}

	int getScore() {
		short score = 0;
		for (Column c : cols)
			score += c.getScore();
		return score + foundations.size() * 100;
	}

	String getHash() {
		StringBuilder sb = new StringBuilder();
		for (byte i = 0; i < cols.length; ++i)
			sb.append(cols[i].getHash()).append(' ');
		draw.forEach(c -> sb.append(c.getHash()));
		foundations.forEach(s -> sb.append(' ').append(s.getHash()));
		return sb.toString();
	}

	BoardState[] getChildBoards() {
		for (byte i = 0; i < cols.length; ++i) {
			// TODO Get valid moves.
		}
		return null;
	}
}