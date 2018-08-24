
package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;


public abstract class StaticObject extends BouncableObject{
    
    public StaticObject(Color color){
        super(color);
    }
    
    @Override
    public abstract boolean isOverlapping(BouncableObject b);
    
    @Override
    public abstract void bounce(BouncableObject b);
    
    @Override
    public void paint(Graphics g){
        
    }
    
}
