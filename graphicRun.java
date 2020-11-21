package Projekt;

public class graphicRun {
    public static void main(String[] args) {

        Boardgame thegame = new Chess();
        ViewControl view = new ViewControl(thegame, 8);
    }

}
