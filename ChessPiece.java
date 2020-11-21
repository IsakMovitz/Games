package Projekt;
import javax.swing.*;
import java.util.ArrayList;

abstract class ChessPiece {
        int current_x, current_y;
        String team;
        boolean activeColor;

        abstract void move(int x, int y);
        abstract ArrayList<String> getMovePath(int new_x, int new_y);
        abstract ImageIcon getIcon();

        int get_x(){
                return current_x;
        }
        int get_y(){
                return current_y;
        }

        String getTeam() {
                return team;
        }
        boolean isActiveColor() {
                return activeColor;
        }
        void changeActiveState() { activeColor = !activeColor;}

        public abstract String toString();
}

