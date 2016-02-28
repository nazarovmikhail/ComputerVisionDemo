package ru.nazarovmiha.project.utils;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nazarov on 29.01.2016.
 */
public class VideoDetection extends BaseDetection
{
    private ScheduledExecutorService timer;
    private VideoCapture capture;
    private boolean cameraActive;


    public VideoDetection(ImageView imageView){
        this.originalFrame = imageView;
    }

    public void init(){
        this.capture = new VideoCapture();
        this.faceCascade = new CascadeClassifier();
        this.absoluteFaceSize = 0;
    }


    public void startCamera()
    {
        originalFrame.setFitWidth(600);

        originalFrame.setPreserveRatio(true);

        if (!this.cameraActive)
        {

            this.capture.open(0);


            if (this.capture.isOpened())
            {
                this.cameraActive = true;


                Runnable frameGrabber = new Runnable() {

                    public void run()
                    {
                        Image imageToShow = grabFrame();
                        originalFrame.setImage(imageToShow);
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
            }
            else
            {
                System.err.println("Failed to open the camera connection...");
            }
        }
        else
        {

            this.cameraActive = false;

            try
            {
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {

                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }

            this.capture.release();

            this.originalFrame.setImage(null);
        }
    }

    public void stopCamera(){
        this.cameraActive = false;
        try
        {
            this.timer.shutdown();
            this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e)
        {
            System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
        }

        this.capture.release();

        this.originalFrame.setImage(null);
    }

    private Image grabFrame()
    {
        Image imageToShow = null;
        Mat frame = new Mat();

        if (this.capture.isOpened())
        {
            try
            {

                this.capture.read(frame);

                if (!frame.empty())
                {
                    this.detectAndDisplay(frame);
                    imageToShow = mat2Image(frame);
                }

            }
            catch (Exception e)
            {
                System.err.println("ERROR: " + e);
            }
        }

        return imageToShow;
    }


}
