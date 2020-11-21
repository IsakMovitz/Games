package Projekt;

import javax.swing.*;
import java.util.*;

// Om en pjäs inte kan gå någonstans är spelet förstört

interface Boardgame {
    public boolean move(int x, int y); //ger true om draget gick bra, annars false
    public Icon getStatus(int x, int y); // returnera innehåll på ruta (i,j)
    public String getMessage(); // returnera OK (eller liknande) eller felmeddelande avseende senaste drag
}

class Chess implements Boardgame {

    private ChessPiece[][] chessBoard = new ChessPiece[8][8];
    private ArrayList<ChessPiece> pieces = new ArrayList<>();
    private String currentMessage = "Start playing!";
    private static ChessPiece selectedPiece = null;
    private static boolean selectionState = true;
    private static ChessPiece destinationSquare;

    Chess() {
        // Lägger in alla pjäser på rätt plats i pieces listan
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if ((y == 0 && x == 0) || (y == 0 && x == 7)) {
                    pieces.add(new Rook(x, y, "black"));
                } else if ((y == 7 && x == 0) || (y == 7 && x == 7)) {
                    pieces.add(new Rook(x, y, "white"));
                } else if ((y == 0 && x == 1) || (y == 0 && x == 6)) {
                    pieces.add(new Knight(x, y, "black"));
                } else if ((y == 7 && x == 1) || (y == 7 && x == 6)) {
                    pieces.add(new Knight(x, y, "white"));
                } else if ((y == 0 && x == 2) || (y == 0 && x == 5)) {
                    pieces.add(new Bishop(x, y, "black"));
                } else if ((y == 7 && x == 2) || (y == 7 && x == 5)) {
                    pieces.add(new Bishop(x, y, "white"));
                } else if (y == 0 && x == 3) {
                    pieces.add(new Queen(x, y, "black"));
                } else if (y == 7 && x == 3) {
                    pieces.add(new Queen(x, y, "white"));
                } else if (y == 0 && x == 4) {
                    pieces.add(new King(x, y, "black"));
                } else if (y == 7 && x == 4) {
                    pieces.add(new King(x, y, "white"));

                } else if (y == 1) {
                    pieces.add(new Pawn(x, y, "black"));
                } else if (y == 6) {
                    pieces.add(new Pawn(x, y, "white"));
                }
            }

        }
        updateBoard();

    }

    private void select(int x, int y){
        try {
            if (this.getSquare(x, y).isActiveColor()) {
                selectedPiece = this.getSquare(x, y); // Returnerar den valda pjäsen
                currentMessage = selectedPiece.toString() + "was selected";
                selectionState = false;
            }
            else{
                currentMessage = "It is not " + selectedPiece.getTeam() + "s turn!";
                selectionState = true;
            }
        }
        catch (Exception e) {
            // Om något gått fel vill vi först skriva ut planen igen och sen skriva ut ett meddelande till användaren
            this.updateBoard();
            currentMessage = "Pick a square with an active piece!";
            selectionState = true;
        }
    }


    public boolean move(int x, int y) {
        if (selectionState) {
            this.select(x, y);
        } else {
            ArrayList<String> availableMoves = new ArrayList<String>(); // I den här array:en är den "riktiga" listan med lagliga drag
            String destination = Integer.toString(x) + Integer.toString(y); // Här gör vi om användarens drag till den form vi använder i våra listor
            destinationSquare = this.getSquare(x,y);

            if (selectedPiece instanceof Pawn) {
                String position = Integer.toString(x) + Integer.toString(y);

                if (x == selectedPiece.get_x() - 1 || x == selectedPiece.get_x() + 1) {
                    if (destinationSquare == null) {
                        currentMessage = "A pawn must capture a piece in order to move in diagonal direction!";
                    } else if ((selectedPiece.getTeam().equals("white") && y == selectedPiece.get_y() - 1) || (selectedPiece.getTeam().equals("black") && y == selectedPiece.get_y() + 1)) {
                        availableMoves.add(position);
                    }
                } else if (destinationSquare != null) {
                    if (!destinationSquare.activeColor)
                        currentMessage = "A pawn can only capture other pieces in diagonal direction!";
                    return false;
                }
            }

            for(String xyValue : selectedPiece.getMovePath(x, y)) {
                // I denna for-loop "går" pjäsen ruta för ruta till sin slutdestination,
                // om den stöter på en pjäs på vägen läggs den rutan till också, men sen slutar den "gå".

                int pos_x = Integer.parseInt(xyValue.split("")[0]);
                int pos_y = Integer.parseInt(xyValue.split("")[1]);

                if (this.getSquare(pos_x, pos_y) == null) {
                    availableMoves.add(xyValue);
                } else if (!this.getSquare(pos_x, pos_y).isActiveColor()) {
                    // Kollar färg på krockpjäs: Om inte samma färg --> Lägg till krockpjäsens position
                    if(selectedPiece instanceof Pawn)break;
                    availableMoves.add(xyValue);
                    break;
                } else if (this.getSquare(pos_x, pos_y).isActiveColor()) {
                    break;
                    // Kollar färg på krockpjäs: Om samma färg --> Lägg inte till krockpjäsens position
                }
            }

            if (availableMoves.contains(destination)) {

                if (destinationSquare == null) {
                    selectedPiece.move(x, y);
                    selectionState = true;
                    this.updateActiveTeam();
                    this.updateBoard();
                    currentMessage = "Move OK!";
                    return true;
                } else if (!destinationSquare.isActiveColor()) {
                    this.deletePieceAt(x, y);
                    selectedPiece.move(x, y);
                    selectionState = true;
                    this.updateActiveTeam();
                    this.updateBoard();
                    currentMessage = "Move OK!";
                    return true;
                }
            } else {
                currentMessage = "You can't move to that square!";
            }
            if(availableMoves.isEmpty()){
                currentMessage = "Pick another piece!";
                selectionState = true;
            }
        }

        return true;
    }


    private void updateBoard() {
        // Refreshar chessBoard och lägger in pjäserna från pieces listan på rätt plats
        chessBoard = new ChessPiece[8][8];

        for (ChessPiece piece : pieces) {
            chessBoard[piece.get_y()][piece.get_x()] = piece;
        }
    }

    private void updateActiveTeam() {
        for (ChessPiece piece : pieces) {
            piece.changeActiveState();
        }
    }

    private void deletePieceAt(int x, int y) {

        // Tar bort ur listan pieces så att pjäsen inte finns kvar när vi uppdaterar och skriver ut board nästa gång
        int counter = 0;
        for (ChessPiece piece : pieces) {
            if (piece.get_x() == x && piece.get_y() == y) {
                pieces.remove(counter);
                break;
            }
            counter++;
        }
    }

    private ChessPiece getSquare(int x, int y) {
        return chessBoard[y][x];
    } // Omvänt pga hur [][] listor fungerar

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
                if (x == 0 && y == 0) {
                    strBuilder.append("    ");
                } else if (y == 0) {
                    strBuilder.append("   " + (x - 1) + "  ");
                } else if (x == 0) {
                    strBuilder.append("   " + (y - 1));
                } else {
                    strBuilder.append("  " + getSquare(x - 1, y - 1));
                }

            strBuilder.append("\n");
        }

        return strBuilder.toString();
    }

    // Implementering av Interfacet Boardgame

    public ImageIcon getStatus(int x, int y) {
        return getSquare(x, y) == null ? new ImageIcon(getClass().getResource("osynlig.png")) : getSquare(x, y).getIcon();
    }

    public String getMessage() {
        return currentMessage;
    }
}

