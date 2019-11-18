package sample;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Tower  {   
    final static String Bullet_Img = "file:src/Image/Bullet.png";
    private final Image image_Bullt = new Image(Bullet_Img, 20, 20, true, true);

    protected int dame; // sat thuong
    protected int range; // tam ban
    protected double x_pos;
    protected double y_pos;
    protected int cost;
    protected String towerImagePath;
    protected Image image;
    protected List<Bullet> bulletList;
    private boolean isFoundEnemy;
    private sample.Enemy targetEnemy;
    private int indexEnemy;
    private int i ;
    private int sizeBulet;

    public Tower(double x_pos, double y_pos) {
        super();
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        i = 0;
        bulletList = new ArrayList<>();
        isFoundEnemy = false;
        sizeBulet = 0;
    }

    public Tower(String towerImagePath) {
        super();
    }

    public Tower() {
        super();
    }

    public void loadImage(String path) {
        this.image = new Image(towerImagePath + ".png", 30, 60,
                true, true);
    }

    public void Shoot(List<sample.Enemy> enemyList)
    {
        if(targetEnemy == null || !utilInRange()){
            isFoundEnemy = false;
        }
        if(isFoundEnemy)
        {
            if(i == 0)
            {
                bulletList.add(new Bullet(targetEnemy, (int)x_pos,(int) y_pos,indexEnemy, image_Bullt ));
                sizeBulet ++;
                isFoundEnemy = false;
            }
            i = (i > 15 ) ? 0 : i + 1;
        }
        else {
//            System.out.println("chua tim thay target");
            setTargetEnemy(enemyList); // tim target
        }
    }

    public void setTargetEnemy(List <sample.Enemy> enemyList) {
        if(enemyList.isEmpty()) {
//            System.out.println("EnemyList Empty");
            isFoundEnemy = false;
            return;
        }
        int preRange = range;
        isFoundEnemy = false;
        for(int i = 0;i < enemyList.size() ; i++)
        {
            int preRange2 = (int) Math.sqrt(Math.pow(x_pos - enemyList.get(i).getX_pos(), 2)
                    + Math.pow(y_pos - enemyList.get(i).getY_pos(), 2));
            if(preRange2 < range && !enemyList.get(i).is_dead() && preRange2 < preRange)
            {
                targetEnemy = enemyList.get(i);
                indexEnemy = i;
                isFoundEnemy = true;
                preRange = preRange2;
            }
        }
    }
    public void RenderBullet(GraphicsContext gc,List<sample.Enemy> enemyList)
    {
        if(bulletList == null) return;
        for(int i = 0 ; i < sizeBulet; i ++)
        {
            if(bulletList.get(i).getTargetEnemy().is_dead())
            {
                isFoundEnemy = false;
                enemyList.remove(bulletList.get(i).getTargetEnemy());
                bulletList.get(i).setTargetEnemy(enemyList);
            }
            bulletList.get(i).Render(gc);
            if(bulletList.get(i).isShoot())
            {
                bulletList.remove(i);
                sizeBulet --;
                if(bulletList == null) return;
                continue;
            }
        }
    }
    public void Render(GraphicsContext gc, List<sample.Enemy> enemyList) {
        gc.drawImage(image, x_pos, y_pos);
        Shoot(enemyList);
        RenderBullet(gc, enemyList);
    }

    public double getY_pos() {
        return y_pos;
    }

    public double getX_pos() {
        return x_pos;
    }

    public void setY_pos(double y_pos) {
        this.y_pos = y_pos;
    }

    public void setX_pos(double x_pos) {
        this.x_pos = x_pos;
    }

    public int getRange() {
        return range;
    }
    public boolean utilInRange() {
        return (range > (int) Math.sqrt(Math.pow(this.x_pos - targetEnemy.getX_pos(), 2)
                + Math.pow(this.y_pos - targetEnemy.getY_pos(), 2))|| targetEnemy.getX_pos() > 1200);
    }
}
