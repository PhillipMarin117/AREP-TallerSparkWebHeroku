package edu.escuelaing.arep.sparkherokulive;

public class AlphaAdvantageHttpStockService extends HttpStockService {
    String stock="BF";
    @Override
    public String getURL() {
        return "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=fb&apikey=SXI7D93R69CG44IO";
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
