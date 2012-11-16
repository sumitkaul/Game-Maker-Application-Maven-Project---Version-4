package gameMaker;

import lookandfeel.ThemeHandler;
import view.OptionsFrame;

public class gameMaker {

    public static void main(String[] args) throws Exception {
        ThemeHandler.showThemePanel();
        System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/target/natives/");
        System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
        OptionsFrame optionFrame = new OptionsFrame();
    }
}
