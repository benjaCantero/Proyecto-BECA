package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import entidad.Entity;
import entidad.Player;
import tile.TileManager;
import javax.swing.JPanel;
public class GamePanel extends JPanel implements Runnable{
	
    // Ajustes de la pantalla
    final int originalTileSize = 16;  // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; 
    public final int screenHeigth = tileSize * maxScreenRow;
    
    // Ajustes del mundo 
    
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50; 
    
    //FPS
    int FPS = 60;

    //System
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionCheck coCheck = new CollisionCheck(this);
    public assetSetter aSetter = new assetSetter(this);
    public UI ui = new UI(this); 
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    
    //Entidad y objetos 
    public Player player = new Player (this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];


    ArrayList<Entity> entityList = new ArrayList<>();
    
    
    
    //GAME STATE
    public int GameState;
    public final int titleState = 0;
    public final int PlayState = 1;
    public final int PauseState = 2;
    public final int dialogueState = 3;


    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeigth));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }
    
    public void setupGame () {
    	aSetter.setObject();
    	aSetter.setNPC();
        aSetter.setMonster();

    	//playMusic(0);
    	GameState = titleState;
    }

    public void startGameThread () {

        gameThread = new Thread(this);
        gameThread.start();

    }


    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while (gameThread != null) {



            update();


            repaint();



            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;

                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        
    }
    public void update () {

        if (GameState == PlayState) {
        	//player
            player.update();
            
            //NPC
            for(int i = 0; i < npc.length; i++) {
            	if(npc[i] != null) {
            		npc[i].update();
            		
            	}
            }
            for (int i = 0; i<monster.length; i++){
                if(monster[i]!=null){
                    monster[i].update();
                }

            }
        }
        if (GameState == PauseState) {
            
        }



    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        //DEBUG

        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
             drawStart = System.nanoTime();
        }
        
        //TITLE SCREEN 
        if(GameState == titleState) {
        	ui.draw(g2);
        }
        
        //OTROS
        else {
        	//Tile
        	tileM.draw(g2);
        	
        	//Add Entities to the list 
        	entityList.add(player);
        	
        	for(int i = 0; i < npc.length; i++) {
        		if(npc[i] != null) {
        			entityList.add(npc[i]);
        		}
        	}
        	
        	for(int i = 0; i < obj.length; i++) {
        		if(obj[i] != null ) {
        			entityList.add(obj[i]);
        		}
        	}

            for(int i = 0; i < monster.length; i++) {
        		if(monster[i] != null ) {
        			entityList.add(monster[i]);
        		}
        	}
        	
        	//Sort
        	Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
        		
        	});
        	
        	//Draw Entities
        	for(int i = 0; i < entityList.size(); i++) {
        		entityList.get(i).draw(g2);
        	}
        	//Empty Entity 
        	for(int i = 0; i < entityList.size(); i++) {
        		entityList.remove(i);
        	}
        	
            
            // UI
            ui.draw(g2);
        	
        }

        // DEBUG
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: "+ passed, 10, 400);
            System.out.println("Draw time: "+ passed);
            }

        g2.dispose();
    }
    
    public void playMusic(int i) {
    	music.setFile(i);
    	music.play();
    	music.loop();
    }
    
    public void stopMusic() {
    	music.stop();
    	
    }
    
    public void playSE(int i) {
    	
    	se.setFile(i);
    	se.play();
    }



    
}







