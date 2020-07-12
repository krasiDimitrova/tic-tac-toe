package com.example.kpk.tictactoe.services;

import java.util.List;

import com.example.kpk.tictactoe.models.Move;
import com.example.kpk.tictactoe.models.PlayerSymbol;
import com.example.kpk.tictactoe.models.PlayerType;
import com.example.kpk.tictactoe.models.Position;

import org.springframework.stereotype.Service;

@Service
public class ComputerPlayerServiceImpl implements ComputerPlayerService {

	private PlayerSymbol player;

	private PlayerSymbol computer;

	@Override
	public Position findBestPosition(List<Move> moves, PlayerSymbol player) {
		PlayerSymbol board[][] = convertToBoard(moves, player);

		int bestVal = -1000;
		Position bestPosition = new Position(-1, -1);

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (board[row][col] == null) {
					board[row][col] = computer;

					int moveVal = minimax(board, 0, false);

					board[row][col] = null;

					if (moveVal > bestVal) {
						bestPosition = new Position(row, col);
						bestVal = moveVal;
					}
				}
			}
		}

		return bestPosition;
	}

	private PlayerSymbol[][] convertToBoard(List<Move> moves, PlayerSymbol player) {
		this.player = player;
		this.computer = player == PlayerSymbol.X ? PlayerSymbol.O : PlayerSymbol.X;

		PlayerSymbol board[][] = new PlayerSymbol[3][3];

		for (Move move : moves) {
			Position movePosition = move.getBoardPosition();
			PlayerSymbol playerSymbol = move.getPlayerType() == PlayerType.PLAYER ? player : computer;
			board[movePosition.getRow()][movePosition.getColumn()] = playerSymbol;
		}

		return board;
	}

	/*
	 * This is the minimax function. It considers all the possible ways the game can
	 * go and returns the value of the board
	 */
	private int minimax(PlayerSymbol board[][], int depth, Boolean isMax) {
		int score = evaluate(board);

		// If Maximizer has won the game
		if (score == 10) {
			return score;
		}

		// If Minimizer has won the game
		if (score == -10) {
			return score;
		}

		// If there are no more moves and no winner then it is a tie
		if (!arePositionsLeft(board)) {
			return 0;
		}

		// If this maximizer's move
		if (isMax) {
			int best = -1000;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == null) {
						board[i][j] = computer;
						best = Math.max(best, minimax(board, depth + 1, !isMax));
						board[i][j] = null;
					}
				}
			}
			return best;
		}
		// If this minimizer's move
		else {
			int best = 1000;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == null) {
						board[i][j] = player;
						best = Math.min(best, minimax(board, depth + 1, !isMax));
						board[i][j] = null;
					}
				}
			}
			return best;
		}
	}

	private int evaluate(PlayerSymbol board[][]) {
		// Checking for Rows for COMPUTER or PLAYER victory.
		for (int row = 0; row < 3; row++) {
			if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
				if (board[row][0] == computer) {
					return +10;
				} else if (board[row][0] == player) {
					return -10;
				}
			}
		}

		// Checking for Columns for COMPUTER or PLAYER victory.
		for (int col = 0; col < 3; col++) {
			if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
				if (board[0][col] == computer) {
					return +10;
				} else if (board[0][col] == player) {
					return -10;
				}
			}
		}

		// Checking for Diagonals for COMPUTER or PLAYER victory.
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			if (board[0][0] == computer) {
				return +10;
			} else if (board[0][0] == player) {
				return -10;
			}
		}

		if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			if (board[0][2] == computer) {
				return +10;
			} else if (board[0][2] == player) {
				return -10;
			}
		}

		// Else if none of them have won then return 0
		return 0;
	}

	private boolean arePositionsLeft(PlayerSymbol board[][]) {
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++)
				if (board[row][col] == null)
					return true;
		return false;
	}
}