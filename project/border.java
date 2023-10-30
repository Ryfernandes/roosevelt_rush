public class Border extends Body {
    private boolean upper;

    //Constructors
    public Border(int xLoc, int yLoc, int standardWidth, int standardHeight, String filePath, boolean upper) {
        super(xLoc, yLoc, standardWidth, standardHeight, filePath);
        this.upper = upper;
    }

    public Border(String filePath, boolean upper) {
        super(filePath);
        this.upper = upper;
    }

    public Border(boolean upper) {
        super();
        this.upper = upper;
    }

    //Changes border size and orientation as the window resizes
    public void update(int[] infoList, double multiplier) {
        super.setHeight((int) ((infoList[2] + 30) / multiplier));
        
        if(upper == true) {
            int newY = (int) ((0 - infoList[2] - 30) / (2 * multiplier));
            super.setLocation(getX(), newY);
        } else {
            int newY = (int) ((infoList[2] + 30 + 2 * (GraphicsManager.STANDARD_WINDOW_HEIGHT) * multiplier) / (2 * multiplier));
            super.setLocation(getX(), newY);
        }
    }

    public boolean isClearable() {
        return false;
    }

    public boolean fixLocation() {
        return false;
    }
}

