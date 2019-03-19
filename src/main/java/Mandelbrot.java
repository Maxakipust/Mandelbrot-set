import javax.imageio.ImageIO;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;

public class Mandelbrot {
    static int MAX_ITER = 255;
    public int mandlebrot(ComplexNumber c){
        ComplexNumber z = new ComplexNumber(0,0);
        int n = 0;
        while(z.magnitude() <= 2 && n<MAX_ITER){
            z = z.square().add(c);
            n+=1;
        }
        return n;
    }

    public BufferedImage drawImage(ComplexNumber start, ComplexNumber end, int xPix, int yPix){
        BufferedImage img = new BufferedImage(xPix, yPix,BufferedImage.TYPE_INT_RGB);
        double width = end.getReal() - start.getReal();
        double height = end.getImaginary() - start.getImaginary();
        double deltaReal = width/xPix;
        double deltaImaginary = height/yPix;
        int x = 0;
        int y = yPix;
        for(double real = start.getReal(); real<end.getReal(); real+=deltaReal){
            for(double imaginary = end.getImaginary(); imaginary<start.getImaginary(); imaginary-=deltaImaginary){
                ComplexNumber c = new ComplexNumber(real, imaginary);
                int m = mandlebrot(c);
                Color color;
                if(m!=255) {
                    float colorNum = (float) m / MAX_ITER;
                    color = Color.getHSBColor(colorNum, 1, 1);
                }else{
                    color = Color.BLACK;
                }
                try {
                    img.setRGB(x, y, color.getRGB());
                }catch (ArrayIndexOutOfBoundsException ex){
                    //System.err.println("Coordinate out of bounds ("+x+","+y+")");
                }
                y--;
            }
            y=yPix;
            x++;
        }
        return img;
    }

    public BufferedImage drawImage(double x, double y, double r, int pixels){
        return drawImage(new ComplexNumber(x-r,y+r), new ComplexNumber(x+r,y-r),pixels,pixels);
    }

    public static void main(String[] args) {
        Mandelbrot m = new Mandelbrot();
        double x = -0.7463;
        double y = 0.1102;
        double r = 0.005;
        BufferedImage img = m.drawImage(x,y,r,5000);
        try {
            File output = new File("output.png");
            ImageIO.write(img, "png", output);
            System.out.println("file saved");
        }catch (Exception ex){
            System.err.println("error writing file");
        }
    }
}
