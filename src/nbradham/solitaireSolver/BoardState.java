package nbradham.solitaireSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import nbradham.solitaireSolver.Card.Suit;

final class BoardState {
	private final Column[] cols;
	private final Queue<Card> draw;
	private final ArrayList<Suit> founds;
	private BoardState parent;
	private int srcCol, srcInd, destCol;

	BoardState(Column[] columns, Queue<Card> drawPile, ArrayList<Suit> foundations) {
		this(columns, drawPile, foundations, -1, -1, -1, null);
	}

	BoardState(Column[] columns, Queue<Card> drawPile, ArrayList<Suit> foundations, int mvSrcCol, int mvSrcInd,
			int mvDestCol, BoardState parentState) {
		cols = columns;
		draw = drawPile;
		founds = foundations;
		setMove(mvSrcCol, mvSrcInd, mvDestCol);
		setParent(parentState);
	}

	int getScore() {
		short score = 0;
		for (Column c : cols)
			score += c.getScore();
		return score + founds.size() * 100;
	}

	String getHash() {
		StringBuilder sb = new StringBuilder();
		for (byte i = 0; i < cols.length; ++i)
			sb.append(cols[i].getHash()).append(' ');
		draw.forEach(c -> sb.append(c.getHash()));
		founds.forEach(s -> sb.append(' ').append(s.getHash()));
		return sb.toString();
	}

	BoardState[] getChildBoards() {
		ArrayList<BoardState> childs = new ArrayList<>();
		for (byte i = 0; i < cols.length; ++i)
			for (int c = cols[i].size() - 1; c > -1 && canMove(i, c); --c)
				for (byte d = 0; d < cols.length; ++d) {
					int ds = cols[d].size();
					if (d != i && (ds == 0 || cols[i].get(c).rank().val() == cols[d].get(ds - 1).rank().val() - 1))
						childs.add(newMove(i, c, d));
				}
		if (!draw.isEmpty()) {
			BoardState tbs = copy();
			for (int i = tbs.cols.length - 1; i > -1 && !tbs.draw.isEmpty(); --i)
				tbs.cols[i].add(tbs.draw.poll());
			tbs.setMove(10, -1, -1);
			tbs.setParent(this);
			childs.add(tbs);
		}
		return childs.toArray(new BoardState[0]);
	}

	private boolean canMove(byte i, int c) {
		Card cr = cols[i].get(c), cn;
		return c == cols[i].size() - 1
				|| (cr.suit() == (cn = cols[i].get(c + 1)).suit() && cr.rank().val() == cn.rank().val() + 1);
	}

	private BoardState newMove(byte srcCol, int startCard, byte destCol) {
		BoardState tbs = copy();
		tbs.cols[srcCol].move(startCard, tbs.cols[destCol]);
		Suit r = tbs.cols[destCol].removeRun();
		if (r != null)
			tbs.founds.add(r);
		tbs.setMove(srcCol, startCard, destCol);
		tbs.setParent(this);
		return tbs;
	}

	private BoardState copy() {
		Column[] newCols = new Column[cols.length];
		for (byte i = 0; i < newCols.length; ++i)
			newCols[i] = cols[i].copy();
		Queue<Card> newDraw = new LinkedList<>();
		draw.forEach(c -> newDraw.offer(c));
		ArrayList<Suit> newF = new ArrayList<>();
		founds.forEach(s -> newF.add(s));
		return new BoardState(newCols, newDraw, newF);
	}

	private void setMove(int mvSrcCol, int mvSrcInd, int mvDestCol) {
		srcCol = mvSrcCol;
		srcInd = mvSrcInd;
		destCol = mvDestCol;
	}

	private void setParent(BoardState parentState) {
		parent = parentState;
	}

	ArrayList<Suit> getFoundations() {
		return founds;
	}

	BoardState getParent() {
		return parent;
	}

	int getSrcCol() {
		return srcCol;
	}

	int getSrcInd() {
		return srcInd;
	}

	int getDestCol() {
		return destCol;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s", founds, Arrays.toString(cols), draw);
	}
}