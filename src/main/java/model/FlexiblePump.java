package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FlexiblePump {
    private Double current;
    private Double efficiency;
    private Double steeringParameter;
    private static Double MAX_EFFICIENCY = 0.97;
    private static Double MIN_EFFICIENCY = 0.88;

}
