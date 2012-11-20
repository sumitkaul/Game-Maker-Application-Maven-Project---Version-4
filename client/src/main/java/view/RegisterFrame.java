package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Player;
import utility.Constants;
import view.communication.ClientHandler;

public class RegisterFrame extends LoginFrame {

    @SuppressWarnings("deprecation")
	public RegisterFrame() {
        super();
       
        getLogin().addActionListener(new RegisterButtonListner());
        getLogin().setLabel("register");
    }

    class RegisterButtonListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RegisterButtonListner.class);

            if (e.getSource() ==  getLogin()) {

//                Player player = Player.getInstance();
//                player.setUsername(username.getText());
//                player.setPassword(password.getText());
//                player.setUsertype("GameMaker");

                setVisible(false);

                String user_name = getUsername().getText();
                @SuppressWarnings("deprecation")
				String pass_word = getPassword().getText();

                boolean registerok = ClientHandler.userRegister(user_name, pass_word, Constants.HOST, Constants.PATH+"/registerUser", new Exception[1]);


                GameMakerView d = GameMakerView.getInstance();
                if (registerok) {
                    Player.getInstance().setUsername(user_name);
                    Player.getInstance().setPassword(pass_word);

                    d.getButtonPanel().getUserName().setText("Welcome " + user_name);
                } else {
                    JOptionPane.showMessageDialog(d.getBaseFrame(), "Registration Faile");
                    Player.setInstanceNull();
                }


//                if (new RegisterConfiguration().isRegisterPlayer(player));
//                {
//                    Design d = Design.getInstance();
//                    d.getButtonPanel().getUserName().setText("Welcome " + player.getUsername());
//                }
            }
        }
    }
}
