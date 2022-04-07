public abstract class Block {

    private MazeWall wallNorth;
    private MazeWall wallSouth;
    private MazeWall wallEast;
    private MazeWall wallWest;
    private int[] location;
    private int blockIndex;

    /**
     * Constructs and initialises new Block
     * @param location location of block on maze
     */
    public Block(int[] location, int blockIndex)
    {
        this.location = location;
        this.blockIndex = blockIndex;
        wallEast = new MazeWall();
        wallSouth = new MazeWall();
        wallNorth = new MazeWall();
        wallWest = new MazeWall();
    }

    /**
     * Returns location of block in maze
     * @return location
     */
    public int[] getLocation(){
        return location;
    }

    /**
     * Returns Wall object of north facing wall
     * @return north Wall object
     */
    public MazeWall getWallNorth() {
        return wallNorth;
    }

    /**
     * Returns Wall object of south facing wall
     * @return south Wall object
     */
    public MazeWall getWallSouth() {
        return wallSouth;
    }

    /**
     * Returns Wall object of east facing wall
     * @return east Wall object
     */
    public MazeWall getWallEast() {
        return wallEast;
    }

    /**
     * Returns Wall object of west facing wall
     * @return west Wall object
     */
    public MazeWall getWallWest() {
        return wallWest;
    }

    public void setWallNorth(MazeWall wallNorth) {
        this.wallNorth = wallNorth;
    }

    public void setWallSouth(MazeWall wallSouth) {
        this.wallSouth = wallSouth;
    }

    public void setWallEast(MazeWall wallEast) {
        this.wallEast = wallEast;
    }

    public void setWallWest(MazeWall wallWest) {
        this.wallWest = wallWest;
    }

    public int getBlockIndex() {
        return blockIndex;
    }
}
