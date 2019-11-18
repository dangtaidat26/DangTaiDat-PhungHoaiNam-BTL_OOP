package sample;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Tower  {   
    final static String Bullet_Img = "file:src/Image/Bullet.png";
    private final Image image_Bullt = new Image(Bullet_Img, 20, 20, true, true); // cài đặt kích thước mặc định của đạn
    protected int dame;                     // sát thương
    protected int range;                    // tầm bắn
    protected double x_pos;                 // vị trí theo x trên bản đồ
    protected double y_pos;                 // vị trí theo y trên bản đồ
    protected int cost;                     // giá tháp
    protected String towerImagePath;        // link ảnh
    protected Image image;                  // ảnh
    protected List<Bullet> bulletList;      // các viên đạn được bắn ra của tháp, vì các viên đạn này ko nhất thiết cùng bắn 1 mục tiêu nên cần quản lý
    private boolean isFoundEnemy;           // biến logic xác định việc đã tìm thấy mục tiêu 
    private sample.Enemy targetEnemy;       // mục tiêu vào tầm bắn
    private int indexEnemy;                 // thứ tự mục tiêu trong dãy
    private int i;                          // biến dùng cho vòng lặp
    private int sizeBulet;                  // số lượng đạn đã bắn và được lưu trữ

    
    /* Constructor */
    public Tower(double x_pos, double y_pos)
    {
        super();
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        i = 0;
        bulletList = new ArrayList<>();
        isFoundEnemy = false;
        sizeBulet = 0;
    }



    public Tower(String towerImagePath)
    {
        super();
    }

    public Tower()
    {
        super();
    }

    /* Tải ảnh vào set kích thước */
    public void loadImage(String path)
    {
        this.image = new Image(towerImagePath + ".png", 30, 60, true, true);
    }


    /* Việc tạo sự kiện bắn mục tiêu */
    public void Shoot(List<sample.Enemy> enemyList)
    {
        /* Khi chưa có mục tiêu hoặc có mục tiêu nhưng chưa vào đúng tầm bắn thì đặt isFoundEnemy = false */
        if(targetEnemy == null || !utilInRange())
        {
            isFoundEnemy = false;
        }
        /* Nếu đã xác định mục tiêu, thì bắn ra đạn (tạo ra thêm đạn thuộc tháp đó quản lý với mục tiêu bắn là mục tiêu đã xác định) */
        if(isFoundEnemy)
        {
            if(i == 0)
            {
                /* Bắn đạn */
                bulletList.add(new Bullet(targetEnemy, (int)x_pos,(int) y_pos, indexEnemy, image_Bullt ));
                /* Tăng số lượng đạn đã bắn lưu lại thêm*/
                sizeBulet ++; 
                /* gán lại biến tìm kiếm = false để tái tạo quá trình tìm quái gần nhất với tháp */
                isFoundEnemy = false;
            }
            /* Nếu số đạn đã bắn của tháp > 15 thì reset lại về 0 để tránh chiếm dụng nhiều bộ nhớ quá mức, đồng thời giảm thời gian truy xuất
               do đó giảm hiện tượng giật khi chơi
            */
            i = (i > 15 ) ? 0 : i + 1;
        }
        else
        {
            setTargetEnemy(enemyList); // nếu chưa có mục tiêu thì tiếp tục dò tìm
        }
    }
    
    
    /* Kiểm tra chừng nào mục tiêu còn ở trong tầm bắn của tháp */
    public boolean utilInRange()
    {
        return (range > (int) Math.sqrt(Math.pow(this.x_pos - targetEnemy.getX_pos(), 2)
                + Math.pow(this.y_pos - targetEnemy.getY_pos(), 2))|| targetEnemy.getX_pos() > 1200);
    }


    /* Tìm mục tiêu */
    public void setTargetEnemy(List <sample.Enemy> enemyList)
    {
        /* Nếu quái chưa ra hoặc tạm thời bị diệt hết, thì ngừng thực hiện hàm*/
        if(enemyList.isEmpty())
        {
            isFoundEnemy = false;
            return;
        }

        int preRange = range;
        isFoundEnemy = false;
        for(int i = 0; i < enemyList.size() ; i++)
        {
            /* Tính khoảng cách từ tháp đến mục tiêu = giải tích trong mặt phẳng*/
            /* Khoảng cách 2 điểm = sqrt(deltaX ^ 2 + deltaY ^ 2) */
            int preRange2 = (int) Math.sqrt(Math.pow(x_pos - enemyList.get(i).getX_pos(), 2)
                    + Math.pow(y_pos - enemyList.get(i).getY_pos(), 2));
            /* Điều kiện thiết lập mục tiêu là khoảng cách từ tháp đến quái nằm trong phạm vi tầm bắn và nó chưa chết
               mặt khác, tại mỗi thời điểm một tháp chỉ được chọn 1 mục tiêu để bắn nên có thêm điều kiện cuối cùng
               tháp sẽ ưu tiên chọn quái gần nó nhất, nếu có cái mới xuất hiện gần nó hơn thì sẽ được chọn làm mục tiêu bắn
               quái trước đó sẽ được thả để các tháp phía sau (nếu có) bắn tiếp
            */ 
            if(preRange2 < range && !enemyList.get(i).is_dead() && preRange2 < preRange)
            {
                /* Chỉ định mục tiêu */ 
                targetEnemy = enemyList.get(i);
                indexEnemy = i;
                isFoundEnemy = true;
                /* gán khoảng cách quái trước đó với preRange để so sánh với quái có chỉ số tiếp theo */
                preRange = preRange2;
            }
        }
    }


    /* Hàm vẽ / hiển thị đạn trên màn hình chơi */
    public void RenderBullet(GraphicsContext gc,List<sample.Enemy> enemyList)
    {
        /* Nếu tháp chưa phát hiện quái thì thoát khỏi hàm*/
        if(bulletList == null) return;
        /* Nếu tháp đã bắt đầu bắn thì bắt đầu duyệt để xử lý từng viên đạn */
        for(int i = 0 ; i < sizeBulet; i++)
        {
            /* Nếu mục tiêu bị viên đạn nào bắn đã chết thì xóa mục tiêu đó khỏi danh sách,
               đồng thời set lại biến tìm kiếm về false để tháp tìm mục tiêu khác
            */
            if(bulletList.get(i).getTargetEnemy().is_dead())
            {
                isFoundEnemy = false;
                enemyList.remove(bulletList.get(i).getTargetEnemy());
                bulletList.get(i).setTargetEnemy(enemyList);
            }
            /* Nếu mục tiêu chưa chết, vẽ viên đạn đó ra, tạo hiệu ứng di chuyển */
            bulletList.get(i).Render(gc);
            if(bulletList.get(i).isShoot())
            {
                /* Khi đạn chạm vào mục tiêu, xóa hình ảnh và dữ liệu về nó, biến sizeBulet lưu số đạn đang bắn giảm 1 */
                bulletList.remove(i);
                sizeBulet--;
                /* Nếu các viên đạn đều đã bắn trúng mục tiêu thì thoát hàm, còn đạn thì duyệt tiếp viên đạn tiếp theo */
                if(bulletList == null) return;
                continue;
            }
        }
    }


    /* Vẽ và tạo hiệu ứng chuyển động của đạn, quái */
    public void Render(GraphicsContext gc, List<sample.Enemy> enemyList)
    {
        gc.drawImage(image, x_pos, y_pos);
        Shoot(enemyList);
        RenderBullet(gc, enemyList);
    }


    /* Setter và Getter */
    public double getY_pos()
    {
        return y_pos;
    }

    public double getX_pos()
    {
        return x_pos;
    }

    public void setY_pos(double y_pos)
    {
        this.y_pos = y_pos;
    }

    public void setX_pos(double x_pos)
    {
        this.x_pos = x_pos;
    }

    public int getRange()
    {
        return range;
    }
    
}
