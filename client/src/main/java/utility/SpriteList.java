package utility;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import view.GameMakerView;

import model.SpriteModel;

public class SpriteList implements Serializable {

	private static SpriteList sharedSpriteList = null;
	private List<SpriteModel> spriteList;
	private SpriteModel selectedSpriteModel;
	private List<SpriteModel> selectedSpriteModels;

	private List<SpriteModel> toBeRemovedSpriteModels;
	
	public static SpriteList getInstance(){
		if(sharedSpriteList == null){
			sharedSpriteList = new SpriteList();
		}
		return sharedSpriteList;
	}

    protected SpriteList(){
        this.spriteList = new ArrayList<SpriteModel>();
        this.toBeRemovedSpriteModels = new ArrayList<SpriteModel>();
        this.selectedSpriteModels = new ArrayList<SpriteModel>();
    }

    public void addSprite(SpriteModel sprite){
    	getSpriteList().add(sprite);
    }
    
    public void removeSprite(SpriteModel spriteModel){
    	getSpriteList().remove(spriteModel);
    	GameMakerView.getInstance().getGamePanel().unregisterModel(spriteModel);
  
    }

    
    /*
     * GETTERS AND SETTERS
     */
    
	public List<SpriteModel> getSpriteList() {
		return spriteList;
	}

	public SpriteModel getSelectedSpriteModel() {
		return selectedSpriteModel;
	}
	
	public List<SpriteModel> getSelectedSpriteModels() {
		return selectedSpriteModels;
	}

	public void setSelectedSpriteModel(SpriteModel selectedSpriteModel) {
		this.selectedSpriteModel = selectedSpriteModel;
	}
	
	public void setSelectedSpriteModels(List<SpriteModel> selectedSpriteModels) {
		this.selectedSpriteModels = selectedSpriteModels;
	}

	public void setToBeRemovedSpriteModels(List<SpriteModel> toBeRemovedSpriteModels) {
		this.toBeRemovedSpriteModels = toBeRemovedSpriteModels;
	}

	public List<SpriteModel> getToBeRemovedSpriteModels() {
		return toBeRemovedSpriteModels;
	}
	
	public void setSpriteList(List<SpriteModel> spriteList) {
        this.spriteList = spriteList;
    }


}


