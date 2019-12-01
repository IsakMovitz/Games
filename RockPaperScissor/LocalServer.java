import java.net.*;
import java.io.*;
import java.util.*;
public class LocalServer {
    public static void main( String[] args) {
        try {
            ServerSocket sock = new ServerSocket(1716,100);
            while (true)
                new ClientHandler(sock.accept()).start();
        }
        catch(IOException e)
        {System.err.println(e);
        }
    }
}

class ClientHandler extends Thread {
    static int numberOfPls = 0;
    BufferedReader in;
    PrintWriter out;

    public ClientHandler(Socket socket){
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out= new PrintWriter(socket.getOutputStream());
        }
        catch(IOException e) {System.err.println(e);
        }
    }

    public void run() {
        Random random=new Random();
        String[] hand={"STEN","SAX","PASE"};
        try {
            String name=in.readLine();
            System.out.println((++numberOfPls)+": "+name);
            out.println("Hello, "+name);
            out.flush();
            while(true) {
                String input = in.readLine();
                if(input==null || input.equals("")) break;
                out.println(hand[random.nextInt(3)]);
                out.flush();
            }
            out.println("Bye " + name);
            out.flush();
            System.out.println(name + " stopped playing");
            numberOfPls--;
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
}
