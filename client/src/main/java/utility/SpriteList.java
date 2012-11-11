package utility;


import java.util.ArrayList;
import java.util.List;

import view.Design;

import model.SpriteModel;

public class SpriteList {

	private static SpriteList sharedSpriteList = null;
	private List<SpriteModel> spriteList;
	private SpriteModel selectedSpriteModel;
	
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
    }

    public void addSprite(SpriteModel sprite){
    	getSpriteList().add(sprite);
    }
    
    public void removeSprite(SpriteModel spriteModel){
    	getSpriteList().remove(spriteModel);
    	Design.getInstance().getGamePanel().unregisterModel(spriteModel);
  
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

	public void setSelectedSpriteModel(SpriteModel selectedSpriteModel) {
		this.selectedSpriteModel = selectedSpriteModel;
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


