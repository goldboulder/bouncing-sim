package bouncingsim;

import java.awt.Color;


public class NormalBall extends Ball{

    
    public NormalBall(double xPosition, double yPosition, double xVelocity, double yVelocity, double rotation, double rotateSpeed, double size, double density, Color color, double dragMultiplier, double dragSubtracter){
        super(xPosition,yPosition,xVelocity,yVelocity,rotation,rotateSpeed,size,density,color,dragMultiplier,dragSubtracter); 
    }
    
    @Override
    public boolean wallBounce(double xSize, double ySize){
        if ((int)(getXPosition() - (getSize() / 2)) <= 0){
            setXVelocity(Math.abs(getXVelocity()));
            return true;
        }
        if ((int)(getXPosition() + (getSize() / 2)) >= xSize){
            setXVelocity(Math.abs(getXVelocity()) * -1);
            return true;
        }
        if ((int)(getYPosition() - (getSize() / 2)) <= 0){
            setYVelocity(Math.abs(getYVelocity()));
            return true;
        }
        if ((int)(getYPosition() + (getSize() / 2)) >= ySize){
            setYVelocity(Math.abs(getYVelocity()) * -1);
            return true;
        }
        return false;
    }
    
    public void dragUpdate(){
        
        double xVelocity = getXVelocity();
        double yVelocity = getYVelocity();
        double dragMultiplier = getDragMultiplier();
        double dragSubtracter = getDragSubtracter();
                      
        xVelocity *= dragMultiplier;
        yVelocity *= dragMultiplier;
        
        if(xVelocity < dragSubtracter * -1){
            xVelocity += dragSubtracter;
        }
        else if (xVelocity > dragSubtracter){
            xVelocity -= dragSubtracter;
        }
        else{
            xVelocity = 0;
        }
        
        if(yVelocity < dragSubtracter * -1){
            yVelocity += dragSubtracter;
        }
        else if (yVelocity > dragSubtracter){
            yVelocity -= dragSubtracter;
        }
        else{
            yVelocity = 0;
        }

        setXVelocity(xVelocity);
        setYVelocity(yVelocity);
        setXPosition(getXPosition() + xVelocity);
        setYPosition(getYPosition() + yVelocity);
        
    }
    
}

