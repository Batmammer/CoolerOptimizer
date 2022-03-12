import model.BigPump;
import model.DataRow;
import model.FlexiblePump;

import java.util.ArrayList;
import java.util.List;

public class CoolerOptimizer {

    public void calculate(List<DataRow> dataRows) {
        List<DataRow> cleanRows = dropSwitchRows(dataRows);
        calculateFlow(cleanRows);
        simulateFor2BigPumps(cleanRows);
        simulateFor1BigPump(cleanRows);
        simulateFor1BigPump2Flexible(cleanRows);
    }

    private List<DataRow> dropSwitchRows(List<DataRow> dataRows) {
        List<DataRow> cleanRows = new ArrayList<>();
        for (DataRow dataRow : dataRows) {
            if (dataRow.getFlexiblePumps().get(0).getCurrent() < 1.0
                    || dataRow.getFlexiblePumps().get(1).getCurrent() < 1.0) {
                cleanRows.add(dataRow);
            }
        }
        return cleanRows;
    }

    private void simulateFor2BigPumps(List<DataRow> dataRows) {
        System.out.println("Simulation 2 x SZDY 194 320KW 6000V + 1 x SVEE 355ML 48s 500V");
        for (DataRow dataRow : dataRows) {
            Double sumFlow = 0d;
            for (FlexiblePump flexiblePump : dataRow.getFlexiblePumps()) {
                sumFlow += flexiblePump.getFlow();
            }
            Double newBigFlow = (dataRow.getFlow() - sumFlow) / 2;
            BigPump p1 = dataRow.getBigPumps().get(0);
            p1.setFlow(newBigFlow);
            p1.calculateCurrent();
            BigPump p2 = dataRow.getBigPumps().get(1);
            p2.setFlow(newBigFlow);
            p2.calculateCurrent();
            BigPump p3 = dataRow.getBigPumps().get(2);
            p3.setFlow(0d);
            p3.setCurrent(0d);
            BigPump p4 = dataRow.getBigPumps().get(3);
            p4.setFlow(0d);
            p4.setCurrent(0d);
            if (p1.isValid() && p2.isValid()) {
                System.out.println(dataRow);
            } else {
                System.out.println("Not able to meet desired flow and pressure, not enough power on pump.");
                return;
            }
        }
    }

    private void simulateFor1BigPump(List<DataRow> dataRows) {
        System.out.println("Simulation 1 x SZDY 194 320KW 6000V + 1 x SVEE 355ML 48s 500V");
        for (DataRow dataRow : dataRows) {
            Double sumFlow = 0d;
            for (FlexiblePump flexiblePump : dataRow.getFlexiblePumps()) {
                sumFlow += flexiblePump.getFlow();
            }
            Double newBigFlow = (dataRow.getFlow() - sumFlow);
            BigPump p1 = dataRow.getBigPumps().get(0);
            p1.setFlow(newBigFlow);
            p1.calculateCurrent();
            BigPump p2 = dataRow.getBigPumps().get(1);
            p2.setFlow(0d);
            p2.setCurrent(0d);
            BigPump p3 = dataRow.getBigPumps().get(2);
            p3.setFlow(0d);
            p3.setCurrent(0d);
            BigPump p4 = dataRow.getBigPumps().get(3);
            p4.setFlow(0d);
            p4.setCurrent(0d);
            if (p1.isValid()) {
                System.out.println(dataRow);
            } else {
                System.out.println("Not able to meet desired flow and pressure, not enough power on pump.");
                return;
            }
        }
    }

    private void simulateFor1BigPump2Flexible(List<DataRow> dataRows) {
        System.out.println("Simulation 1 x SZDY 194 320KW 6000V + 2 x SVEE 355ML 48s 500V");
        Double flexPumpMaxFlow = 0d;
        Double flexPumpMaxEff = 0d;
        for (DataRow dataRow : dataRows) {
            for (FlexiblePump flexiblePump : dataRow.getFlexiblePumps()) {
                if (flexiblePump.getFlow() > flexPumpMaxFlow) {
                    flexPumpMaxFlow = flexiblePump.getFlow();
                    flexPumpMaxEff = flexiblePump.getEfficiency();
                }
            }
        }
        Double flexPumpMinFlow = FlexiblePump.MIN_EFFICIENCY / flexPumpMaxEff * flexPumpMaxFlow;
        flexPumpMaxFlow = FlexiblePump.MAX_EFFICIENCY / flexPumpMaxEff * flexPumpMaxFlow;
        for (DataRow dataRow : dataRows) {
            BigPump p1 = dataRow.getBigPumps().get(0);
            p1.setCurrent(BigPump.POWER / BigPump.VOLTAGE);
            Double flexDesiredFlow = (dataRow.getFlow() - p1.calculateFlow()) / 2;
            BigPump p2 = dataRow.getBigPumps().get(1);
            p2.setFlow(0d);
            p2.setCurrent(0d);
            BigPump p3 = dataRow.getBigPumps().get(2);
            p3.setFlow(0d);
            p3.setCurrent(0d);
            BigPump p4 = dataRow.getBigPumps().get(3);
            p4.setFlow(0d);
            p4.setCurrent(0d);
            if (flexDesiredFlow < flexPumpMaxFlow && flexDesiredFlow > flexPumpMinFlow) {
                System.out.println(dataRow);
            } else if (flexDesiredFlow > flexPumpMaxFlow) {
                System.out.println("Not able to meet desired flow and pressure, not enough power on pump.");
                return;
            } else {
                System.out.println("Not able to meet desired flow and pressure, not to much flow, eff should be: "
                        + FlexiblePump.MIN_EFFICIENCY * flexDesiredFlow / flexPumpMinFlow);
            }
        }
    }

    private void calculateFlow(List<DataRow> dataRows) {
        System.out.println("Normal simulation");
        for (DataRow dataRow : dataRows) {
            Double sumFlow = 0d;
            for (BigPump bigPump : dataRow.getBigPumps()) {
                sumFlow += bigPump.calculateFlow();
            }
            for (FlexiblePump flexiblePump : dataRow.getFlexiblePumps()) {
                flexiblePump.calculateFlow(dataRow.getFlow() - sumFlow);
            }
            System.out.println(dataRow);
        }
    }
}
