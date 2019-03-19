import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class GUI extends Application {
    private static final int SIZE = 500;

    private BufferedImage bufferedImage;

    private ImageView imageView;
    private Mandelbrot m;

    private Integer firstX;
    private Integer firstY;

    private ComplexNumber start;
    private ComplexNumber end;

    private double r = 2;

    //Creating the mouse event handler
    private EventHandler<MouseEvent> twoClickEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            System.out.println("click");
            if(firstX==null||firstY==null){
                System.out.printf("first click at");
                firstX = (int)e.getX();
                firstY = (int)e.getY();
                System.out.println("("+firstX+","+firstY+")");
            }else{
                System.out.println("second click at");
                Integer endX = (int)e.getX();
                Integer endY = (int)e.getY();
                System.out.println("("+endX+","+endY+")");

                double newStartX = map(firstX,0,SIZE,start.getReal(),end.getReal());
                double newStartY = map(firstY, 0,SIZE, start.getImaginary(), end.getImaginary());
                double newEndX = map(endX,0,SIZE, start.getReal(),end.getReal());
                double newEndY = map(endY, 0,SIZE, start.getImaginary(),end.getImaginary());
                start = new ComplexNumber(newStartX,newStartY);
                end = new ComplexNumber(newEndX, newEndY);
                bufferedImage = m.drawImage(start,end,SIZE,SIZE);
                WritableImage img = SwingFXUtils.toFXImage(bufferedImage,null);
                imageView.setImage(img);
                firstX = null;
                firstY = null;
            }
        }
    };

    private EventHandler<MouseEvent> oneClickEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            double x = map(e.getX(), 0, SIZE, start.getReal(), end.getReal());
            double y = map(e.getY(), 0, SIZE, start.getImaginary(), end.getImaginary());
            r = r/2;
            start = new ComplexNumber(x-r,y+r);
            end = new ComplexNumber(x+r,y-r);
            bufferedImage = m.drawImage(x,y,r,SIZE);
            imageView.setImage(SwingFXUtils.toFXImage(bufferedImage,null));
        }
    };


    @Override
    public void start(Stage primaryStage) throws Exception {

        m = new Mandelbrot();
        start = new ComplexNumber(-2,2);
        end = new ComplexNumber(2,-2);
        bufferedImage = m.drawImage(start,end,SIZE,SIZE);
        WritableImage img = SwingFXUtils.toFXImage(bufferedImage,null);
        imageView = new ImageView(img);
        imageView.setX(0);
        imageView.setY(0);
        imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, oneClickEventHandler);

        //creating a Group object
        Group group = new Group(imageView);

        //Creating a Scene by passing the group object, height and width
        Scene scene = new Scene(group ,SIZE, SIZE);
        scene.setOnKeyPressed((EventHandler<KeyEvent>) e -> {
            if(e.getCode() == KeyCode.SPACE) {
                System.out.println("reset");
                start = new ComplexNumber(-2, 2);
                end = new ComplexNumber(2, -2);
                r = 2;
                firstX = null;
                firstY = null;
                bufferedImage = m.drawImage(start, end, SIZE, SIZE);
                imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            }else if(e.getCode() == KeyCode.S){
                System.out.println("save");
                try {
                    File output = new File("output.png");
                    ImageIO.write(bufferedImage, "png", output);
                }catch (Exception ex){
                    System.err.println("error writing file");
                }
            }
        });


        //Setting the title to Stage.
        primaryStage.setTitle("Sample Application");

        //Adding the scene to Stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
