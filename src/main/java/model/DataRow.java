package model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DataRow {
    private LocalDateTime dateTime;
    private Double flow;
    private static final Double DESIRED_PRESSURE = 0.53;
    private List<BigPump> bigPumps;
    private List<FlexiblePump> flexiblePumps;

}
