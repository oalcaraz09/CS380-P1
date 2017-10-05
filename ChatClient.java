
/*  Oscar Alcaraz
	CS 380 Networks
	Project 1
*/

import java.io.*;
import java.net.*;


public class ChatClient {

    public static void main(String[] args) {


        try  {

            // Connect to server
            Socket socket = new Socket("18.221.102.182",38001);
            
            Thread sendThread = new Thread(new AdditionalThread(socket));
            sendThread.start();
            
        } 
        
        catch (Exception e) { e.printStackTrace(); }


    }

    static class AdditionalThread implements Runnable {

        protected Socket socket;
        protected String userName;

        public AdditionalThread() {}

        public AdditionalThread(Socket socket) {
        	
            this.socket = socket;
            userName = null;
        }

        public void run() {

            // Receive Thread
            Thread receiver = new Thread(new ServerChat(socket));
            receiver.start();

            try {
            	
            	//Initialize Streams
                OutputStream outStream = socket.getOutputStream();
                InputStream inStream = socket.getInputStream();

                // Send to user & Check
                sendUserName();

                if(socket.isConnected()) {
                	
                    // Connection Successfull
                    Thread.sleep(300);
                    System.out.println("--- YOU ARE NOW CONNECTED ---\n");
                    
                    int count = 0;
                    while(!socket.isClosed()) {
                    	
                        if(count == 0) {
                        	
                            // Notify when ready
                            System.out.println("-Ready for input-");
                            ++count;
                        }
                        
                        sendMessage();
                    }
                    
                    //Notify when Disconnected
                    System.out.println("\n--- DISCONNECTED ---\n");
                }


            } 
            catch (Exception e) { e.printStackTrace(); }
        }

        // Sends the username to the server
        //Chech if name in use
        public void sendUserName() {
        	
            System.out.print("Username: ");
            String uName = null;
            
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            
            try {
            	
                OutputStream outStream = socket.getOutputStream();
                PrintStream outS = new PrintStream(outStream, true, "UTF-8");
                
                uName = bufferRead.readLine();
                
                //Check if Username is in use
                this.userName = uName;
                outS.println(userName);
                System.out.println();
                
            }
            
            catch (IOException e) {  }
        }

        //Send input to the server
        public void sendMessage() {

            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String message = null;
            
            try {
            	
                OutputStream outStream = socket.getOutputStream();
                PrintStream outS = new PrintStream(outStream, true, "UTF-8");
                
                message = bufferRead.readLine();
                
                //Check if User types 'Exit' to terminate
                if(message.equals(":exit")) {
                	
                    socket.close();
                    return;
                }
                
                outS.println(message);
                
            } 
            
            catch (Exception e) {  }

        }

    }

}