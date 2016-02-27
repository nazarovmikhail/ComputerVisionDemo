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

    /**
     * The action triggered by pushing the button on the GUI
     */

    public void startCamera()
    {
        // set a fixed width for the frame
        originalFrame.setFitWidth(600);
        // preserve image ratio
        originalFrame.setPreserveRatio(true);

        if (!this.cameraActive)
        {
             // start the video capture
            this.capture.open(0);

            // is the video stream available?
            if (this.capture.isOpened())
            {
                this.cameraActive = true;

                // grab a frame every 33 ms (30 frames/sec)
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
                // log the error
                System.err.println("Failed to open the camera connection...");
            }
        }
        else
        {
            // the camera is not active at this point
            this.cameraActive = false;
            // stop the timer
            try
            {
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                // log the exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }

            // release the camera
            this.capture.release();
            // clean the frame
            this.originalFrame.setImage(null);
        }
    }

    public void stopCamera(){
        this.cameraActive = false;
        // stop the timer
        try
        {
            this.timer.shutdown();
            this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e)
        {
            // log the exception
            System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
        }

        // release the camera
        this.capture.release();
        // clean the frame
        this.originalFrame.setImage(null);
    }
    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Image} to show
     */
    private Image grabFrame()
    {
        // init everything
        Image imageToShow = null;
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened())
        {
            try
            {
                // read the current frame
                this.capture.read(frame);

                // if the frame is not empty, process it
                if (!frame.empty())
                {
                    // face detection
                    this.detectAndDisplay(frame);

                    // convert the Mat object (OpenCV) to Image (JavaFX)
                    imageToShow = mat2Image(frame);
                }

            }
            catch (Exception e)
            {
                // log the (full) error
                System.err.println("ERROR: " + e);
            }
        }

        return imageToShow;
    }


}
