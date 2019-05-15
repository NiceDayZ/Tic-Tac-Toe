package ttb_Src_C;
import javax.swing.*;


public class TTB_Main_Client {
	public static void main (String[] args){
		TTB_Client Obj = new TTB_Client("188.173.49.219");
		Obj.setSize(340,430);
		Obj.setVisible(true);
		Obj.setLocationRelativeTo(null);
		Obj.setResizable(false);
		Obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Obj.startRunning();
		
	}
}
