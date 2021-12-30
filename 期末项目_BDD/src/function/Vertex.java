package function;


public class Vertex {

    private Vertex low;
    private Vertex high;
    private int val,id,mark,index;
    private boolean isChanged;

    public boolean isChanged() {
        return isChanged;
    }
    public void setChanged(boolean changed) {
        isChanged = changed;
    }
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
    public void setVal(int val) {
        this.val = val;
    }
    public void setLow(Vertex low) {
        this.low = low;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setHigh(Vertex high) {
        this.high = high;
    }
    public Vertex getLow() {
        return low;
    }
    public Vertex getHigh() {
        return high;
    }
    public int getVal() {
        return val;
    }
    public int getIndex() {
        return index;
    }
    public int getId() {
        return id;
    }
}
