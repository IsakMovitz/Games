package Projekt;

import javax.swing.*;
import java.util.ArrayList;

class King extends ChessPiece {

    King(int x, int y, String team){
        this.current_x = x;
        this.current_y = y;
        this.team = team;
        activeColor = this.team.equals("white");
    }

    @Override
    public void move(int new_x, int new_y) {
        current_x = new_x;
        current_y = new_y;
    }

    public ImageIcon getIcon(){
        if(this.team.equals("white")) {
            return new ImageIcon(getClass().getResource("v_kung.png"));
        }
        else{
            return new ImageIcon(getClass().getResource("s_kung.png"));
        }
    }

    @Override
    public ArrayList<String> getMovePath(int new_x, int new_y) {
        ArrayList<String> movesPathList = new ArrayList<String>();
        String direction;
        int changeX= current_x;
        int changeY = current_y;


        // Sätter variabeln direction till vald riktning
        if (new_x > current_x && new_y < current_y)
            direction = "up-right";
        else if (new_x > current_x && new_y > current_y)
            direction = "down-right";
        else if (new_x < current_x && new_y > current_y)
            direction = "down-left";
        else if (new_x < current_x && new_y < current_y)
            direction = "up-left";
        else if (current_x < new_x && new_y == current_y) {
            direction = "right";
        } else if (current_x > new_x && new_y == current_y) {
            direction = "left";
        } else if (current_y < new_y && new_x == current_x) {
            direction = "down";
        } else {
            direction = "up";
        }


        // Väljer direction
        if (direction.equals("up")) {
            movesPathList.add(Integer.toString(current_x) + Integer.toString(current_y-1));

        } else if (direction.equals("right")) {
            movesPathList.add(Integer.toString(current_x +1) + Integer.toString(current_y));
        } else if (direction.equals("down")) {

            movesPathList.add(Integer.toString(current_x) + Integer.toString(current_y +1));

        } else if (direction.equals("left")) {
            movesPathList.add(Integer.toString(current_x -1) + Integer.toString(current_y));
        }

        else if(direction.equals("up-right")) {
            if(changeX < 7 && changeY > 0) {         // Uppåt till höger
                changeX++;
                changeY--;
                movesPathList.add(Integer.toString(changeX) + Integer.toString(changeY));
            }
        }

        else if(direction.equals("down-right")) {
            if(changeX < 7 && changeY < 7) {              // Nedåt till höger
                changeX++;
                changeY++;
                movesPathList.add(Integer.toString(changeX) + Integer.toString(changeY));

            }
        }

        else if(direction.equals("down-left")) {
            if(changeX > 0 && changeY < 7) {              // Nedåt till vänster
                changeX--;
                changeY++;
                movesPathList.add(Integer.toString(changeX) + Integer.toString(changeY));

            }
        }

        else if(direction.equals("up-left")) {
            if(changeX > 0 && changeY > 0) {             // Uppåt till vänster
                changeX--;
                changeY--;
                movesPathList.add(Integer.toString(changeX) + Integer.toString(changeY));
            }
        }


        return movesPathList;
    }

    @Override
    public String toString(){
        if (team.equals("white"))
            return "w-K ";
        else
            return "b-K ";
    }
}