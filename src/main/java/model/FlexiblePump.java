package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FlexiblePump {
    private int id;
    private Double current;
    private Double efficiency;
    private Double steeringParameter;
    private Double flow;
    public static Double MAX_EFFICIENCY = 0.97;
    public static Double MIN_EFFICIENCY = 0.88;
    public static final Double VOLTAGE = 500.0;
    public static final Double POWER = 315000.0;

    public Double calculateFlow(Double flow) {
        if (current > 1.0) {
            this.flow = flow;
        } else {
            this.flow = 0d;
        }
        return this.flow;
    }
}
