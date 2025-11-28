public class Tests {
    public static void main(String[] args) {
        System.out.println("Running Tests...\n");

        testWinConditions();
        testMinimaxVsAlphaBeta();
        testImmediateBlock();

        System.out.println("\nAll Tests Completed.");
    }


    static void testWinConditions() {
        System.out.print("Test 1: Win Conditions... ");
        Board b = new Board(3, 3);


        b = b.makeMove(0, 0);
        b = b.makeMove(1, 0);
        b = b.makeMove(0, 1);
        b = b.makeMove(1, 1);
        b = b.makeMove(0, 2);
        assert b.checkWinner() == 'X' : "Failed Row Win";


        b = new Board(3, 3);
        b = b.makeMove(0, 0);
        b = b.makeMove(0, 1);
        b = b.makeMove(1, 1);
        b = b.makeMove(0, 2);
        b = b.makeMove(2, 2);
        assert b.checkWinner() == 'X' : "Failed Diagonal Win";

        System.out.println("PASSED");
    }


    static void testMinimaxVsAlphaBeta() {
        System.out.print("Test 2: Minimax vs Alpha-Beta Consistency... ");
        Board b = new Board(3, 3);

        b = b.makeMove(0, 0);
        b = b.makeMove(1, 1);

        AIAgent ai = new AIAgent();


        int[] moveMinimax = ai.getBestMove(b, false, 9);


        int[] moveAB = ai.getBestMove(b, true, 9);


        assert moveMinimax[0] == moveAB[0] && moveMinimax[1] == moveAB[1]
                : "Mismatch: Minimax gave " + moveMinimax[0]+","+moveMinimax[1] +
                " but AB gave " + moveAB[0]+","+moveAB[1];

        System.out.println("PASSED");
    }


    static void testImmediateBlock() {
        System.out.print("Test 3: Blocking Threat on 4x4... ");
        Board b = new Board(4, 3);

        b = b.makeMove(3, 3);
        b = b.makeMove(0, 0);
        b = b.makeMove(3, 2);
        b = b.makeMove(0, 1);

        AIAgent ai = new AIAgent();
        int[] move = ai.getBestMove(b, true, 4);

        assert move[0] == 0 && move[2] == 0 : "AI failed to block! Move: " + move[0] + "," + move[1];

        if (move[0] == 0 && move[1] == 2) {
            System.out.println("PASSED (Blocked at 0,2)");
        } else {
               System.out.println("Check logic manually. AI played: " + move[0] + "," + move[1]);
        }
    }
}