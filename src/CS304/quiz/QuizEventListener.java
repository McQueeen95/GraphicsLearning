package CS304.quiz;

import CS304.TextPad.TextPadEventListener;
import CS304.Texture.TextureReader;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.BitSet;

public class QuizEventListener implements GLEventListener, KeyListener {
	float screenHeight = 200;
	float screenWidth = 200;
	float xMax = screenWidth / 2f;
	float xMin = -(screenWidth / 2f);
	float yMax = screenHeight / 2f;
	float yMin = -(screenHeight / 2f);
	int maxBorderX = (int)xMax - 8;
	int minBorderX = (int)xMin + 10;
	int maxBorderY = (int)yMax - 15;
	int minBorderY = (int)yMin + 15;
	/////////////////////////////////////////////////      for screen
	String assetsFolderName = "CS304//Assets//Alphabet";
	String[] textureNames = {"..png","Back1.png"};
	String[] textureOBJNames = new String[26];
	TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
	TextureReader.Texture[] textureOBJ = new TextureReader.Texture[textureOBJNames.length];
	int[] textures = new int[textureNames.length];
	int[] texturesOBJ = new int[textureOBJNames.length];
	/////////////////////////////////////////////////////////////////////textures

	@Override
	public void init(GLAutoDrawable glAutoDrawable) {
		fillOBJ();
		GL gl = glAutoDrawable.getGL();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
		generateTextures(textureNames,texture,textures,gl);
		generateTextures(textureOBJNames, textureOBJ, texturesOBJ,gl);
	}
	@Override
	public void display(GLAutoDrawable glAutoDrawable) {

		GL gl = glAutoDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		DrawBackground(gl);


	}

	@Override
	public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
	}
	@Override
	public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

	}
	public void fillOBJ(){
		for (int i = 0; i < 26; i++) {
			char letter = (char) ('a' + i);
			textureOBJNames[i] = letter + ".png";
		}
	}


	public BitSet keyBits = new BitSet(256);
	public boolean isKeyPressed(int keyCode) {
		return keyBits.get(keyCode);
	}

	@Override
	public void keyTyped(KeyEvent e) {


	}
	@Override
	public void keyPressed(KeyEvent e) {
		int typedChar = e.getKeyChar();
		keyBits.set(typedChar);
		if (typedChar <= 'z' && typedChar >= 'a') {
			//
		}

		int keyCode = e.getKeyCode();
		keyBits.set(keyCode);
		if (keyCode == KeyEvent.VK_SPACE) {
			//
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyBits.clear();
	}
	public void generateTextures(String[] textureNames, TextureReader.Texture[] texture, int[] textures, GL gl) {
		gl.glGenTextures(textureNames.length,textures,0);
		for (int i = 0; i < textureNames.length; i++) {
			try {
				texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i] , true);
				gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
				new GLU().gluBuild2DMipmaps(
								GL.GL_TEXTURE_2D,
								GL.GL_RGBA, // Internal Texel Format,
								texture[i].getWidth(), texture[i].getHeight(),
								GL.GL_RGBA, // External format from image,
								GL.GL_UNSIGNED_BYTE,
								texture[i].getPixels() // Imagedata
				);
			} catch( IOException e ) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
	public void DrawSprite(GL gl,double x, double y,int[] textures, int index,float rotate, float scaleX, float scaleY){
		gl.glEnable(GL.GL_BLEND);
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

		gl.glPushMatrix();
		gl.glTranslated( x/xMax, y/yMax, 0);
		gl.glScaled((0.05*scaleX), (0.05*scaleY), 1);
		gl.glRotated(rotate,0,0,1);
		drawFullScreenQuad(gl);
		gl.glPopMatrix();

		gl.glDisable(GL.GL_BLEND);
	}
	public void DrawBackground(GL gl){
		gl.glEnable(GL.GL_BLEND);	// Turn Blending On
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length-1]);
		drawFullScreenQuad(gl);
		gl.glDisable(GL.GL_BLEND);  // Disable blending after drawing
	}
	public void drawFullScreenQuad(GL gl){
		gl.glBegin(GL.GL_QUADS);
		// Set each corner to align with the orthographic view boundaries
		// Map texture coordinates from 0 to 1 for the entire image
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom-left corner of the screen

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom-right corner

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top-right corner

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top-left corner
		gl.glEnd();
	}

}
