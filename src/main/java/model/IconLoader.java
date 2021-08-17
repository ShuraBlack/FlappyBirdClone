package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @since 27.05.2021
 * @author ShuraBlack
 * Part of my basic draw template.
 * Loads images out of the resource folder. This is just a temporarily object
 */
public class IconLoader {
    private BufferedImage icon;

    public IconLoader(String path) {
        try {
            icon = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return icon;
    }
}
