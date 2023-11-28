package main;

import entidad.Entity;
import entidad.Player;

public class CollisionCheck {
	
	GamePanel gp;
	
	public CollisionCheck(GamePanel gp) {
		
		this.gp = gp;
		
	}
	
	public void checkTile(Entity entidad) {
		
		int entidadLeftWorldX = entidad.worldX + entidad.solidArea.x;
		int entidadRightWorldX = entidad.worldX + entidad.solidArea.x + entidad.solidArea.width;
		int entidadTopWorldY = entidad.worldY + entidad.solidArea.y;
		int entidadBottonWorldY = entidad.worldY + entidad.solidArea.y + entidad.solidArea.height;
		
		int entidadLeftCol = entidadLeftWorldX/gp.tileSize;
		int entidadRightCol = entidadRightWorldX/gp.tileSize;
		int entidadTopRow = entidadTopWorldY/gp.tileSize;
		int entidadBottonRow = entidadBottonWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entidad.direccion) {
		case "up":
			entidadTopRow = (entidadTopWorldY - entidad.speed)/gp.tileSize;
			tileNum1 =  gp.tileM.mapTileNum[entidadLeftCol][entidadTopRow];
			tileNum2 = gp.tileM.mapTileNum[entidadRightCol][entidadTopRow];	
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entidad.collisionOn = true;
				
			}
			break;
		case "down":
			entidadBottonRow = (entidadBottonWorldY + entidad.speed)/gp.tileSize;
			tileNum1 =  gp.tileM.mapTileNum[entidadLeftCol][entidadBottonRow];
			tileNum2 = gp.tileM.mapTileNum[entidadRightCol][entidadBottonRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entidad.collisionOn = true;
				
			}
			break;
		case "left":
			entidadLeftCol = (entidadLeftWorldX - entidad.speed)/gp.tileSize;
			tileNum1 =  gp.tileM.mapTileNum[entidadLeftCol][entidadTopRow];
			tileNum2 = gp.tileM.mapTileNum[entidadLeftCol][entidadBottonRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entidad.collisionOn = true;
				
			}
			break;
		case "rigth":
			entidadRightCol = (entidadRightWorldX + entidad.speed)/gp.tileSize;
			tileNum1 =  gp.tileM.mapTileNum[entidadRightCol][entidadTopRow];
			tileNum2 = gp.tileM.mapTileNum[entidadRightCol][entidadBottonRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entidad.collisionOn = true;
				
			}
			break; 	
		}
		
	}
	public int checkObjects(Entity entidad, boolean player) {
		
		int index = 999;
		
		for(int i = 0; i < gp.obj.length; i++) {
			if(gp.obj[i] != null) {
				
				//obtener la posición del área sólida y vacía
				
				entidad.solidArea.x = entidad.worldX + entidad.solidArea.x;
				entidad.solidArea.y = entidad.worldY + entidad.solidArea.y;
				
				//obtener la posición del área sólida de los objetos
				
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch(entidad.direccion) {
				case "up":entidad.solidArea.y -= entidad.speed; break;
				case "down":entidad.solidArea.y += entidad.speed;break;
				case "left":entidad.solidArea.x -= entidad.speed;break;
				case "rigth":entidad.solidArea.x += entidad.speed;break;

				}
				if(entidad.solidArea.intersects (gp.obj[i].solidArea)) {
					if(gp.obj[i].collision == true) {
						entidad.collisionOn = true;
					}
					if(player == true) {
						index = i;
						}
					}

				entidad.solidArea.x = entidad.solidAreaDefaultX;
				entidad.solidArea.y = entidad.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
				
			}
		}
		
		return index;		
	}
	
	//check de colicion con entidades 
	public int checkEntity(Entity entidad, Entity[] target) {
		
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				
				//obtener la posición del área sólida y vacía
				
				entidad.solidArea.x = entidad.worldX + entidad.solidArea.x;
				entidad.solidArea.y = entidad.worldY + entidad.solidArea.y;
				
				//obtener la posición del área sólida de los objetos
				
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
				
				switch(entidad.direccion) {
				case "up": entidad.solidArea.y -= entidad.speed; break;
			
				case "down": entidad.solidArea.y += entidad.speed; break;
				
				case "left":entidad.solidArea.x -= entidad.speed; break;
				
				case "rigth": entidad.solidArea.x += entidad.speed;break;
				}
				
				if(entidad.solidArea.intersects (target[i].solidArea)) {

					if (target [i] != entidad) {
					entidad.collisionOn = true;
					index = i;						
					}
				}


				entidad.solidArea.x = entidad.solidAreaDefaultX;
				entidad.solidArea.y = entidad.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
				
			}
		}
		
		return index;
		
	}
	//check colicion npc a jugador
	public boolean checkPlayer(Entity entidad) {

		boolean contactPlayer = false; 

		//obtener la posición del área sólida y vacía
		
		entidad.solidArea.x = entidad.worldX + entidad.solidArea.x;
		entidad.solidArea.y = entidad.worldY + entidad.solidArea.y;
		
		//obtener la posición del área sólida de los objetos
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		switch(entidad.direccion) {

		case "up":entidad.solidArea.y -= entidad.speed;break;

		case "down":entidad.solidArea.y += entidad.speed;break;

		case "left":entidad.solidArea.x -= entidad.speed;break;

		case "rigth":entidad.solidArea.x += entidad.speed;break;

		}
		if(entidad.solidArea.intersects (gp.player.solidArea)) {
			entidad.collisionOn = true;
			contactPlayer = true;
		}

		entidad.solidArea.x = entidad.solidAreaDefaultX;
		entidad.solidArea.y = entidad.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return contactPlayer;
	}


}













