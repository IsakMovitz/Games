package Projekt;
import javax.swing.*;
import java.awt.*;

class Square extends JButton {

    private Color color = Color.white;
    String pos_x;
    String pos_y;

    Square(ImageIcon pieceType, String pos_x, String pos_y) {
        setFont(new Font("Times New Roman", Font.PLAIN, 40));
        setBackground(color);
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        setIcon(pieceType);

    }
}


