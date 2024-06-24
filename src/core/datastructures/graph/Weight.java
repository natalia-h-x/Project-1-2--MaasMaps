package core.datastructures.graph;

import core.models.gtfs.Time;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Weight {
    private int weight;

    public boolean isReachable() {
        return weight != Integer.MAX_VALUE;
    }

    public Time weightTime() {
        return Time.of(weight);
    }

    public Time time() {
        return Time.of(getWeight());
    }

    public Time waitTime() {
        return Time.of(0);
    }
}
