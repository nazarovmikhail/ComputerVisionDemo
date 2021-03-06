package ru.nazarovmiha.project.utils;

import com.sun.istack.internal.Nullable;
import com.sun.javafx.geom.Vec3d;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

import static org.opencv.highgui.Highgui.imencode;

/**
 * Created by Nazarov on 28.02.2016.
 */

class BaseDetection {
    protected ImageView originalFrame;
    protected CascadeClassifier faceCascade;
    protected int absoluteFaceSize;
    protected float time;


    protected void detectAndDisplay(Mat frame)
    {
        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayFrame, grayFrame);


        if (this.absoluteFaceSize == 0)
        {
            int height = grayFrame.rows();
            if (Math.round(height * 0.2f) > 0)
            {
                this.absoluteFaceSize = 20; //Math.round(height * 0.2f);
            }
        }



        this.faceCascade.load("C:\\Users\\Nazarov\\IdeaProjects\\ComputerVisionDemo\\src\\main\\resources\\haarcascades\\cascade.xml");
        //this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2,0|Objdetect.CASCADE_SCALE_IMAGE,
        //        new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
        long startTime = System.currentTimeMillis();

        this.faceCascade.detectMultiScale(grayFrame, faces, 1.2, 3, 0,new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
        long endTime = System.currentTimeMillis();
        time = (float)((endTime - startTime))/1000f;

        Rect[] facesArray = faces.toArray();
        for (int i = 0; i < facesArray.length; i++)
              Core.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 0, 255, 255), 3);
    }

    protected Mat image2Mat(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] buffer = new byte[width * height * 4];

        PixelReader reader = image.getPixelReader();
        WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
        reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);

        Mat mat = new Mat(height, width, CvType.CV_8UC4);
        mat.put(0, 0, buffer);
        return mat;
    }


    protected Image mat2Image(Mat frame)
    {

        MatOfByte buffer = new MatOfByte();

        imencode(".png", frame, buffer);

        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }
}
