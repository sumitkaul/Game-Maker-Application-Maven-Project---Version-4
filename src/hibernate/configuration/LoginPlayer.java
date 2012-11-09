package hibernate.configuration;

import java.util.List;

import model.Player;

import org.hibernate.Query;
import org.hibernate.Session;

import view.Design;

public class LoginPlayer {
	
	public LoginPlayer(String username,String password)
	{
		final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LoginPlayer.class);
		Design d=Design.getInstance();
		LOG.debug("Logging in player");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Player where username = :username ");
        query.setParameter("username",username);
        @SuppressWarnings("unchecked")
		List <Player>list = query.list();
        for(Player player:list)
        {
        	LOG.debug(player.getUsername()+"   "+player.getPassword());
        	if(player.getPassword().equals(password))
        		d.getButtonPanel().getUserName().setText("Welcome "+player.getUsername());			        						        		
        	else
        		d.getButtonPanel().getUserName().setText("invalid username and password");
        }
	}

}
