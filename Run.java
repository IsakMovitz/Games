//package Projekt;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class Run {
//    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        Chess chessGame = new Chess();                 // Model object is created
//        ChessPiece selectedPiece = null;
//        ChessPiece destinationSquare;
//
//
//        System.out.println("\nWelcome to Chess!\n");
//
//        while (true) {
//            System.out.println(chessGame); // Skriver ut spelplanen
//
//            while (true) {
//                // I denna while-loop väljer användaren vilken pjäs den vill flytta
//                try {
//                    // Här testar vi om vi valt en pjäs och om den valda pjäsen är i det aktiva laget
//                    System.out.println();
//                    System.out.print("Choose a piece (x, y): ");
//                    int x = scan.nextInt();
//                    int y = scan.nextInt();
//
//                    if (!chessGame.getSquare(x, y).isActiveColor()) {
//                        // Om den valda pjäsen inte är en del av det aktiva laget --> Skapa en Exception (error)
//                        throw new Exception();
//                    }
//
//                    selectedPiece = chessGame.getSquare(x, y); // Returnerar den valda pjäsen
//                    selectedPiece.get_x(); // Vi testar att använda en metod på pjäsen för att kolla att vi inte valt en null-ruta
//
//                    break;
//                } catch (Exception e) {
//                    // Om något gått fel vill vi först skriva ut planen igen och sen skriva ut ett meddelande till användaren
//                    System.out.println(chessGame); // Print the current board
//                    System.out.println("Pick a square with an active piece!");
//                }
//            }
//
//            while(true) {
//                // Här väljer användaren vart hen vill flytta sin pjäs
//
//                System.out.print("Make a move with the " + selectedPiece.toString().replaceAll(" ","") + ": ");
//                int move_x = scan.nextInt();
//                int move_y = scan.nextInt();
//                ArrayList<String> availableMoves = new ArrayList<String>(); // I den här array:en är den "riktiga" listan med lagliga drag
//
//                String position = Integer.toString(move_x) + Integer.toString(move_y); // Här gör vi om användarens drag till den form vi använder i våra listor
//                destinationSquare = chessGame.getSquare(move_x, move_y);
//
//
//                if(selectedPiece instanceof Pawn){              // Testar fallet då pawn attackerar diagonalt
//                    if(move_x == selectedPiece.get_x() -1 || move_x == selectedPiece.get_x() + 1){
//                        if(destinationSquare == null){
//                            System.out.println("A pawn is required to capture an enemy to move diagonally!");
//                            continue;
//                        }
//                        else if((selectedPiece.getTeam().equals("white") && move_y == selectedPiece.get_y() -1) || (selectedPiece.getTeam().equals("white") && move_y == selectedPiece.get_y() + 1 )){
//                            if(destinationSquare.isActiveColor()){
//                                System.out.println("A pawn is required to capture an enemy to move diagonally!");
//                                continue;
//                            }
//                        }
//                        else{
//                            availableMoves.add(position);
//                        }
//                    }
//
//                    else{
//                        for (String xyValue : selectedPiece.getMovePath(move_x, move_y)) {
//                            // I denna for-loop "går" pjäsen ruta för ruta till sin slutdestination,
//                            // om den stöter på en pjäs på vägen läggs den rutan till också, men sen slutar den "gå".
//                            int pos_x = Integer.parseInt(xyValue.split("")[0]);
//                            int pos_y = Integer.parseInt(xyValue.split("")[1]);
//
//                            if (chessGame.getSquare(pos_x, pos_y) == null) {
//                                availableMoves.add(xyValue);
//                            }
//                            else{
//                                break;
//                            }
//                        }
//
//                    }
//                }
//
//
//                else{
//                    for (String xyValue : selectedPiece.getMovePath(move_x, move_y)) {
//                        // I denna for-loop "går" pjäsen ruta för ruta till sin slutdestination,
//                        // om den stöter på en pjäs på vägen läggs den rutan till också, men sen slutar den "gå".
//                        int pos_x = Integer.parseInt(xyValue.split("")[0]);
//                        int pos_y = Integer.parseInt(xyValue.split("")[1]);
//
//                        if (chessGame.getSquare(pos_x, pos_y) == null) {
//                            availableMoves.add(xyValue);
//                        } else if (!chessGame.getSquare(pos_x, pos_y).isActiveColor()) {  // kollar färg på krockpjäs, om inte samma färg så lägg till
//                            availableMoves.add(xyValue);
//                            break;
//                        } else if (chessGame.getSquare(pos_x, pos_y).isActiveColor()) {
//                            break;
//                        }
//                    }
//                }
//
//                if (availableMoves.contains(position)) {
//
//                    if (destinationSquare == null) {
//                        selectedPiece.move(move_x, move_y);
//                        break;
//                    }
//                    else if (!destinationSquare.isActiveColor()) {
//                        chessGame.deletePieceAt(move_x, move_y);
//                        selectedPiece.move(move_x, move_y);
//                        break;
//                    }
//                } else {
//                    System.out.println(chessGame); // Print the current board
//                    System.out.println("You can't move to that square!");
//                }
//
//
//            }
//
//            chessGame.updateActiveTeam();
//            chessGame.updateBoard();
//        }
//    }
//}