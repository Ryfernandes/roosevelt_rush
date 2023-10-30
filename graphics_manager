//Ryan Fernandes and Tim Ecklekamp
//June 8, 2023
//This project is a runner-like game in which you play as Theodore Roosevelt
//and have to avoid oncoming bulls and mooses. You have a gun that you can fire
//if you pick up bullets that may come in handy if you are trapped behind a wall of obstacles.


//Imports

import java.util.ArrayList;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JFrame;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

public class GraphicsManager extends Canvas{
    //Instance Variables
    

    //Holds bodies and obstacles to be displayed/painted
    private ArrayList<Body> bodies;
    private ArrayList<Obstacle> obstacles;

    private Bullet bullet;

    //Holds data for the back-end standard frame size
    public static int STANDARD_WINDOW_WIDTH = 1000;
    public static int STANDARD_WINDOW_HEIGHT = 600;

    //Holds information about game environment
    private int currentHeight;
    private int currentWidth;
    private int yBuffer;
    private int xBuffer;
    private double multiplier;

    //Holds information about keys pressed
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean space;
    private boolean keyS;
    private boolean sDown;

    //Holds gameplay and scoring information
    private int lanes;
    private int score;
    private boolean play;

    private int bullets = 0;

    //Defines boundaries for bullets to spawn in
    private int bulletXMin = 200;
    private int bulletYMin = 150;
    private int bulletXMax = 800;
    private int bulletYMax = 450;

    private int previousScore = 0;
    private int highScore = 0;

    private int shotDistance;

    //Stores file paths for different images
    public final String BULL_SKIN = "IMAGE PATH";
    public final String MOOSE_SKIN = "IMAGE PATH";
    public final String BULLET_SKIN = "IMAGE PATH";

    public static void main(String args[]) {
        //Creates the frame for the game, formats it, and adds the canvas
        JFrame frame = new JFrame("Roosevelt Rush!"); //Create window
        GraphicsManager canvas = new GraphicsManager(14); //Make new canvas
        canvas.setSize(GraphicsManager.STANDARD_WINDOW_WIDTH, GraphicsManager.STANDARD_WINDOW_HEIGHT); //Set canvas size
        canvas.setBackground(Color.gray);
        frame.add(canvas); //Add canvas to frame
        frame.pack(); //Resize frame to fit canvas
        frame.setVisible(true); //Make window visible
        frame.addKeyListener(new KeyDetector(canvas));

        //Creates the background and top/bottom boundaries
        Body background = new Body(GraphicsManager.STANDARD_WINDOW_WIDTH/2, GraphicsManager.STANDARD_WINDOW_HEIGHT/2, GraphicsManager.STANDARD_WINDOW_WIDTH, GraphicsManager.STANDARD_WINDOW_HEIGHT, "IMAGE PATH");
        canvas.addBody(background);
        Border bottom = new Border(GraphicsManager.STANDARD_WINDOW_WIDTH/2, GraphicsManager.STANDARD_WINDOW_HEIGHT, STANDARD_WINDOW_WIDTH, 100, "IMAGE PATH", false);
        canvas.addBody(bottom);
        Border top = new Border(GraphicsManager.STANDARD_WINDOW_WIDTH/2, GraphicsManager.STANDARD_WINDOW_HEIGHT, STANDARD_WINDOW_WIDTH, 100, "IMAGE PATH", true);
        canvas.addBody(top);
        
        canvas.setScore(0);

        //Reads from high score file in order to set it to the high score if there is one or 0 if there isn't
        String directory = System.getProperty("user.home");
        String fileName = "RooseveltRushScores.txt";
        String completePath = directory + File.separator + fileName;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(completePath))) {
            String line = bufferedReader.readLine();
            canvas.setHighScore(Integer.parseInt(line));
        } catch (FileNotFoundException e) {
            canvas.setHighScore(0);
        } catch (IOException e) {
            System.out.println("IO Exception");
        }


        //Game loop until they close the window
        while(true) {
            //Waits until space is pressed to start
            if(canvas.getSpace()) {
                //Creates player, bullets, and resets variables to default
                canvas.setPlay(true);

                int delay = 150;
                int count = 0;
                int bulletDelay = 300;
                int bulletCount = 0;
                boolean bulletInPlay = false;

                canvas.setBullets(0);

                canvas.setScore(0);

                Player myPlayer = new Player(500, 500, 45, 45, "IMAGE PATH", "Ryan");
                canvas.addBody(myPlayer);

                myPlayer.setSpeed(0.4);

                //Plays a round of the game
                while(canvas.getPlay()) {
                    //Moves obstacles down
                    canvas.updateBodies();
        
                    //Moves player according to arrows
                    if(canvas.getRight()) {
                        myPlayer.moveRight();
                    }
        
                    if(canvas.getLeft()) {
                        myPlayer.moveLeft();
                    }
        
                    if(canvas.getUp()) {
                        myPlayer.moveUp();
                    }
        
                    if(canvas.getDown()) {
                        myPlayer.moveDown();
                    }
        
                    //Adjusts speed to be a fraction of normal if going diagonally
                    if((canvas.getLeft() || canvas.getRight()) && (canvas.getUp() || canvas.getDown())) {
                        myPlayer.setSpeed(0.4 / Math.sqrt(2));
                    } else {
                        myPlayer.setSpeed(0.4);
                    }

                    //Shoots gun if there are bullets and s is pressed. Registers if it his anything
                    if(canvas.getS()) {
                        canvas.setS(false);
                        if(canvas.getBullets() > 0) {
                            canvas.registerShot(myPlayer.getX(), myPlayer.getY(), myPlayer.getWidth());
                            canvas.setBullets(canvas.getBullets() - 1);
                        }
                    }
        
                    //Checks if the player is colliding with obstacles, and ends game if so
                    if(myPlayer.checkCollisions(canvas.getObstacles())) {
                        canvas.setPlay(false);
                        canvas.endPlay();

                        //Writes the current high score to save file
                        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(completePath))) {
                            String content = "" + canvas.getHighScore();
                            bufferedWriter.write(content);
                        } catch (IOException e) {
                            System.out.println("IO Exception");
                        }
                    }
        
                    //Destroys obstacles past end of screen
                    if(canvas.destroyBodies()) {
                        canvas.setScore(canvas.getScore() + 1);
                    }
                    
                    //Spawns in new obstacles after a set amount of time
                    if(count >= delay) {
                        String skin = canvas.MOOSE_SKIN;

                        //Randomizes skin
                        if(Math.random() > 0.5) {
                            skin = canvas.BULL_SKIN;
                        }

                        //Finds available lanes to spawn in and goes to a random one
                        Obstacle temp = new Obstacle(100, 100, 49, 112, skin, "Wall");
                        ArrayList<Integer> freeLanes = canvas.getLanes();
                        if(freeLanes.size() > 0) {
                            temp.randomSpawn(freeLanes, canvas.numLanes());
                            if(canvas.getBodies().get(1).isBullet()) {
                                canvas.addObstacle(temp, 2); 
                            } else {
                                canvas.addObstacle(temp, 1);
                            }
                        }
        
                        count = 0;
                        delay -= 10;
        
                        if(delay < 10) {
                            delay = 10;
                        }
                    }

                    //Puts a new bullet in play a certain amount of time after one is picked up
                    if(bulletCount > bulletDelay && !bulletInPlay) {
                        canvas.addBullet();
                        bulletDelay += 10;
                        bulletInPlay = true;
                    }

                    //Picks up bullet, doesn't render it, and increases bullet count
                    if(canvas.getBullet() != null && myPlayer.collidesWith(canvas.getBullet())) {
                        canvas.setBullets(canvas.getBullets() + 1);
                        canvas.removeBullet();
                        bulletInPlay = false;
                        bulletCount = 0;
                    }
                    
                    canvas.repaint();
                    
                    try {
                        Thread.sleep(10);
                    } catch(InterruptedException error) {
                        System.out.println("Interrupted: " + error);
                    }
        
                    count++;
                    bulletCount++;
                }
            }

            canvas.clear();
            canvas.updateBodies();
            canvas.repaint();
        }
    }

    //Constructor
    public GraphicsManager(int lanes) {
        bodies = new ArrayList<Body>();
        obstacles = new ArrayList<Obstacle>();
        this.lanes = lanes;
        shotDistance = 120;
        repaint();
    }

    //Basic functions to move between class and main (getters and setters)
    public void addBody(Body newBody) {
        bodies.add(newBody);
    }

    public void addObstacle(Obstacle newBody) {
        obstacles.add(newBody);
        bodies.add(newBody);
    }

    public void addObstacle(Obstacle newBody, int index) {
        obstacles.add(newBody);
        addBody(newBody, index);
    }

    public void addBody(Body newBody, int index) {
        bodies.add(index, newBody);
    }

    //Updates objects in bodies list one by one
    public void updateBodies() {
        int[] infoList = {currentHeight, currentWidth, yBuffer, xBuffer};

        for(int i = 0; i < bodies.size(); i++) {
            bodies.get(i).update(infoList, multiplier);
        }
    }

    //Destroys destroyable bodies in objects list one by one (obstacle past end)
    public boolean destroyBodies() {
        boolean respawnNeeded = false;

        for(int i = 0; i < bodies.size(); i++) {
            if(bodies.get(i).isDestroyable()) {
                bodies.remove(i);
                respawnNeeded = true;
                i--;
            }
        }

        return respawnNeeded;
    }


    public void registerShot(int playerX, int playerY, int playerWidth) {
        //Determines if shot is a hit, and removes the given animal if it is

        for(int i = 0; i < obstacles.size(); i++) {
            if(obstacles.get(i).isShootable(playerX, playerY, playerWidth, shotDistance)) {
                obstacles.remove(i);
                i--;
            }
        }
        for(int i = 0; i < bodies.size(); i++) {
            if(bodies.get(i).isShootable(playerX, playerY, playerWidth, shotDistance)) {
                bodies.remove(i);
                i--;
            }
        }
    }

    //Clears objeccts list for home screen
    public void clear() {
        for(int i = 0; i < bodies.size(); i++) {
            if(bodies.get(i).isClearable()) {
                bodies.remove(i);
                i--;
            }
        }
        for(int i = 0; i < obstacles.size(); i++) {
            if(obstacles.get(i).isClearable()) {
                obstacles.remove(i);
                i--;
            }
        }
    }

    //Interacts with keyDetector classto eaily indicate what keys are pressed/released
    public void registerKeyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == 37) {
            left = true;
        } else if(keyCode == 38) {
            up = true;
        } else if(keyCode == 39) {
            right = true;
        } else if(keyCode == 40) {
            down = true;
        } else if(keyCode == 32) {
            space = true;
        } else if (keyCode == 83 && !sDown) {
            keyS = true;
            sDown = true;
        }
    }

    public void registerKeyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == 37) {
            left = false;
        } else if(keyCode == 38) {
            up = false;
        } else if(keyCode == 39) {
            right = false;
        } else if(keyCode == 40) {
            down = false;
        } else if(keyCode == 32) {
            space = false;
        } else if(keyCode == 83) {
            keyS = false;
            sDown = false;
        }
    }

    //More getters and setters
    public boolean getRight() {
        return right;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getUp() {
        return up;
    }

    public boolean getDown() {
        return down;
    }

    public boolean getS() {
        return keyS;
    }

    public void setS(boolean value) {
        keyS = value;
    }

    public boolean getSpace() {
        return space;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHighScore(int score) {
        this.highScore = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public boolean getPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    //Returns the non-occupied lanes using an "occupiesLane()" function for obstacles
    public ArrayList<Integer> getLanes() {
        ArrayList<Integer> laneList = new ArrayList<Integer>();

        for(int i = 1; i <= lanes; i++) {
            laneList.add(i);
        }

        for(Obstacle obstacle : obstacles) {
            int remove = obstacle.occupiesLane(lanes);
            if(remove > 0) {
                for(int i = 0; i < laneList.size(); i++) {
                    if(laneList.get(i) == remove) {
                        laneList.remove(i);
                        break;
                    }
                }
            }
        }

        return laneList;
    }

    public int numLanes() {
        return lanes;
    }

    public void addBullet() {
        //Adds bullet to objects to be drawn
        bullet = new Bullet(BULLET_SKIN, 20, 20, bulletXMin, bulletYMin, bulletXMax, bulletYMax);
        addBody(bullet, 1);
    }

    public Bullet getBullet() {
        return bullet;
    }

    //Takes bullet out of list to be drawn
    public void removeBullet() {
        for(int i = 0; i < bodies.size(); i++) {
            if(bodies.get(i).isBullet()) {
                bodies.remove(i);
                i--;
            }
        }
        bullet = null;
    }

    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }

    //Ends game and updates high score
    public boolean endPlay() {
        previousScore = score;

        if(score > highScore) {
            highScore = score;
            return true;
        }

        return false;
    }

    //Takes all data from bodies and obstacles list in order to accurately draw the images to scale
    public void paint(Graphics g) {
        Dimension size = getSize();

        currentHeight = size.height;
        currentWidth = size.width;

        //Determines which direction to base multiplier on
        if(size.width >= size.height * GraphicsManager.STANDARD_WINDOW_WIDTH/GraphicsManager.STANDARD_WINDOW_HEIGHT) {
            multiplier = (double) size.height / GraphicsManager.STANDARD_WINDOW_HEIGHT;
        } else {
            multiplier = (double) size.width / GraphicsManager.STANDARD_WINDOW_WIDTH;
        }

        //X and Y of upper-right
        int originX = (int) (size.width - GraphicsManager.STANDARD_WINDOW_WIDTH * multiplier) / 2;
        int originY = (int) (size.height - GraphicsManager.STANDARD_WINDOW_HEIGHT * multiplier) / 2;;

        xBuffer = originX;
        yBuffer = originY;

        //Gets necessary data from each body and prints it to scale
        for(Body body: bodies) {
            BufferedImage image = body.getImage();

            int x = body.getX();
            int y = body.getY();
            int width = body.getWidth();
            int height = body.getHeight();

            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            int plotX = (int) (originX + multiplier * (x - width/2));
            int plotY = (int) (originY + multiplier * (y - height/2));

            g.drawImage(image, plotX, plotY, (int) (plotX + multiplier * width), (int) (plotY + multiplier * height), 0, 0, imgWidth, imgHeight, null);
        }

        g.setColor(Color.white);
        FontMetrics centerMetrics = g.getFontMetrics(new Font("Helvetica", Font.BOLD, (int) (80 * multiplier)));
        FontMetrics subtitleMetrics = g.getFontMetrics(new Font("Helvetica", Font.BOLD, (int) (30 * multiplier)));
        
        //Prints text for home screen and for the rounds
        if(play) {
            g.setFont(new Font ("Helvetica", Font.BOLD, (int) (30 * multiplier)));
            g.drawString("Score: " + score, xBuffer + (int) (10 * multiplier), yBuffer + (int) (35 * multiplier));
            g.setFont(new Font ("Helvetica", Font.BOLD, (int) (30 * multiplier)));
            g.drawString("Bullets: " + bullets, xBuffer + (int) (10 * multiplier), yBuffer + (int) ((45 + subtitleMetrics.getHeight()) * multiplier));
        } else {
            g.setFont(new Font ("Helvetica", Font.BOLD, (int) (30 * multiplier)));
            g.drawString("Previous Score: " + previousScore, xBuffer + (int) (10 * multiplier), yBuffer + (int) (35 * multiplier));
            g.setFont(new Font ("Helvetica", Font.BOLD, (int) (30 * multiplier)));
            g.drawString("High Score: " + highScore, xBuffer + (int) (10 * multiplier), yBuffer + (int) ((45 + subtitleMetrics.getHeight()) * multiplier));
            g.setFont(new Font ("Helvetica", Font.BOLD, (int) (80 * multiplier)));
            g.drawString("Roosevelt Rush", xBuffer + (int) ((GraphicsManager.STANDARD_WINDOW_WIDTH * multiplier - centerMetrics.stringWidth("Roosevelt Rush")) / 2), yBuffer + (int) ((GraphicsManager.STANDARD_WINDOW_HEIGHT * multiplier - centerMetrics.getHeight())/ 2) + centerMetrics.getAscent());
            g.setFont(new Font ("Helvetica", Font.BOLD, (int) (30 * multiplier)));
            g.drawString("Space to Play", xBuffer + (int) ((GraphicsManager.STANDARD_WINDOW_WIDTH * multiplier - subtitleMetrics.stringWidth("Space to Play")) / 2), yBuffer + (int) (((GraphicsManager.STANDARD_WINDOW_HEIGHT - subtitleMetrics.getHeight()/2 - 80) * multiplier) + subtitleMetrics.getAscent()));
            g.drawString("Arrows to Move, S to Shoot", xBuffer + (int) ((GraphicsManager.STANDARD_WINDOW_WIDTH * multiplier - subtitleMetrics.stringWidth("Arrows to Move, S to Shoot")) / 2), yBuffer + (int) (((GraphicsManager.STANDARD_WINDOW_HEIGHT - subtitleMetrics.getHeight()/2 - 60 + subtitleMetrics.getHeight()) * multiplier) + subtitleMetrics.getAscent()));
        }
    }
}
