
package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;


public abstract class BouncableObject {
    
    Color color;
    
    public BouncableObject(Color color){
        this.color = color;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor (Color c){
        this.color = c;
    }
    
    public abstract void paint(Graphics g);
    
    public abstract boolean isOverlapping(BouncableObject b);
    
    public abstract void bounce(BouncableObject b);
    
    
    public void keyUpdate(boolean up, boolean left, boolean right, boolean down){
        
    }
    
}
