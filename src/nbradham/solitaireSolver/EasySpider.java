package nbradham.solitaireSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

final class EasySpider {

	private void start() {
		Scanner scan = new Scanner(System.in);
		Column[] cols = new Column[10];
		for (byte i = 0; i < cols.length; i++) {
			System.out.printf("Enter column %d: ", i);
			cols[i] = Column.parse(scan.nextLine());
		}
		System.out.print("Enter cards in deck: ");
		Queue<Card> draw = new LinkedList<>();
		for (String str : scan.nextLine().split(" "))
			draw.offer(Card.parse(str));
		scan.close();
		PriorityQueue<BoardState> queue = new PriorityQueue<>((a, b) -> b.getScore() - a.getScore());
		BoardState bs = new BoardState(cols, draw, new ArrayList<>());
		HashSet<String> history = new HashSet<>();
		queue.offer(bs);
		history.add(bs.getHash());
		while ((bs = queue.poll()) != null) {
			for (BoardState chk : bs.getChildBoards()) {
				if (chk.foundations().size() == 8) {
					BoardState l = chk;
					while ((l = l.parent()) != null)
						System.out.printf("(%d, %d) -> %d%n", l.mvSrcCol(), l.mvSrcInd(), l.mvDestCol());
					return;
				}
				String h = chk.getHash();
				if (!history.contains(h)) {
					history.add(h);
					queue.add(chk);
				}
			}
		}
	}

	public static void main(String[] args) {
		new EasySpider().start();
	}
}