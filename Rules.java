/*
 * @author Phoebe Lu
 * Taken from assignment 1. Only made a minor alteration so rules becomes static
 */
public class Rules {
	public Rules() {
		numberToMoves = new String[5];
		numberToMoves[0] = "Rock";
		numberToMoves[1] = "Paper";
		numberToMoves[2] = "Scissors";
		numberToMoves[3] = "Lizard";
		numberToMoves[4] = "Spock";
	}

	public int getResult(String move1, String move2) {
		return rules[moveToNumber(move1)][moveToNumber(move2)];
	}

	private int moveToNumber(String move) {
		for (int i = 0; i < numberToMoves.length; i++) {
			if (numberToMoves[i] == move) {
				return i;
			}
		}
		return -1;
	}

	private String[] numberToMoves;
	private static final int[][] rules = { { 0, -1, 1, 1, -1 }, { 1, 0, -1, -1, 1 }, { -1, 1, 0, 1, -1 },
			{ -1, 1, -1, 0, 1 }, { 1, -1, 1, -1, 0 } };
}
