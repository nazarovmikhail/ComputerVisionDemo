package ru.nazarovmiha.project.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

/**
 * Created by Nazarov on 28.02.2016.
 */
public class ImageDetection extends BaseDetection {
    private Image image;
    private Image imageToShow;

    ImageDetection(Image img){
        this.image = img;
    }
    public void init(){
        this.faceCascade = new CascadeClassifier();
        this.absoluteFaceSize = 0;
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
