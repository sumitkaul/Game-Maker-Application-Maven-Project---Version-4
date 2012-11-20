package config;

import db.DatabaseHandler;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.jboss.logging.Logger;

public class UndeployListener implements ServletContextListener {
    
    private static Logger log = Logger.getLogger(UndeployListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("----- Deploying Team3 A9 Web Application -----");
        DatabaseHandler.getDatabaseHandlerInstance();
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("-----  Undeloying Team3 A9 Web Application -----");
        DatabaseHandler.getDatabaseHandlerInstance().cleanUp();
    }
}
