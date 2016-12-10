package mytunes.bll;

/**
 *
 * @author Simon Birkedal
 */
public class TimeFormat
{
    public static String formatDouble(double value)
    {
        double minutes = value / 60;
        double seconds = value % 60;
        
        return String.format("%02d:%02d", (int) minutes, (int) seconds);
    }
    
}
