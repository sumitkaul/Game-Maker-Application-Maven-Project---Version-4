package db;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.jboss.logging.Logger;

/**
 * <p>The implementation of DatabaseService Interface. The port for accessing
 * the actual Database.</p>
 *
 * @author $Author: hss $
 */
public class DatabaseHandler {

    private static final Logger logger = Logger.getLogger(DatabaseHandler.class.getName());
    private static DatabaseHandler instance;
    private SessionFactory sessionFactory;
    private ServiceRegistry serviceRegistry;

    private DatabaseHandler(String dbConfigFile) {
        Configuration configuration = null;

        try {
            configuration = new Configuration();
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info(configuration.toString());
        configuration.configure(dbConfigFile);

        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
    }

    private static DatabaseHandler getDatabaseHandler(String dbConfigFile) {
        if (instance == null) {
            instance = new DatabaseHandler(dbConfigFile);
        }
        return instance;

    }

    public static DatabaseHandler getDatabaseHandlerInstance() {
        return DatabaseHandler.getDatabaseHandler("hibernate.cfg.xml");
    }

    public synchronized void cleanUp() {
        sessionFactory.close();
    }

    public synchronized Session getHibernateSession() {
        Session session = sessionFactory.openSession();

        String log = "Distribute new Hibernate Session: " + session.hashCode();
        log += "\nPlease remember to close this Session when finish!";
        logger.info(log);

        return session;

    }

    public static List Query(String sql) {
        DatabaseHandler db = DatabaseHandler.getDatabaseHandlerInstance();
        Session s = db.getHibernateSession();
        Query q = s.createSQLQuery(sql);
        List r = q.list();
        s.close();

        return r;
    }

    public static int ExecuteQuery(String sql) {
        DatabaseHandler db = DatabaseHandler.getDatabaseHandlerInstance();
        Session s = db.getHibernateSession();
        Query q = s.createSQLQuery(sql);
        Transaction t = s.beginTransaction();
        int r = q.executeUpdate();
        t.commit();
        s.close();

        return r;
    }
}
