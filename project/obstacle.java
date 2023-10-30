import java.util.ArrayList;

public class Obstacle extends Body {
    private String type;
    private static String[] TYPE_LIST = {"Wall"};
    
    //We were going to use type more extensively, but shifted the focus of our game, making it no longer necessary

    //Constructors
    public Obstacle(int xLoc, int yLoc, int standardWidth, int standardHeight, String filePath, String type) {
        super(xLoc, yLoc, standardWidth, standardHeight, filePath);
        
        if(isValidType(type)) {
            this.type = type;
        } else {
            this.type = TYPE_LIST[0];
        }
    }

    public Obstacle(String filePath, String type) {
        super(filePath);

        if(isValidType(type)) {
            this.type = type;
        } else {
            this.type = TYPE_LIST[0];
        }
    }

    public Obstacle() {
        super();
        type = TYPE_LIST[0];
    }

    public boolean isValidType(String type) {
        for(String test: TYPE_LIST) {
            if(test.equals(type)) {
                return true;
            }
        }

        return false;
    }

    public boolean fixLocation() {
        if(super.getX() + super.getWidth()/2 > GraphicsManager.STANDARD_WINDOW_WIDTH) {
            int newX = GraphicsManager.STANDARD_WINDOW_WIDTH - super.getWidth() / 2;
            super.setLocation(newX, super.getY());
            return true;
        } else if(super.getX() - super.getWidth()/2 < 0) {
            int newX = super.getWidth() / 2;
            super.setLocation(newX, super.getY());
            return true;
        }

        return false;
    }
    
    public void randomSpawn(ArrayList<Integer> lanes, int totalLanes) {
        int lane = (int) (Math.random() * lanes.size());
        int newX = GraphicsManager.STANDARD_WINDOW_WIDTH / totalLanes * lanes.get(lane) - GraphicsManager.STANDARD_WINDOW_WIDTH / totalLanes / 2;
        super.setLocation(newX, 0 - super.getHeight()/2 - 10);
    }

    public int occupiesLane(int lanes) {
        if(getY() <= getHeight()/2 + 10) {
            int lane = (int) (((2.0 * lanes * getX() + GraphicsManager.STANDARD_WINDOW_WIDTH) / (2.0 * GraphicsManager.STANDARD_WINDOW_WIDTH)) + 0.5);
            return lane;
        }

        return -1;
    }

    //Determines if obstacle is "in range"
    public boolean isShootable(int playerX, int playerY, int playerWidth, int shotDistance) {
        int upperX1 = getX() - getWidth()/2;
        int upperX2 = playerX - playerWidth/2;
        int lowerX1 = getX() + getWidth()/2;
        int lowerX2 = playerX + playerWidth/2;

        boolean xOverlap = upperX1 <= lowerX2 && lowerX1 >= upperX2;
        
        return xOverlap && ((playerY - getY() - getHeight()/2) < shotDistance) && ((playerY - getY() - getHeight()/2) > 0);
    }

    public boolean isDestroyable() {
        return super.getY() > GraphicsManager.STANDARD_WINDOW_HEIGHT + super.getHeight()/2;
    }

    public boolean isClearable() {
        return true;
    }

    public boolean isObstacle() {
        return true;
    }

    public void update(int[] infoList, double multiplier) {
        int yAdjust = GraphicsManager.STANDARD_WINDOW_HEIGHT/150;
        if(yAdjust == 0) {
            yAdjust = 1;
        }
        super.updateLocation(0, yAdjust);
    }
}
