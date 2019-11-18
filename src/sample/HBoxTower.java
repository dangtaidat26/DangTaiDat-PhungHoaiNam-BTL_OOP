package sample;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import sample.Enemy;
import sample.Tower;

import sample.*;
import sample.Enemy;
import sample.Tower;
import sample.Tower.*;
import  sample.NormalEnemy;
import sample.SniperTower;


/* Hộp chọn tháp */
public abstract class HBoxTower
{
    private final static int SCREEN_TITLEMAP = 30;  // Định dạng kích thước ô trong bản đồ 
    protected HBox Hbox_Tower = new HBox();     
    protected  ImageView imageView_Hbox = new ImageView();
    protected Image image_Hbox;
    protected int x_pos;
    protected int y_pos;
    protected Tower tower;
    private final int BoxTower_WIDTH = 30;          // tháp rộng 1 ô
    private final int BoxTower_HEIGHT =  60;        // cao 2 ô
    protected boolean isPut;                        // kiểm tra 1 vị trí trên bản đồ đã có tháp đặt chưa, nếu đã có thì thao tác đặt mới bị hủy bỏ
    protected boolean isDrag;                       // Biến logic kiểm tra việc ô hiển thị tháp có được kéo ko (việc đặt tháp là kéo và thả tại vị trí mong muốn)
    protected boolean canPut;                       // kiểm tra tính hợp lệ của việc đặt tháp. Tháp được phép đặt tại vị trí cỏ, ko được đặt ở gạch viền và đường đi
    

    /* Constructor */
    public HBoxTower()
    {
        Hbox_Tower.setPrefWidth(BoxTower_WIDTH);
        Hbox_Tower.setPrefHeight(BoxTower_HEIGHT);
        Hbox_Tower.setStyle("-fx-border-color: red;"
                + "-fx-border-width: 1;"
                + "-fx-border-style: solid;");
        insertImage();
        isPut = false;
        isDrag = false;
        canPut = false;
    }


    /* Tải ảnh */
    void insertImage()
    {
        this.setupGestureSource();
        Hbox_Tower.getChildren().add(imageView_Hbox);
    }

    /* Setter */
    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }
    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }
    public void setPosition(int x_pos, int y_pos)
    {
        this.setX_pos(x_pos);
        this.setY_pos(y_pos);
    }

    /* Xử lý sự kiện trên chuột */
    public void setupGestureSource()
    {

        imageView_Hbox.setOnDragDetected(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                /* Đặt ảnh tại vị trí bảng kéo thả */
                Dragboard db = imageView_Hbox.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                Image sourceImage = imageView_Hbox.getImage();
                content.putImage(sourceImage);
                db.setContent(content);
                event.consume();
            }
        });
        imageView_Hbox.setOnMouseEntered(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                imageView_Hbox.setCursor(Cursor.HAND);
            }
        });
    }


    /* Xử lý việc kéo thả */
    public void setupGestureTarget(Scene scene, int[][] MapTitle)
    { 
        scene.setOnDragOver(new EventHandler <DragEvent>()
        {
            @Override
            public void handle(DragEvent event)
            {
                /* Nếu xảy ra sự kiện kéo */
                Dragboard db = event.getDragboard();
                if(db.hasImage())
                {
                    event.acceptTransferModes(TransferMode.MOVE);
                    /* Xác định tọa độ được thả */
                    setPosition((int)event.getX() , (int) event.getY() );
                    /* chia tỉ lệ để lấy vị trí trên bản đồ game */
                    int x_tiles = (int) event.getSceneX() / SCREEN_TITLEMAP;
                    int y_tiles = (int) event.getSceneY() / SCREEN_TITLEMAP;
                    /* Nếu vị trí đặt là cỏ, ngay dưới nó cx là cỏ thì có thể đặt được */
                    if (MapTitle[y_tiles][x_tiles] == 1 && MapTitle[y_tiles+1][x_tiles] == 1)
                    {
                        canPut = true;
                    }
                    else
                    {
                        canPut = false;
                        isDrag = true;
                    }
                }
                event.consume();

            }
        });
        scene.setOnDragDropped(new EventHandler <DragEvent>()
        {
            @Override
            public void handle(DragEvent event)
            {
                Dragboard db = event.getDragboard();
                if(db.hasImage())
                {
                    int x_tiles = (int) event.getSceneX() / SCREEN_TITLEMAP;
                    int y_tiles = (int) event.getSceneY() / SCREEN_TITLEMAP;

                    if (MapTitle[y_tiles][x_tiles] == 1 && MapTitle[y_tiles+1][x_tiles] == 1)
                    {
                        /* nếu hợp lệ, dựng cờ đánh dấu vị trí đặt tháp lên */
                        MapTitle[y_tiles][x_tiles] = 0;
                        MapTitle[y_tiles+1][x_tiles] = 0;
                        tower.setX_pos((x_tiles) * SCREEN_TITLEMAP);
                        tower.setY_pos((y_tiles) * SCREEN_TITLEMAP);
                        isPut = true;
                        isDrag = false;
                        canPut = false;
                    }
                    else
                    {
                        /* không thỏa điều kiện đặt tháp, không cho phép đặt */

                        isPut = false;
                        isDrag = false;
                    }
                }
                else
                {
                    event.setDropCompleted(false);
                }
                event.consume();
            }
        });
    }

    public Tower getTower()
    {
        return tower;
    }

    public boolean isPut()
    {
        return isPut;
    }

    public void setPut(boolean put)
    {
        isPut = put;
    }

    public HBox getHbox_Tower()
    {
        return Hbox_Tower;
    }

    public abstract void Render_Hbox(GraphicsContext gc);
}
