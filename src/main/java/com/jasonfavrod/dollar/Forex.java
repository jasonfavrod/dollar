package com.jasonfavrod.dollar;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;


/**
 * Get data on foreign currency (to USD) exchange rates,
 * including the US Dollar Index.
 *
 * @author Jason Favrod <mail@jasonfavrod.com>
 * @version 1.0.0
 * @since 1.0.0
 */
class Forex
{
    private API api;

    /** Use to get the price of one Canadian Dollar in terms of US Dollars. */
    public final String CAD     = "symbols=^CADUSD";

    /** Use to get the price of one Euro in terms of US Dollars. */
    public final String EURO    = "symbols=^EURUSD";

    /** Use to get the price of one Swiss Franc in terms of US Dollars. */
    public final String FRANC   = "symbols=^CHFUSD";

    /** Use to get the price of one Swiss Franc in terms of US Dollars. */
    public final String POUND   = "symbols=^GBPUSD";

    /** Use to get the price of one Ruble in terms of US Dollars. */
    public final String RUBLE   = "symbols=^RUBUSD";

    /** Use to get the price of one Krona in terms of US Dollars. */
    public final String KRONA   = "symbols=^SEKUSD";

    /** Use to get the price of one Yen in terms of US Dollars. */
    public final String YEN     = "symbols=^JPYUSD";

    /** Use to get the price of one Yuan in terms of US Dollars. */
    public final String YUAN    = "symbols=^CNYUSD";


    /**
     * Forex Constructor
     *
     * @param api Provide a REST API (implementation of the API interface)
     * for fetching Forex price data.
     * @see dollar.API
     * @since 1.0.0
     */
    Forex(API api)
    {
        this.api = api;
    }


    /**
     * Get the price of a foreign currency.
     *
     * <p>Get the last price for a transaction in a given currency (in
     * terms of US Dollars).</p>
     *
     * @param currency One of the class currency constants (e.g. EURO, YEN).
     * @return The price of one unit of the foreign currency in terms
     * of US Dollars.
     * @since 1.0.0
     */
    public Double getLastPrice(String currency) throws IOException
    {
        JSONObject jsonObject;
        JSONArray jsonArray;

        jsonObject = api.get(currency);
        jsonArray = jsonObject.getJSONArray("results");
        jsonObject = jsonArray.getJSONObject(0);

        return jsonObject.getDouble("lastPrice");
    }


    /**
     * Get the current US Dollar Index value.
     * 
     * @return The current US Dollar Index value.
     * @since 1.0.0
     */
    public Double getUSDX() throws IOException
    {
        Double index = 50.14348112;

        index *= Math.pow(this.getLastPrice(this.EURO), -0.576);
        index *= Math.pow(this.getLastPrice(this.POUND), -0.119);
        index *= Math.pow(this.getLastPrice("symbols=^USDJPY"), 0.136);
        index *= Math.pow(this.getLastPrice("symbols=^USDCAD"), 0.091);
        index *= Math.pow(this.getLastPrice("symbols=^USDSEK"), 0.042);
        index *= Math.pow(this.getLastPrice("symbols=^USDCHF"), 0.036);

        return index;
    }
}
