
package bouncingsim;

import javax.swing.JFrame;


public class BouncingSim extends JFrame{

    public BouncingSim(){
        setTitle("Bouncing Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new SandboxPanel(this));
        pack();
        //centers the frame on the screen
        setLocationRelativeTo(null);
        setVisible(true);
}

    public static void main(String[] args) {
        new BouncingSim();
    }
    
}
