package Lab2;
import java.util.*;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Lab2_Femtonspelet{
    public static void main(String[] args) {
        Boardgame thegame = new Femtonspelet();
        ViewControl view = new ViewControl(thegame, 4);
    }
}

interface Boardgame {
    public boolean move(int i, int j); //ger true om draget gick bra, annars false
    public String getStatus(int i, int j); // returnera innehåll på ruta (i,j)
    public String getMessage(); // returnera OK (eller liknande) eller felmeddelande avseende senaste drag
}

class Femtonspelet implements Boardgame {

    private String currentMessage;
    private String[][] board = new String[4][4];   // spelplanen
    private static String[] numberList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", ""};  // Nummer vi fyller den med
    private int i_empty, j_empty;                         //index till den tomma rutan
    //int the_move;

    Femtonspelet() {
        i_empty = 3;                            // Initierar spelplanen
        j_empty = 3;

        int numberList_index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = numberList[numberList_index];
                numberList_index++;
            }
        }

        int counter = 0;
        while (counter < 1000) {                       // Makes some random moves to shuffle
            Random randomGenerator = new Random();
            int i = randomGenerator.nextInt(4);
            int j = randomGenerator.nextInt(4);

            this.move(i, j);
            counter++;
        }
        currentMessage = "No message yet";

    }

    public boolean move(int i, int j) {



        if (i < 4 && j < 4 && i >= 0 && j >= 0) {

            if (i - 1 >= 0) {
                if (board[i - 1][j].equals("")) {
                    board[i_empty][j_empty] = board[i][j];
                    board[i][j] = "";
                    i_empty = i;
                    j_empty = j;

                    currentMessage = "OK"; // Löste det såhär for now.
                    return true;    // Förstår inte hur jag ska komma åt det returnerade värdet från move i getMessage metoden utan att kalla på den igen.
                }
            }

            if (j - 1 >= 0) {
                if (board[i][j - 1].equals("")) {
                    board[i_empty][j_empty] = board[i][j];
                    board[i][j] = "";
                    i_empty = i;
                    j_empty = j;

                    currentMessage = "OK";
                    return true;
                }
            }

            if (i + 1 < 4) {
                if (board[i + 1][j].equals("")) {
                    board[i_empty][j_empty] = board[i][j];
                    board[i][j] = "";
                    i_empty = i;
                    j_empty = j;

                    currentMessage = "OK";
                    return true;
                }
            }

            if (j + 1 < 4) {
                if (board[i][j + 1].equals("")) {
                    board[i_empty][j_empty] = board[i][j];
                    board[i][j] = "";
                    i_empty = i;
                    j_empty = j;

                    currentMessage = "OK";
                    return true;
                }
            }

            currentMessage = "Please choose a position next to the empty one!";
            return false;
        }

        currentMessage = "Please choose a position within the board!";
        return false;
    }


    public String getStatus(int i, int j) {
        return board[i][j];

    }

    public String getMessage() {

        return currentMessage;
    }
}


class Square extends JButton {

    Color color = Color.green;
    String pos_i;
    String pos_j;
    String text;

    Square(String text, String pos_i, String pos_j) {
        setText(text);
        setFont(new Font("Times New Roman", Font.PLAIN, 40));
        setBackground(color);
        this.pos_i = pos_i;
        this.pos_j = pos_j;
    }
}

class ViewControl extends JFrame implements ActionListener {

    private Boardgame game;
    private int size;
    private Square[][] board;
    private JLabel mess = new JLabel();

    ViewControl(Boardgame thegame, int n) {  // OK med fler parametrar om ni vill!
        size = n;
        game = thegame;
        board = new Square[size][size];

        mess.setText(game.getMessage());

        JFrame frame = new JFrame("Boardgame");
        JPanel panel = new JPanel();


        panel.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Square square = new Square(game.getStatus(i, j), Integer.toString(i), Integer.toString(j));
                board[i][j] = square;
                board[i][j].addActionListener(this);
                panel.add(square);


                square.setActionCommand(square.pos_i + " " + square.pos_j);
            }

        }
        frame.add(mess, BorderLayout.SOUTH);


        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        setDefaultCloseOperation(ViewControl.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {

        String command = ae.getActionCommand();
        String[] commandList = command.split(" ", 2);
        int input_i = Integer.parseInt(commandList[0]);
        int input_j = Integer.parseInt(commandList[1]);

        boolean valid_move = game.move(input_i, input_j);

        mess.setText(game.getMessage());
        if (valid_move) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    board[i][j].setText(game.getStatus(i, j));
                }
            }
        }
    }
}




