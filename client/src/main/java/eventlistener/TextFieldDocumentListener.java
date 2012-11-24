package eventlistener;

import facade.Facade;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.SpriteModel;
import utility.SpriteList;
import utility.enums.PropertyField;
import view.GameMakerView;

public class TextFieldDocumentListener implements DocumentListener{

	@Override
	public void changedUpdate(DocumentEvent e) {
		fieldModified(e);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		fieldModified(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		fieldModified(e);
	}
	
	private void fieldModified(DocumentEvent e){
		//get the owner of this document
        String owner = (String) e.getDocument().getProperty("owner");
        JTextField textField = (JTextField) e.getDocument().getProperty("parent");
        

		Facade facade = GameMakerView.getInstance().getFacade();
        
        String textString = textField.getText();
    
        DefaultListModel spriteListIndividualModel = GameMakerView.getInstance().getSpriteListIndividualModel();
        DefaultListModel spriteListGroupModel = GameMakerView.getInstance().getSpriteListGroupModel();
        
        if(owner.equalsIgnoreCase(PropertyField.SPRITE_NAME.toString())){
			String previousName = SpriteList.getInstance().getSelectedSpriteModel().getId();
			if(textString != null && !textString.equalsIgnoreCase("")){
				SpriteList.getInstance().getSelectedSpriteModel().setId(textString);
				
				for(int i=0; i<spriteListIndividualModel.size(); i++){
					String tempString = (String) spriteListIndividualModel.get(i);
					if(tempString.equalsIgnoreCase(previousName)){
						spriteListIndividualModel.set(i, textString);
					}
				}
			}
        }
        else if(owner.equalsIgnoreCase(PropertyField.GROUP_NAME.toString())){
			String previousGroupId = SpriteList.getInstance().getSelectedSpriteModel().getGroupId();
			if(textString != null && !textString.equalsIgnoreCase("")){
				SpriteModel spriteModel = SpriteList.getInstance().getSelectedSpriteModel();
				spriteModel.setGroupId(textString);

				for(int i=0; i<spriteListGroupModel.size(); i++){
					String tempString = (String) spriteListGroupModel.get(i);
					if(tempString.equalsIgnoreCase(previousGroupId)){
						spriteListGroupModel.set(i, textString);		
					}
				}
				for(int i=0; i<spriteListGroupModel.size(); i++){
					String tempString = (String) spriteListGroupModel.get(i);
					if(tempString.equalsIgnoreCase(textString))
						spriteListGroupModel.remove(i);
				}
				if(!spriteListGroupModel.contains(spriteModel.getGroupId()))
					spriteListGroupModel.addElement(spriteModel.getGroupId());
			}
			if(textString != null && !textString.equalsIgnoreCase(""))
				SpriteList.getInstance().getSelectedSpriteModel().setGroupId(textString);
        }
        else if(owner.equalsIgnoreCase(PropertyField.VELOCITY_X.toString())){
    		if(textString!= null && !textString.equalsIgnoreCase(""))
				SpriteList.getInstance().getSelectedSpriteModel().setSpeedX(Double.parseDouble(textString));	
		
        }
        else if(owner.equalsIgnoreCase(PropertyField.VELOCITY_Y.toString())){
			if(textString != null && !textString.equalsIgnoreCase(""))
				SpriteList.getInstance().getSelectedSpriteModel().setSpeedY(Double.parseDouble(textString));
		
        }
        else if(owner.equalsIgnoreCase(PropertyField.WIDTH.toString())){
			if(textString != null && !textString.equalsIgnoreCase("")){
				SpriteList.getInstance().getSelectedSpriteModel().setWidth(Double.valueOf(textField.getText()));
				facade.getGamePanel().repaint();
			}
        }
        else if(owner.equalsIgnoreCase(PropertyField.HEIGHT.toString())){
			if(textString != null && !textString.equalsIgnoreCase("")){
				SpriteList.getInstance().getSelectedSpriteModel().setHeight(Double.valueOf(textString));
				facade.getGamePanel().repaint();
			}
        }
        
		
	}

}
