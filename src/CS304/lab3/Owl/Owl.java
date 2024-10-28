package CS304.lab3.Owl;

import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
public class Owl extends JFrame {
	static FPSAnimator animator = null;

	public static void main(String[] args) {
		new Owl();
		animator.start();
	}
	public Owl(){
		super("this is a wired Owl who looking to a ball");
		GLCanvas glCanvas = new GLCanvas();
		glCanvas.addGLEventListener(new OwlEventListener());
		getContentPane().add(glCanvas, BorderLayout.CENTER);
		animator = new FPSAnimator(glCanvas,60);
		setSize(600,600);
		setLocationRelativeTo(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
