package com.jasonfavrod.dollar;

import java.util.Properties;
import java.io.InputStream;
import java.io.File;
import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.*;

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
                System.out.println(forexSource);
                System.out.println(new Timestamp(System.currentTimeMillis()));
                //indexDollar(barchart);
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

    private static Boolean indexDollar(API api)
    {
        Forex forex;
        Double usdx;

        try {
            forex = new Forex(api);
            usdx = forex.getUSDX();
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
