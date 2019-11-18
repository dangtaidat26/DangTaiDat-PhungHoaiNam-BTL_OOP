package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

import sample.Enemy;
public class NormalEnemy extends Enemy{

    static final int speed = 1;
    static final int blood_first =300;
    static final int armor_normal = 2;
    static final String Normal_Image = "file:src/Image/Enemy";

    public NormalEnemy(List<Point> pointList)
    {
        super();
        setFirst_Blood(blood_first);
        setSpeed(speed);
        setArmor(armor_normal);
        loadImage(Normal_Image);
        loadRoad(pointList);
        setPosition(roadList.get(0).getX(), roadList.get(0).getY());
        setDri(angle_Right);
        this.i = 0;
        angle = 0;
        getRoadList();
        //System.out.println("SS :");
    }

    @Override
    public void ShowObject(GraphicsContext gc) {
        Move();
        gc.drawImage(this.image,x_pos, y_pos);
    }

    @Override
    public void Render(GraphicsContext gc) {
        Move();
        gc.drawImage(image,this.x_pos, this.y_pos, 50, 50);
        gc.setFill(Color.GRAY);
        gc.fillRect(x_pos + image.getWidth()/4 , y_pos - 3, image.getWidth()/2, 2);
        gc.setFill(Color.RED);
        gc.fillRect(x_pos + image.getWidth()/4 , y_pos - 3, image.getWidth()/2 * getBlood()/blood_first, 2);
//        gc.setStroke(Color.GREEN);
//        gc.strokeLine(x_pos + 25, y_pos + 25, 600, 360);
    }
}
