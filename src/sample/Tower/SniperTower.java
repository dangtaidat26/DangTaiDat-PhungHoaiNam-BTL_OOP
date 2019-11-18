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

public class SniperTower extends Tower
{
    private final Image image_NormalTower = new Image("file:src/Image/Tower.png", 60 , 60, true, true); // Tải và set kích thước mặc định cho ảnh tháp
    private int newDame;    // Sát thương
    private int newRange;   // Tầm bắn
    private int newCost;    // Giá
    private final static int SCREEN_TITLEMAP = 30;
    private final int Range_Sniper = 250; // tầm bắn mặc định của tháp dạng sniper
    List<Tower> towerList = new ArrayList<>(); // danh sách các tháp được đặt
    ImageView iv;

    
    /* Constructor dạng kế thừa */
    public SniperTower()
    {
        super();
    }

    /* Constructor */
    public SniperTower(double x_pos, double y_pos) {
        super(x_pos, y_pos);
        this.dame = newDame;
        this.cost = newCost;
        this.towerImagePath = "file:src/Image/Tower";
        this.image = image_NormalTower;
        this.range = Range_Sniper;
    }
}

