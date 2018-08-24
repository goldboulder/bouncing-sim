
package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;


public class Line extends StaticObject{
    
    private Point p1;
    private Point p2;
    
    public Line(Point p1, Point p2, Color color){
        super(color);
        this.p1 = p1;
        this.p2 = p2;
    }
    
    public Line(double x1, double y1, double x2, double y2, Color color){
        super(color);
        this.p1 = new Point(x1,y1,color);
        this.p2 = new Point(x2,y2,color);
    }
    
    public Point getPoint1(){
        return p1;
    }
    
    public Point getPoint2(){
        return p2;
    }
    
    public void setPoint1(Point p){
        this.p1 = p;
    }
    
    public void setPoint2(Point p){
        this.p2 = p;
    }
    
    public boolean isOverlapping(BouncableObject otherB){
        if (otherB instanceof Ball){
            Ball otherBall = (Ball) otherB;
            
            double x1 = p1.getX();
            double y1 = p1.getY();
            double x2 = p2.getX();
            double y2 = p2.getY();
            double xb = otherBall.getXPosition();
            double yb = otherBall.getYPosition();
            double sb = otherBall.getSize();
            
            //translate point 1 to (0,0)
            x2 -= x1;
            y2 -= y1;
            xb -= x1;
            yb -= y1;
            x1 = 0;
            y1 = 0;
            
            // rotate line so point 2 is on the +x axis
            double angle = Math.atan2(y2, x2);
            
            double ballAngle = Math.atan2(yb,xb);
            double ballDistance = Math.sqrt(Math.pow(xb, 2) + Math.pow(yb, 2));
            x2 = Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2));
            
            y2 = 0;
            xb = ballDistance * Math.cos(ballAngle - angle);
            yb = ballDistance * Math.sin(ballAngle - angle);
            
            
            if (xb > 0 && xb < x2 && Math.abs(yb) < sb / 2){
                return true;
                
            }
            // is in range of endpoint 1?
            if (sb / 2.0 > ballDistance){
                return true;
            }
            // is in range of endpoint 2?
            xb -= x2;
            yb -= y2;
            ballDistance = Math.sqrt(Math.pow(xb, 2) + Math.pow(yb, 2));
            
            if (sb / 2.0 > ballDistance){
                return true;
            }
                
        
        }
        return false;
    }
    
    public void bounce(BouncableObject b2){
        
        if (b2 instanceof Ball){
            Ball ball2 = (Ball) b2;
            BouncingMechanics.bounce(ball2,this);
        }
    }
    
    @Override
    public void paint(Graphics g){
        int xC1 = (int) p1.getX();
        int yC1 = (int) p1.getY();
        int xC2 = (int) p2.getX();
        int yC2 = (int) p2.getY();
        g.setColor(color);
        g.drawLine(xC1, yC1, xC2, yC2);
    }
    
}
