package faewulf.antiraidfarm.utils;

public class converter {
    public static String convertTicksToTime(long ticks) {
        long seconds = ticks / 20;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        if (hours > 0)
            return String.format("%02dh%02dm%02ds", hours, minutes, seconds);
        else if (minutes > 0)
            return String.format("%02dm%02ds", minutes, seconds);
        else
            return String.format("%02ds", seconds);

    }
}
