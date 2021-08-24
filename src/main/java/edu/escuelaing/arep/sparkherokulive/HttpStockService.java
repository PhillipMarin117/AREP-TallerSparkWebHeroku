package edu.escuelaing.arep.sparkherokulive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class HttpStockService {

    private static  final HttpStockService _instance = createService();

    private static final String USER_AGENT = "Mozilla/5.0";

    private HashMap<URL, String> Cache = new HashMap <URL, String> ();

    public static HttpStockService getService(){
       return  _instance;
    }

    public static HttpStockService createService(){
        return new IEXCloudHttpStockService();
    }

    public String TimeSeriesDaily() throws IOException {

        String responseStr = "None";
        URL obj = new URL(getURL());
        if(Cache.containsKey(obj)){
            return Cache.get(obj);
        }
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            responseStr = response.toString();


            // print result
            System.out.println(responseStr);
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return responseStr;
    }

    abstract public String getURL();

    abstract public void setStock(String stock);

    abstract public String getStock();
}

