package CS304.lab5;

import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class NinjaSkyTypo extends JFrame {
    private GLCanvas glCanvas;
    static FPSAnimator animator = null;
    private NinjaSkyTypoEventListener listener = new NinjaSkyTypoEventListener();
    public static void main(String[] args) {
        new NinjaSkyTypo();
        animator.start();
    }
    public NinjaSkyTypo(){
        super("Ninja Sky Typooo");
        glCanvas = new GLCanvas();
        glCanvas.addGLEventListener(listener);
        glCanvas.addKeyListener(listener);
        getContentPane().add(glCanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(glCanvas, 60);
        setSize(700, 700);
        setLocationRelativeTo(this);
        setVisible(true);
        setFocusable(true);
        glCanvas.requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
