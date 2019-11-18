package sample;


import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import sample.Tower;

import java.util.ArrayList;
import java.util.List;

public class SniperTower extends Tower {
    private final Image image_NormalTower = new Image("file:src/Image/Tower.png",
            60 , 60, true, true);
    private int newDame;
    private int newRange;
    private int newCost;
    private final static int SCREEN_TITLEMAP = 30;
    private final int Range_Sniper = 250;
    List<Tower> towerList = new ArrayList<>(); //ds tháp được đặt
    
    ImageView iv;

    public SniperTower() {
        super();

    }

    public SniperTower(double x_pos, double y_pos) {
        super(x_pos, y_pos);
        this.dame = newDame;
        this.cost = newCost;
        this.towerImagePath = "file:src/Image/Tower";
        this.image = image_NormalTower;
        this.range = Range_Sniper;
    }
}

