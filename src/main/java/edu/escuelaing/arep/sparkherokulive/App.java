package edu.escuelaing.arep.sparkherokulive;

import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

public class App {

    /**
     * This main method uses SparkWeb static methods and lambda functions to
     * create a simple Hello World web app. It maps the lambda function to the
     * /hello relative URL.
     */
    public static void main(String[] args) {
        // root is 'src/main/resources', so put files in 'src/main/resources/public'
        staticFiles.location("/public"); // Static files
        port(getPort());
        get("/inputdata", (req, res) -> inputDataPage(req, res));
        get("/results", (req, res) -> resultsPage(req, res));
        get("/facadealpha","application/json", (req, res) -> facadeAlpha(req, res));
        get("/stockservice","application/json", (req, res) -> stockservice(req, res));
        get("/JSClient", (req, res) -> facadeJSClient(req,res));

    }

    private static String  facadeJSClient(Request req, Response res){
        String api = req.queryParams("api");
        String stock = req.queryParams("st");
        String pageContent="";
        if(api==null){
            api="";
        }
        if(stock==null || stock==""){
            pageContent=JSClient.Principal();
        }
        try{
            if(api.equalsIgnoreCase("stockservice")){
                pageContent=stockservice(req,res);
            }
            else if(api.equalsIgnoreCase("facadealpha")){
                pageContent=facadeAlpha(req,res);
            }
        }catch (NullPointerException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pageContent;
    }


    private static String facadeAlpha(Request req, Response res) {
        String stock = req.queryParams("st");
        String response = "None";
        HttpStockService stockService = Cache.getInstance().getServiceAlpha();
        if(stock!=null && stock!=""){
            stockService.setStock(stock);
        }
        try {
            response= stockService.TimeSeriesDaily();
        }catch (IOException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }


    private static String stockservice(Request req, Response res) {
        String stock = req.queryParams("st");
        String response = "None";
        HttpStockService stockService = Cache.getInstance().getServiceIEX();
        if(stock!=null && stock!=""){
            stockService.setStock(stock);
        }
        try {
            response= stockService.TimeSeriesDaily();
        }catch (IOException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    private static String inputDataPage(Request req, Response res) {
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>HTML Forms</h2>"
                + "<form action=\"/results\">"
                + "  First name:<br>"
                + "  <input type=\"text\" name=\"firstname\" value=\"Mickey\">"
                + "  <br>"
                + "  Last name:<br>"
                + "  <input type=\"text\" name=\"lastname\" value=\"Mouse\">"
                + "  <br><br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "<p>If you click the \"Submit\" button, the form-data will be sent to a page called \"/results\".</p>"
                + "</body>"
                + "</html>";
        return pageContent;
    }

    private static String resultsPage(Request req, Response res) {
        return req.queryParams("firstname") + " "
                + req.queryParams("lastname");
    }

    /**
     * This method reads the default port as specified by the PORT variable in
     * the environment.
     *
     * Heroku provides the port automatically so you need this to run the
     * project on Heroku.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

    /*private static String getStockInfo(Request req,Response res){
        res.type("application/json");
        String responseStr = "none";
        try{
            HttpStockService stockService = HttpStockService.createService();
            responseStr = stockService.getStock(req);
        }
        catch (IOException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE,null,ex);
        }
        return responseStr;
    }*/
}
