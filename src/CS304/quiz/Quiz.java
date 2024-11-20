package CS304.quiz;

import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quiz extends JFrame{
	private GLCanvas glCanvas;
	static FPSAnimator animator = null;
	private QuizEventListener listener = new QuizEventListener();
	public static void main(String[] args) {
		new Quiz();
		animator.start();
	}
	public Quiz(){
		super("Quiz");
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
