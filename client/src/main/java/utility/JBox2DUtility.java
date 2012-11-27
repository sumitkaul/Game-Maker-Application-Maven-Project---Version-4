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
	
	public float pixelToCentimeters(double pixels)
	{
		return (float) (pixels * Constants.PIXEL_TO_CENTIMERTER_PARAM);
	}
	
	public double centimetersToPixels(float centimerters)
	{
		return centimerters * Constants.CENTIMERTER_TO_PIXEL_PARAM;
	}

}
