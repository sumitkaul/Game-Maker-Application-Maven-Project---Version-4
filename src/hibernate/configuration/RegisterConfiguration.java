package hibernate.configuration;

import model.Player;

import org.hibernate.Session;


public class RegisterConfiguration {
	
	public boolean isRegisterPlayer(Player player)
	{
		final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RegisterConfiguration.class);
		LOG.debug("Start registration");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(player);
        session.getTransaction().commit();
        return true;
	}

}
