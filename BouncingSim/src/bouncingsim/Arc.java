
package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;


public class Arc extends StaticObject{
    private double x;
    private double y;
    private double s;
    private double startAngle;
    private double arcAngle;
    
    public Arc(double x, double y, double s, double startAngle, double arcAngle,Color color){
        super(color);
        this.x = x;
        this.y = y;
        this.s = s;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
        if (this.arcAngle < 0){
            this.arcAngle *= -1;
            this.startAngle -= arcAngle;
        }
    }
    
    public Arc(Point p1, Point p2, Point p3, Color color){
        super(color);
        double x1 = p1.getX();
        double x2 = p2.getX();
        double x3 = p3.getX();
        double y1 = p1.getY();
        double y2 = p2.getY();
        double y3 = p3.getY();
        
        double x4 = (x1 + x3) / 2;
        double y4 = (y1 + y3) / 2;
        double x5 = (x2 + x3) / 2;
        double y5 = (y2 + y3) / 2;
        
        double s4 = -1 / ((y1 - y3)/ (x1 - x3));
        double s5 = -1 / ((y2 - y3)/ (x2 - x3));
        
        if (s4 > 1e10)
            s4 = 1e10;
        if (s5 > 1e10)
            s5 = 1e10;
        
        double b4 = y4 - s4 * x4;
        double b5 = y5 - s5 * x5;
        
        s4 *= -1;
        b5 *= -1;
        
        this.x = (b4 + b5) / (s4 + s5);
        this.y = this.x * -1 *s4 + b4;
        this.s = 2.0 * Math.sqrt(Math.pow(this.x - x1,2) + Math.pow(this.y - y1,2));
        
        double a1 = (Math.atan2(this.y - y1, x1 - this.x)+ 2 * Math.PI) % (2 * Math.PI);
        double a2 = (Math.atan2(this.y - y2, x2 - this.x)+ 2 * Math.PI) % (2 * Math.PI);
        double a3 = (Math.atan2(this.y - y3, x3 - this.x)+ 2 * Math.PI) % (2 * Math.PI);
        
        this.startAngle = a1;
        
        double aa1 = a1;
        double aa2 = a2;
        double aa3 = a3;
        
        aa2 -= aa1;
        aa3 -= aa1;
        aa1 = 0;
        
        aa2 = (aa2 + 2 * Math.PI) % (2 * Math.PI);
        aa3 = (aa3 + 2 * Math.PI) % (2 * Math.PI);
        
        if (aa3 > aa1 && aa3 < aa2){
            this.arcAngle = (aa2 + 2 * Math.PI) % (2 * Math.PI);//????????
        }
        else{
            this.arcAngle = (aa2 - 2 * Math.PI) % (2 * Math.PI);
        }
        
        if (this.arcAngle < 0){
            arcAngle *= -1;
            startAngle -= arcAngle;
        }
        
    }
    
    
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getS(){
        return s;
    }
    
    public double getStartAngle(){
        return startAngle;
    }
    
    public double getArcAngle(){
        return arcAngle;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public void setSize(double s){
        this.s = s;
    }
    
    public void setStartAngle(double startAngle){
        this.startAngle = startAngle % (2 * Math.PI);
    }
    
    public void setArcAngle(double arcAngle){
        this.arcAngle = arcAngle;
        if (arcAngle < 0)
            this.arcAngle = 0;
        if (arcAngle > (2 * Math.PI))
            this.arcAngle = 2 * Math.PI;
    }
    
    @Override
    public boolean isOverlapping(BouncableObject otherB) {
        
        if (otherB instanceof Ball){
            Ball otherBall = (Ball) otherB;
            
            double x = this.x;
            double y = this.y;
            double s = this.s;
            double startAngle = this.startAngle;
            double arcAngle = this.arcAngle;
            double xb = (otherBall).getXPosition();
            double yb = otherBall.getYPosition();
            double sb = otherBall.getSize();
            
            //center arc at (0,0)
            xb -= x;
            yb -= y;
            x = 0;
            y = 0;
            
            //check if touching main line
            double a = (Math.atan2(-yb,xb) + Math.PI * 2) % (Math.PI * 2);
            double d = Math.sqrt(Math.pow(xb,2) + Math.pow(yb,2));

            // rotate so the arc starts on the +x axis
            a = ((a - startAngle) + (2 * Math.PI)) % (2 * Math.PI);
            
            if (a <= arcAngle && d >= s/2 - sb/2 && d <= s/2 + sb/2){
            
                return true;
                
            }
            //check if touching point
            Point p1 = new Point(this.x + this.s/2 * Math.cos(this.startAngle),this.y - this.s/2 * Math.sin(this.startAngle),Color.BLACK);
            Point p2 = new Point(this.x + this.s/2 * Math.cos(this.startAngle + arcAngle),this.y - this.s/2 * Math.sin(this.startAngle + arcAngle),Color.BLACK);
            if(p1.isOverlapping(otherBall) || p2.isOverlapping(otherBall))
                return true;
            
        }
        return false;

    }
    
    //returns the angle of a point relitive to the arc's center
    public double angleOfPoint(double xp, double yp){
        return Math.atan2(yp - y,x - xp);
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
        g.drawArc((int)x - (int)(s/2),(int)y - (int)(s/2), (int)s, (int)s, (int)(startAngle * 180 / Math.PI), (int)(arcAngle * 180 / Math.PI));
    }
    
    
    
}
