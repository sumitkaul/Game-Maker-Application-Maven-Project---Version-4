package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;


//import com.mysql.jdbc.Blob;
import java.sql.Blob;

@Deprecated
public class Resource {
	
	private String name;
	private String type;
	private byte[] data;
	private String username;
	
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public byte[] getData() {
		return data;
	}
	
	 
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
//	 /** Don't invoke this.  Used by Hibernate only. */ 
//	  public void setDataBlob(Blob blob) {  
//	   this.data = this.toByteArray(blob); 
//	  } 
//	  
//	  /** Don't invoke this.  Used by Hibernate only. */ 
//	  public Blob getDataBlob() {  
//	   return  Hibernate.getLobCreator(HibernateUtil.getSessionFactory().getCurrentSession()).createBlob(this.data); 
//	  } 
//	  
//	  private byte[] toByteArray(Blob fromBlob) {  
//	   ByteArrayOutputStream baos = new ByteArrayOutputStream();  
//	   try {   
//	    return toByteArrayImpl(fromBlob, baos);  
//	   } catch (SQLException e) {   
//	    throw new RuntimeException(e); 
//	   } catch (IOException e) {   
//	    throw new RuntimeException(e);  
//	   } finally {   
//	    if (baos != null) {  
//	     try {   
//	      baos.close(); 
//	     } catch (IOException ex) {    }   }  } } 
	  
//	  private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos)  
//	  throws SQLException, IOException {  
//	   byte[] buf = new byte[4000];  
//	   InputStream is = fromBlob.getBinaryStream();  
//	   try {   for (;;) {    
//	    int dataSize = is.read(buf);   
//	    if (dataSize == -1)     break;   
//	    baos.write(buf, 0, dataSize);   }  } finally {   if (is != null) { 
//	     try {     is.close();    } catch (IOException ex) {    }   }  }  
//	    return baos.toByteArray(); }
	  
	 }
