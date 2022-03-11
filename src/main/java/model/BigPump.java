package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BigPump {
    private Double current;
    private Double valveAngle;
}
