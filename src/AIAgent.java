import java.util.Comparator;
import java.util.List;

public class AIAgent {
    private int nodesExplored;

    public int[] getBestMove(Board board, boolean useAlphaBeta, int maxDepth) {
        nodesExplored = 0;
        long startTime = System.currentTimeMillis();

        int[] bestMove = null;
        int bestValue = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;


        List<int[]> moves = sortMoves(board, board.getLegalMoves());

        for (int[] move : moves) {
            Board nextState = board.makeMove(move[0], move[1]);
            int value;

            if (useAlphaBeta) {

                value = minimaxAB(nextState, maxDepth - 1, alpha, beta, false);
                alpha = Math.max(alpha, value);
            } else {

                value = minimax(nextState, false);
            }


            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Algorithm: " + (useAlphaBeta ? "Alpha-Beta" : "Minimax") +
                " | Depth: " + maxDepth +
                " | Nodes: " + nodesExplored +
                " | Time: " + duration + "ms");

        return bestMove;
    }

    // --- Plain Minimax (Optimality verification for 3x3) ---
    private int minimax(Board board, boolean isMaximizing) {
        nodesExplored++;
        char winner = board.checkWinner();
        if (winner == 'X') return 1000; // X wins
        if (winner == 'O') return -1000; // O wins
        if (board.isTerminal()) return 0; // Draw

        List<int[]> moves = board.getLegalMoves(); // No sorting required for plain minimax

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : moves) {
                maxEval = Math.max(maxEval, minimax(board.makeMove(move[0], move[1]), false));
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : moves) {
                minEval = Math.min(minEval, minimax(board.makeMove(move[0], move[1]), true));
            }
            return minEval;
        }
    }


    private int minimaxAB(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        nodesExplored++;
        char winner = board.checkWinner();

        // Terminal states score
        if (winner == 'X') return 10000 + depth;
        if (winner == 'O') return -10000 - depth;
        if (board.isTerminal()) return 0;


        if (depth == 0) {
            return evaluateHeuristic(board);
        }

        List<int[]> moves = sortMoves(board, board.getLegalMoves());

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : moves) {
                Board nextState = board.makeMove(move[0], move[1]);
                int eval = minimaxAB(nextState, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : moves) {
                Board nextState = board.makeMove(move[0], move[1]);
                int eval = minimaxAB(nextState, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }


    private int evaluateHeuristic(Board board) {
        int score = 0;
        int m = board.getM();
        int k = board.getK();


        score += countPotentialLines(board, 'X') * 10;
        score -= countPotentialLines(board, 'O') * 10;

        return score;
    }

    private int countPotentialLines(Board board, char player) {
        int m = board.getM();
        int k = board.getK();
        int potential = 0;
        char opp = (player == 'X') ? 'O' : 'X';


        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= m - k; j++) {
                if (checkSegment(board, i, j, 0, 1, k, opp)) potential++;
            }
        }

        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j < m; j++) {
                if (checkSegment(board, i, j, 1, 0, k, opp)) potential++;
            }
        }

        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= m - k; j++) {
                if (checkSegment(board, i, j, 1, 1, k, opp)) potential++;
            }
        }

        for (int i = 0; i <= m - k; i++) {
            for (int j = k - 1; j < m; j++) {
                if (checkSegment(board, i, j, 1, -1, k, opp)) potential++;
            }
        }
        return potential;
    }


    private boolean checkSegment(Board b, int r, int c, int dr, int dc, int k, char opp) {
        for (int step = 0; step < k; step++) {
            if (b.getCell(r + step * dr, c + step * dc) == opp) return false;
        }
        return true;
    }


    private List<int[]> sortMoves(Board board, List<int[]> moves) {
        int center = board.getM() / 2;
        moves.sort(Comparator.comparingInt(m -> {

            int dr = m[0] - center;
            int dc = m[1] - center;
            return dr * dr + dc * dc;
        }));
        return moves;
    }
}
