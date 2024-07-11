package nbradham.solitaireSolver;

final record Card(Rank rank, Suit suit) {

	static enum Suit {
		CLUBS("Clubs"), DIAMONDS("Diamonds"), HEARTS("Hearts"), SPADES("Spades");

		private final String str;

		Suit(String string) {
			str = string;
		}

		char getHash() {
			return switch (this) {
			case CLUBS -> 'C';
			case DIAMONDS -> 'D';
			case HEARTS -> 'H';
			case SPADES -> 'S';
			default -> 'I';
			};
		}

		@Override
		public String toString() {
			return str;
		}

		private static final Suit parse(String str) {
			return switch (str.charAt(0)) {
			case 'C' -> CLUBS;
			case 'D' -> DIAMONDS;
			case 'H' -> HEARTS;
			case 'S' -> SPADES;
			default -> null;
			};
		}
	};

	static enum Rank {
		ACE("Ace"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"),
		JACK("Jack"), QUEEN("Queen"), KING("King");

		private final String str;

		Rank(String string) {
			str = string;
		}

		char getHash() {
			return switch (this) {
			case ACE -> 'A';
			case EIGHT -> '8';
			case FIVE -> '5';
			case FOUR -> '4';
			case JACK -> 'J';
			case KING -> 'K';
			case NINE -> '9';
			case QUEEN -> 'Q';
			case SEVEN -> '7';
			case SIX -> '6';
			case TEN -> 'T';
			case THREE -> '3';
			case TWO -> '2';
			default -> '0';
			};
		}

		byte val() {
			return switch (this) {
			case ACE -> 1;
			case EIGHT -> 8;
			case FIVE -> 5;
			case FOUR -> 4;
			case JACK -> 11;
			case KING -> 13;
			case NINE -> 9;
			case QUEEN -> 12;
			case SEVEN -> 7;
			case SIX -> 6;
			case TEN -> 10;
			case THREE -> 3;
			case TWO -> 2;
			default -> 0;
			};
		}

		@Override
		public String toString() {
			return str;
		}

		private static final Rank parse(String str) {
			return switch (str.charAt(0)) {
			case 'A' -> ACE;
			case '2' -> TWO;
			case '3' -> THREE;
			case '4' -> FOUR;
			case '5' -> FIVE;
			case '6' -> SIX;
			case '7' -> SEVEN;
			case '8' -> EIGHT;
			case '9' -> NINE;
			case '1' -> TEN;
			case 'J' -> JACK;
			case 'Q' -> QUEEN;
			case 'K' -> KING;
			default -> null;
			};
		}
	}

	String getHash() {
		return new StringBuilder().append(rank.getHash()).append(suit.getHash()).toString();
	}

	@Override
	public String toString() {
		return rank + " of " + suit;
	}

	static Card parse(String s) {
		int i = s.length() - 1;
		return new Card(Rank.parse(s.substring(0, i)), Suit.parse(s.substring(i)));
	}
}