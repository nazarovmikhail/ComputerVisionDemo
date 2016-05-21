package ru.nazarovmiha.project.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nazarov on 27.02.2016.
 */
public class Utils {

    public static void FileChooseMethod(ImageView picture,Label time){
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilterjpg =
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterpng =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterppm =
                new FileChooser.ExtensionFilter("ppm files (*.ppm)", "*.ppm");
        FileChooser.ExtensionFilter extFilterbmp =
                new FileChooser.ExtensionFilter("bmp files (*.bmp)", "*.bmp");
        fileChooser.getExtensionFilters()
                .addAll(extFilterpng, extFilterjpg, extFilterppm, extFilterbmp);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            ImageDetection my = new ImageDetection(image);
            my.init();
            picture.setImage(my.DetectionImage());
            time.setText(String.format("%.4f sec", my.time));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
