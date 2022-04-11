public class MazeWall{
    public boolean active;
    private boolean start;
    private boolean finish;


    public MazeWall() {
    }

    public boolean getActive() {
        return active;
    }

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