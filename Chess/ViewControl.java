package Projekt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ViewControl extends JFrame implements ActionListener {

    private Boardgame game;
    private int size;
    private Square[][] board;
    private JLabel mess = new JLabel();

    ViewControl(Boardgame thegame, int n) {
        size = n;
        game = thegame;
        board = new Square[size][size];

        mess.setText(game.getMessage());

        JFrame frame = new JFrame("Chess");
        JPanel panel = new JPanel();


        panel.setLayout(new GridLayout(size, size));

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Square square = new Square((ImageIcon) game.getStatus(x,y), Integer.toString(x), Integer.toString(y));
                board[y][x] = square;
                square.addActionListener(this);
                panel.add(square);
                if((x+y) % 2 == 0){
                    square.setBackground(Color.darkGray);
                }

                square.setActionCommand(square.pos_x + " " +  square.pos_y);
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

        int input_x = Character.getNumericValue(command.toCharArray()[0]);
        int input_y = Character.getNumericValue(command.toCharArray()[2]);

        boolean valid_move = game.move(input_x, input_y);

        mess.setText(game.getMessage());

        if (valid_move) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {

                    board[y][x].setIcon(game.getStatus(x, y));
                }
            }
        }
    }
}
