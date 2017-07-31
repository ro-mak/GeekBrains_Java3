package lesson_05;


import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Car implements Runnable {


    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            Race.startCountDownLatch.countDown();
            Race.prepareToRaceCyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        Race.finishCountDownLatch.countDown();

        readWriteLock.readLock().lock();
        if (!race.hasWinner()) {
            readWriteLock.readLock().unlock();
            readWriteLock.writeLock().lock();
            System.out.println(getName() + " WINS");
            race.setHasWinner(true);
            readWriteLock.writeLock().unlock();
        } else {
            readWriteLock.readLock().unlock();
        }

    }
}
