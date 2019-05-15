package ttb_Src;

import javax.swing.JFrame;

public class TTB_Server_Main {
	
	public static void main (String[] args){
		TTB_Server Obj = new TTB_Server();
		Obj.setVisible(true);
		Obj.setSize(340,430);
		Obj.setLocationRelativeTo(null);
		Obj.setResizable(false);
		Obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Obj.startRunning();
	}

}
