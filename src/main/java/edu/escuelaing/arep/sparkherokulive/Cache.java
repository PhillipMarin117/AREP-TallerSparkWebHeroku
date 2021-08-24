package edu.escuelaing.arep.sparkherokulive;

public class Cache {

    private static Cache _instance = new Cache();
    private HttpStockService serviceAlpha;
    private HttpStockService serviceIEX;

    private Cache(){
        serviceAlpha = new AlphaAdvantageHttpStockService();
        serviceIEX = new IEXCloudHttpStockService();
    }
    public static Cache getInstance(){
        return _instance;
    }
    public HttpStockService getServiceAlpha(){
        return serviceAlpha;
    }
    public HttpStockService getServiceIEX(){
        return serviceIEX;
    }
}
