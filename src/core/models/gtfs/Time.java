package core.models.gtfs;

import lombok.Data;

@Data
public class Time implements Comparable<Time> {
    private int hours;
    private int minutes;
    private int seconds;

    private Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public static Time empty() {
        return new Time(0, 0, 0);
    }

    public static Time of(int hours, int minutes, int seconds) {
        return new Time(hours, minutes, seconds);
    }

    public static Time of(String string) {
        if (string.contains("Infinity"))
            return new Time(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        String[] parts = string.split(":");
        return new Time(parts.length > 0 && parts[0].isBlank() ? 0 : Integer.parseInt(parts[0]), parts.length > 1 && parts[1].isBlank() ? 0 : Integer.parseInt(parts[1]), parts.length > 2 && parts[2].isBlank() ? 0 : Integer.parseInt(parts[2]));
    }

    public static Time of(int seconds) {
        return new Time(0, 0, 0).update(seconds);
    }

    public boolean isEmpty() {
        return toSeconds() == 0;
    }

    public Time update(int seconds) {
        this.hours = seconds / 3600; seconds %= 3600;
        this.minutes = seconds / 60; seconds %= 60;
        this.seconds = seconds;

        return this;
    }

    public Time minus(Time time) {
        return Time.of(toSeconds() - time.toSeconds());
    }

    public Time add(Time time) {
        return Time.of(toSeconds() + time.toSeconds());
    }

    public Time add(int seconds) {
        return Time.of(toSeconds() + seconds);
    }

    public int toSeconds() {
        if (hours == Integer.MAX_VALUE || minutes == Integer.MAX_VALUE || seconds == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        return (3600 * hours) + (60 * minutes) + seconds;
    }

    public double toMinutes() {
        if (hours == Integer.MAX_VALUE || minutes == Integer.MAX_VALUE || seconds == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        return (60.0d * hours) + minutes + (seconds / 60.0d);
    }

    public double toHours() {
        if (hours == Integer.MAX_VALUE || minutes == Integer.MAX_VALUE || seconds == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        return hours + (minutes / 60.0d) + (seconds / 3600.0d);
    }

    @Override
    public int compareTo(Time o) {
        return Integer.compare(o.toSeconds(), toSeconds());
    }

    @Override
    public String toString() {
        String[] clock = "%d hour;%d minute;%d second".formatted(hours, minutes, seconds).split(";");
        String string = (hours   > 0 ? clock[0] + (hours   > 1 ? "s" : "") + (minutes > 0 ^ seconds > 0 ? " and " : minutes > 0 && seconds > 0 ? ", " : "") : "")
                      + (minutes > 0 ? clock[1] + (minutes > 1 ? "s" : "") + (              seconds > 0 ? " and " :                                     "") : "")
                      + (seconds > 0 ? clock[2] + (seconds > 1 ? "s" : "")                                                                                  : "");

        if (string.equals(""))
            string = "0 seconds";

        return string;
    }

    public String toISOString() {
        return "%d:%d:%d".formatted(hours, minutes, seconds);
    }

    public Time clone() {
        return Time.of(toSeconds());
    }
}
