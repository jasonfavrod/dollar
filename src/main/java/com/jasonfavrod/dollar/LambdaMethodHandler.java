package com.jasonfavrod.dollar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Properties;
import com.jasonfavrod.dollar.Barchart;
import com.jasonfavrod.dollar.Forex;
import com.jasonfavrod.dollar.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class LambdaMethodHandler {
    private static String forexSource;
    
	public LambdaResponse handleRequest(Map<String,Object> input, Context context) {
		String res = "";
		LambdaLogger logger = context.getLogger();

		logger.log("Running LambdaMethodHandler");
		logger.log("Input " + input);

        try {
			Barchart barchart = new Barchart(apikey());
			forexSource = barchart.API_URL;
			indexDollar(barchart, logger);
			res = "OK";
        }
        catch(FileNotFoundException fnf) {
            System.out.println(fnf);
            res = fnf.getMessage();
        }
        catch(IOException ioe){
            System.out.println(ioe);
            res = ioe.getMessage();
        }
        catch (IndexOutOfBoundsException iobe) {
        	System.out.println(iobe);
        	res = iobe.getMessage();
        }
        
        return new LambdaResponse(res);
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

    private static Boolean indexDollar(API api, LambdaLogger logger)
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
            logger.log(sqle.getMessage());
        }
        catch(FileNotFoundException fnfe) {
            System.out.println(fnfe);
            logger.log(fnfe.getMessage());
        }
        catch(IOException ioe) {
            System.out.println(ioe);
            logger.log(ioe.getMessage());
        }

        return true;
    }
}
