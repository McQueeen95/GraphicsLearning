package CS304.TextPad;

import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class TextPad extends JFrame {
    private GLCanvas glCanvas;
    static FPSAnimator animator = null;
    private TextPadEventListener listener = new TextPadEventListener();

    public static void main(String[] args) {
        new TextPad();
        animator.start();
    }
    public TextPad(){
        super("TextPad my bro");
        glCanvas = new GLCanvas();
        glCanvas.addGLEventListener(listener);
        glCanvas.addKeyListener(listener);
        getContentPane().add(glCanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(glCanvas, 60);
        setSize(800, 800);
        setLocationRelativeTo(this);
        setVisible(true);
        setFocusable(true);
        glCanvas.requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
