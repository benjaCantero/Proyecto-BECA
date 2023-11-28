package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import entidad.Entity;
import object.OBJ_Heart;
public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinish = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0: es la primera pantalla y 1: es la segunda 




    public UI(GamePanel gp) {
        this.gp = gp;
        
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        
        
        
        
        //Create Hub Object
     Entity heart = new OBJ_Heart(gp);
     heart_full = heart.image;
     heart_half = heart.image2;
     heart_blank = heart.image3;


    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonica);
        //g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        
        //TITLE STATE
        if(gp.GameState == gp.titleState) {
        	drawTitleScreen();
        }

        // Play State
        if (gp.GameState == gp.PlayState) {
        	drawPlayerLife();

        }
        
        //Pause State
        if (gp.GameState == gp.PauseState) {
        	drawPlayerLife();
            DrawPauseScreen();
        }
        
        //Dialogue State
        if(gp.GameState == gp.dialogueState) {
        	drawPlayerLife();
        	drawDialogueScreen();
        }
    }
    
    public void drawPlayerLife() {
    	
    	//gp.player.life = 5;   cambiar la vida 
    	
    	int x = gp.tileSize/2;
    	int y = gp.tileSize/2;
    	int i = 0;
    	
    	//Draw Max Life 
    	while(i < gp.player.maxLife/2) {
    		g2.drawImage(heart_blank, x, y, null);
    		i++;
    		x += gp.tileSize;
    	}
    	
    	//Reset
    	x = gp.tileSize/2;
    	y = gp.tileSize/2;
    	i = 0;
    	
    	//Draw Current Life 
    	while(i < gp.player.life) {
    		g2.drawImage(heart_half, x, y, null);
    		i++;
    		if(i < gp.player.life) {
    			g2.drawImage(heart_full, x, y, null);
    		}
    		i++;
    		x += gp.tileSize;
    	}
    }
    
    public void drawTitleScreen() {
    	
    	if(titleScreenState == 0) {
    		
        	g2.setColor(new Color(0, 0, 0));
        	g2.fillRect(0, 0, gp.screenWidth, gp.screenHeigth);
        	
        	//TITLE NAME
        	g2.setFont(g2.getFont().deriveFont(Font.BOLD,65F));
        	String text = "blue boy adventure";
        	int x = getXforCenterText(text);
        	int y = gp.tileSize;
        	
        	//SOMBRA
        	g2.setColor(Color.gray);
        	g2.drawString(text, x+5, y+5);
        	//MAIN COLOR 
        	g2.setColor(Color.white);
        	g2.drawString(text, x, y);
        	
        	//BLUE BOY IMAGE
        	x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        	y += gp.tileSize*2;
        	g2.drawImage(gp.player.down1, x, x, gp.tileSize*2, gp.tileSize*2, null);
        	
        	//MENU
        	g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        	
        	text = "New Game";
        	x = getXforCenterText(text);
        	y += gp.tileSize;
        	g2.drawString(text, x, y);
        	if(commandNum == 0) {
        		g2.drawString(">", x-gp.tileSize, y);
        	}
        	
        	text = "Load Game";
        	x = getXforCenterText(text);
        	y += gp.tileSize;
        	g2.drawString(text, x, y);
        	if(commandNum == 1) {
        		g2.drawString(">", x-gp.tileSize, y);
        	}
        	
        	text = "Quit";
        	x = getXforCenterText(text);
        	y += gp.tileSize;
        	g2.drawString(text, x, y);
        	if(commandNum == 2) {
        		g2.drawString(">", x-gp.tileSize, y);
        	}
    	}
    	else if (titleScreenState == 1) {
    		//Class Selection Screen
    		g2.setColor(Color.white);
    		g2.setFont(g2.getFont().deriveFont(42F));
    		
    		String text = "Selecciona tu Clase";
    		int x = getXforCenterText(text);
    		int y = gp.tileSize*3;
    		g2.drawString(text, x, y);
    		
    		text = "Luchador";
    		x = getXforCenterText(text);
    		y += gp.tileSize*3;
    		g2.drawString(text, x, y);
    		if(commandNum == 0) {
    		g2.drawString(">", x-gp.tileSize, y);
    		}
    		
    		text = "Mago";
    		x = getXforCenterText(text);
    		y += gp.tileSize;
    		g2.drawString(text, x, y);
    		if(commandNum == 1) {
    		g2.drawString(">", x-gp.tileSize, y);
    		}
    		
    		text = "Soldado";
    		x = getXforCenterText(text);
    		y += gp.tileSize;
    		g2.drawString(text, x, y);
    		if(commandNum == 2) {
    		g2.drawString(">", x-gp.tileSize, y);
    		}
    		
    		text = "Back";
    		x = getXforCenterText(text);
    		y += gp.tileSize*2;
    		g2.drawString(text, x, y);
    		if(commandNum == 3) {
    		g2.drawString(">", x-gp.tileSize, y);
    		}
    	}
    
    }   

    public void DrawPauseScreen(){
        String text = "Pausa";

        int x = getXforCenterText(text);
        int y = gp.screenHeigth/2;

        g2.drawString(text, x, y);
    }
    
    public void drawDialogueScreen() {
    	
    	//ventana
    	int x = gp.tileSize*2;
    	int y = gp.tileSize/2;
    	int width = gp.screenWidth - (gp.tileSize*4);
    	int higth = gp.tileSize*4;
    	
    	drawSubWindow(x, y, width, higth);
    	
    	g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
    	x += gp.tileSize;
    	y += gp.tileSize;
    	
    	for(String line : currentDialogue.split("\n")) {
    		g2.drawString(line, x, y);
    		
    	}
    	g2.drawString(currentDialogue, x, y);
    }
    
    public void drawSubWindow(int x, int y, int width, int higth) {
    	
    	Color c = new Color(0,0,0,200);
    	g2.setColor(c);
    	g2.fillRoundRect(x, y, width, higth, 35, 35);
    	
    	c = new Color(255,255, 255);
    	g2.setColor(c);
    	g2.setStroke(new BasicStroke(5));
    	g2.drawRoundRect(x+5, y+5, width-10, higth-10, 25, 25);
    	
    	
    	
    	
    	
    }
    public int getXforCenterText(String text){
                int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}






