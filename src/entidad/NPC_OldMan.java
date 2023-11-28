package entidad;


import java.util.Random;

import main.GamePanel;


public class NPC_OldMan extends Entity {
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direccion = "down";
		speed = 1;
		
		getImage();
		setDialogue();
	}
	
    public void getImage() {

        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");    
        left2 = setup("/npc/oldman_left_2");    
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");

    }

	public void setDialogue() {
		dialogues[0]= "Hola Mamahuevaso!";
		dialogues[1]= "Que te trae por estas zonas gancho";
		dialogues[2]= "algun dialogo de aventuras xd";
		dialogues[3]= "un chao del npichi";
	}

    
    
    public void setAction() {
    	
    	actionLockCounter ++;
    	
    	if(actionLockCounter == 60) {
        	Random random = new Random();
        	int i = random.nextInt(100)*1;
        	
        	if(i <= 25) {
        		direccion = "up";
           	}
        	if(i > 25 && i <= 50) {
        	direccion = "down";
        	}
        	if(i > 50 && i <= 75) {
        		direccion = "left";
        	}
        	if(i > 75 && i <= 100) {
        		direccion = "rigth";
        	}
        	actionLockCounter = 0;
    	}
    	
    }
    
    public void speak() {
    	super.speak();

    }
    

}













