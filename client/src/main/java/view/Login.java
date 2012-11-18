package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import net.miginfocom.swing.MigLayout;



public class Login implements ActionListener {
	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(Login.class);
	
	public JFrame loginFrame;
	private Register regFrame;

	private JButton okButton, regButton;
    private JTextField userName, passWord;

    public Login (){
    	
    	loginFrame = new JFrame();
    	okButton = new JButton("Ok");
    	okButton.addActionListener(this);
    	regButton = new JButton("Register");
    	regButton.addActionListener(this);
    	userName = new JTextField("",10);
    	passWord = new JPasswordField("", 10);
    	
    	loginFrame.setLayout(new MigLayout("center,center"));

    	loginFrame.add(new JLabel("User Name: "));
        loginFrame.add(userName, "wrap,span,grow");
        loginFrame.add(new JLabel("Password: "));
        loginFrame.add(passWord, "wrap,span,grow");
        loginFrame.add(okButton);
        loginFrame.add(regButton);
        
    	loginFrame.setSize(300, 300);
        loginFrame.setFocusable(true);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setTitle("Login Page");
        loginFrame.setVisible(true);
    }
    /*
    public static void main(String[] args){
    	new Login();
    }
    */
    public Register getRegFrame() {
		return regFrame;
	}

	public void setRegFrame(Register regFrame) {
		this.regFrame = regFrame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		  if (e.getSource() == okButton) {			  
			  if(userName.getText().equalsIgnoreCase("")||passWord.getText().equalsIgnoreCase("")){
				  JOptionPane.showMessageDialog(loginFrame, "UserName and Password can not be empty");
			  }else{
				  boolean result = true;//test, change default back to false later
				  
				  //Login HTTPrequest to LoginServlet return result
				  
				  if(result){
				  GameMakerView.getInstance().setUserName(userName.getText());
				  loginFrame.setVisible(false);
				  }
				  else{
				  JOptionPane.showMessageDialog(loginFrame, "UserName or Password wrong");
				  }
			  }
		  }
		  
		  if (e.getSource() == regButton) {
			  loginFrame.setVisible(false);
			  regFrame.regFrame.setVisible(true);
		  }
		
	}

}
