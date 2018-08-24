
package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;


public class Box extends StaticObject{
    
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    
    public Box(double x1, double y1, double x2, double y2, Color color){
        super(color);
        if (x1 < x2){
            this.x1 = x1;
            this.x2 = x2;
        }
        else{
            this.x1 = x2;
            this.x2 = x1;
        }
        if (y1 < y2){
            this.y1 = y1;
            this.y2 = y2;
        }
        else{
            this.y1 = y2;
            this.y2 = y1;
        }
        
    }
    public Box(Point p1, Point p2,Color color){
        super(color);
        double x1Temp = p1.getX();
        double x2Temp = p2.getX();
        double y1Temp = p1.getY();
        double y2Temp = p2.getY();
        if (x1Temp < x2Temp){
            this.x1 = x1Temp;
            this.x2 = x2Temp;
        }
        else{
            this.x1 = x2Temp;
            this.x2 = x1Temp;
        }
        if (y1Temp < y2Temp){
            this.y1 = y1Temp;
            this.y2 = y2Temp;
        }
        else{
            this.y1 = y2Temp;
            this.y2 = y1Temp;
        }
        
    }
    
    public double getX1(){
        return x1;
    }
    public double getY1(){
        return y1;
    }
    
    public double getX2(){
        return x2;
    }
    public double getY2(){
        return y2;
    }
    
    public void setX1(double x){
        this.x1 = x;
    }
    public void setY1(double y){
        this.y1 = y;
    }
    
    public void setX2(double x){
        this.x2 = x;
    }
    public void setY2(double y){
        this.y2 = y;
    }

    @Override
    public boolean isOverlapping(BouncableObject otherB) {
        
        if (otherB instanceof Ball){
            Ball otherBall = (Ball) otherB;
            double xb = otherBall.getXPosition();
            double yb = otherBall.getYPosition();
            double sb = otherBall.getSize();
            Line l1 = new Line(x1,y1,x1,y2,Color.BLACK);
            Line l2 = new Line(x1,y1,x2,y1,Color.BLACK);
            Line l3 = new Line(x1,y2,x2,y2,Color.BLACK);
            Line l4 = new Line(x2,y1,x2,y2,Color.BLACK);
            
            if (otherBall.isOverlapping(l1) || otherBall.isOverlapping(l2) || otherBall.isOverlapping(l3) || otherBall.isOverlapping(l4)){
                return true;
            }
            
            
            
            if(xb > x1 && xb < x2 && yb > y1 && yb < y2){
                return true;
            }
            
            Point p1 = new Point(x1,y1,Color.BLACK);
            Point p2 = new Point(x1,y2,Color.BLACK);
            Point p3 = new Point(x2,y1,Color.BLACK);
            Point p4 = new Point(x2,y2,Color.BLACK);
            
            if (otherBall.isOverlapping(p1) || otherBall.isOverlapping(p2) || otherBall.isOverlapping(p3) || otherBall.isOverlapping(p4)){
                return true;
            }
            
            
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
        g.fillRect((int)x1,(int)y1,(int)(x2-x1),(int)(y2-y1));
    }

    
    
}
