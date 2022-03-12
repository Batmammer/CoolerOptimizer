import com.opencsv.CSVReader;
import model.BigPump;
import model.DataRow;
import model.FlexiblePump;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CoolingOptimizerApp {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage CoolingOptimizer input.csv");
            System.exit(1);
        }
        List<DataRow> dataRows = readDataLineByLine(args[0]);
        CoolerOptimizer coolerOptimizer = new CoolerOptimizer();
        coolerOptimizer.calculate(dataRows);
    }

    public static List<DataRow> readDataLineByLine(String file)
    {
        List<DataRow> dataRows = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(file);

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                dataRows.add(readDataRow(nextRecord));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dataRows;
    }

    private static DataRow readDataRow(String[] nextRecord) {
        DataRow dataRow = new DataRow();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dataRow.setDateTime(LocalDateTime.parse(nextRecord[0].split("\\+")[0], formatter));
        dataRow.setFlow(Double.parseDouble(nextRecord[1]) + Double.parseDouble(nextRecord[2]));
        dataRow.setPressure((Double.parseDouble(nextRecord[3]) + Double.parseDouble(nextRecord[4])) / 2);
        List<BigPump> bigPumps = new ArrayList<>();
        bigPumps.add(new BigPump(1, Double.parseDouble(nextRecord[5]), null));
        bigPumps.add(new BigPump(2, Double.parseDouble(nextRecord[6]), null));
        bigPumps.add(new BigPump(3, Double.parseDouble(nextRecord[7]), null));
        bigPumps.add(new BigPump(4, Double.parseDouble(nextRecord[8]), null));
        dataRow.setBigPumps(bigPumps);
        List<FlexiblePump> flexiblePumps = new ArrayList<>();
        flexiblePumps.add(new FlexiblePump(1, Double.parseDouble(nextRecord[9]),Double.parseDouble(nextRecord[10]),Double.parseDouble(nextRecord[11]), null));
        flexiblePumps.add(new FlexiblePump(2, Double.parseDouble(nextRecord[12]),Double.parseDouble(nextRecord[13]),Double.parseDouble(nextRecord[14]), null));
        dataRow.setFlexiblePumps(flexiblePumps);
//        System.out.println(dataRow);
        return dataRow;
    }
}
