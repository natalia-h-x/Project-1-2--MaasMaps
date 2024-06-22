package core.datastructures.graph;

import core.models.gtfs.Time;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Weight {
    private int value;
    private int waitTime;

    public boolean isReachable() {
        return value != Integer.MAX_VALUE;
    }

    public int getTotal() {
        return value + waitTime;
    }

    public Time time() {
        return Time.of(getTotal());
    }

    public Time weightTime() {
        return Time.of(value);
    }

    public Time waitTime() {
        return Time.of(waitTime);
    }
}
