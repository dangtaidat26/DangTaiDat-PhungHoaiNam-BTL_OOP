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

public abstract class HBoxTower {
    private final static int SCREEN_TITLEMAP = 30;
    protected HBox Hbox_Tower = new HBox();
    protected  ImageView imageView_Hbox = new ImageView();
    protected Image image_Hbox;
    protected int x_pos;
    protected int y_pos;
    protected Tower tower;
    private final int BoxTower_WIDTH = 30;
    private final int BoxTower_HEIGHT =  60;
    protected boolean isPut;
    protected boolean isDrag;
    protected boolean canPut;
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
    void insertImage(){
        this.setupGestureSource();
        Hbox_Tower.getChildren().add(imageView_Hbox);
    }

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
    public void setupGestureSource(){// Xu ly phan click chuot

        imageView_Hbox.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                System.out.println("DRAG DETECTED");
                /* allow any transfer mode */
                Dragboard db = imageView_Hbox.startDragAndDrop(TransferMode.MOVE);

                /* put a image on dragboard */
                ClipboardContent content = new ClipboardContent();

                Image sourceImage = imageView_Hbox.getImage();
                content.putImage(sourceImage);
                db.setContent(content);
                event.consume();
            }
        });
        imageView_Hbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
//                System.out.println("MOUSE ENTERED");
                imageView_Hbox.setCursor(Cursor.HAND);
//                    System.out.println("e...: "+e.getSceneX());

            }
        });
    }
    public void setupGestureTarget(Scene scene, int[][] MapTitle){ // Xu li phan keo tha
        //ImageView target = new ImageView(i);
        scene.setOnDragOver(new EventHandler <DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println("DRAG OVER");
                Dragboard db = event.getDragboard();

                if(db.hasImage()){
                    event.acceptTransferModes(TransferMode.MOVE);
                    setPosition((int)event.getX() , (int) event.getY() );
                    int x_tiles = (int) event.getSceneX() / SCREEN_TITLEMAP;
                    int y_tiles = (int) event.getSceneY() / SCREEN_TITLEMAP;
                    if (MapTitle[y_tiles][x_tiles] == 1 && MapTitle[y_tiles+1][x_tiles] == 1)
                    {
                        canPut = true;
                    }
                    else canPut = false;
                    isDrag = true;
                }
                event.consume();

            }
        });
        scene.setOnDragDropped(new EventHandler <DragEvent>(){
            @Override
            public void handle(DragEvent event) {
//                System.out.println("DRAG DROPPED");
                Dragboard db = event.getDragboard();
                if(db.hasImage()){
                    //imageView_Hbox.setImage(db.getImage());
                    int x_tiles = (int) event.getSceneX() / SCREEN_TITLEMAP;
                    int y_tiles = (int) event.getSceneY() / SCREEN_TITLEMAP;

                    if (MapTitle[y_tiles][x_tiles] == 1 && MapTitle[y_tiles+1][x_tiles] == 1) {
//                        System.out.println("event : "+event.getSceneX() +", " + event.getSceneY());
                        MapTitle[y_tiles][x_tiles] = 0;
                        MapTitle[y_tiles+1][x_tiles] = 0;
                        tower.setX_pos((x_tiles) *SCREEN_TITLEMAP);
                        tower.setY_pos((y_tiles) * SCREEN_TITLEMAP);
                        isPut = true;
                        isDrag = false;
                        canPut = false;
                    }else {
                        isPut = false;
                        isDrag = false;
                    }
                }else{
                    event.setDropCompleted(false);
                }
                event.consume();
            }
        });
    }

    public Tower getTower() {
        return tower;
    }

    public boolean isPut() {
        return isPut;
    }

    public void setPut(boolean put) {
        isPut = put;
    }

    public HBox getHbox_Tower() {
        return Hbox_Tower;
    }

    public abstract void Render_Hbox(GraphicsContext gc);
}
