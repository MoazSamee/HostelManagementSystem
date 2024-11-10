package model.Hostel;

public class Package {
    private String packageName;
    private double dailyCharges;
    private double weeklyCharges;
    private double monthlyCharges;
    private String roomType;

    public Package(String packageName, double dailyCharges, double weeklyCharges, double monthlyCharges, String roomType) {
        this.packageName = packageName;
        this.dailyCharges = dailyCharges;
        this.weeklyCharges = weeklyCharges;
        this.monthlyCharges = monthlyCharges;
        this.roomType = roomType;
    }

    public String getPackageName() {
        return packageName;
    }

    public double getDailyCharges() {
        return dailyCharges;
    }

    public double getWeeklyCharges() {
        return weeklyCharges;
    }

    public double getMonthlyCharges() {
        return monthlyCharges;
    }

    public String getRoomType() {
        return roomType;
    }
}
