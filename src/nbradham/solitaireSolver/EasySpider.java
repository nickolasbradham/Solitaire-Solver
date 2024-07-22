package nbradham.solitaireSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

final class EasySpider {

	private boolean run = true;

	private void start() {
		Scanner scan = new Scanner(System.in);
		Column[] cols = new Column[10];
		for (byte i = 0; i < cols.length; i++) {
			System.out.printf("Enter column %d: ", i);
			cols[i] = Column.parse(scan.nextLine());
		}
		System.out.print("Enter cards in deck: ");
		Queue<Card> draw = new LinkedList<>();
		String sStr = scan.nextLine();
		if (sStr.length() > 0)
			for (String str : sStr.split(" "))
				draw.offer(Card.parse(str));
		scan.close();
		PriorityQueue<BoardState> queue = new PriorityQueue<>((a, b) -> b.getScore() - a.getScore());
		BoardState bs = new BoardState(cols, draw, new ArrayList<>());
		HashSet<String> history = new HashSet<>();
		queue.offer(bs);
		history.add(bs.getHash());
		new Thread(() -> {
			while (run) {
				System.out.printf("Queue/History: %d/%d%nPeek: %s%n", queue.size(), history.size(), queue.peek());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		while ((bs = queue.poll()) != null) {
			for (BoardState chk : bs.getChildBoards()) {
				if (chk.getFoundations().size() == 8) {
					run = false;
					BoardState l = chk;
					do
						System.out.printf("(%d, %d) -> %d%n", l.getSrcCol(), l.getSrcInd(), l.getDestCol());
					while ((l = l.getParent()) != null);
					return;
				}
				String h = chk.getHash();
				if (!history.contains(h)) {
					history.add(h);
					queue.add(chk);
				}
			}
		}
		run = false;
		System.out.println("No solution found.");
	}

	public static void main(String[] args) {
		new EasySpider().start();
	}
}