package Projekt;

import javax.swing.*;
import java.util.ArrayList;

class Bishop extends ChessPiece {

    Bishop(int x, int y, String team){
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
            return new ImageIcon(getClass().getResource("v_lopare.png"));
        }
        else{
            return new ImageIcon(getClass().getResource("s_lopare.png"));
        }
    }

    @Override
    public ArrayList<String> getMovePath(int new_x, int new_y) {
        
        ArrayList<String> movesPathList = new ArrayList<String>();
        String direction;
        int changeX= current_x;
        int changeY = current_y;
        
        
        if(new_x > current_x && new_y < current_y) direction = "up-right";
        
        else if(new_x > current_x && new_y > current_y) direction = "down-right";

        else if(new_x < current_x && new_y > current_y) direction = "down-left";
        
        else if(new_x < current_x && new_y < current_y) direction = "up-left";
        
        else{
            return movesPathList;
        }
        if(direction.equals("up-right")) {
            while (changeX < 7 && changeY > 0) {         // Uppåt till höger
                changeX++;
                changeY--;
                movesPathList.add(Integer.toString(changeX) + Integer.toString(changeY));
            }
        }

        if(direction.equals("down-right")) {
            while (changeX < 7 && changeY < 7) {              // Nedåt till höger
                changeX++;
                changeY++;
                movesPathList.add(Integer.toString(changeX) + Integer.toString(changeY));

            }
        }

        if(direction.equals("down-left")) {
            while (changeX > 0 && changeY < 7) {              // Nedåt till vänster
                changeX--;
                changeY++;
                movesPathList.add(Integer.toString(changeX) + Integer.toString(changeY));

            }
        }

        if(direction.equals("up-left")) {
            while (changeX > 0 && changeY > 0) {             // Uppåt till vänster
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
            return "w-B ";
        else
            return "b-B ";
    }
}
