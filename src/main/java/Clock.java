public class Clock {
    private int cycle;

    Clock(){
        cycle = 1;
    }
    public int getCycle() {
        return cycle;
    }

    public void increment(){
        cycle++;
    }

}
