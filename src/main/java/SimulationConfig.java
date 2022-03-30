import java.util.List;

public class SimulationConfig {
    int processors;
    List<Task> tasks;
    SimulationConfig(){}

    public void setProcessors(int processors) {
        this.processors = processors;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getProcessors() {
        return processors;
    }
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "SimulationConfig{" +
                "processors=" + processors +
                ", tasks=" + tasks +
                '}';
    }
}
