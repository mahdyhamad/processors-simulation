import java.util.ArrayList;

public class Scheduler {
    private final Clock clock;
    private Processor[] processors;
    private NodePriorityQueue<Task> readyQueue;
    private ArrayList<Task> completedTasks;

    Scheduler(Processor[] processors){
        this.clock = new Clock();
        this.processors = processors;
        this.readyQueue = new NodePriorityQueue<Task>();
        this.completedTasks = new ArrayList<Task>();
    }

    public ArrayList<Task> getCompletedTasks() {
        return completedTasks;
    }

    public Clock getClock() {
        return clock;
    }

    public boolean isReadyQueueEmpty(){
        return readyQueue.isEmpty();
    }

    public boolean isAllProcessorsIdle(){
        for (Processor p: processors){
            if (p.getStatus() != 'i')
                return false;
        }
        return true;
    }

    public void appendToReadyQueue(Task t){
        readyQueue.insert(t);
    }

    private Task fetchTask(){
        return readyQueue.serve();
    }

    private void assignTaskToProcessor(Task t, Processor p){
        p.assignTask(t);
        t.onExecution(clock.getCycle());
    }

    private void interruptProcessor(Processor p, Task t){
        // handle process interruption and task
        Task interrupted = p.getTask();
        interrupted.onInterrupt(clock.getCycle());
        readyQueue.insert(interrupted);

        p.assignTask(t);
    }

    private void onTaskComplete(Task t){
        t.onComplete(this.clock.getCycle());
        completedTasks.add(t);
    }

    public void fetchCycle(){
        System.out.println("Clock Cycle: " + clock.getCycle());
        for (Processor p: processors){
            if (p.isBusy()){
                if (readyQueue.getCount() > 0 && readyQueue.getTop().item.getPriority() == 1 && p.getTask().getPriority() == 0){
                    interruptProcessor(p, readyQueue.serve());
                }
            }
            else if (p.isIdle()){
                if (readyQueue.getCount() > 0){
                    assignTaskToProcessor(fetchTask(), p);
                }
            }
            System.out.println("Processor " + p.getId() + " status: " +  p.getStatus());

            Task task = p.fetchCycle();

            if (task != null && task.isComplete()) {
                onTaskComplete(task);
                p.assignTask(null);
            }
        }
        clock.increment();
    }
}
