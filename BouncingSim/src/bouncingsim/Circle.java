
package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends StaticObject{
    double x;
    double y;
    double size;
    
    public Circle(double x, double y, double size,Color color){
        super(color);
        this.x = x;
        this.y = y;
        this.size = size;
    }
    
    public Circle(Point p1, Point p2, Color color){
        super(color);
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        double s = 2.0 * (Math.sqrt(Math.pow(x2 - x1,2) + Math.pow(y2 - y1,2)));
        this.x = x1;
        this.y = y1;
        this.size = s;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getSize(){
        return size;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public void setSize(double size){
        this.size = size;
    }
    
    @Override
    public boolean isOverlapping(BouncableObject otherB) {
        
        if (otherB instanceof Ball){
            Ball otherBall = (Ball) otherB;
            double xb = otherBall.getXPosition();
            double yb = otherBall.getYPosition();
            double sb = otherBall.getSize();
            if (Math.sqrt(Math.pow(x - xb,2) + Math.pow(y - yb,2)) <= ((size + sb) / 2)){
                return true;
            }
            return false;
            
            
        }
        return false;

    }
    
    @Override
    public void bounce(BouncableObject b) {
        if (b instanceof Ball){
            Ball ball = (Ball) b;
            BouncingMechanics.bounce(ball,this);
        }
    }
    @Override
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval((int)(x - (size / 2)),(int)(y - (size / 2)), (int) size, (int) size);
    }
    
    
    
}
