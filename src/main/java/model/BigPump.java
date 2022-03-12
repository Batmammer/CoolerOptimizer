package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BigPump {
    private int id;
    private Double current;
    private Double flow;
    public static final Double VOLTAGE = 6000.0;
    public static final Double POWER = 320000.0;
    public static final Double Q = 950.0;
    public static final Double LOSES = 0.05;

    public Double calculateFlow() {
        if (current < 1.0) {
            flow = 0d;
        } else {
            flow = current * VOLTAGE / POWER * Q / DataRow.DESIRED_PRESSURE * (1 - LOSES);
        }
        return this.flow;
    }

    public Double calculateCurrent() {
        if (flow < 1.0) {
            current = 0d;
        } else {
            current = flow / (VOLTAGE / POWER * Q / DataRow.DESIRED_PRESSURE * (1 - LOSES));
        }
        return this.current;
    }

    public boolean isValid() {
        return current * VOLTAGE < POWER;
    }
}
