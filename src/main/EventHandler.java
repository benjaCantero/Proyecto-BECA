package main;

import java.awt.Rectangle;

import org.w3c.dom.events.Event;

public class EventHandler {
	
	GamePanel gp;
	EventRect eventRect[][];
	int previousEventX,previousEventy;
	boolean canTouchEvent;

	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		int col = 0;
		int row = 0;
		while (col < gp.maxWorldCol && row <gp.maxWorldRow) {

		eventRect [col] [row] = new EventRect();
		eventRect[col] [row].x = 23;
		eventRect[col] [row].y = 23;
		eventRect[col] [row].width = 2;
		eventRect[col] [row].height = 2;
		eventRect[col] [row].eventRectDefaultX = eventRect[col] [row].x;
		eventRect[col] [row].eventRectDefaultY = eventRect[col] [row].y;

		col++;
		if (col == gp.maxWorldCol) {
			col = 0;
			row++;
		}
		}

	}
	
	public void checkEvent() {
		//COMPRUEBA SI EL PERSONAJE ESTA A MAS DE UNA CASILLA DEL ULTIMO EVENTO

		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventy);
		int distance = Math.max(xDistance,yDistance);
		if (distance > gp.tileSize) {
			canTouchEvent = true;
		}
		if (canTouchEvent == true) {
		if(hit(27,16,"rigth") == true) 
		{damagePit(27, 16, gp.dialogueState);}

		if(hit(23,19,"any") == true) 
		{damagePit(27, 16, gp.dialogueState);}

		if(hit(23,12,"up") == true) 
		{healingPool(23, 12,gp.dialogueState);}			
		}
		
	}
	public boolean hit(int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col] [row].x = col*gp.tileSize + eventRect[col] [row].x;
		eventRect[col] [row].y = row*gp.tileSize + eventRect[col] [row].y;
		
		if(gp.player.solidArea.intersects(eventRect[col] [row]) && eventRect[col][row].eventDone == false) {
			if(gp.player.direccion.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {                                      
				hit = true;

				previousEventX = gp.player.worldX;
				previousEventy = gp.player.worldY;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col] [row].x = eventRect[col] [row].eventRectDefaultX;
		eventRect[col] [row].y = eventRect[col] [row].eventRectDefaultY;
		
		
		return hit;
	}
	
	public void damagePit( int col, int row, int GameState) {
		
		gp.GameState = GameState;
		gp.ui.currentDialogue = "caiste en un poso";
		gp.player.life -= 1;
		//eventRect [col] [row].eventDone = true;
		canTouchEvent = false;

		}
	
	public void healingPool( int col, int row, int GameState) {
		
		if(gp.keyH.enterPressed == true) {
			gp.GameState = GameState;
			gp.ui.currentDialogue = "bebiste agua. \ntu vida se a recuperado";
			gp.player.life = gp.player.maxLife;
		}
		
	}
	

}















