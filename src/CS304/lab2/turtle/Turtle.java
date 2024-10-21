package CS304.lab2.turtle;
import CS304.lab1.SimpleGLEventListener;
import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class Turtle extends JFrame{
    static FPSAnimator animator =null;
    public static void main(String[] args) {

        new Turtle();
        animator.start();
    }
    public Turtle(){

        super("Turtle");
        GLCanvas glcanvas = new GLCanvas();
        // this the place that we will put our JFrame on it(GLcanvas)

        glcanvas.addGLEventListener(new TurtleEventListener());
        // to run we need to add GL event listener

        getContentPane().add(glcanvas, BorderLayout.CENTER);
        // now we gonna add our object at center of the BorderLayout

        animator = new FPSAnimator(glcanvas, 60);

        setSize(800, 500); // container size => the size of the window
        setLocationRelativeTo(this);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
