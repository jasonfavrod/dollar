package com.jasonfavrod.dollar;

import java.util.Properties;
import java.io.InputStream;
import java.io.File;
import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;

import java.lang.IndexOutOfBoundsException;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Collects price data relavent to the US Dollar and stores
 * that data for later analysis.
 *
 * @author Jason Favrod <mail@jasonfavrod.com>
 * @version 1.0.0
 */
public class App 
{
    private static String forexSource;

    public static void main(String args[])
    {
        try {
            String cmd = args[0];

            if (cmd.equalsIgnoreCase("usdx")) {
                Barchart barchart = new Barchart(apikey());
                forexSource = barchart.API_URL;
                indexDollar(barchart);
            }
        }
        catch(FileNotFoundException fnf) {
            System.out.println(fnf);
        }
        catch(IOException ioe){
            System.out.println(ioe);
        }
        catch (IndexOutOfBoundsException iobe) {
            usage();
        }
    }


    private static void usage()
    {
        String mesg = "Dollar\nGet Info on the US Dollar.\n";
        mesg += "usage: Dollar [COMMAND]\n\n";
        mesg += "commands:\n";
        mesg += "---------\n";
        mesg += "usdx - Fetch the current US Dollar index value and store it\nin the configured database.";
        System.out.println(mesg);
    }


    private static String apikey() throws FileNotFoundException,IOException
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = loader.getResourceAsStream("config.properties");
        Properties config = new Properties();

        config.load(in);
        in.close();

        return config.getProperty("API_KEY");
    }


    private static String connectionString() throws FileNotFoundException,IOException
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = loader.getResourceAsStream("config.properties");
        Properties config = new Properties();

        config.load(in);
        in.close();

        String connString = config.getProperty("DB_URL");
        connString += "?user=" +config.getProperty("DB_USER");
        connString += "&password=" + config.getProperty("DB_PASSWORD");
        return connString;
    }


    private static Boolean indexDollar(API api)
    {
        Forex forex;
        Double usdx;
        Connection conn = null;
        String stmt;

        try {
            conn = DriverManager.getConnection(connectionString());
            forex = new Forex(api);
            usdx = BigDecimal.valueOf(forex.getUSDX()).setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            stmt = "INSERT INTO price_data.dollar ";
            stmt += "(price,uom,sample_date,sample_time,source) VALUES (?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setDouble(1, usdx);
            ps.setString(2, "DXY");
            ps.setObject(3, LocalDate.now());
            ps.setObject(4, LocalTime.now());
            ps.setString(5, forexSource);

            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        catch(FileNotFoundException fnfe) {
            System.out.println(fnfe);
        }
        catch(IOException ioe) {
            System.out.println(ioe);
        }

        return true;
    }
}
