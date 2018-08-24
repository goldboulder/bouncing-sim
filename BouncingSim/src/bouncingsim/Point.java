
package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;


public class Point extends StaticObject{
    
    private double x;
    private double y;
    
    public Point(double x, double y,Color color){
        super(color);
        this.x = x;
        this.y = y;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    @Override
    public boolean isOverlapping(BouncableObject otherB){
        if (otherB instanceof Ball){
            Ball otherBall = (Ball) otherB;
            if (Math.sqrt(Math.pow(x - otherBall.getXPosition(),2) + Math.pow(y - otherBall.getYPosition(),2)) <= ((otherBall.getSize()) / 2)){
                return true;
            }
                return false;
        }
        
        
        return false;
    }
    
    @Override
    public void bounce(BouncableObject b2){
        
        if (b2 instanceof Ball){
            Ball ball2 = (Ball) b2;
            BouncingMechanics.bounce(ball2,this);
        }
    }
    
    @Override
    public void paint(Graphics g){
        int xC = (int) x;
        int yC = (int) y;
        g.setColor(color);
        g.fillOval(xC - 2, yC - 2, 4, 4);
    }
    
}
