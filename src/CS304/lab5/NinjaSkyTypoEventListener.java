package CS304.lab5;

import CS304.Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.BitSet;

public class NinjaSkyTypoEventListener implements GLEventListener, KeyListener {
    float screenHeight = 200;
    float screenWidth = 200;
    float xMax = screenWidth / 2f;
    float xMin = -(screenWidth / 2f);
    float yMax = screenHeight / 2f;
    float yMin = -(screenHeight / 2f);
    int xSoldier = 0, ySoldier = 0;
    int xLetter = 0 , yLetter = (int)screenHeight-10;
    int xStar = 0, yStar = 0;
    float starRotate = 0;
    int animationIndex = 0, animationSpeed = 5,animationCounter = 0;
    int letterIndex = 0;
    int healthBarIndex = 0,maxHealth = 100,currHealth = maxHealth;
    float healthBarWidth = 100; // Set the width of the health bar
    float healthBarHeight = 10; // Set the height of the health bar
    float healthBarX = -70; // Position the health bar near the top-left
    float redHealthBarX = -70;
    float healthBarY = 85; // Position vertically near the top

    String assetsFolderName = "CS304//Assets//Alphabet";

    String[] textureNames = {"Man1.png","Man2.png","Man3.png","Man4.png","ninjaStar.png","HealthB.png","HealthA.png","Back.png"};

    String[] textureLettersNames = new String[26];
    public void fillLetters(){
        for (int i = 0; i < 26; i++) {
            char letter = (char) ('a' + i);
            textureLettersNames[i] = letter + ".png";
        }
    }

    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    TextureReader.Texture[] textureLetter = new TextureReader.Texture[textureLettersNames.length];

    int[] textures = new int[textureNames.length];
    int[] texturesLetters = new int[textureLettersNames.length];


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
        yLetter -= 1; // here the letter going down
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        {
            DrawBackground(gl);
            handleKeyPress();
            DrawSprite(gl, xSoldier, ySoldier, textures, animationIndex, 0, 2); // draw the soldier
//            DrawSprite(gl,-70,85,textures,healthBarIndex,0,1);// draw the health bar
            float healthPercentage = currHealth / (float) maxHealth;
//            System.out.println(healthPercentage);
            drawHealthBar(gl,healthPercentage);
        }//^Soldier & back-ground
        {
            if (isStarFlying) {
                yStar += 1;
                DrawSprite(gl, xStar, yStar, textures, 4, starRotate += 10, 5); // draw our star
                if (yStar > yMax - 10) {
                    isStarFlying = false;
                }
            }
        }//^Ninja Star
        {
            DrawSprite(gl, xLetter, yLetter, texturesLetters, letterIndex, 0, 1); // draw letters
            double dist = sqrdDistance(xStar,yStar,xLetter,yLetter);
            double radii = Math.pow(0.5*0.1*yMax+0.5*0.1*yMax,2);
            boolean isCollided = dist<=radii;
//            System.out.println("Distance: " + dist + " | Collision: " + isCollided);
            if (isCollided ) {
                // Collision happened
//                System.out.println("Collision detected! Resetting letter and star.");
                // Reset letter position and choose a new random letter
                xLetter = (int) (Math.random() * (screenWidth - 10) + 1) - (int) xMax + 5;
                yLetter = (int) yMax; // Reset to top of the screen
                letterIndex = (int) (Math.random() * 26); // Pick a new random letter
                isStarFlying = false; // Reset the star's state
            } else if (yLetter < yMin + 10) {
                // Letter missed by the ninja star, decrease health
                currHealth = Math.max(0, currHealth - 10); // Decrease health by 10, but not below 0
//                System.out.println("Letter missed! Decreasing health to " + currHealth);
                // Reset letter position and choose a new random letter
                xLetter = (int) (Math.random() * (screenWidth - 10) + 1) - (int) xMax + 5;
                yLetter = (int) yMax; // Reset to top of the screen
                letterIndex = (int) (Math.random() * 26); // Pick a new random letter
                redHealthBarX -= 2;
            }
        }//^Letters
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
    public double sqrdDistance(int x, int y, int x1, int y1){
        return Math.pow(x-x1,2)+Math.pow(y-y1,2);
    }
    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

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
    public void DrawSprite(GL gl,int x, int y,int[] textures, int index,float rotate, float scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated( x/xMax, y/yMax, 0);
        gl.glScaled((0.05*scale), (0.05*scale), 1);
        gl.glRotated(rotate,0,0,1);
        drawFullScreenQuad(gl);
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    public void drawHealthBar(GL gl,float healthPercentage) {
        gl.glEnable(GL.GL_BLEND);

        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[5]);	// Turn Blending On
        gl.glPushMatrix();
        gl.glTranslatef(healthBarX / xMax, healthBarY / yMax, 0);
        gl.glScaled(0.2,0.02,1);
        drawFullScreenQuad(gl);
        gl.glPopMatrix();

        // Draw the filled (red) health bar scaled to current health
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[6]); // HealthA (red bar texture)
        gl.glPushMatrix();
        gl.glTranslatef((redHealthBarX/ xMax), healthBarY / yMax, 0);
        gl.glScalef(0.2f * healthPercentage, 0.02f , 1); // Scaled by health
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
    public void handleKeyPress() {
        boolean flag = false;
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (xSoldier > xMin+5) {
                xSoldier--;
            }
            flag = true;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (xSoldier < xMax-5) {
                xSoldier++;
            }
            flag = true;
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (ySoldier > yMin+10) {
                ySoldier--;
            }
            flag = true;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (ySoldier < yMax-10) {
                ySoldier++;
            }
            flag = true;
        }
        if (flag) { // only increment if movement is detected
            animationCounter++;
            if (animationCounter >= animationSpeed) {
                animationIndex++;
                animationIndex %= 4;
                animationCounter = 0;
            }
        }
    }

    boolean isStarFlying = false;
    public BitSet keyBits = new BitSet(256);
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.set(keyCode);

        if (keyCode == KeyEvent.VK_SPACE && !isStarFlying) {
            isStarFlying = true;
            xStar = xSoldier; // Initialize x position at soldier's position
            yStar = ySoldier + 10; // Set the star just above the soldier
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

    public boolean isKeyPressed(int keyCode) {
        return keyBits.get(keyCode);
    }
}
