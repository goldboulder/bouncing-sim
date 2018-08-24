
package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;


public abstract class MovableObject extends BouncableObject{
    
    double xPosition;
    double yPosition;
    private double xVelocity;
    private double yVelocity;
    private double density;
    private double dragMultiplier;
    private double dragSubtracter;
    private double rotation;
    private double rotateSpeed;
    private boolean kill;
    
    public MovableObject(double xPosition, double yPosition,Color c, double xVelocity, double yVelocity, double density, double rotation, double rotateSpeed, double dragMultiplier, double dragSubtracter){
        super(c);
        this.kill = false;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.density = density;
        this.rotation = rotation;
        this.rotateSpeed = rotateSpeed;
        if (dragMultiplier < 0){
            this.dragMultiplier = 0;
        }
        else if (dragMultiplier > 1){
            this.dragMultiplier = 1;
        }
        else{
            this.dragMultiplier = dragMultiplier;
        }
        
        if (dragSubtracter < 0){
            this.dragSubtracter = 0;
        }
        else{
            this.dragSubtracter = dragSubtracter;
        }
    }
    
    public boolean getKill(){
        return kill;
    }
    
    public void setKill(boolean k){
        this.kill = k;
    }
    
    public double getXPosition(){
        return xPosition;
    }
    
    public double getYPosition(){
        return yPosition;
    }
    
    public double getXVelocity(){
        return xVelocity;
    }

    public double getYVelocity(){
        return yVelocity;
    }
    
    public double getDragMultiplier(){
        return dragMultiplier;
    }
    
    public double getDragSubtracter(){
        return dragSubtracter;
    }
    public void setXPosition(double x){
        this.xPosition = x;
    }
    
    public void setYPosition(double y){
        this.yPosition = y;
    }
    
    public void setXVelocity(double x){
        this.xVelocity = x;
    }
    
    public void setYVelocity(double y){
        this.yVelocity = y;
    }
    
    public void setDragMultiplier(double d){
        if (d < 0){
            dragMultiplier = 0;
            return;
        }
        if (d > 1){
            dragMultiplier = 1;
            return;
        }
        dragMultiplier = d;
    }
    
    public void setDragSubtracter(double d){
        if (d < 0){
            dragSubtracter = 0;
            return;
        }
        dragSubtracter = d;
    }
    
    public double totalMomentum(){
        return mass() * speed();
    }
    
    public double xMomentum(){
        return mass() * xVelocity;
    }
    
    public double yMomentum(){
        return mass() * yVelocity;
    }
    
    public double speed(){
        return Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));
    }
    
    public double direction(){

        return Math.atan2(yVelocity, xVelocity);

    }
    
    public double keneticEnergy(){
        return Math.pow(speed(), 2) * mass() / 2.0;
    }
    
    public abstract double area();
    
    public double mass(){
        return density * area();
    }
    
    public double getDensity(){
        return density;
    }
    
    public void setDensity(double d){
        this.density = d;
    }

    @Override
    public abstract void paint(Graphics g);
    
    @Override
    public abstract boolean isOverlapping(BouncableObject b);
    
    @Override
    public abstract void bounce(BouncableObject b);
    
    public abstract boolean wallBounce(double xSize, double ySize);
    
    public abstract void dragUpdate();

    
    
    
}
