
/*  Oscar Alcaraz
	CS 380 Networks
	Project 1
*/

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class ServerChat implements  Runnable {

	//Socket user connects to
    private Socket socket;

    // Constructor
    public ServerChat(Socket s) {
    	
        socket = s;
    }

    public void run() {

            try {
            	
            	//Declare Input Stream
                InputStream inStream = socket.getInputStream();
                InputStreamReader iSR = new InputStreamReader(inStream, "UTF-8");
                
                //Initialize Buffer
                BufferedReader bufferRead = new BufferedReader(iSR);

                String serverMsg = "";

                //Take in a message from the server
                while((serverMsg = bufferRead.readLine()) != null) {

                    if(serverMsg.equals("Name in use.")) {
                    	
                        System.out.println("--- USER NAME TAKEN ---\n");
                        socket.close();
                        System.exit(0);
                        
                    } 
                    
                    else if(serverMsg.equals("Closing Connection..")) {
                    	
                        System.out.println("\n" + serverMsg + "\n");
                        socket.close();
                        System.exit(0);
                        
                    } else  {
                       
                        System.out.println(serverMsg);
                    }
                }

            } 
            
            catch (Exception e) { }
    }

}