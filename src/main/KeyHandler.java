package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rigthpressed, enterPressed;
    //DEBUG
    boolean checkDrawTime = false;
    
    public KeyHandler (GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        
        if(gp.ui.titleScreenState == 0) {
        	
            //TITLE STATE
            if(gp.GameState == gp.titleState) {
            	
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0) {
                    	gp.ui.commandNum = 2;
                    }

                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;   
                    if(gp.ui.commandNum > 2) {
                    	gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                	if(gp.ui.commandNum == 0) {
                		gp.ui.titleScreenState = 1;
                	}
                	if (gp.ui.commandNum == 1) {
                		//add later
                	}
                	if (gp.ui.commandNum == 2) {
                		System.exit(0);
                	}
                    	
                }
        	
            }
        }
            else if(gp.ui.titleScreenState == 1) {
            	
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0) {
                    	gp.ui.commandNum = 3;
                    }

                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;   
                    if(gp.ui.commandNum > 3) {
                    	gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                	if(gp.ui.commandNum == 0) {
                		System.out.println("hacer algunas cosas específicas de luchador");
                		gp.GameState = gp.PlayState;
                		//gp.playMusic(0);
                	}
                	if (gp.ui.commandNum == 1) {
                		System.out.println("hacer algunas cosas específicas de Mago");
                		gp.GameState = gp.PlayState;
                		//gp.playMusic(0);
                	}
                	if (gp.ui.commandNum == 2) {
                		System.out.println("hacer algunas cosas específicas de soldado");
                		gp.GameState = gp.PlayState;
                		//gp.playMusic(0);
                	}
                	if (gp.ui.commandNum == 3) {
                		gp.ui.titleScreenState = 0;
                	}
                    	
                }
        	
            }
     
        
        
    	//PLAY STATE
        if(gp.GameState == gp.PlayState) {
        	
            if (code == KeyEvent.VK_W) {
                upPressed = true;

            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
                
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
                
            }
            if (code == KeyEvent.VK_D) {
                rigthpressed = true;
                
            }
            if (code == KeyEvent.VK_P) {
                gp.GameState = gp.PauseState;
            
            }
            if (code == KeyEvent.VK_ENTER) {
            	enterPressed = true;
            
            }
             
                    //DEBUG
            if (code == KeyEvent.VK_T) {
                if(checkDrawTime == false){
                    checkDrawTime = true;
                    }
            else if ( checkDrawTime == true) {
                        checkDrawTime = false;
                    }
            }
        }
        
        //estado de pausa
        else if(gp.GameState == gp.PauseState) {
            if (code == KeyEvent.VK_P) {
                gp.GameState = gp.PlayState;
            
            }
        }
        
        //estado del dialogo
        else if(gp.GameState == gp.dialogueState) {
        	if(code == KeyEvent.VK_ENTER) {
        		gp.GameState = gp.PlayState;
        	}
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        
        if (code == KeyEvent.VK_W) {
            upPressed = false;

        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
            
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
            
        }
        if (code == KeyEvent.VK_D) {
            rigthpressed = false;
            
        }

    }    
}

