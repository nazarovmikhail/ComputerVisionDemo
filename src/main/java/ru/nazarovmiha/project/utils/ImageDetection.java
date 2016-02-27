package ru.nazarovmiha.project.utils;

import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Created by Nazarov on 28.02.2016.
 */
public class ImageDetection extends BaseDetection {
    private Image image;
    private Image imageToShow;

    ImageDetection(Image img){
        this.image = img;
    }

    public void Detection(){
        Mat mat = image2Mat(this.image);
        detectAndDisplay(mat);
        this.imageToShow = mat2Image(mat);
        originalFrame.setImage(imageToShow);
    }

    public Image DetectionImage(){
        Mat mat = image2Mat(this.image);
        detectAndDisplay(mat);
        this.imageToShow = mat2Image(mat);
        return imageToShow;
    }

}
