package Projekt;

import javax.swing.*;
import java.util.ArrayList;

class Pawn extends ChessPiece {
    private boolean hasMoved;

    Pawn(int x, int y, String team) {
        this.current_x = x;
        this.current_y = y;
        this.team = team;
        activeColor = this.team.equals("white");
        this.hasMoved = false;
    }

    @Override
    public void move(int new_x, int new_y) {
        current_x = new_x;
        current_y = new_y;
        this.hasMoved = true;
    }

    public ImageIcon getIcon(){
        if(this.team.equals("white")) {
            return new ImageIcon(getClass().getResource("v_bonde.png"));
        }
        else{
            return new ImageIcon(getClass().getResource("s_bonde.png"));
        }
    }


    @Override
    public ArrayList<String> getMovePath(int new_x, int new_y) {
        ArrayList<String> movesPathList = new ArrayList<String>();

        if (this.team.equals("white")) {
            for (int y = current_y - 1; y >= (!hasMoved ? current_y - 2 : current_y - 1); y--) {
                movesPathList.add(Integer.toString(current_x) + Integer.toString(y));
            }
        } else {
            for (int y = current_y + 1; y <= (!hasMoved ? current_y + 2: current_y + 1); y++) {
                movesPathList.add(Integer.toString(current_x) + Integer.toString(y));
            }
        }

        return movesPathList;
    }

    @Override
    public String toString(){
        if (team.equals("white"))
            return "w-p ";
        else
            return "b-p ";
    }
}