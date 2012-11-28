/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbox2d;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ralekar
 */
public class JBoxObjectList {

    public static List<JBoxSpriteModel>listJBoxSpriteModel;

    public JBoxObjectList() 
    {
    
        listJBoxSpriteModel=new ArrayList<JBoxSpriteModel>();
        
    }
    
    public void registerSpriteModel(JBoxSpriteModel sprite)
    {
        listJBoxSpriteModel.add(sprite);
    }
    
    
    
    
}

