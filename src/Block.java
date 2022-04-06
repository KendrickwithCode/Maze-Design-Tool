public class Block {

    private IWall wallNorth;
    private IWall wallSouth;
    private IWall wallEast;
    private IWall wallWest;
    private final int[] location;

    /**
     * Constructs and initialises new Block
     * @param location location of block on maze
     */
    public Block(int[] location){
        this.location = location;
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
    public IWall getWallNorth() {
        return wallNorth;
    }

    /**
     * Returns Wall object of south facing wall
     * @return south Wall object
     */
    public IWall getWallSouth() {
        return wallSouth;
    }

    /**
     * Returns Wall object of east facing wall
     * @return east Wall object
     */
    public IWall getWallEast() {
        return wallEast;
    }

    /**
     * Returns Wall object of west facing wall
     * @return west Wall object
     */
    public IWall getWallWest() {
        return wallWest;
    }


}
