package CS304.lab5.MonsterRush;


import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class MonsterRush extends JFrame{
    private GLCanvas glCanvas;
    static FPSAnimator animator = null;
    private MonsterRushEventListener listener = new MonsterRushEventListener();
    public static void main(String[] args) {
        new MonsterRush();
        animator.start();
    }
    public MonsterRush(){
        super("Monster Rush");
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
