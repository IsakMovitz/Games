import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;


class RPSSkel extends JFrame implements ActionListener {
    Gameboard myboard, computersboard;
    int counter; // To count ONE ... TWO  and on THREE you play
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    JButton closebutton;
    String computerMove;

    RPSSkel() {
        counter = 1;
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            socket=new Socket("localhost",1716);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Får från servern
            out = new PrintWriter(socket.getOutputStream()); // Ger till servern
            out.println("Isak Pettersson");
            out.flush();
            System.out.println(in.readLine());
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        closebutton = new JButton("Close");
        closebutton.addActionListener(this);
        closebutton.setActionCommand(closebutton.getText());



        myboard = new Gameboard("Myself", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String command = ae.getActionCommand();

                if(counter == 1){
                    myboard.resetColor();
                    computersboard.resetColor();
                    myboard.setLower("Ett...");
                    counter++;
                }
                else if(counter == 2){
                    myboard.setLower("Två...");
                    counter++;
                }
                else if(counter == 3){

                    try{
                        out.println(command);
                        out.flush();
                        computerMove = in.readLine();

                    }
                    catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }


                    myboard.markPlayed(command);
                    computersboard.markPlayed(computerMove);
                    counter = 1;

                    if (command.equals(computerMove)) { // || (command == "PASE" && computerMove == "PASE") || (command == "PASE" && computerMove == "PASE")) {
                        myboard.setLower("Draw!");
                        computersboard.setLower("Draw!");
                    } else if ((command.equals("STEN") && computerMove.equals("SAX")) || (command.equals("SAX") && computerMove.equals("PASE")) || (command.equals("PASE") && computerMove.equals("STEN"))) {
                        myboard.setLower("Win!");
                        computersboard.setLower("Lose!");
                        myboard.wins();
                    } else if ((command.equals("SAX") && computerMove.equals("STEN")) || (command.equals("PASE") && computerMove.equals("SAX")) || (command.equals("STEN") && computerMove.equals("PASE"))) {
                        myboard.setLower("Lose!");
                        computersboard.setLower("Win!");
                        computersboard.wins();
                    }
                }

            }
        }); // Must be changed

        computersboard = new Gameboard("Computer");

        JPanel boards = new JPanel();
        boards.setLayout(new GridLayout(1, 2));
        boards.add(myboard);
        boards.add(computersboard);
        add(boards, BorderLayout.CENTER);
        add(closebutton, BorderLayout.SOUTH);
        setSize(400, 550);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String command = ae.getActionCommand();

        if(command == "Close"){
            try{
                out.println("");
                out.flush();
                System.out.println(in.readLine());
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);

        }
    }

}


