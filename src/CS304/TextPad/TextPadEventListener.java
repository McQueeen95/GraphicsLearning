package CS304.TextPad;

import CS304.Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class TextPadEventListener implements GLEventListener,KeyListener {
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
    double yScroll;
    double scale = 1;
    /////////////////////////////////////////////////      for screen
    String assetsFolderName = "CS304//Assets//Alphabet";
    String[] textureNames = {"..png","Back3.png"};
    String[] textureLettersNames = new String[26];
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    TextureReader.Texture[] textureLetter = new TextureReader.Texture[textureLettersNames.length];
    int[] textures = new int[textureNames.length];
    int[] texturesLetters = new int[textureLettersNames.length];
    /////////////////////////////////////////////////////////////////////textures
    int blinking = 0;
    int dotX = minBorderX;
    int dotY = maxBorderY;
    /////////////////////////////////////////////////////////////////////dot
    int letterIndex = -1;
    int letterX,letterY;
    List<Letter> letters = new ArrayList<>();
    /////////////////////////////////////////////////////////////////////letters
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        fillLetters();
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
        generateTextures(textureNames,texture,textures,gl);
        generateTextures(textureLettersNames,textureLetter,texturesLetters,gl);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        gl.glPushMatrix();
        gl.glTranslated(0,yScroll/yMax,1);
        gl.glScaled(scale, scale, 1);
        blinkingDot(gl, dotX, dotY);
        TypeIt(gl);
        gl.glPopMatrix();

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
    public void blinkingDot(GL gl,double x , double y){
        if (blinking >=10 && blinking < 20) {
            DrawSprite(gl,x,y,textures,0,0,1f,1.588f);
            blinking++;
        } else if (blinking == 20) {
            blinking = 0;
        }else {
            blinking++;
        }
    }

    public BitSet keyBits = new BitSet(256);

    public boolean isKeyPressed(int keyCode) {
        return keyBits.get(keyCode);
    }
    public void TypeIt(GL gl){
        Iterator<Letter> iterator = letters.iterator();
        while (iterator.hasNext()) {
            Letter letter = iterator.next();
            if (letterIndex >= 0) {
                if (dotX >= maxBorderX) {
                    dotX = minBorderX;
                    dotY = letterY - 20;
                    if (dotY <= minBorderY) {
                        yScroll +=20;
                    }
                }
                DrawSprite(gl,letter.x,letter.y,texturesLetters,letter.letterIndex,0,1f,1.588f);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int typedChar = e.getKeyChar();
        keyBits.set(typedChar);
        if (typedChar <= 'z' && typedChar >= 'a') {
            letterIndex = typedChar - 'a';
            letterX = dotX;
            letterY = dotY;
            dotX = letterX + 10;
            letters.add(new Letter(letterX,letterY,letterIndex));
        }
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            dotX += 10;
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            dotX = minBorderX;
            dotY -= 20;
            if (dotY <= minBorderY) {
                yScroll +=20;
            }
        }
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (!letters.isEmpty()) {
                letters.remove(letters.size() - 1);
                dotX -= 10;
            }
        }
        if (keyCode == KeyEvent.VK_UP) {
            yScroll++;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            yScroll--;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            scale+=0.05;
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            scale-=0.05;
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void fillLetters(){
        for (int i = 0; i < 26; i++) {
            char letter = (char) ('a' + i);
            textureLettersNames[i] = letter + ".png";
        }
        System.out.println("Done loading all letters");
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
    class Letter{
        int x,y;
        int letterIndex;

        public Letter(int x, int y, int letterIndex) {
            this.x = x;
            this.y = y;
            this.letterIndex = letterIndex;
        }
    }
}
