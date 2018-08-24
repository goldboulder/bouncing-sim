
package bouncingsim;

import java.awt.Color;


public class BouncingMechanics {
    
    private final static Color BROWN = new Color(150,75,37);
    private final static Color DARK_RED = new Color(128,0,0);
    private final  static Color ORANGE = new Color(255,100,0);
   
    public static void bounce(Ball ball1, Ball ball2){
        if (bounceCancel(ball1,ball2))
            return;
        if (bounceCancel(ball2,ball1))
            return;
        double v1 = ball1.speed();
        double v2 = ball2.speed();
        double o1 = ball1.direction();
        double o2 = ball2.direction();
        double m1 = ball1.mass();
        double m2 = ball2.mass();
        double p = Math.atan2(ball1.getYPosition()-ball2.getYPosition(),ball1.getXPosition()-ball2.getXPosition());
        
        double u1 = (v1*Math.cos(o1-p)*(m1-m2)+2*m2*v2*Math.cos(o2-p))/(m1+m2);
        double u2 = (v2*Math.cos(o2-p)*(m2-m1)+2*m1*v1*Math.cos(o1-p))/(m1+m2);
        
        double v1x = u1 * Math.cos(p) + v1*Math.sin(o1-p)*Math.cos(p+Math.PI/2);
        double v2x = u2 * Math.cos(p) + v2*Math.sin(o2-p)*Math.cos(p+Math.PI/2);
        double v1y = u1 * Math.sin(p) + v1*Math.sin(o1-p)*Math.sin(p+Math.PI/2);
        double v2y = u2 * Math.sin(p) + v2*Math.sin(o2-p)*Math.sin(p+Math.PI/2);
        
        ball1.setXVelocity(v1x);
        ball1.setYVelocity(v1y);
        ball2.setXVelocity(v2x);
        ball2.setYVelocity(v2y);
    }
    
    public static void bounce(Ball b, Point p){
        if (bounceCancel(b,p))
            return;
        double ballDirection = b.direction();
        double angle = Math.atan2(p.getY() - b.getYPosition(),p.getX() - b.getXPosition()) + Math.PI / 2;////// 
        double speed = b.speed();
        double collisionAngle = ballDirection - angle;
        double newAngle = ballDirection - 2 * collisionAngle;/////////
        b.setXVelocity(speed * Math.cos(newAngle));
        b.setYVelocity(speed * Math.sin(newAngle));
       
    }
    
    public static void bounce(Ball b, Line l){
        if (bounceCancel(b,l))
            return;
        double x1 = l.getPoint1().getX();
        double y1 = l.getPoint1().getY();
        double x2 = l.getPoint2().getX();
        double y2 = l.getPoint2().getY();
        double xb = b.getXPosition();
        double yb = b.getYPosition();
        double sb = b.getSize();
        int bouncingMethod = 0;
        
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
        
        if (xb >= 0 && xb <= x2 && Math.abs(sb / 2.0) > yb){
            bouncingMethod = 1;
        }
        // is in range of endpoint 1?
        if (sb / 2.0 > ballDistance)
            bouncingMethod = 2;
        // is in range of endpoint 2?
        xb -= x2;
        yb -= y2;
        ballDistance = Math.sqrt(Math.pow(xb, 2) + Math.pow(yb, 2));
        if (sb / 2.0 > ballDistance)
            bouncingMethod = 3;
        
        
        if (bouncingMethod == 1){
            
            double ballDirection = b.direction();
            angle = Math.atan2(l.getPoint1().getY()-l.getPoint2().getY(),l.getPoint1().getX()-l.getPoint2().getX());////// 
            double speed = b.speed();
            double collisionAngle = ballDirection - angle;
            double newAngle = ballDirection - 2 * collisionAngle;/////////
            b.setXVelocity(speed * Math.cos(newAngle));
            b.setYVelocity(speed * Math.sin(newAngle));
        }
        
        if (bouncingMethod == 2){
            bounce(b,l.getPoint1());
        }
        if (bouncingMethod == 3){
            bounce(b,l.getPoint2());
        }
        
       
    }
    
    public static void bounce(Ball b, Box o){
        if (bounceCancel(b,o))
            return;
        double x1 = o.getX1();
        double x2 = o.getX2();
        double y1 = o.getY1();
        double y2 = o.getY2();
        double xb = b.getXPosition();
        double yb = b.getYPosition();
        double sb = b.getSize();
        
        if(xb > (x1 - sb) && xb < (x2 + sb) && yb > y1 && yb < y2){
            b.setXVelocity(b.getXVelocity() * -1);
            return;
        }
        
        if(yb > (y1 - sb) && yb < (y2 + sb) && xb > x1 && xb < x2){
            b.setYVelocity(b.getYVelocity() * -1);
            return;
        }
        Point p1 = new Point(x1,y1,Color.BLACK);
        Point p2 = new Point(x1,y2,Color.BLACK);
        Point p3 = new Point(x2,y1,Color.BLACK);
        Point p4 = new Point(x2,y2,Color.BLACK);
        
        if(b.isOverlapping(p1)){
            bounce(b,p1);
            return;
        }
        if(b.isOverlapping(p2)){
            bounce(b,p2);
            return;
        }
        if(b.isOverlapping(p3)){
            bounce(b,p3);
            return;
        }
        if(b.isOverlapping(p4)){
            bounce(b,p4);
            return;
        }
       
    }
    
    public static void bounce(Ball b, Circle p){
        if (bounceCancel(b,p))
            return;
        double ballDirection = b.direction();
        double angle = Math.atan2(p.getY() - b.getYPosition(),p.getX() - b.getXPosition()) + Math.PI / 2;
        double speed = b.speed();
        double collisionAngle = ballDirection - angle;
        double newAngle = ballDirection - 2 * collisionAngle;
        b.setXVelocity(speed * Math.cos(newAngle));
        b.setYVelocity(speed * Math.sin(newAngle));
       
    }
    
    
    public static void bounce(Ball b, Arc a){
        if (bounceCancel(b,a))
            return;
            
        double x = a.getX();
        double y = a.getY();
        double s = a.getS();
        double startAngle = a.getStartAngle();
        double arcAngle = a.getArcAngle();
        double xb = b.getXPosition();
        double yb = b.getYPosition();
        double sb = b.getSize();
            
        //center arc at (0,0)
        xb -= x;
        yb -= y;
        x = 0;
        y = 0;
            
        //check if touching main line
        double an = Math.atan2(-yb,xb);
        double d = Math.sqrt(Math.pow(xb,2) + Math.pow(yb,2));
        
        // rotate so the arc starts on the +x axis
        an = ((an - startAngle) + (2 * Math.PI)) % (2 * Math.PI);
        if (an >= 0 && an <= arcAngle && d >= s/2 - sb/2 && d <= s/2 + sb/2){
            //bounce off arc
            bounce(b,new Circle(a.getX(),a.getY(),a.getS(),Color.BLACK));
            return;
        }
        //check if touching point
        Point p1 = new Point(a.getX() + a.getS()/2 * Math.cos(startAngle),a.getY() - a.getS()/2 * Math.sin(startAngle),Color.BLACK);
        Point p2 = new Point(a.getX() + a.getS()/2 * Math.cos(startAngle + arcAngle),a.getY() - a.getS()/2 * Math.sin(startAngle + arcAngle),Color.BLACK);
        //bounce off points
        if(p1.isOverlapping(b)){
            bounce(b,p1);
            return;
        }
        
        if(p2.isOverlapping(b)){
            bounce(b,p2);
            return;
        }
        

       
    }
    
    public static void bounce(Ball b, Ring r){
        if (bounceCancel(b,r))
            return;
        double ballDirection = b.direction();
        double angle = Math.atan2(r.getY() - b.getYPosition(),r.getX() - b.getXPosition()) + Math.PI / 2;
        double speed = b.speed();
        double collisionAngle = ballDirection - angle;
        double newAngle = ballDirection - 2 * collisionAngle;
        b.setXVelocity(speed * Math.cos(newAngle));
        b.setYVelocity(speed * Math.sin(newAngle));
       
    }
    
    public static void slowDown(Ball b){
        if (b.getXVelocity() < 0)
            b.setXVelocity(b.getXVelocity() * 0.99 + 0.0015);
        if (b.getXVelocity() > 0)
            b.setXVelocity(b.getXVelocity() * 0.99 - 0.0015);
        if (Math.abs(b.getXVelocity()) < 0.0025)
            b.setXVelocity(0);
        if (b.getYVelocity() < 0)
            b.setYVelocity(b.getYVelocity() * 0.99 + 0.0015);
        if (b.getYVelocity() > 0)
            b.setYVelocity(b.getYVelocity() * 0.99 - 0.0015);
        if (Math.abs(b.getYVelocity()) < 0.0025)
            b.setYVelocity(0);
    }
    
    public static void kill(Ball b){
        b.setKill(true);
    }
    
    public static boolean bounceCancel(Ball b, BouncableObject o){
        if ((o.getColor().equals(Color.RED) || o.getColor().equals(DARK_RED)) && !b.getColor().equals(ORANGE)){
            kill(b);
            return true;
        }
        if(o.getColor().equals(BROWN)){
            return true;
        }
        return false;
        
    }
    
    
    
    
}
