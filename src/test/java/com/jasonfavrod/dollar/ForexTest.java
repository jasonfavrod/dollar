package com.jasonfavrod.dollar;

import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ForexTest extends TestCase
{
    Forex forex;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ForexTest( String testName ) throws IOException
    {
        super( testName );

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = loader.getResourceAsStream("config.properties");
        Properties config = new Properties();

        config.load(in);
        in.close();

        MockBarchart barchart = new MockBarchart(config.getProperty("API_KEY"));
        forex = new Forex(barchart);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ForexTest.class );
    }


    public void testGetLastPrice()
    {
        Boolean res = true;
        Double cad = 0.00;

        try {
            cad = forex.getLastPrice(forex.CAD);
            if (cad != 0.76656) res = false;
        }
        catch(IOException ioe) {
            System.out.println("Forex.getLastPrice() error.");
            System.out.println(ioe);
        }

        assertTrue( res );
    }


    /**
     * Rigourous Test :-)
     */
    public void testGetUSDX()
    {
        Boolean res = false;

        try {
            Double indexValue = forex.getUSDX();
            System.out.println("USDX = " + indexValue);
            if (indexValue > 0.00) res = true;
        }
        catch(IOException ioe) {
            System.out.println("Forex.getUSDX() error.");
            System.out.println(ioe);
        }
        
        assertTrue( res );
    }
}
