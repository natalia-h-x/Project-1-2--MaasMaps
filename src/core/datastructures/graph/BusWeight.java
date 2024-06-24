package core.datastructures.graph;

import core.models.gtfs.Time;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BusWeight extends Weight {
    private int waitTime;

    public BusWeight(int weight, int waitTime) {
        super(weight);
        this.waitTime = waitTime;
    }

    @Override
    public int getWeight() {
        return super.getWeight() + waitTime;
    }

    @Override
    public Time time() {
        return Time.of(getWeight());
    }

    public Time waitTime() {
        return Time.of(waitTime);
    }
}
