import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        SimulationConfigHandler configHandler = new SimulationConfigHandler();
        configHandler.read();

        Processor[] processors = new Processor[configHandler.config.getProcessors()];
        ArrayList<Task> tasks = (ArrayList<Task>) configHandler.config.getTasks();

        for (int i=0; i<configHandler.config.getProcessors(); i++){
            processors[i] = new Processor(i+1);
        }
        Scheduler scheduler = new Scheduler(processors);

        while(tasks.size() != 0 || !scheduler.isAllProcessorsIdle() || !scheduler.isReadyQueueEmpty()){
            for (int k=0; k<tasks.size(); k++){
                Task t = tasks.get(k);
                if (t.getCreationTime() == scheduler.getClock().getCycle()){
                    scheduler.appendToReadyQueue(t);
                    tasks.remove(k);
                }
            }
            scheduler.fetchCycle();
        }

        SimulationReportHandler report = new SimulationReportHandler(
            scheduler.getCompletedTasks(),
            configHandler.config.getProcessors(),
            scheduler.getClock().getCycle()
        );
        report.export_to_xlsx();
    }
}


/*
* Statistic:
* * For A Task:
* * * 1- Burst time     : is the total time taken by the process for its execution on the CPU.
* * * 2- Arrival time   : is the time when a process enters into the ready state and is ready for its execution.
* * * 3- Exit time      : is the time when a process completes its execution and exit from the system.
* * * 4- Response time  : is the time spent when the process is in the ready state and gets the CPU for the first time.
* * *                     (Response time = Time at which the process gets the CPU for the first time - Arrival time)
* * * 5- Waiting time   : is the total time spent by the process in the ready state waiting for CPU.
* * *                     (Waiting time = Turnaround time - Burst time)
* * * 6- Turnaround time: is the total amount of time spent by the process from coming in the ready state for the first time to its completion.
* * *                     (Turnaround time = Burst time + Waiting time) or (Turnaround time = Exit time - Arrival time)
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* * For A CPU:
* * * 1- Throughput     : is a way to find the efficiency of a CPU. It can be defined as the number of processes
* *                       executed by the CPU in a given amount of time
* */