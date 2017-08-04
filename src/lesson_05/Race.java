
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Race {
    public static final int CARS_COUNT = 4;
    static CyclicBarrier prepareToRaceCyclicBarrier = new CyclicBarrier(CARS_COUNT);
    static CountDownLatch startCountDownLatch = new CountDownLatch(CARS_COUNT);
    static CountDownLatch finishCountDownLatch = new CountDownLatch(CARS_COUNT);
    private boolean hasWinner = false;
    private Vector<Stage> stages;

    public Vector<Stage> getStages() {
        return stages;
    }

    public Race(Stage... stages) {
        this.stages = new Vector<>(Arrays.asList(stages));
    }

    public void startRace() {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT / 2, 80),
                new Road(40), new Tunnel(CARS_COUNT / 4, 40), new Road(20));

        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            startCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            finishCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }
}

