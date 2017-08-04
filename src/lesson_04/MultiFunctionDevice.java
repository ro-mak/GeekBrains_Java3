
import java.util.ArrayList;

public class MultiFunctionDevice {

    private final Object scanMonitor;
    private final Object printMonitor;
    private final long PRINTING_DELAY = 50;
    private final long SCANNING_DELAY = 50;
    private ArrayList<Document> printQueue;
    private ArrayList<Document> scanQueue;
    private Thread thread1;
    private Thread thread2;
    private int numberOfDocumentToPrint;
    private int numberOfDocumentToScan;

    public MultiFunctionDevice() {
        scanMonitor = new Object();
        printMonitor = new Object();
        printQueue = new ArrayList<>();
        scanQueue = new ArrayList<>();
    }

    public void addToQueue(Document document) {
        if (document.getScanningOrPrinting().equals("printing")) {
            printQueue.add(document);
        } else if (document.getScanningOrPrinting().equals("scanning")) {
            scanQueue.add(document);
        }
    }

    public void start() {
        System.out.println("Started");
        thread1 = new Thread(() -> {
            while (!thread1.isInterrupted()) {
                if (printQueue.size() > 0) {
                    processingPrintQueue();
                }
            }
        });
        thread2 = new Thread(() -> {
            while (!thread2.isInterrupted()) {
                if (scanQueue.size() > 0) {
                    processingScanQueue();
                }
            }
        });
        thread1.setName("printing");
        thread2.setName("scanning");
        thread1.start();
        thread2.start();
    }

    public void stop() {
        thread1.interrupt();
        thread2.interrupt();
        System.out.println("Stopped");
        System.exit(0);
    }

    private void processingPrintQueue() {
        System.out.println("Количество докуметов в очереди на печать: " + printQueue.size());
        for (int i = 0; i < printQueue.size(); i++) {
            Document document = printQueue.get(i);
            print(document);
            numberOfDocumentToPrint++;
            printQueue.remove(i);
        }
    }

    private void processingScanQueue() {
        System.out.println("Количество докуметов в очереди на сканирование: " + scanQueue.size());
        for (int i = 0; i < scanQueue.size(); i++) {
            Document document = scanQueue.get(i);
            scan(document);
            numberOfDocumentToScan++;
            scanQueue.remove(i);
        }
    }

    private void scan(Document document) {
        synchronized (scanMonitor) {
            int numberOfPages = document.getPages();
            for (int i = 0; i < numberOfPages; i++) {
                System.out.println("Идет сканирование документа № "
                        + (numberOfDocumentToScan + 1)
                        + ". Сканируется страница № "
                        + (i + 1) +" из " + (numberOfPages));

                delay(SCANNING_DELAY);
            }
            System.out.println("Сканирование документа № " + (numberOfDocumentToScan + 1) + " завершено.");

        }
    }

    private void print(Document document) {
        synchronized (printMonitor) {
            int numberOfPages = document.getPages();
            for (int i = 0; i < numberOfPages; i++) {
                System.out.println("Идет печать документа № "
                        + (numberOfDocumentToPrint + 1)
                        +". Печатается страница № "
                        + (i + 1)+ " из " + numberOfPages);
                delay(PRINTING_DELAY);
            }
            System.out.println("Печать документа № " + (numberOfDocumentToPrint + 1) + " завершена.");
        }
    }

    private void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            String threadName = Thread.currentThread().getName();
            if(threadName.equals("scanning")){
                System.err.println("\nСканирование прервано.");
            }else if(threadName.equals("printing")){
                System.err.println("\nПечать прервана.");
            }
            System.exit(0);
        }
    }

}
