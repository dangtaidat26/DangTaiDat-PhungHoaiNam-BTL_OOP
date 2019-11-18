package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import java.util.List;




public class Bullet extends GameTile{
    final static int dame = 3;                      // sát thương gây ra bởi đạn
    final static int speed_bullet = 5;              // tốc độ bay của đạn
    private SnapshotParameters snapshotParameters = new SnapshotParameters();


    private sample.Enemy TargetEnemy;               // mục tiêu được chọn để bắn
    private boolean is_found;                       // biến logic xác định việc đã tìm thấy mục tiêu bắn chưa
    protected int speed;                            // tốc độ đạn
    private double angle;                           // góc bắn
    private double sinX;                            // sinX, cosX được dùng để xác định góc bắn
    private double cosX;                            // (đường tròn lượng giác)
    private int indexListEnemy;                     // chỉ số vị trí của mục tiêu trong danh sách, dùng khi xác định mục tiêu bắn
    private boolean isshoot ;                       // biến logic xác định việc bắn trúng hay ko, nếu trúng, mục tiêu bị trừ lượng máu = sát thương - giáp
    private ImageView imageView = new ImageView();
    public Point Destination  = new Point(0, 0);    // điểm mà đạn bay tới


    /* Constructor */
    public Bullet(sample.Enemy enemy, int x_pos, int y_pos, int indexListEnemy, Image image_Bullt)
    {
        /* Khởi tạo cơ bản */
        super(x_pos, y_pos);
        this.image = image_Bullt;
        imageView.setImage(image);
        setSpeed(speed_bullet);
        is_found = false;
        angle = 0;
        sinX = 0;
        cosX = 1;
        this.indexListEnemy = indexListEnemy;
        isshoot = false;
        snapshotParameters.setFill(Color.TRANSPARENT);

        /* Sau 2 lệnh dưới đây, đạn mới thực sự xác định được mục tiêu cần bắn */
        setTargetEnemy(enemy);
        setDestination(enemy.getPosition());
    }


    public boolean isShoot()
    {
        /* Do mục tiêu là di động, nên việc bắn trúng là ướng lượng tương đối
           Ta tính được một ô trên bản đồ là hình vuông có cạnh 30 pixel
           chênh lệch 2 phía mỗi bên nửa ô sẽ bù trừ thời gian trễ để đạn bay tới điểm mà nó khóa làm mục tiêu
        */
        return (x_pos + 15 >= Destination.getX() && x_pos - 15 <= Destination.getX()
                && y_pos + 15 >= Destination.getY() && y_pos - 15 <= Destination.getY());
    }


    /* Tìm mục tiêu để bắn - tương tự phần giải thích tại Class Tower*/
    public void setTargetEnemy(List<sample.Enemy> listTarget)
    {
        if(TargetEnemy.is_dead() && !listTarget.isEmpty() && indexListEnemy < listTarget.size())
        {
            int preRange = (int) Math.sqrt(Math.pow(x_pos - listTarget.get(indexListEnemy).getX_pos(), 2)
                    + Math.pow(y_pos - listTarget.get(indexListEnemy).getY_pos(), 2));
            for(int i = indexListEnemy; i < listTarget.size(); i ++)
            {
                int preRange2 = (int) Math.sqrt(Math.pow(x_pos - listTarget.get(i).getX_pos(), 2)
                        + Math.pow(y_pos - listTarget.get(i).getY_pos(), 2));
                if(preRange >  preRange2 && preRange2 < 250)
                {
                    preRange = preRange2;
                    indexListEnemy = i;
                }
            }
            if(preRange < 250) TargetEnemy = listTarget.get(indexListEnemy);
            System.out.println(preRange);
        }
    }


    public void setTargetEnemy(sample.Enemy targetEnemy)
    {
        TargetEnemy = targetEnemy;
    }

    public sample.Enemy getTargetEnemy()
    {
        return TargetEnemy;
    }

    /* Tính toán góc bắn */
     public void setAngle()
    {
        /* Xác định vị trí mục tiêu */
        setDestination(TargetEnemy.getPosition());
        /* Tính khoảng cách từ tháp đến mục tiêu */
        double del = Math.sqrt(Math.pow((x_pos - Destination.getX()), 2) + Math.pow(y_pos - Destination.getY(), 2));
        /* Dùng hệ thức lượng tính giá trị sin cos để suy luận góc bắn */
        sinX = (Destination.y - y_pos == 0) ? 0 : (Destination.y - (double) y_pos)/del;
        cosX = (Destination.x - x_pos == 0) ? 0 : (Destination.x - (double) x_pos)/del;
        double angle2 = Math.abs(Math.asin(sinX) * 180 / Math.PI) ;
        /* Lý luận tương tự phần tìm đường đi của enemy */
        if(sinX >= 0 &&  cosX >= 0)
        {
            angle2 = 270 - angle2;
        }
        else if(sinX >= 0 && cosX <= 0)
        {
            angle2 = 90 + angle2;
        }
        else if(sinX <= 0 && cosX >= 0)
        {
            angle2 = 270 + angle2;
        }
        else if(sinX <= 0 && cosX <= 0)
        {
            angle2 = 90 - angle2;
        }
        double delta_angle = (angle - angle2 > 180) ? (angle - angle2 - 360) : angle - angle2;

        angle = angle2;
        this.imageView.setRotate(imageView.getRotate() + delta_angle);
        image = imageView.snapshot(snapshotParameters, null);
    }



    public void  setBullet(sample.Enemy enemy, int x_pos, int y_pos)
    {

    }
    public Bullet getBullet()
    {
        return this;
    }

    /* Constructor */
    public Bullet(int x_pos, int y_pos)
    {
        super(x_pos, y_pos);
    }

    
    @Override
    public void loadImage(String path)
    {

    }

   
    @Override
    public void ShowObject(GraphicsContext gc)
    {
    }

    public void move()
    {

        x_pos += ((double) speed) * cosX;
        y_pos += ((double) speed) * sinX;
        setAngle();
    }


    @Override
    public void Render(GraphicsContext gc) {
        if(!isshoot)
        {
            move();
            gc.drawImage(image, x_pos , y_pos);

        }
        isshoot = isShoot();
        if(isshoot) {
            TargetEnemy.bleed(dame);
        }
    }

    public void setDestination(Point destination) {
        this.Destination = destination;
    }

    public Point getDestination() {
        return Destination;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    

    public static int getDame() {
        return dame;
    }

    public void setSinX(double sinX) {
        this.sinX = sinX;
    }
    public void setCosX(double cosX) {
        this.cosX = cosX;
    }

}
