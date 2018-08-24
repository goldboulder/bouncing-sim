package bouncingsim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;


public class FieldPanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
    
    private BouncingSim frame;
    private JPanel parent;
    
    private final int xSize;
    private final int ySize;
    private final int graphicsTick = 20;
    private final int mechanicsTick = 20;
    private Timer graphicsClock;
    private Timer mechanicsClock;
    private boolean upArrowPressed;
    private boolean leftArrowPressed;
    private boolean rightArrowPressed;
    private boolean downArrowPressed;
    private String selection;
    private double density;
    private double dragM;
    private double dragS;
    private Color colorAttribute;
    private final Color BROWN = new Color(150,75,37);
    private final Color DARK_RED = new Color(128,0,0);
    private final Color DARK_BLUE = new Color(0,0,128);
    private final Color PINK = new Color(255,128,128);
    private final Color LIGHT_BLUE = new Color(200,200,255);
    private final Color TAN = new Color(255,128,64);
    private final Color ORANGE = new Color(255,100,0);
    private final Color LIGHT_ORANGE = new Color(255,150,30);
    private Color newObjectColor;
    private Point[] newObjectData;
    private BouncableObject newBouncableObject;
    private boolean[][] hasBounced;
    private final int objectLimit = 256;
    
    
    private BouncableObject mainBall;
    ArrayList ballList;

    
    public FieldPanel(BouncingSim frame, JPanel parent, int xSize, int ySize){
        setFocusable(true);
        requestFocusInWindow();
        this.parent = parent;

        this.frame = frame;
        this.xSize = xSize;
        this.ySize = ySize;
        mainBall = new MainBall(300,300,0,0,0,0,50,1,Color.GREEN,0.99,0.001);
        ballList = new ArrayList<BouncableObject>();
        graphicsClock = new Timer(graphicsTick,this);
        graphicsClock.setActionCommand("Graphics");
        graphicsClock.addActionListener(this);
        graphicsClock.start();
        mechanicsClock = new Timer(mechanicsTick,this);
        mechanicsClock.setActionCommand("Mechanics");
        mechanicsClock.addActionListener(this);
        mechanicsClock.start();
        newObjectData = new Point[3];
        selection = "Controllable Ball";
        density = 1;
        dragM = 1;
        dragS = 0;
        colorAttribute = Color.BLACK;
        newObjectColor = Color.GRAY;
        hasBounced = new boolean[objectLimit][objectLimit];

        
        
        
        //Static objects should be placed after all movable objects
        ballList.add(mainBall);
        //Arc arc = new Arc(new Point(200,100,Color.BLACK),new Point(100,200,Color.BLACK),new Point(200,200,Color.BLACK),Color.BLACK);
        //Arc arc = new Arc(150,150,100 * Math.sqrt(2),Math.PI / 4,-Math.PI,Color.BLACK);
        //ballList.add(arc);
        
        //ballList.add(new NormalBall(400,200,0,0,0,0,50,1,Color.BLACK,1,0));

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        
        
        setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        
    }
    
    public void resetNewObjectData(){
        this.newObjectData = new Point[3];
    }
    
    public ArrayList getBallList(){
        return ballList;
    }
    
    public void setSelection(String selection){
        this.selection = selection;
        
    }
    
    public void setDensity(double d){
        this.density = d;
    }
    
    public void setDragM(double d){
        this.dragM = d;
    }
    
    public void setDragS(double d){
        this.dragS = d;
    }
    public void setNewColors(String s, String a){
        if (s.equals("Ball")){
            if(a.equals("Bouncy")){
                colorAttribute = DARK_BLUE;
                newObjectColor = LIGHT_BLUE;
            }
            if(a.equals("Mud")){
                colorAttribute = ORANGE;
                newObjectColor = LIGHT_ORANGE;
            }
            if(a.equals("Deadly")){
                colorAttribute = DARK_RED;
                newObjectColor = PINK;
            }
        }
        
        else{
            if(a.equals("Bouncy")){
                colorAttribute = Color.BLACK;
                newObjectColor = Color.GRAY;
            }
            if(a.equals("Mud")){
                colorAttribute = BROWN;
                newObjectColor = TAN;
            }
            if(a.equals("Deadly")){
                colorAttribute = Color.RED;
                newObjectColor = Color.PINK;
            }
        }
        
    }
    

    
    public void paintComponent(Graphics g){
        g.clearRect(0, 0, xSize, ySize);
        for(int i = ballList.size() - 1; i >= 0; i--){
            ((BouncableObject)ballList.get(i)).paint(g);
        }

        if (newBouncableObject != null)
            newBouncableObject.paint(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Mechanics")){
            collisionDetection();

            for(int i = 0; i < ballList.size(); i++){
                if (ballList.get(i) instanceof MovableObject){
                    
                    MovableObject m = (MovableObject) (ballList.get(i));
                    if (m.wallBounce(xSize, ySize)){
                        for (int j = 0; j < ballList.size();j++){
                            hasBounced[i][j] = false;
                        }
                        hasBounced[i][i] = true;
                    }
                    m.dragUpdate();
                }
                if (ballList.get(i) instanceof MainBall){
                    
                    MainBall b = (MainBall) ballList.get(i);
                    b.keyUpdate(upArrowPressed,leftArrowPressed,rightArrowPressed,downArrowPressed);
                }
              
            }
            
        }
        else if (e.getActionCommand().equals("Graphics")){
        repaint();
        }
    }
    

    
    public void collisionDetection(){
        

        boolean slow = false;
        for (int i = 0; i < ballList.size(); i++){
            
            if ((BouncableObject)ballList.get(i) instanceof MovableObject){////
                
            MovableObject bb = (MovableObject)ballList.get(i);////
            if (hasBounced[i][i] == true && !isValidSpot(bb)){/////
                bb.setXPosition(bb.getXPosition() + bb.getXVelocity());//////     +??
                bb.setYPosition(bb.getYPosition() + bb.getYVelocity());////////
            }////
            else{////////
                
                    
                    
                    
            }///////
            } 
            hasBounced[i][i] = false;
        }

        for (int i = 0; i < ballList.size(); i++){
            //kill
            if ((BouncableObject)ballList.get(i) instanceof MovableObject){
                MovableObject k = (MovableObject)ballList.get(i);
                if (k.getKill()){
                    ballList.remove(k);
                }
            }
            
            
            
            
            
            for (int j = i + 1; j < ballList.size(); j++){
                BouncableObject b1 = (BouncableObject)ballList.get(i);
                BouncableObject b2 = (BouncableObject)ballList.get(j);
                
                //slow down
                
                if (b1.isOverlapping(b2) && b2.getColor().equals(BROWN)){
                    slow = true;
                }
                
                if (b1.isOverlapping(b2) && hasBounced[i][i] == false && hasBounced[i][j] == false){
                    
                    b1.bounce(b2);
                    
                    if (b1 instanceof MovableObject){
                        
                        updateBounceData(i,j);
                    }
                    if (b2 instanceof MovableObject){
                        updateBounceData(j,i);
                    }
                    
                }
                //if object does not bounce this tick, <and is not overlapping anything> clear the bouce data for that object
                //so it can bounce with anything next tick
                else{
                    
                    boolean stillOverlapping = false;
                    for (int m = 0; m < ballList.size(); m++){
                        BouncableObject b3 = (BouncableObject)ballList.get(m);
                        if (i != m && b1.isOverlapping(b3)){
                            stillOverlapping = true;
                            
                            
                        }
                    }
                    if (!stillOverlapping){
                        for (int m = 0; m < ballList.size(); m++){
                            hasBounced[i][m] = false;
                        }
                    }
                    else{
                        
                    }
                    
                }
            }
            if (slow && (BouncableObject)ballList.get(i) instanceof Ball){
            BouncingMechanics.slowDown((Ball)ballList.get(i));
            slow = false;
            }
        }
        
        
        
    }
    
    public void updateBounceData(int i, int j){
        for (int m = 0; m < ballList.size(); m++){
            hasBounced[i][m] = false;
            
        }
        hasBounced[i][j] = true;
        hasBounced[i][i] = true;
    }
    
    public boolean isValidSpot(BouncableObject b){
        
        for (int i = 0; i < ballList.size(); i++){
            BouncableObject b2 = (BouncableObject)ballList.get(i);
            if (b.isOverlapping(b2)){
                return false;
            }
        }
        if (b.isOverlapping(new Line(0,0,xSize,0,Color.BLACK))){

            return false;
        }
        if (b.isOverlapping(new Line(0,0,0,ySize,Color.BLACK))){

            return false;
        }
        if (b.isOverlapping(new Line(xSize,0,xSize,ySize,Color.BLACK))){

            return false;
        }
        if (b.isOverlapping(new Line(0,ySize,0,ySize,Color.BLACK))){

            return false;
        }
        if (ballList.size() == objectLimit){

            return false;
        }
        return true;
    }
    

    
    

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP){
            upArrowPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            leftArrowPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            rightArrowPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            downArrowPressed = true;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP){
            upArrowPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            leftArrowPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            rightArrowPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            downArrowPressed = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(selection.equals("Point")){
            Point p = new Point(e.getX(),e.getY(),colorAttribute);
            if(isValidSpot(p))
                ballList.add(p);
        }
        if(selection.equals("Line") || selection.equals("Ball")|| selection.equals("Controllable Ball")|| selection.equals("Box")|| selection.equals("Circle") || selection.equals("Ring")){
            resetNewObjectData();
            newObjectData[0] = new Point(e.getX(),e.getY(),Color.BLACK);
        }
        
        if  (selection.equals("Arc")){
            if (newObjectData[0] == null){
                newObjectData[0] = new Point(e.getX(),e.getY(),Color.BLACK);
                return;
            }
            if (newObjectData[1] == null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.BLACK);
                return;
            }

            
            newObjectData[2] = new Point(e.getX(),e.getY(),Color.BLACK);
            
            if (isValidSpot(newBouncableObject) && newObjectData[2] != null){
                    ballList.add(new Arc(newObjectData[0],newObjectData[1],newObjectData[2],colorAttribute));
                    
            }
            resetNewObjectData();
            
            newBouncableObject = null;
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try{
            
        if(selection.equals("Line")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.BLACK);
                if (isValidSpot(newBouncableObject))
                    ballList.add(new Line(newObjectData[0],newObjectData[1],colorAttribute));
                else
                    resetNewObjectData();
            }
        }
        if(selection.equals("Box")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.BLACK);
                if (isValidSpot(newBouncableObject)){
                    ballList.add(new Box(newObjectData[0],newObjectData[1],colorAttribute));
                
                }
                else
                    resetNewObjectData();
            }
        }
        if(selection.equals("Circle")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.BLACK);
                if (isValidSpot(newBouncableObject)){
                    ballList.add(new Circle(newObjectData[0],newObjectData[1],colorAttribute));
                
                }
                else
                    resetNewObjectData();
            }
        }
        
        if(selection.equals("Ring")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.BLACK);
                if (isValidSpot(newBouncableObject)){
                    ballList.add(new Ring(newObjectData[0],newObjectData[1],colorAttribute));
                    
                }
                else
                    resetNewObjectData();
            }
        }

        
        if(selection.equals("Ball")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.BLACK);
                double newSize = 2.0 * Math.sqrt(Math.pow(newObjectData[1].getX() - newObjectData[0].getX(),2) + Math.pow(newObjectData[1].getY() - newObjectData[0].getY(),2));
                if (isValidSpot(newBouncableObject)){
                    
                    ballList.add(0,new NormalBall(newObjectData[0].getX(),newObjectData[0].getY(),0,0,0,0,newSize,density,colorAttribute,dragM,dragS));
                }
                else
                    resetNewObjectData();
            }
        }
        
        if(selection.equals("Controllable Ball")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.BLACK);
                double newSize = 2.0 * Math.sqrt(Math.pow(newObjectData[1].getX() - newObjectData[0].getX(),2) + Math.pow(newObjectData[1].getY() - newObjectData[0].getY(),2));
                if (isValidSpot(newBouncableObject))
                    ballList.add(0,new MainBall(newObjectData[0].getX(),newObjectData[0].getY(),0,0,0,0,newSize,density,Color.GREEN,dragM,dragS));
                else
                    resetNewObjectData();
            }
        }
        newBouncableObject = null;
        
        }
        catch(NullPointerException g){
            
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        requestFocusInWindow();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(selection.equals("Line")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.GRAY);
                newBouncableObject = new Line(newObjectData[0],newObjectData[1],newObjectColor);
            }
        }
        
        if(selection.equals("Box")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.GRAY);
                newBouncableObject = new Box(newObjectData[0],newObjectData[1],newObjectColor);
            }
        }
        
        if(selection.equals("Circle")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.GRAY);
                newBouncableObject = new Circle(newObjectData[0],newObjectData[1],newObjectColor);
            }
        }
        
        if(selection.equals("Ring")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.GRAY);
                newBouncableObject = new Ring(newObjectData[0],newObjectData[1],newObjectColor);
            }
        }
        
        
        
        if(selection.equals("Ball")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.BLACK);
                double newSize = 2.0 * Math.sqrt(Math.pow(newObjectData[1].getX() - newObjectData[0].getX(),2) + Math.pow(newObjectData[1].getY() - newObjectData[0].getY(),2));
                newBouncableObject = new NormalBall(newObjectData[0].getX(),newObjectData[0].getY(),0,0,0,0,newSize,1,newObjectColor,1,0);
            }
        }
        if(selection.equals("Controllable Ball")){
            if(newObjectData[0] != null){
                newObjectData[1] = new Point(e.getX(),e.getY(),Color.BLACK);
                double newSize = 2.0 * Math.sqrt(Math.pow(newObjectData[1].getX() - newObjectData[0].getX(),2) + Math.pow(newObjectData[1].getY() - newObjectData[0].getY(),2));
                newBouncableObject = new MainBall(newObjectData[0].getX(),newObjectData[0].getY(),0,0,0,0,newSize,1,new Color(200,255,200),1,0);
            }
        }
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
        if(selection.equals("Arc")){
            if(newObjectData[0] != null && newObjectData[1] == null){
                Point point = new Point(e.getX(),e.getY(),Color.GRAY);
                newBouncableObject = new Line(newObjectData[0],point,newObjectColor);
                return;
            }
            if(newObjectData[1] != null && newObjectData[2] == null){
                Point point2 = new Point(e.getX(),e.getY(),Color.GRAY);
                newBouncableObject = new Arc(newObjectData[0],newObjectData[1],point2,newObjectColor);
                return;
            }
        }
    }
    
}
