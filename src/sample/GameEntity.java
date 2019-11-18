package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameEntity {
    protected Image image;
    protected int x_pos;
    protected int y_pos;
    protected double x_velocity;
    protected double y_velocity;
    protected double width;
    protected double height;

    public GameEntity() {
        this.x_pos = 0;
        this.y_pos = 0;
        this.x_velocity = 0;
        this.y_velocity = 0;
    }

    abstract public void loadImage(String path);
    abstract public void ShowObject(GraphicsContext gc);
    abstract public void Render(GraphicsContext gc);

    public int getX_pos() {
        return x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public double getX_velocity() {
        return x_velocity;
    }

    public void setX_velocity(double x_velocity) {
        this.x_velocity = x_velocity;
    }

    public double getY_velocity() {
        return y_velocity;
    }

    public void setY_velocity(double y_velocity) {
        this.y_velocity = y_velocity;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setPosition(int x_pos, int y_pos)
    {
        setX_pos(x_pos);
        setY_pos(y_pos);
    }
    public void setVelocity(double x, double y) {
        setX_velocity(x);
        setY_velocity(y);
    }
    public void addVelocity(double x, double y)
    {
        x_velocity += x;
        y_velocity += y;
    }
    public void updatePosition(double time)
    {
        x_pos += x_velocity * time;
        y_pos += y_velocity * time;
    }

    /*public void setImage(Image image) {
        this.image = image;
    }*/
    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    /*vẽ hình ảnh liên kết vào khung vẽ (thông qua lớp
	GraphicsContext) bằng cách sử dụng vị trí làm tọa độ*/
    public void render(GraphicsContext gc){
        gc.drawImage(image, x_pos, y_pos);
    }

    /*trả về một đối tượng JavaFX Rectangle2D để biết vùng vị trí của đối tượng, hữu ích trong
	việc phát hiện va chạm do phương thức giao nhau của nó*/
    public Rectangle2D getBoundary(){
        return new Rectangle2D(x_pos, y_pos, width, height);
    }

    /*xác định xem các đối tượng  có giao nhau không*/
    public boolean intersects(GameEntity other) {
        return this.getBoundary().intersects(other.getBoundary());
    }

    public String toString()
    {
        return " Position: [" + x_pos + "," + y_pos + "]"
                + " Velocity: [" + x_velocity + "," + y_velocity + "]";
    }
}
