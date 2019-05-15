package ttb_Src_C;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTB_Client extends JFrame{
	
	//********GUI***********
		private JLabel chatWindow;
		private JLabel XLabel;
		private JLabel OLabel;
		private ObjectOutputStream output;
		private ObjectInputStream input;
		private ServerSocket server;
		private Socket connection;
		private JButton button[] = new JButton[9];
		private String serverIP;
		
		//*****STATICS***********
		private static int intArray[] = new int[9];
		private static int x=10;
		private static int y=80;
		private static int XScore=0;
		private static int OScore=0;
		private static boolean able=false;
		private static boolean start=false;
		
		public TTB_Client (String host){
			super("Tic-Tac-Bear_Client");
			serverIP=host;
			
			this.setLayout(null);
			
			chatWindow = new JLabel();
			chatWindow.setText("Tic-Tac-Bear");
			chatWindow.setLocation(20, 50);
			chatWindow.setFont(new Font("Courier", Font.BOLD,14));
			chatWindow.setSize(400,20);
			add(chatWindow);
			
			XLabel = new JLabel("X: 0");
			XLabel.setLocation(10,10);
			XLabel.setForeground(Color.RED);
			XLabel.setFont(new Font(null, Font.BOLD, 30));
			XLabel.setSize(100,40);
			add(XLabel);
			
			OLabel = new JLabel("0: 0");
			OLabel.setLocation(260,10);
			OLabel.setForeground(Color.BLUE);
			OLabel.setFont(new Font(null, Font.BOLD, 30));
			OLabel.setSize(100,40);
			add(OLabel);
			
			
			for(int i=0; i<9; i++){
				
				final int ii = i;
				
				intArray[i] = 0;
				button[i] = new JButton();
				button[i].setBounds(x, y, 100, 100);
				button[i].setEnabled(true);
				button[i].setFont(new Font(null, Font.BOLD, 80));
				button[i].addActionListener(new ActionListener(){
				
					public void actionPerformed(ActionEvent arg0) {
					 if(able==true)
						if(intArray[ii]==0){
						button[ii].setForeground(Color.BLUE);
						button[ii].setText("0");
						intArray[ii] = 2;
						able=false;
						checkWin();
						try {
							output.writeObject(ii);
							output.flush();
						} catch (IOException e) {
							
						}
					}
					
				}});
				x+=100;
				add(button[i]);
				if((i+1)%3==0) {x=10; y += 100;}
			}
			
			
			
		}
		
		public void startRunning(){
			
					try{
						
						connectToServer();
						setupStreams();
						chatWindow.setText("");
						whilePlaying();
					}catch(EOFException eofException){
						showMessage("\n Server ended the connection! ");
					}catch(IOException e){} 
					
					finally{
						closeConnection(); 
					}
				
			
		}
		
		private void connectToServer() throws IOException{
			showMessage(" Attempting connection... \n");
			connection =  new Socket(InetAddress.getByName(serverIP), 1998);
			showMessage("Connection Established! Connected to: " + connection.getInetAddress().getHostName());
		}
		
		private void setupStreams() throws IOException{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			
			input = new ObjectInputStream(connection.getInputStream());
			
			showMessage("\n Streams are now setup \n");
			able=false;
		}
		
		private void whilePlaying() throws IOException{
			/********************************/
			int inputIndex;
			do{
				try{
					
					inputIndex = (Integer) input.readObject();
					intArray[inputIndex]=1;
					button[inputIndex].setForeground(Color.RED);
					button[inputIndex].setText("X");
					able=true;
					checkWin();
					
				}catch(ClassNotFoundException e){}
			}while(connection.isConnected());
			
		}
		
		public void closeConnection(){
			showMessage("\n Closing Connections... \n");
			try{
				output.close();
				input.close(); 
				connection.close(); 
			}catch(IOException ioException){
				
			}
		}
		
		private void showMessage(String message){
			chatWindow.setText(message);
		}
		
		private void checkWin(){
			     
				 if(intArray[0]==intArray[1] && intArray[0]==intArray[2] && intArray[0]!=0){Win(intArray[0]); reset();}
			else if(intArray[3]==intArray[4] && intArray[3]==intArray[5] && intArray[3]!=0){Win(intArray[3]); reset();}
			else if(intArray[6]==intArray[7] && intArray[6]==intArray[8] && intArray[6]!=0){Win(intArray[6]); reset();}
			else if(intArray[0]==intArray[3] && intArray[0]==intArray[6] && intArray[0]!=0){Win(intArray[0]); reset();}
			else if(intArray[1]==intArray[4] && intArray[1]==intArray[7] && intArray[1]!=0){Win(intArray[1]); reset();}
			else if(intArray[2]==intArray[5] && intArray[2]==intArray[8] && intArray[2]!=0){Win(intArray[2]); reset();}
			else if(intArray[0]==intArray[4] && intArray[0]==intArray[8] && intArray[0]!=0){Win(intArray[0]); reset();}
			else if(intArray[2]==intArray[4] && intArray[2]==intArray[6] && intArray[2]!=0){Win(intArray[2]); reset();}
			
				 boolean x=true;
				 for(int i=0;i<9;i++)
					 if (intArray[i]==0)
						 x=false;
				 if(x) reset();
				 
			
			
			
		}
		private void Win(int x){
			if(x==1){
				XScore++;
				XLabel.setText("X: "+String.valueOf(XScore));
				}
			if(x==2){
				OScore++;
				OLabel.setText("0: "+ String.valueOf(OScore));
			}
		}
		
		private void reset(){
			start=!start;
			able=start;
			for(int i=0;i<9;i++){
				intArray[i]=0;
				button[i].setText("");
			}
		}
	}
