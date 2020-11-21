package Projekt;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class Knight extends ChessPiece {

    Knight(int x, int y, String team) {
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
        if(this.team == "white") {
            return new ImageIcon(getClass().getResource("v_springare.png"));
        }
        else{
            return new ImageIcon(getClass().getResource("s_springare.png"));
        }
    }


    public ArrayList<String> getMovePath(int new_x, int new_y) {
        ArrayList<String> availableMovesList = new ArrayList<String>();
        ArrayList<String> movesPathList = new ArrayList<String>();
        List<Integer> positionPairList = new ArrayList<>();
        int counter = 0;
        String newPosition;

        // 1:a möjliga draget
        positionPairList.add((current_x - 1));
        positionPairList.add((current_y - 2));

        // 2:a möjliga draget
        positionPairList.add((current_x + 1));
        positionPairList.add((current_y - 2));

        // 3:e möjliga draget
        positionPairList.add((current_x - 2));
        positionPairList.add((current_y - 1));

        // 4:e möjliga draget
        positionPairList.add((current_x + 2));
        positionPairList.add((current_y - 1));

        // 5:e möjliga draget
        positionPairList.add((current_x - 2));
        positionPairList.add((current_y + 1));

        // 6:e möjliga draget
        positionPairList.add((current_x + 2));
        positionPairList.add((current_y + 1));

        // 7:e möjliga draget
        positionPairList.add((current_x - 1));
        positionPairList.add((current_y + 2));

        // 8:e möjliga draget
        positionPairList.add((current_x + 1));
        positionPairList.add((current_y + 2));


        for (int positionValue : positionPairList) {
            if ((counter % 2) == 0)
                if (positionValue >= 0 && positionValue <= 7 && positionPairList.get(counter + 1) >= 0 && positionPairList.get(counter+1) <= 7) {
                    availableMovesList.add(Integer.toString(positionValue) + Integer.toString(positionPairList.get(counter+1)));
                }
            counter++;
        }

        newPosition = Integer.toString(new_x) + Integer.toString(new_y);

        if(availableMovesList.contains(newPosition)){
            movesPathList.add(newPosition);
        }


        return movesPathList;
    }


    @Override
    public String toString(){
        if (team.equals("white"))
            return "w-Kt";
        else
            return "b-Kt";
    }
}