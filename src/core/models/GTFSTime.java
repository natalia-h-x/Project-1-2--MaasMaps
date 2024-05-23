package core.models;

import lombok.Data;

@Data
public class GTFSTime implements Comparable<GTFSTime> {
    private int hours;
    private int minutes;
    private int seconds;

    private GTFSTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public static GTFSTime of(String string) {
        if (string.contains("Infinity"))
            return new GTFSTime(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        String[] parts = string.split(":");
        return new GTFSTime(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public static GTFSTime of(int seconds) {
        return new GTFSTime(0, 0, 0).update(seconds);
    }

    public GTFSTime update(int seconds) {
        this.hours = seconds / 3600; seconds %= 3600;
        this.minutes = seconds / 60; seconds %= 60;
        this.seconds = seconds;

        return this;
    }

    public GTFSTime minus(GTFSTime time) {
        return GTFSTime.of(toSeconds() - time.toSeconds());
    }

    public GTFSTime add(GTFSTime time) {
        return GTFSTime.of(toSeconds() + time.toSeconds());
    }

    public GTFSTime add(int seconds) {
        return GTFSTime.of(toSeconds() + seconds);
    }

    public int toSeconds() {
        if (hours == Integer.MAX_VALUE || minutes == Integer.MAX_VALUE || seconds == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        return 3600 * hours + 60 * minutes + seconds;
    }

    @Override
    public int compareTo(GTFSTime o) {
        return Integer.compare(toSeconds(), o.toSeconds());
    }

    @Override
    public String toString() {
        String[] clock = "%d hour;%d minute;%d second".formatted(hours, minutes, seconds).split(";");
        return (hours   > 0 ? clock[0] + (hours   > 1 ? "s" : "") + (minutes > 0 ^ seconds > 0 ? " and " : minutes > 0 && seconds > 0 ? ", " : "") : "")
             + (minutes > 0 ? clock[1] + (minutes > 1 ? "s" : "") + (              seconds > 0 ? " and " :                                     "") : "")
             + (seconds > 0 ? clock[2] + (seconds > 1 ? "s" : "")                                                                                  : "") + ".";
    }

    public GTFSTime clone() {
        return GTFSTime.of(toSeconds());
    }
}
