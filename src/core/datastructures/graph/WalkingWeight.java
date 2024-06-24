package core.datastructures.graph;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class WalkingWeight extends Weight {
    private int maxWalking;

    public WalkingWeight(int value, int maxWalking) {
        super(value);
        this.maxWalking = maxWalking;
    }

    @Override
    public boolean isReachable() {
        return super.isReachable() && getWeight() < maxWalking;
    }
}
