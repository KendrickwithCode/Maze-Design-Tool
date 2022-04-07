public abstract class Block {

    protected MazeWall wallNorth;
    protected MazeWall wallSouth;
    protected MazeWall wallEast;
    protected MazeWall wallWest;
    protected final int[] location;
    protected final int blockIndex;

    /**
     * Constructs and initialises new Block
     * @param location int[x,y] x,y location of block on maze
     */
    public Block(int[] location, int blockIndex)
    {
        this.location = location;
        this.blockIndex = blockIndex;
        wallEast = new MazeWall();
        wallSouth = new MazeWall();
    }

    /**
     * Returns location of block in maze
     * @return blocks location int[x,y]
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

    /**
     * Sets Wall object of north facing wall
     * @param wallNorth north facing wall
     */
    public void setWallNorth(MazeWall wallNorth) {
        this.wallNorth = wallNorth;
    }

    /**
     * Sets Wall object of south facing wall
     * @param wallSouth south facing wall
     */
    public void setWallSouth(MazeWall wallSouth) {
        this.wallSouth = wallSouth;
    }

    /**
     * Sets Wall object of east facing wall
     * @param wallEast east facing wall
     */
    public void setWallEast(MazeWall wallEast) {
        this.wallEast = wallEast;
    }

    /**
     * Sets Wall object of west facing wall
     * @param wallWest west facing wall
     */
    public void setWallWest(MazeWall wallWest) {
        this.wallWest = wallWest;
    }

    /**
     * Returns the blocks Arraylist index
     * @return blocks Arraylist index
     */
    public int getBlockIndex() {
        return blockIndex;
    }
}
