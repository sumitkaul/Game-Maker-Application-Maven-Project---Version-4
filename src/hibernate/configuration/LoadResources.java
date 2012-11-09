package hibernate.configuration;

import java.awt.Dimension;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Player;
import model.Resource;

import org.hibernate.Query;
import org.hibernate.Session;

import view.Design;

public class LoadResources{

	//return a list of resource records from the Resources table
	public List<Resource> LoadResources(String username) throws IOException {
	    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LoadResources.class);
		boolean loginFlag=false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Resource where username = :username ");
        query.setParameter("username","tim");
        @SuppressWarnings("unchecked")
        List<Resource> list = query.list();
    return list;
    

	}

}

//An example of loading a resource from the DB
//LoadResources l = new LoadResources();
// try {
//	l.LoadResources("pranav");
//} catch (IOException e1) {
//	// TODO Auto-generated catch block
//	e1.printStackTrace();
//}          