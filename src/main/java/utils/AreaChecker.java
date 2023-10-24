package utils;

public class AreaChecker {
    public static boolean isInArea(double x, double y, double r) {
        // Верхний правый угол - треугольник
        if (x >= 0 && y >= 0) {
            return (x+y) <= r;
        }
        // Верхний левый угол - прямоугольник
        if (x <= 0 && y >= 0) {
            return (x >= -r/2) && (y <= r);
        }
        // Нижний левый угол - 1/4 круга
        if (x >= 0 && y <= 0) {
            return (x * x + y * y) <= r * r / 4;
        }
        // Нижний правый угол - ничего нет
        return false;
    }
}
