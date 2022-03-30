import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SimulationReportHandler {

    private ArrayList<Task> tasks;
    private int processorsCount;
    private int simulationTime;


    SimulationReportHandler(ArrayList<Task> tasks, int processorsCount, int simulationTime){
        this.tasks = tasks;
        this.processorsCount = processorsCount;
        this.simulationTime = simulationTime;
    }
    public void export_to_xlsx() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet tasksSheet = workbook.createSheet("Tasks");

        // creating a row object
        XSSFRow row;

        // This data needs to be written (Object[])
        Map<String, Object[]> tasksData = new TreeMap<String, Object[]>();

        tasksData.put(
            "1",
            new Object[] {
            "Task ID",
            "Priority",
            "Arrival Time",
            "Burst Time",
            "Exit Time",
            "Assigned On",
            "Waiting Time",
            "Turn-Around Time"
            }
        );
        for (int i=0; i<tasks.size(); i++){
            Task task = tasks.get(i);
            tasksData.put(
                String.valueOf(i+2),
                new Object[]{
                    String.valueOf(task.getId()),
                    String.valueOf(task.getPriority()),
                    String.valueOf(task.getCreationTime()),
                    String.valueOf(task.getBurstTime()),
                    String.valueOf(task.getExitTime()),
                    String.valueOf(task.getAssignedOn()),
                    String.valueOf(task.calculateWaitingTime()),
                    String.valueOf(task.calculateTurnAroundTime())
                }
            );
        }
        tasksData.put(
                String.valueOf(tasks.size()+2),
                new Object[]{
                        "Number of processors = " + this.processorsCount + " Processors"
                }
        );
        tasksData.put(
            String.valueOf(tasks.size()+3),
            new Object[]{
                    "Total Simulation Time = " + this.simulationTime + " Cycles"
            }
        );
        Set<String> keyid = tasksData.keySet();
        int rowid = 0;

        // writing the data into the sheets...
        for (String key : keyid) {
            row = tasksSheet.createRow(rowid++);
            Object[] objectArr = tasksData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }

        // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
        FileOutputStream out = new FileOutputStream(new File("Simulation Report.xlsx"));
        workbook.write(out);
        out.close();
    }
}
