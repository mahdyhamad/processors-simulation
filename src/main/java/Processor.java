public class Processor implements ProcessorInterface {
    private int id;
    private char status;
    private Task task;

    Processor(int id){
        this.id = id;
        this.status = 'i';
    }

    public int getId() {
        return id;
    }

    public char getStatus() {
        return status;
    }

    public Task getTask() {
        return task;
    }

    private void updateStatus(){
        if (task == null){
            status = 'i';
        }
        else if (task.isComplete()){
            status = 'i';
        }
        else{
            status = 'b';
        }
    }

    public void assignTask(Task t){
        task = t;
        updateStatus();
    }

    public boolean isIdle(){
        return status == 'i';
    }

    public boolean isBusy(){
        return status == 'b';
    }

    public Task fetchCycle(){
        if (task != null && !task.isComplete()){
            task.fetchCycle();
        }
        updateStatus();
        return task;
    }

}
