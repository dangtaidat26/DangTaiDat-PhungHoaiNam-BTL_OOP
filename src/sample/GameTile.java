package sample;

public abstract class GameTile extends GameEntity {
    protected int x_pos;
    protected int y_pos;
    public GameTile(int x_pos, int y_pos){
        super();
        setPosition(x_pos, y_pos);
    }

    public GameTile() {

    }

    @Override
    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    @Override
    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    @Override
    public int getY_pos() {
        return y_pos;
    }

    @Override
    public void setPosition(int x_pos, int y_pos) {
        super.setPosition(x_pos, y_pos);
    }
}
