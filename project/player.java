import java.util.ArrayList;

public class Player extends Body{
    private static int NORMAL_SPEED = 10;

    private String name;
    private double speed;
    
    //Constructors
    public Player(int xLoc, int yLoc, int standardWidth, int standardHeight, String filePath, String name) {
        super(xLoc, yLoc, standardWidth, standardHeight, filePath);
        this.name = name;
        speed = 1;
    }

    public Player(int xLoc, int yLoc, int standardWidth, int standardHeight, String filePath) {
        super(xLoc, yLoc, standardWidth, standardHeight, filePath);
        this.name = "Guest";
        speed = 1;
    }

    public Player(String filePath, String name) {
        super(filePath);
        this.name = name;
        speed = 1;
    }

    public Player(String filePath) {
        super(filePath);
        this.name = "Guest";
        speed = 1;
    }

    public Player() {
        super();
        this.name = "Guest";
        speed = 1;
    }

    //Getters and Setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    //Moves player certain units in ceratain directions
    public boolean moveRight() {
        return updateLocation((int) (NORMAL_SPEED * speed), 0);
    }

    public boolean moveLeft() {
        return updateLocation(0 - (int) (NORMAL_SPEED * speed), 0);
    }

    public boolean moveUp() {
        return updateLocation(0, 0 - (int) (NORMAL_SPEED * speed));
    }

    public boolean moveDown() {
        return updateLocation(0, (int) (NORMAL_SPEED * speed));
    }

    public boolean isClearable() {
        return true;
    }

    //Checks if hitboxes overlap with any obstacles, and there is one, reports there is a collision
    public boolean checkCollisions(ArrayList<Obstacle> obstacles) {
        for(Obstacle obstacle: obstacles) {
            if(collidesWith(obstacle)) {
                return true;
            }
        }

        return false;
    }
}
