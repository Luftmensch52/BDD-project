package function;

public class Key implements Comparable<Key>{

    private int a;
    private int b;

    public int getA() {
        return a;
    }
    public int getB() {
        return b;
    }
    public void setA(int a) {
        this.a = a;
    }
    public void setB(int b) {
        this.b = b;
    }
    @Override

    public int compareTo(Key key) {
        int fir = key.getA();
        int sec = key.getB();
        if (this.a < fir){
            return -1;
        }
        if (this.a == fir){
            if (this.b > sec){
                return 1;
            }
            else{
                return -1;
            }
        }
        return 1;

    }
}
