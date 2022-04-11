public class MazeWall{
    public boolean active;
    private boolean start;
    private boolean finish;


    public MazeWall() {
    }

    /**
     * returns if the current wall is an Active wall that can't be passed
     * @return boolean of the active wall if it is ture (enable / can't pass) / False can pass.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Sets if the current wall is an Active wall that can't be passed
     * @param active boolean of the active wall if it is ture (enable / can't pass) / False can pass.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean getFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }


}