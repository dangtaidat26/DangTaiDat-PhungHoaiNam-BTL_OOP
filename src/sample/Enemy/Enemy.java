package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import sample.*;
import sample.Enemy;
import sample.Tower;
import  sample.NormalEnemy;
import sample.SniperTower;


/* Khai báo enum */
enum Direction
{
    LEFT(180), RIGHT(0), UP(270), DOWN(90);
    int direction;
    Direction(int i)
    {
        this.direction = i;
    }
    public int getDirection()
    {
        return direction;
    }
}

public abstract class Enemy extends GameEntity
{
    protected int speed;        // Tốc độ di chuyển
    protected int Blood;        // Trị số máu
    protected int armor;        // Trị số giáp
    private List<Enemy> normalEnemies = new ArrayList<>(); // Danh sách quái
    static final int angle_Right = 0;       // Tưởng tượng theo đường tròn chiều kim đồng hồ
    static final int angle_Left = 180;      // Các hướng tương ứng sẽ có góc như khai báo bên
    static final int angle_Up =  90 ;
    static final int angle_Down = 270;
    protected Point point;                  // Đối tượng 2 tham số x, y mô tả tọa độ trên bản đồ, dùng trong việc tạo đường đi        
    protected List<Point> roadList = new ArrayList<>(); // Danh sách các point
    protected int angle = 0;                            // góc đi
    protected  int i;                                   // biến dùng trong vòng lặp
    protected int dri;                                  // hướng đi


    /* Thêm quái vào danh sách */
    public void adds(Enemy normalEnemy)
    {
        normalEnemies.add(normalEnemy);
    }

    /* Tạo đường đi */
    public void loadRoad(List<Point> pointList)
    {
        roadList.addAll(pointList);
    }

    /* tạo hiệu ứng di chuyển */
    public void Move()
    {
        /***** Xử lý hướng đi của quái theo dạng trục Oxy *******/
        switch (getDri())
        {
            case angle_Right:
                this.x_pos += speed;
                break;
            case angle_Left:
                this.x_pos -= speed;
                break;
            case angle_Down:
                this.y_pos += speed;
                break;
            case angle_Up :
                this.y_pos -= speed;
                break;
        }

        /* Thao tác kiểm tra xem quái đã đi hết đường hay chưa */
        /* Kiểm tra vị trí tiếp theo và vị trí hiện tại, nếu ko lệch thì tức là đã đến cuối đường */
        int delta_x = this.roadList.get(i + 1).getX() - this.x_pos;
        int delta_y = this.roadList.get(i + 1).getY() - this.y_pos;
        if(delta_x == 0 && delta_y == 0 && i < roadList.size())
        {
            i ++;
        }
        /* Xử lý tai lối rẽ, khi gặp rẽ thì xoay vuông góc */
        if(this.dri != nextRoad())
        {
            this.dri = nextRoad();
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            ImageView imageView = new ImageView(this.image);
            imageView.setRotate(imageView.getRotate() + 90);
            this.image = imageView.snapshot(snapshotParameters, null);
        }
    }


    /* Tìm hướng đi kế tiếp là rẽ trái phải hay trên dưới */
    /* Hướng nhìn trên màn hình của người chơi bị ngược so với sự di chuyển thực tế của quái nên nextRoad được định nghĩa như bên dưới
       xem lại khai báo enum để hiểu rõ
    */
    public int nextRoad()
    {
        int delta_x = this.roadList.get(i + 1).getX() - this.roadList.get(i).getX();
        int delta_y = this.roadList.get(i + 1).getY() - this.roadList.get(i).getY();
        if(delta_x == 0 && delta_y > 0)
        {
            return angle_Down;
        }
        if(delta_x == 0 && delta_y < 0)
        {
            return angle_Up;
        }
        if(delta_x > 0 && delta_y == 0)
        {
            return angle_Right;
        }
        if(delta_x < 0 && delta_y == 0)
        {
            return angle_Left;
        }
        return  0;
    }

    /* Trả về vị trí của quái, do từ khi gọi hàm tới lúc trả về có thời gian chênh lệch,
       quái vẫn di chuyển nên + thêm một lượng để ước lượng vị trí hiện tại của quái */
    public Point getPosition()
    {
        point = new Point(x_pos + 5, y_pos + 10);
        return point;
    }

    /* Vẽ quái ra màn hình và tạo hiệu ứng */
    public void RenderList(GraphicsContext gc)
    {
        for (int i = 0; i < normalEnemies.size(); i ++)
        {
            /* Nếu quái bị bắn chết, xóa nó khỏi danh sách */
            if(normalEnemies.get(i).is_dead()) normalEnemies.remove(i);
            /* Nếu không, in nó ra màn hình */
            else
            {
                normalEnemies.get(i).Render(gc);
            }
        }
    }


    /* Khi bị bắn trúng, quái bị trừ máu */
    public void bleed(int blood_delta)
    {
        this.Blood -= blood_delta;
    }

    /* Khi quái bị bắn chết hoặc đi hết đường đi thì đều coi là nó đã chết để tiện xử lý */
    public boolean is_dead()
    {
        return  (this.Blood <= 0  ||  this.x_pos > 1200);
    }

    @Override
    public void loadImage(String path)
    {
        this.image = new Image(path + ".png", 50, 50, true, true);
    }

    public int getDri()
    {
        return dri;
    }
    
    public void setDri(int dri)
    {
        this.dri = dri;
    }

    public Enemy get(int index)
    {
        return normalEnemies.get(index);
    }

    public int size()
    {
        return normalEnemies.size();
    }

    public void getRoadList()
    {
        for(Point p : roadList)
        {
        }
    }

    public List<Enemy> getListEnemy()
    {
        return normalEnemies;
    }


    /* Setter và Getter */
    public void setArmor(int armor)
    {
        this.armor = armor;
    }

    public int getArmor()
    {
        return armor;
    }

    public int getBlood()
    {
        return Blood;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public void setFirst_Blood(int first_Blood)
    {
        Blood = first_Blood;
    }


}
