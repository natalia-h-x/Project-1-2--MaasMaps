package core.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessibilityMeasure {
    private String postalCode;
    private double accessibility;
}
