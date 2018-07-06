package cs1302.fxgame;

import com.michaelcotterell.game.Game;
import com.michaelcotterell.game.GameTime;
import javafx.scene.input.KeyCode;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.application.Platform;
import java.io.File;

/**
     * Invader Class extends Game
     * Same function as Test Game
     * 
     */
public class Invader extends Game {

    Group g = new Group();//group node that will contain alien nodes
    Group bar = new Group();//group node that will contain barrier nodes
    boolean isRunning = true;//used to determine if game is running
    boolean goRight = true;
    boolean isFired = false;//determines if the cannon was fired
    Image shooter;
    ImageView ivS = new ImageView();
    int score = 0;//keeps track of player's score
    Rectangle r = new Rectangle();
    
    
    
    // rectangle to hold the background
    private Rectangle bg = new Rectangle(0, 0, 720, 480) {{ 
         setFill(Color.BLACK); 
    }};
    
    /*private Rectangle shot = new Rectangle(){{
        setTranslateX(0);
        setTranslateY(450);
        setWidth(5);
        setHeight(5);
        setFill(Color.WHITE);
    }};*/
    

    // some text to display the time
    private Text text = new Text() {{
         setTranslateX(500);
         setTranslateY(25);
         setFill(Color.WHITE);
    }};
    
    private Text up = new Text() {{
          setTranslateX(400);
          setTranslateY(50);
          setFill(Color.WHITE);
    }};
    
    private Text gameText = new Text() {{
        setTranslateX(320);
        setTranslateY(200);
        setFill(Color.WHITE);
    }};
      
    
    /**
     * 
     * Method called when isRunning = false
     * Means that the player has lost and the game is over
     */
    public void gameOver(){
        getSceneNodes().getChildren().removeAll(text,g);
        gameText.setText("Game Over");
        getSceneNodes().getChildren().addAll(gameText);
        System.out.println("Game Over");
    }
    
    /**
     * Creates a "laser" that the cannon shoots
     * takes in the x position of the cannon as a parameter
     */
    public void shoot(double position){
        
        r.setTranslateY(450);
        r.setTranslateX(position);
        r.setWidth(5);
        r.setHeight(5);
        r.setFill(Color.WHITE);
        isFired=true; 
        getSceneNodes().getChildren().add(r);
        
    
    }
    /**
     * Constructs a new test game.
     * Initializes the starting objects for the game
     * F.E. the cannon, barriers, aliens
     */
    public void initG(){

        shooter = new Image("https://authordougmccoy.files.wordpress.com/2013/05/space-invaders.jpg");
        ivS.setImage(shooter);
        ivS.setFitWidth(50);
        ivS.setFitHeight(50);
        ivS.setTranslateX(10);
        ivS.setTranslateY(435);
        
        
      for(int x = 0; x<8; x++)
      {
        Rectangle b = new Rectangle();
        b.setWidth(30);
        b.setHeight(20);
        b.setTranslateX((x*70)+70);
        b.setTranslateY(400);
        b.setFill(Color.YELLOWGREEN);
        bar.getChildren().add(b);
      
      }
        
      for(int k = 0; k<5; k++)
      {
          for (int i = 0; i < 11; i++) {
          Image image1 = new Image("http://sandbox.yoyogames.com/extras/image/name/san2/248/430248/original/Alien2.jpg");
          ImageView iv1 = new ImageView();
          iv1.setImage(image1);
          iv1.setFitWidth(40);
          iv1.setFitHeight(40);
          iv1.setTranslateX((i*30)+25);
          iv1.setTranslateY((k*30)+25);
          g.getChildren().add(iv1);
        }
      }
      
    }
    
    
    /**
     * Constructs a new Invader game
     * @param stage the primary stage
     */
    public Invader(Stage stage) {
      super(stage, "Invader", 60, 640, 480);
      initG();
      getSceneNodes().getChildren().addAll(bg, ivS, g, bar, text, up);
    } // TestGame
    


    @Override
    public void update(Game game, GameTime gameTime) {
        text.setText("Score: " + Integer.toString(score));
        up.setText("Up Arrow = Shoot" + "\n" + "Right/Left Arrows = Move Right/Left");
        /*Rectangle shot = new Rectangle();
        //shot.setTranslateY(450);
        shot.setWidth(5);
        shot.setHeight(5);
        shot.setFill(Color.WHITE);*/

        if (game.getKeyManager().isKeyPressed(KeyCode.UP)) {
          shoot(ivS.getTranslateX());
          //r.setTranslateX();
          //getSceneNodes().getChildren().add(r);
         
        }
       
        if (game.getKeyManager().isKeyPressed(KeyCode.DOWN)) text.setTranslateY(ivS.getTranslateY() + 1);
        if (game.getKeyManager().isKeyPressed(KeyCode.LEFT)) ivS.setTranslateX(ivS.getTranslateX() - 2);
        if (game.getKeyManager().isKeyPressed(KeyCode.RIGHT)){
         ivS.setTranslateX(ivS.getTranslateX() + 2);
         getSceneNodes().getChildren().remove(up);
        }
        if(isRunning == true && isFired == true ){
         
          r.setTranslateY(r.getTranslateY() - 2);
        }
        if(isRunning == true && goRight == true){ 
          g.setTranslateX(g.getTranslateX() + 1);
        }
        if(g.getTranslateX() >= 300){
         goRight = false;
         g.setTranslateY(g.getTranslateY() + 10);
        }
        if(goRight == false && isRunning == true){
         g.setTranslateX(g.getTranslateX() - 1);
        }
        if(g.getTranslateX() <= 0){
          goRight = true;
          g.setTranslateY(g.getTranslateY() + 10);
        }
        if(g.getTranslateY() >= 300){
          isRunning = false;
          gameOver();
        }
        for(int u = 0; u<55; u++){
          if(r.getBoundsInParent().intersects(g.getChildren().get(u).getBoundsInParent())){
            getSceneNodes().getChildren().remove(g.getChildren().get(u));
            g.getChildren().get(u).setVisible(false);
            score += 10;
            getSceneNodes().getChildren().remove(r);
          }
        }
        for(int a = 0; a<8; a++){
          if(r.getBoundsInParent().intersects(bar.getChildren().get(a).getBoundsInParent())){
            bar.getChildren().get(a).setVisible(false);
            getSceneNodes().getChildren().removeAll(r,bar.getChildren().get(a));
            
          }
        }
          
        
    } // update
    
    

} // Invader

