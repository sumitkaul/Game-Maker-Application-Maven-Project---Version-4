package hibernate.configuration;

import java.util.List;

import model.Player;

import org.hibernate.Query;
import org.hibernate.Session;

import view.Design;

public class LoginConfiguration {
	
	public boolean loginConfigurationCheck(String username,String password) {
		// TODO Auto-generated constructor stub
	    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LoginConfiguration.class);
		boolean loginFlag=false;
		LOG.debug("Maven + Hibernate + MySQL");
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
        		loginFlag= true;
        }
        return loginFlag;
	}

}
