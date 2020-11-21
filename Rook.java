package Projekt;

import javax.swing.*;
import java.util.ArrayList;

class Rook extends ChessPiece {

    Rook(int x, int y, String team){
        this.current_x = x;
        this.current_y = y;
        this.team = team;

        if(team.equals("white")){
            activeColor = true;
        }
        else{
            activeColor = false;
        }

    }

    @Override
    public void move(int new_x, int new_y) {
        current_x = new_x;
        current_y = new_y;
    }


    public ImageIcon getIcon(){
        if(this.team.equals("white")) {
            return new ImageIcon(getClass().getResource("v_torn.png"));
        }
        else{
            return new ImageIcon(getClass().getResource("s_torn.png"));
        }
    }

    @Override
    public ArrayList<String> getMovePath(int new_x, int new_y) {
        ArrayList<String> movesPathList = new ArrayList<String>();
        String direction;

        if (current_x < new_x) {
            direction = "right";
        } else if (current_x > new_x) {
            direction = "left";
        } else if (current_y < new_y) {
            direction = "down";
        } else {
            direction = "up";
        }

        if (direction.equals("up")) {
            for(int y = current_y - 1; y >= new_y; y--) {
                movesPathList.add(Integer.toString(current_x) + Integer.toString(y));
            }
        } else if (direction.equals("right")) {
            for(int x = current_x + 1; x <= new_x; x++) {
                movesPathList.add(Integer.toString(x) + Integer.toString(current_y));
            }
        } else if (direction.equals("down")) {
            for(int y = current_y + 1; y <= new_y; y++) {
                movesPathList.add(Integer.toString(current_x) + Integer.toString(y));
            }
        } else if (direction.equals("left")) {
            for(int x = current_x - 1; x >= new_x; x--) {
                movesPathList.add(Integer.toString(x) + Integer.toString(current_y));
            }
        }

        return movesPathList;
    }

    @Override
    public String toString(){
        if (team.equals("white"))
            return "w-R ";
        else
            return "b-R ";
    }
}

