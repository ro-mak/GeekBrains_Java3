
import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    private Semaphore tunnelWidthSemaphore;

    public Tunnel(int width, int length) {
        this.tunnelWidthSemaphore = new Semaphore(width);
        this.length = length;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " +
                        description);
                tunnelWidthSemaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " +
                        description);
                tunnelWidthSemaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
