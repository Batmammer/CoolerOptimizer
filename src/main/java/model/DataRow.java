package model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DataRow {
    private LocalDateTime dateTime;
    private Double flow;
    public static final Double DESIRED_PRESSURE = 0.53;
    private Double pressure;
    private List<BigPump> bigPumps;
    private List<FlexiblePump> flexiblePumps;

}
