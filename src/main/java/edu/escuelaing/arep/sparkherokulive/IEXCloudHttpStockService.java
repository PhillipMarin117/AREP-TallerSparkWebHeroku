package edu.escuelaing.arep.sparkherokulive;

public class IEXCloudHttpStockService extends HttpStockService{
    String stock="AP";
    @Override
    public String getURL() {
        return "https://cloud.iexapis.com/stable/stock/aapl/quote?token=YOUR_TOKEN_HERE";
    }

    @Override
    public void setStock(String stock) {
        this.stock=stock;
    }

    @Override
    public String getStock() {
        return stock;
    }


}
