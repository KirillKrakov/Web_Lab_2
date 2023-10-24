package utils;

import java.util.Arrays;
import java.util.List;

public class CoordinatesValidator {
    private final double x, y, r;
    private final String type;

    public CoordinatesValidator(double x, double y, double r, String type) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.type = type;
    }

    public boolean checkData() {
        return checkX() && checkY() && checkR();
    }

    private boolean checkX() {
        return x >= -5 && x <= 3;
    }

    private boolean checkY() {
        if (type.equals("form")) {
            List<Integer> validXValues = Arrays.asList(-4, -3, -2, -1, 0, 1, 2, 3, 4);
            return validXValues.contains((int) y) && y == (int) y;
        } else return y >= -4 && y <= 4;
    }

    private boolean checkR() {
        return r >= 2 && r <= 5;
    }
}
