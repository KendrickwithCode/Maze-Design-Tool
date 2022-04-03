public class Block {

    private String wallNorth;  //<<--- Change types to Wall class when created
    private String wallSouth;
    private String wallEast;
    private String wallWest;
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
    public String getWallNorth() {  //<<--- Change types to Wall class when created
        return wallNorth;
    }

    /**
     * Returns Wall object of south facing wall
     * @return south Wall object
     */
    public String getWallSouth() {  //<<--- Change types to Wall class when created
        return wallSouth;
    }

    /**
     * Returns Wall object of east facing wall
     * @return east Wall object
     */
    public String getWallEast() {  //<<--- Change types to Wall class when created
        return wallEast;
    }

    /**
     * Returns Wall object of west facing wall
     * @return west Wall object
     */
    public String getWallWest() {  //<<--- Change types to Wall class when created
        return wallWest;
    }


}
