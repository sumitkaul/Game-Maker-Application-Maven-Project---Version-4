package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

public class Register implements ActionListener{
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(Register.class);
	
	public JFrame regFrame;
	private Login logFrame;
    private JButton okButton, cancelButton;
    private JTextField userName, passWord, passWord2;

    public Register (){
    	
    	regFrame = new JFrame();
    	okButton = new JButton("Ok");
    	okButton.addActionListener(this);
    	cancelButton = new JButton("Cancel");
    	cancelButton.addActionListener(this);
    	userName = new JTextField("",10);
    	passWord = new JPasswordField("", 10);
    	passWord2 = new JPasswordField("", 10);
    	
    	regFrame.setLayout(new MigLayout("center,center"));

    	regFrame.add(new JLabel("User Name: "));
        regFrame.add(userName, "wrap,span,grow");
        regFrame.add(new JLabel("Password: "));
        regFrame.add(passWord, "wrap,span,grow");
        regFrame.add(new JLabel("re-Password: "));
        regFrame.add(passWord2, "wrap,span,grow");
        regFrame.add(okButton);
        regFrame.add(cancelButton);
        
    	regFrame.setSize(300, 300);
        regFrame.setFocusable(true);
        //regFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regFrame.setLocationRelativeTo(null);
        regFrame.setTitle("Registration Page");
        regFrame.setVisible(false);
    }
    /*
    public static void main(String[] args){
    	new Register();
    }
	*/
	public Login getLogFrame() {
		return logFrame;
	}

	public void setLogFrame(Login logFrame) {
		this.logFrame = logFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		  if (e.getSource() == okButton) {
			  if(userName.getText().equalsIgnoreCase("")||passWord.getText().equalsIgnoreCase("")||passWord2.getText().equalsIgnoreCase("")){
				  JOptionPane.showMessageDialog(regFrame, "UserName and Password can not be empty");
			  }else if(!passWord.getText().equals(passWord2.getText())){
				  JOptionPane.showMessageDialog(regFrame, "Password does not match ");
			  }else{
				  //Httprequest to signup servlet
				  regFrame.setVisible(false);
				  logFrame.loginFrame.setVisible(true);
			  }
		  }
		  
		  if (e.getSource() == cancelButton) {
			  regFrame.setVisible(false);
			  logFrame.loginFrame.setVisible(true);
		  }
		
	}
	
}

