import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Body {
    private int xLoc;
    private int yLoc;
    private int standardWidth;
    private int standardHeight;
    private String filePath;
    private BufferedImage image;

    //Constructors
    public Body(int xLoc, int yLoc, int standardWidth, int standardHeight, String filePath) {        
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        
        if(standardWidth > GraphicsManager.STANDARD_WINDOW_WIDTH || standardWidth <= 0) {
            this.standardWidth = 10;
        } else {
            this.standardWidth = standardWidth;
        }

        if(standardHeight > GraphicsManager.STANDARD_WINDOW_HEIGHT || standardHeight <= 0) {
            this.standardHeight = 10;
        } else {
            this.standardHeight = standardHeight;
        }

        this.filePath = filePath;

        changeImage(this.filePath);
        fixLocation();
    }

    public Body(String filePath) {
        xLoc = 0;
        yLoc = 0;
        standardWidth = 10;
        standardHeight = 10;

        this.filePath = filePath;

        changeImage(this.filePath);
        fixLocation();
    }

    public Body() {
        xLoc = 0;
        yLoc = 0;
        standardWidth = 10;
        standardHeight = 10;
        filePath = "";

        changeImage(this.filePath);
        fixLocation();
    }

    public int getX() {
        return xLoc;
    }

    public int getY() {
        return yLoc;
    }

    public int getWidth() {
        return standardWidth;
    }

    public int getHeight() {
        return standardHeight;
    }

    public void setHeight(int newHeight) {
        standardHeight = newHeight;
    }

    public void setWidth(int newWidth) {
        standardWidth = newWidth;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean updateLocation(int x, int y) {
        xLoc += x;
        yLoc += y;

        return fixLocation();
    }

    public boolean setLocation(int x, int y) {
        xLoc = x;
        yLoc = y;

        return fixLocation();
    }

    //Returns body to the bound if it goes out
    public boolean fixLocation() {
        boolean adjusted = false;
        
        if(xLoc + standardWidth/2 > GraphicsManager.STANDARD_WINDOW_WIDTH) {
            xLoc = GraphicsManager.STANDARD_WINDOW_WIDTH - standardWidth / 2;
            adjusted = true;
        } else if(xLoc - standardWidth/2 < 0) {
            xLoc = standardWidth / 2;
            adjusted = true;
        }

        if(yLoc + standardHeight/2 > GraphicsManager.STANDARD_WINDOW_HEIGHT) {
            yLoc = GraphicsManager.STANDARD_WINDOW_HEIGHT - standardHeight / 2;
            adjusted = true;
        } else if(yLoc - standardHeight/2 < 0) {
            yLoc = standardHeight / 2;
            adjusted = true;
        }

        return adjusted;
    }

    public boolean collidesWith(Body other) {
        boolean xOverlap;
        boolean yOverlap;

        int upperX1 = xLoc - standardWidth/2;
        int upperX2 = other.xLoc - other.standardWidth/2;
        int lowerX1 = xLoc + standardWidth/2;;
        int lowerX2 = other.xLoc + other.standardWidth/2;

        xOverlap = upperX1 <= lowerX2 && lowerX1 >= upperX2;

        int upperY1 = yLoc - standardHeight/2;
        int upperY2 = other.yLoc - other.standardHeight/2;
        int lowerY1 = yLoc + standardHeight/2;;
        int lowerY2 = other.yLoc + other.standardHeight/2;

        yOverlap = upperY1 <= lowerY2 && lowerY1 >= upperY2;

        return xOverlap && yOverlap;
    }

    public boolean inCorner() {
        return ((xLoc + standardWidth/2 == GraphicsManager.STANDARD_WINDOW_WIDTH || xLoc - standardWidth/2 == 0) && (yLoc + standardHeight/2 == GraphicsManager.STANDARD_WINDOW_HEIGHT || yLoc - standardHeight/2 == 0));
    }

    public boolean isDestroyable() {
        return false;
    }

    public void update(int[] infoList, double multiplier) {}

    public boolean isClearable() {
        return false;
    }

    public boolean isShootable(int playerX, int playerY, int playerWidth, int shotDistance) {
        return false;
    }

    public boolean isBullet() {
        return false;
    }

    public boolean isObstacle() {
        return false;
    }

    public void changeImage(String filePath) {
        try {
            File imageFile = new File(filePath);
            image = ImageIO.read(imageFile);
        } catch(IOException error) {
            System.out.println("Error changing image: " + error.getMessage());
        }
    }
}
