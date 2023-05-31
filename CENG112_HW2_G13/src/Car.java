import java.util.Random;

public class Car {
	
    private double qualityScore;
    private int leftDays;
    private String idNumber;
    private static int counter;
    private static Random random = new Random();

    public Car() {
        qualityScore = random.nextDouble(1.0, 3.0);
        idNumber = Integer.toString(counter);
		counter++;
	}

    public String getIdNumber() {
        return idNumber;
    }

    public String toString() {
        return "car" + idNumber.toString();
    }

    public double getQualityScore() {
        return Math.round(qualityScore * 100.0) / 100.0;
    }

    public int getLeftDays() {
        return leftDays;
    }

    public void rent() {
        leftDays = random.nextInt(1, 5);
    }

    public int decreaseOccupancy() {
        return leftDays--;
    }
}