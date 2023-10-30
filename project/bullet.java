public class Bullet extends Body {
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    
    public Bullet(int xLoc, int yLoc, int standardWidth, int standardHeight, String filePath, int xMin, int yMin, int xMax, int yMax) {
        super(xLoc, yLoc, standardWidth, standardHeight, filePath);
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;

        randomSpawn();
    }

    public Bullet(String filePath, int standardWidth, int standardHeight, int xMin, int yMin, int xMax, int yMax) {
        super(filePath);
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;

        super.setWidth(standardWidth);
        super.setHeight(standardHeight);
        
        randomSpawn();
    }

    public Bullet(int standardWidth, int standardHeight, int xMin, int yMin, int xMax, int yMax) {
        super();
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;

        super.setWidth(standardWidth);
        super.setHeight(standardHeight);
        
        randomSpawn();
    }

    public boolean isClearable() {
        return true;
    }

    public boolean isBullet() {
        return true;
    }

    public void randomSpawn() {
        int xVal = (int) (Math.random() * (xMax - xMin) + xMin);
        int yVal = (int) (Math.random() * (yMax - yMin) + yMin);

        super.setLocation(xVal, yVal);
    }
}
