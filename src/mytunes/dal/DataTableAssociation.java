package mytunes.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 *
 * @author Simon Birkedal
 */
public class DataTableAssociation {

    private static DataTableAssociation instance;

    public static DataTableAssociation getInstance()
    {
        if (instance == null)
        {
            instance = new DataTableAssociation();
        }
        return instance;
    }

    private DataTableAssociation()
    {
    }

    public ArrayList<String> readTableData(String fileName)
    {
        Charset charset = Charset.forName("UTF-8");
        File file = new File(fileName);
        ArrayList<String> dataCodeArray = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset))
        {
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                dataCodeArray.add(line);
            }
        }
        catch (IOException x)
        {
            System.err.format("IOException: %s%n", x);
        }
        System.out.println("READ!");
        return dataCodeArray;
    }

    public void writeTableData(String message, int startIndex, String fileName)
    {
        Charset charset = Charset.forName("UTF-8");
        File file = new File(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset))
        {
            writer.write(message, startIndex, message.length());
        }
        catch (IOException x)
        {
            System.err.format("IOException: %s%n", x);
        }
    }
}
