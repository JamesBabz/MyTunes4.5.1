package mytunes.bll;

/**
 * @author Simon Birkedal
 */
public class TimeFormat
{
    /**
     * Formats a double input into a string representation in Minutes:Seconds.
     * @param value The double to be converted to a time format.
     * @return Returns the timeformat as a string.
     */
    public static String formatDouble(double value)
    {
        double minutes = value / 60;
        double seconds = value % 60;
        
        return String.format("%02d:%02d", (int) minutes, (int) seconds);
    }
    
}
