package nbradham.solitaireSolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import nbradham.solitaireSolver.Card.Suit;

record BoardState(Column[] columns, Queue<Card> drawPile, ArrayList<Suit> foundations, int mvSrcCol, int mvSrcInd,
		int mvDestCol, BoardState parent) {

	BoardState(Column[] columns, Queue<Card> drawPile, ArrayList<Suit> foundations) {
		this(columns, drawPile, foundations, -1, -1, -1, null);
	}

	int getScore() {
		short score = 0;
		for (Column c : columns)
			score += c.getScore();
		return score + foundations.size() * 100;
	}

	String getHash() {
		StringBuilder sb = new StringBuilder();
		for (byte i = 0; i < columns.length; ++i)
			sb.append(columns[i].getHash()).append(' ');
		drawPile.forEach(c -> sb.append(c.getHash()));
		foundations.forEach(s -> sb.append(' ').append(s.getHash()));
		return sb.toString();
	}

	BoardState[] getChildBoards() {
		ArrayList<BoardState> childs = new ArrayList<>();
		for (byte i = 0; i < columns.length; ++i)
			for (int c = columns[i].size(); c > -1; --c)
				for (byte d = 0; d < columns.length; ++d)
					if (d != i
							&& columns[i].get(c).rank().val() == columns[d].get(columns[d].size() - 1).rank().val() + 1)
						childs.add(newMove(i, c, d));
		return childs.toArray(new BoardState[0]);
	}

	private BoardState newMove(byte srcCol, int startCard, byte destCol) {
		Column[] newCols = new Column[columns.length];
		for (byte i = 0; i < newCols.length; ++i)
			newCols[i] = columns[i].copy();
		Queue<Card> newDraw = new LinkedList<>();
		drawPile.forEach(c -> newDraw.offer(c));
		ArrayList<Suit> newF = new ArrayList<>();
		foundations.forEach(s -> newF.add(s));
		newCols[srcCol].move(startCard, newCols[destCol]);
		Suit r = newCols[destCol].removeRun();
		if (r != null)
			newF.add(r);
		return new BoardState(newCols, newDraw, newF, srcCol, startCard, destCol, this);
	}
}