package com.jasonfavrod.dollar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;


class Barchart implements API
{
    public final String API_URL= "https://marketdata.websol.barchart.com";
    private String apikey;

    Barchart(String apikey)
    {
        this.apikey = apikey;
    }

    public JSONObject get(String params) throws IOException,MalformedURLException,ProtocolException
    {
        URL url;
        HttpsURLConnection conn;
        BufferedReader responseReader;
        InputStreamReader inputStreamReader;
        String inputLine;
        String response = "";

        params += "&apikey=" + apikey;
        url = new URL(API_URL + "/getQuote.json?" + params);

        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        inputStreamReader = new InputStreamReader(conn.getInputStream());
        responseReader = new BufferedReader(inputStreamReader);

        while ( (inputLine = responseReader.readLine()) != null)
        {
            response += inputLine;
        }
        
        responseReader.close();
        return new JSONObject(response);
    }
}
