/**
 * 
 */
package utility;

import java.util.ArrayList;
import java.util.List;

import view.Design;



public class Layers {

	private static Layers layer;
	public static Layers getInstance() {
		if(layer==null)
			layer = new Layers();
		return layer;
	}
	List<String> layers;
	
	public List<String> getLayers() {
		return layers;
	}

	public void setLayers(List<String> layers) {
		this.layers = layers;
	}
	
	@SuppressWarnings("unchecked")
	public String addNewLayer() {
		String layer = "Layer "+layers.size();
		this.layers.add(layer);
		
		Design.getInstance().getLayerBox().addItem(layer);
		
		
		return layer;
	}

	public Layers() {
		layers = new ArrayList<String>();
		
		layers.add(Constants.ALL_LAYERS);
	}
	
	
}
