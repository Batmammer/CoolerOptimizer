import com.opencsv.CSVReader;
import model.BigPump;
import model.DataRow;
import model.FlexiblePump;

import java.io.File;
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
        System.out.println((new File("abc")).getAbsolutePath());
        readDataLineByLine(args[0]);


    }

    public static void readDataLineByLine(String file)
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
    }

    private static DataRow readDataRow(String[] nextRecord) {
        DataRow dataRow = new DataRow();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dataRow.setDateTime(LocalDateTime.parse(nextRecord[0].split("\\+")[0], formatter));
        dataRow.setFlow(Double.parseDouble(nextRecord[1]) + Double.parseDouble(nextRecord[2]));
        List<BigPump> bigPumps = new ArrayList<>();
        bigPumps.add(new BigPump(Double.parseDouble(nextRecord[3]), null));
        bigPumps.add(new BigPump(Double.parseDouble(nextRecord[4]), null));
        bigPumps.add(new BigPump(Double.parseDouble(nextRecord[5]), null));
        bigPumps.add(new BigPump(Double.parseDouble(nextRecord[6]), null));
        dataRow.setBigPumps(bigPumps);
        List<FlexiblePump> flexiblePumps = new ArrayList<>();
        flexiblePumps.add(new FlexiblePump(Double.parseDouble(nextRecord[7]),Double.parseDouble(nextRecord[8]),Double.parseDouble(nextRecord[9])));
        flexiblePumps.add(new FlexiblePump(Double.parseDouble(nextRecord[10]),Double.parseDouble(nextRecord[11]),Double.parseDouble(nextRecord[12])));
        dataRow.setFlexiblePumps(flexiblePumps);
        System.out.println(dataRow);
        return dataRow;
    }
}
