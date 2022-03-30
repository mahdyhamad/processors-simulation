public class Task implements Comparable<Task> {
    private int id;
    private char status;
    private int priority;
    private int creationTime;
    private int requestedTime;
    private int burstTime;
    private int completionTime;
    private int assignedOn;
    private int arrivalTime;
    private int exitTime;

    private boolean interrupted;

    Task(){}
    Task(int id, char status, int priority, int creationTime, int requestedTime){
        this.id = id;
        this.status = status;
        this.priority = priority;
        this.creationTime = creationTime;
        this.requestedTime = requestedTime;
        this.interrupted = false;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setStatus(char status) {
        this.status = status;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setCreationTime(int creationTime) {
        this.creationTime = this.arrivalTime = creationTime;
    }
    public void setRequestedTime(int requestedTime) { this.requestedTime = this.burstTime = requestedTime; }
    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public void setArrivalTime(int arrivalTime) {
        // is the time when a process enters into the ready state and is ready for its execution.
        this.arrivalTime = arrivalTime;
    }
    public void setExitTime(int exitTime) {
        // is the time when a process completes its execution and exit from the system.
        this.exitTime = exitTime;
    }

    public int calculateWaitingTime() {
        // is the total time spent by the process in the ready state waiting for CPU.
        // Waiting time = Turnaround time - Burst time
        return this.calculateTurnAroundTime() - this.burstTime + 1;
    }
    public int calculateTurnAroundTime() {
        // is the total amount of time spent by the process from coming in the ready state for the first time to its completion.
        // Turnaround time = Exit time - Arrival time
        return this.exitTime - this.arrivalTime;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public int getId() {return id;}
    public char getStatus(){
        return status;
    }
    public int getPriority(){
        return priority;
    }
    public int getCreationTime() { return creationTime; }
    public int getRequestedTime(){
        return requestedTime;
    }
    public int getCompletionTime() {
        return completionTime;
    }
    public int getAssignedOn() { return assignedOn; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getExitTime() { return exitTime; }

    public void onExecution(int cycle){
        this.status = 'e';
        if (!interrupted)
            this.assignedOn = cycle;
    }
    public void onInterrupt(int cycle){
        this.interrupted = true;
        this.status = 'w';
    }
    public void onComplete(int cycle){
        this.completionTime = cycle;
        this.exitTime = cycle;
        this.status = 'c';
    }

    public void ready(){ this.status = 'w'; }
    public void complete(){ this.status = 'c';}

    public boolean isReady(){ return this.status == 'w'; }
    public boolean isComplete(){ return this.status == 'c'; }

    public void fetchCycle(){
        if (requestedTime != 0){
            requestedTime--;
        }
        if (requestedTime == 0){
            complete();
        }
    }

    public int compareStatusTo(char s){
        if (status == s)
            return 0;
        else if (status == 'w')
            return 1;
        else if (s == 'w')
            return -1;
        return 0;
    }

    @Override
    public int compareTo(Task e) {
        // comparison will be based on priority
        if (interrupted && !e.interrupted){
            return 1;
        }
        else if (e.interrupted && !interrupted){
            return -1;
        }
        if (compareStatusTo(e.getStatus()) != 0){
            return compareStatusTo(e.getStatus());
        }
        if (priority == e.getPriority()){
            if (requestedTime > e.getRequestedTime()){
                return 1;
            }
            else if (e.getRequestedTime() > requestedTime){
                return -1;
            }
            else{
                if (creationTime < e.getCreationTime()){
                    return 1;
                }
                else if (e.getCreationTime() < creationTime){
                    return -1;
                }
            }
        }

        else{
            if (priority == 1)
                return 1;
            else
                return -1;
        }
        return 0;
    }
}
