package gameMaker;

import lookandfeel.ThemeHandler;
import imagewizard.ImageData;
import imagewizard.Wizard;
import view.Design;
import view.OptionsFrame;

public class gameMaker {

    public static void main(String[] args) throws Exception {
        //if you want the defualt theme back, just comment out the belowing code
        ThemeHandler.showThemePanel();
        OptionsFrame optionFrame = new OptionsFrame();
    }
}
