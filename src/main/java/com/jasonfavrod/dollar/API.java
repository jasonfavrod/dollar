package com.jasonfavrod.dollar;

import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;


/**
 * An interface between a REST interface providing price data
 * and this application.
 *
 * @author Jason Favrod <mail@jasonfavrod.com>
 * @version 1.0.0
 * @since 1.0.0
 */
interface API
{
    JSONObject get(String params) throws IOException,MalformedURLException,ProtocolException;
}
