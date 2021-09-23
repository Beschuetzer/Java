package major.adam;

public enum WeekDays {
    //desc isWeekend
    SUNDAY("Smooth Sunday", true),
    MONDAY("Manic Monday"),
    TUESDAY("Tragic Tuesday"),
    WEDNESDAY("Wicked Wednesday"),
    THURSDAY("Thankful Thursday"),
    FRIDAY("Freeky Friday"),
    SATURDAY("Sunny Saturday", true);

    private String description;
    private boolean isWeekend;

    WeekDays(String description) {
        this(description, false);
    }

    WeekDays(String description, boolean isWeekend) {
        this.description = description;
        this.isWeekend = isWeekend;
    }

    public String getDescription() {
        return description;
    }

    public boolean isWeekend() {
        return isWeekend;
    }
}
