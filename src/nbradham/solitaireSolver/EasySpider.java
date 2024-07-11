package nbradham.solitaireSolver;

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
		BoardState bs = new BoardState(cols, draw);
		HashSet<String> history = new HashSet<>();
		queue.offer(bs);
		history.add(bs.getHash());
		while ((bs = queue.poll()) != null) {
			for (BoardState chk : bs.getChildBoards()) {
				// TODO Process moves.
			}
		}
	}

	public static void main(String[] args) {
		new EasySpider().start();
	}
}