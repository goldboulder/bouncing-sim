package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;


public abstract class Ball extends MovableObject{
    
    
    private double size;
    
    public Ball(double xPosition, double yPosition, double xVelocity, double yVelocity, double rotation, double rotateSpeed, double size, double density, Color color, double dragMultiplier, double dragSubtracter){
        
        super(xPosition,yPosition,color,xVelocity,yVelocity,density,rotation,rotateSpeed,dragMultiplier,dragSubtracter);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.size = size;
        
        
    }
    
    public double getSize(){
        return size;
    }
    
    public double area(){
        return Math.pow(size / 2,2) * Math.PI;
    }
    
    public void setSize(double s){
        this.size = s;
    }
    
    
    public boolean isOverlapping(BouncableObject otherB){
        if (otherB instanceof Ball){
            Ball otherBall = (Ball) otherB;
            if (Math.sqrt(Math.pow(xPosition - otherBall.getXPosition(),2) + Math.pow(yPosition - otherBall.getYPosition(),2)) <= ((size + otherBall.getSize()) / 2)){
                return true;
            }
            return false;
        }
        if (otherB instanceof Point){
            Point point = (Point) otherB;
            if (Math.sqrt(Math.pow(xPosition - point.getX(),2) + Math.pow(yPosition - point.getY(),2)) <= ((size) / 2)){
                return true;
            }
            return false;
        }
        if (otherB instanceof Line){
            Line line = (Line) otherB;
            return line.isOverlapping(this);
        }
        if (otherB instanceof Box){
            Box box = (Box) otherB;
            return box.isOverlapping(this);
        }
        if (otherB instanceof Circle){
            Circle circle = (Circle) otherB;
            return circle.isOverlapping(this);
        }
        
        if (otherB instanceof Arc){
            Arc arc = (Arc) otherB;
            return arc.isOverlapping(this);
        }
        
        if (otherB instanceof Ring){
            Ring ring = (Ring) otherB;
            return ring.isOverlapping(this);
        }
        return false;
    }
    
    @Override
    public void bounce(BouncableObject b2){
        if (b2 instanceof Ball){
            
            Ball ball2 = (Ball) b2;
            
            BouncingMechanics.bounce(this, ball2);
        }
        
        if (b2 instanceof Point){
            Point p = (Point) b2;
            
            BouncingMechanics.bounce(this, p);
            
        }
        
        if (b2 instanceof Line){
            Line line = (Line) b2;
            BouncingMechanics.bounce(this, line);
        }
        
        if (b2 instanceof Box){
            Box box = (Box) b2;
            BouncingMechanics.bounce(this, box);
        }
        
        if (b2 instanceof Circle){
            Circle circle = (Circle) b2;
            BouncingMechanics.bounce(this, circle);
        }
        
        if (b2 instanceof Arc){
            Arc arc = (Arc) b2;
            BouncingMechanics.bounce(this, arc);
        }
        
        if (b2 instanceof Ring){
            Ring ring = (Ring) b2;
            BouncingMechanics.bounce(this, ring);
        }
        
        
    }

    

    
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval((int)(xPosition - (size / 2)),(int)(yPosition - (size / 2)), (int) size, (int) size);
    }
    

    
    

    
}
