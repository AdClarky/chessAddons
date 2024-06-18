package chessboard;

import java.util.ArrayList;
import java.util.List;

public class TempMove {
    private Board board;
    private final List<Move> tempMoves = new ArrayList<>(3);
    private final ArrayList<Piece> tempPieces = new ArrayList<>(2);


    public TempMove(int x, int y, Piece piece, Board board){
        this.board = board;
        Iterable<Move> moves = piece.getMoves(x, y, board);
        for(Move move : moves){
            if(!board.isSquareBlank(move.newX(), move.newY())){ // if taking
                tempMoves.add(new Move(move.newX(), move.newY(), move.newX(), move.newY()));
                tempPieces.add(board.getPiece(move.newX(), move.newY()));
                board.getColourPieces(tempPieces.getLast()).remove(tempPieces.getLast());
            }
            if(move.newX() == move.oldX() && move.newY() == move.oldY()){ // promotion
                tempPieces.add(board.getPiece(move.newX(), move.newY()));
                board.getColourPieces(tempPieces.getLast()).remove(tempPieces.getLast());
                if(move.newY() == 0)
                    board.setSquare(move.newX(), 0, new Queen(move.newX(), 0, Piece.BLACK_PIECE));
                else
                    board.setSquare(move.newX(), move.newY(), new Queen(move.newX(), move.newY(), Piece.WHITE_PIECE));
                board.getColourPieces(tempPieces.getLast().getDirection()).add(board.getPiece(move.newX(), move.newY()));
            }
            tempMoves.add(new Move(move.oldX(), move.oldY(), move.newX(), move.newY()));
            Piece temp = board.getPiece(move.oldX(), move.oldY());
            board.setSquare(move.oldX(), move.oldY(), new Blank(move.oldX(), move.oldY()));
            temp.setX(move.newX());
            temp.setY(move.newY());
            board.setSquare(move.newX(), move.newY(), temp);
        }
    }

    public void undo(){
        for(Move move : tempMoves.reversed()){
            if(move.oldY() == move.newY() && move.newX() == move.oldX()) {
                if(tempPieces.getLast() instanceof Pawn pawn && (pawn.getY() == 7 || pawn.getY() == 0)) // if it was a promotion
                    board.getColourPieces(tempPieces.getLast()).remove(board.getPiece(move.oldX(), move.oldY()));
                board.setSquare(move.newX(), move.newY(), tempPieces.getLast());
                board.getColourPieces(tempPieces.getLast()).add(tempPieces.getLast());
                tempPieces.removeLast();
            }
            else {
                board.setSquare(move.oldX(), move.oldY(), board.getPiece(move.newX(), move.newY()));
                board.setSquare(move.newX(), move.newY(), new Blank(move.newX(), move.newY()));
            }
            board.getPiece(move.oldX(), move.oldY()).setX(move.oldX());
            board.getPiece(move.oldX(), move.oldY()).setY(move.oldY());
        }
        tempMoves.clear();
    }
}
