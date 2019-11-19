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

public class NormalEnemy extends Enemy
{

    /* các chỉ số cơ bản của quái */
    static final int speed = 1;
    static final int blood_first = 200;
    static final int armor_normal = 1;
    /* link ảnh */
    static final String Normal_Image = "file:src/Image/Enemy";

    /* Constructor */
    public NormalEnemy(List<Point> pointList)
    {
        super();
        /* Máu, tốc độ, giáp */
        setFirst_Blood(blood_first);
        setSpeed(speed);
        setArmor(armor_normal);
        loadImage(Normal_Image);
        loadRoad(pointList);
        /* Điểm khởi hành */
        setPosition(roadList.get(0).getX(), roadList.get(0).getY());
        /* Đặt hướng đi */
        setDri(angle_Right);
        this.i = 0;
        angle = 0;
        getRoadList();
    }

    /* Hiển thị quái trên màn hình, tạo hiệu ứng di chuyển = move() */
    @Override
    public void ShowObject(GraphicsContext gc)
    {
        Move();
        gc.drawImage(this.image,x_pos, y_pos);
    }

    /* hiển thị thanh máu trên đầu quái */
    @Override
    public void Render(GraphicsContext gc) {
        Move();
        gc.drawImage(image,this.x_pos, this.y_pos, 50, 50);
        gc.setFill(Color.BLACK);
        gc.fillRect(x_pos + image.getWidth()/4 , y_pos - 3, image.getWidth()/2, 2);
        gc.setFill(Color.BLUE);       // set mầu máu của quái
        gc.fillRect(x_pos + image.getWidth()/4 , y_pos - 3, image.getWidth()/2 * getBlood()/blood_first, 2);
    }
}
