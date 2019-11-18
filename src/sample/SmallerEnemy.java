package sample;
import sample.Enemy;
import javafx.scene.canvas.GraphicsContext;

public class SmallerEnemy extends Enemy {
    final static int speed = 3;
    final static int blood_first = 100;
    final static String SmallEnemy_Image = "/nameimg";


    public SmallerEnemy()
    {
        super();
        setFirst_Blood(blood_first);
        loadImage(SmallEnemy_Image);
        //setPosition(); //set position for object
        //setFirstBlood for object
    }

    @Override
    public void Move() {
        /********* To do for Object move*********/
        /***Left***/
        /***Right***/

        /***Up***/
        /***down***/

    }

    @Override
    public void RenderList(GraphicsContext mainGraphic) {

    }

    @Override
    public Point getPosition() {
        return null;
    }

    @Override
    public void ShowObject(GraphicsContext gc) {

    }

    @Override
    public void Render(GraphicsContext gc) {
        Move();
        gc.drawImage(image, x_pos, y_pos);
    }
}
