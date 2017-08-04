
public class Document {
    private String scanningOrPrinting;
    private int pages;

    public String getScanningOrPrinting() {
        return scanningOrPrinting;
    }

    public int getPages() {
        return pages;
    }

    public Document(){
        scanningOrPrinting = (int)(Math.random()*100) > 50 ? "scanning" : "printing";
        pages = (int)(Math.random()*10);

    }
}
