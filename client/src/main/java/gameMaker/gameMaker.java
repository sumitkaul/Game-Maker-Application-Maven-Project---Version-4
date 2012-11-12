package gameMaker;

import team3.a9.imagewizard.ImageData;
import team3.a9.imagewizard.Wizard;
import team3.a9.lookandfeel.ThemeHandler;
import view.Design;
import view.OptionsFrame;

public class gameMaker {

    public static void main(String[] args) throws Exception {
        //if you want the defualt theme back, just comment out the belowing code
        ThemeHandler.showThemePanel();
        OptionsFrame optionFrame = new OptionsFrame();
    }
}
