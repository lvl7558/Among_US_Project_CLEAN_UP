import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
/**
 * MovableBackground - a class that is meant to serve as the moveable background for the main player
 * @author Luka Lasic
 * @since 20-4-2023
 */
public class MovableBackground extends Pane {
   // strating postion of racer in pxiels based on top left corner
   // and correlates to the window
   private int backgroundPosX = -1930;
   private int backgroundPosY = -160;
   // starting postion is based on window of 800 by 500
   private final int STARTING_X_ON_SCREEN = 400;
   private final int STARTING_Y_ON_SCREEN = 250;
   private Collison collisonDetection;
   private int playerPosistionX = 0;
   private int playerPosistionY = 0;


   private int playerX = STARTING_X_ON_SCREEN;
   private int playerY = STARTING_Y_ON_SCREEN;
   private AmongUsSettings settings = new AmongUsSettings();
   private int widthOfBackground = 0;
   private int heightOfBackground = 0;
   private int speed = 10;//(int)settings.getPlayerSpeed();
   private Collison collison = new Collison();
   private ImageView aPicView = null;
   private ImageView aPicView2 = null;
   private boolean isMaster;
   private Image backgroundCollison;
   private Image backgroundImage;
   private XML_STUFF xmlWorker = new XML_STUFF();

   /*
    * private boolean moveDown;
    * private boolean moveUp;
    * private boolean moveLeft;
    * private boolean moveRight;
    */
   private TrackMovement tm = new TrackMovement();

   /**
    * setting the players movable background based of given string 
    * @param MASK_IMAGE
    * @param BACKGROUND_IMAGE
    */
   public MovableBackground(String MASK_IMAGE, String BACKGROUND_IMAGE) {
      xmlWorker.readXML();
      
      speed = (int) xmlWorker.player.getPlayerSpeed();

      backgroundCollison = new Image(MASK_IMAGE);
      backgroundImage = new Image(BACKGROUND_IMAGE);

      aPicView = new ImageView(MASK_IMAGE);
      aPicView2 = new ImageView(BACKGROUND_IMAGE);
      Image sizer = new Image(MASK_IMAGE);
      // SVGPath maskBackground = new SVGPath();
      // maskBackground.setContent("mask2.svg");
      widthOfBackground = (int) sizer.getWidth();
      heightOfBackground = (int) sizer.getHeight();

      this.getChildren().addAll(aPicView, aPicView2);

   }

   
   /** 
    * update a method to move the background postion while also checking for collsion and returing the point where the player should move
    * @param moveDown
    * @param moveUp
    * @param moveLeft
    * @param moveRight
    * @return Point2D
    */
   public Point2D update(boolean moveDown, boolean moveUp, boolean moveLeft, boolean moveRight) {
      int oldBackgroundPosX = backgroundPosX;
      int oldBackgroundPosY = backgroundPosY;
      int oldPlayerPosistionX = STARTING_X_ON_SCREEN - backgroundPosX;
      int oldPlayerPosistionY = STARTING_Y_ON_SCREEN - backgroundPosY;

      if (moveDown) {
         backgroundPosY -= speed;
      } else if (moveUp) {
         backgroundPosY += speed;
      } else if (moveLeft) {
         backgroundPosX += speed;
      } else if (moveRight) {
         backgroundPosX -= speed;
      }

      Color color = null;

      // get pixel
      playerPosistionX = STARTING_X_ON_SCREEN - backgroundPosX;
      playerPosistionY = STARTING_Y_ON_SCREEN - backgroundPosY;
      // cheking to see if background posision is negative or not
      // to make sure the color reader does not create an error
      // due to putting in a negative number
      // also to make sure the color checker is correct when player postion is postive

      //getting if the posisiotin is correct 
      Collison posistion = collison.checkingCollison(this, backgroundCollison,
            playerPosistionX,
            playerPosistionY, backgroundPosX, backgroundPosY);


      //if there is any collsion make sure to adjust player x and y 
      if (posistion.isCollided()) {
         int adjustX = (int) posistion.getChangePoint().getX();
         int adjustY = (int) posistion.getChangePoint().getY();
         if (adjustX != 0 || adjustY != 0) {
            //System.out.println("AdjustX: " + adjustX);
            //System.out.println("AdjustY  " + adjustY);

            backgroundPosX = backgroundPosX - adjustX;
            backgroundPosY = backgroundPosY - adjustY;
         } else {
            backgroundPosX = oldBackgroundPosX;
            backgroundPosY = oldBackgroundPosY;
            playerPosistionX = STARTING_X_ON_SCREEN - oldBackgroundPosX;
            playerPosistionY = STARTING_Y_ON_SCREEN - oldBackgroundPosY;
         }
      }


      //moving the two background images
      aPicView.setTranslateX(backgroundPosX);
      aPicView.setTranslateY(backgroundPosY);
      aPicView2.setTranslateX(backgroundPosX);
      aPicView2.setTranslateY(backgroundPosY);

      // players coordiante based on where it is on the background
      // only top left corenr for now
      //System.out.println("Background image X Cord " + backgroundPosX);
      //System.out.println("Background image Y Cord " + backgroundPosY);

      //System.out.println("Players X Cord " + playerPosistionX);
      //System.out.println("PLayers Y Cord " + playerPosistionY);
      //color = backgroundCollison.getPixelReader().getColor(playerPosistionX, playerPosistionY);
      //System.out.println("r: " + color.getRed() + ",g:" + color.getGreen() + ",b:" + color.getBlue());

      //Returning the players background postion 
      return new Point2D(backgroundPosX, backgroundPosY);

      /*
       * if(masterCrewmate.getaPicView().getBoundsInParent().intersects(
       * moveablebackground.getAPicView().getBoundsInParent())){
       * System.out.println("Collison");
       * }else{
       * System.out.println("No Collison");
       * 
       * }
       */

   }


   
   /** 
    * @param backgroundPosX
    */
   public void setBackgroundPosX(int backgroundPosX) {
       this.backgroundPosX = backgroundPosX;
   }
   
   /** 
    * @param backgroundPosY
    */
   public void setBackgroundPosY(int backgroundPosY) {
       this.backgroundPosY = backgroundPosY;
   }
   
   /** 
    * @return int
    */
   public int getBackgroundPosX() {
       return backgroundPosX;
   }
   
   /** 
    * @return int
    */
   public int getBackgroundPosY() {
       return backgroundPosY;
   }

   
   /** 
    * @return ImageView
    */
   public ImageView getAPicView() {
      return aPicView;
   }

   
   /** 
    * @param speed
    */
   public void setSpeed(int speed) {
      this.speed = speed;
   }

   
   /** 
    * @return int
    */
   public int getSpeed() {
      return speed;
   }

   
   /** 
    * @return int
    */
   public int getPlayerPosistionX() {
      return playerPosistionX;
   }

   
   /** 
    * @return int
    */
   public int getPlayerPosistionY() {
      return playerPosistionY;
   }

   
   /** 
    * @param playerPosistionX
    */
   public void setPlayerPosistionX(int playerPosistionX) {
      this.playerPosistionX = playerPosistionX;
   }

   
   /** 
    * @param playerPosistionY
    */
   public void setPlayerPosistionY(int playerPosistionY) {
      this.playerPosistionY = playerPosistionY;
   }

   
   /** 
    * @return int
    */
   public int getPlayerX() {
      return playerX;
   }

   
   /** 
    * @param playerX
    */
   public void setPlayerX(int playerX) {
      this.playerX = playerX;
   }

   
   /** 
    * @return int
    */
   public int getPlayerY() {
      return playerY;
   }

   
   /** 
    * @param playerY
    */
   public void setPlayerY(int playerY) {
      this.playerY = playerY;
   }

   
}
