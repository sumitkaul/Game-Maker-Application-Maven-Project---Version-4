package hibernate.configuration;

import model.Resource;

import org.hibernate.Session;


public class SaveResource {
	
	public SaveResource(Resource resource)
	{
		final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SaveResource.class);
		LOG.debug("Start saving");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(resource);
        session.getTransaction().commit();
 	}
}

//An example of saving a resource to the DB
//BufferedImage img = null;
//try {
//  img = ImageIO.read(this.getClass().getResource("test.png"));
//  
//} catch (IOException e1) {
//}
//Resource resource = new Resource();
//resource.setName("test");
//resource.setType("test");
//resource.setUsername("pranav");
//
//ByteArrayOutputStream baos = new ByteArrayOutputStream();  
//try {
//	ImageIO.write((RenderedImage)img, "png", baos);
//} catch (IOException e1) {
//	// TODO Auto-generated catch block
//	e1.printStackTrace();
//}  
//
//byte[] data = baos.toByteArray();
//
//resource.setData(data);
//SaveResource saveResource = new SaveResource(resource);