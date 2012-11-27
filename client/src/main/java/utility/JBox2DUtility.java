package utility;

public final class JBox2DUtility {
	
	private static JBox2DUtility instance = new JBox2DUtility();
	
	private JBox2DUtility()
	{
		
	}
	
	public static JBox2DUtility getInstanceOf()
	{
		return instance;
	}
}
