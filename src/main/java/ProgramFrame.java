import model.IconLoader;
import util.Constant;

import javax.swing.*;
import java.awt.*;

/**
 * @since 27.05.2021
 * @author ShuraBlack
 * Part of my basic draw template.
 * Application window configuration
 */
public class ProgramFrame extends JFrame {
    /**
     * Constructor for setting up the values and configurated them
     */
    public ProgramFrame() {

        // Adds the draw Panel to the window
        this.add(new Setup());

        // Change the Title of the window
        this.setTitle("NotFlappyBird");

        // Change the Icon of the program
        this.setIconImage(new ImageIcon(new IconLoader("/images/Player.png").getImage()).getImage());

        // Default closing operation set to "Close program after closing window"
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Window isnt resizable
        this.setResizable(false);

        // Pack all components into the Frame
        this.pack();

        // Make it visible
        this.setVisible(true);

        // Set the application to fullscreen mode
        fullscreenMode();
    }

    /**
     * Calls the local graphics Environment to access the main Screen of your pc
     * and hands over the application window
     */
    private void fullscreenMode () {
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        device.setFullScreenWindow(this);
    }

}
