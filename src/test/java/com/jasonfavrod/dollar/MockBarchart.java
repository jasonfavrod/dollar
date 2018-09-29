package com.jasonfavrod.dollar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONObject;


class MockBarchart implements API
{
    public final String API_URL= "https://marketdata.websol.barchart.com/getQuote.json";
    private String apikey;

    private final String CAD     = "symbols=^CADUSD";
    private final String EURO    = "symbols=^EURUSD";
    private final String FRANC   = "symbols=^CHFUSD";
    private final String POUND   = "symbols=^GBPUSD";
    private final String RUBLE   = "symbols=^RUBUSD";
    private final String KRONA   = "symbols=^SEKUSD";
    private final String YEN     = "symbols=^JPYUSD";
    private final String YUAN    = "symbols=^CNYUSD";

    MockBarchart(String apikey)
    {
        this.apikey = apikey;
    }

    public JSONObject get(String params) throws IOException,MalformedURLException,ProtocolException
    {
        String response = "{}";

        if (params.equals(CAD))
            response = getResponse("CADUSD");
        else if (params.equals(EURO))
            response = getResponse("EURUSD");
        else if (params.equals(POUND))
            response = getResponse("GBPUSD");
        else if (params.equals("symbols=^USDJPY"))
        	response = getResponse("USDJPY");
        else if (params.equals("symbols=^USDCAD"))
        	response = getResponse("USDCAD");
        else if (params.equals("symbols=^USDSEK"))
        	response = getResponse("USDSEK");
        else if (params.equals("symbols=^USDCHF"))
        	response = getResponse("USDCHF");

        return new JSONObject(response);
    }

    private String getResponse(String filename) throws IOException
    {
        String response = "";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = loader.getResourceAsStream(filename + ".json");

        InputStreamReader reader = new InputStreamReader(in);
        int c = reader.read();

        while ( (int)c != -1) {
            response += (char)c;
            c = reader.read();
        }

        return response;
    }
}

