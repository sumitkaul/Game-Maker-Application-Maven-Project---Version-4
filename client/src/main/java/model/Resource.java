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
 }
