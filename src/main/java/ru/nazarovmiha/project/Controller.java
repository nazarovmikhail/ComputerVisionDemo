package ru.nazarovmiha.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import ru.nazarovmiha.project.utils.VideoDetection;
import ru.nazarovmiha.project.utils.Utils;

import static ru.nazarovmiha.project.utils.Utils.FileChooseMethod;

public class Controller {



    @FXML
    private Button btn_1;

    @FXML
    private Button btn_2;

    @FXML
    private ImageView pic1;

    @FXML
    private Button btn_3;

    @FXML
    private Button btn_4;

    @FXML
    private ImageView pic2;

    @FXML
    private Button btn_5;

    @FXML
    private Button btn_6;

    @FXML
    private ImageView pic3;

    @FXML
    public void onClickBtn1(){
        FileChooseMethod(pic1);
    }
    @FXML
    public void onClickBtn2(){
    }
    @FXML
    public void onClickBtn3(){

    }
    @FXML
    public void onClickBtn4(){

    }

    @FXML
    VideoDetection my = new VideoDetection(pic3);

    @FXML
    public void onClickBtn5(){

        my.init();
        my.startCamera();
    }

    @FXML
    public void onClickBtn6(){
        my.stopCamera();

    }
}
