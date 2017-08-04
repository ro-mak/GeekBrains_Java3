
import java.util.ArrayList;
import java.util.Arrays;


public class HomeWorkGenerics {
    public static void main(String[] args) {
        //region FirstTaskTest
        System.out.println("FirstTask");
        String[] arrayOfStrings = {"Hi", "man!", "What's", "up"};
        System.out.println(Arrays.toString(arrayOfStrings));
        changeElements(arrayOfStrings, 1, 3);
        System.out.println(Arrays.toString(arrayOfStrings));
        System.out.println();
        //endregion

        //region SecondTaskTest
        System.out.println("SecondTask");
        Integer[] arrayOfInts = {1, 2, 3, 4, 5};
        ArrayList<Integer> listOfInts = transformArrayToList(arrayOfInts);
        ArrayList<String> listOfStrings = transformArrayToList(arrayOfStrings);
        System.out.println("Now I'm a list of Ints " + listOfInts);
        System.out.println("Now I'm a list of Strings " + listOfStrings);
        System.out.println();
        //endregion

        //region ThirdTaskTest
        Box<Apple> appleBox1 = new Box<>();
        appleBox1.add(new Apple());
        appleBox1.add(new Apple());
        Box<Orange> orangeBox = new Box<>();
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());
        appleBox1.compare(orangeBox);
        appleBox1.add(new Apple());
        appleBox1.add(new Apple());
        appleBox1.add(new Apple());
        appleBox1.compare(orangeBox);

        Box<Apple> appleBox2 = new Box<>();
        appleBox1.compare(appleBox2);
        appleBox1.putFruitIntoAnotherBox(appleBox2);
        appleBox1.compare(appleBox2);
        //endregion
    }

    //region FirstTask
    private static <T> void changeElements(T[] array, int elementNumber1, int elementNumber2) {
        T temp = array[elementNumber1];
        array[elementNumber1] = array[elementNumber2];
        array[elementNumber2] = temp;
    }
    //endregion

    //region SecondTask
    private static <T> ArrayList<T> transformArrayToList(T[] array) {
        ArrayList<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(array));
        return list;
    }
    //endregion

    //region ThirdTask
    private static abstract class Fruit {
        float weight;

        float getWeight() {
            return weight;
        }
    }

    private static class Apple extends Fruit {
        Apple() {
            weight = 1f;
        }
    }

    private static class Orange extends Fruit {
        Orange() {
            weight = 1.5f;
        }
    }

    private static class Box<T extends Fruit> {
        private ArrayList<T> fruit;

        Box() {
            fruit = new ArrayList<>();
        }


        void addAllFruit(ArrayList<T> fruit) {
            this.fruit.addAll(fruit);
        }

        void putFruitIntoAnotherBox(Box<T> anotherBox) {
            anotherBox.addAllFruit(fruit);
            fruit.clear();
        }

        boolean compare(Box<?> box) {
            if (this.getWeight() > box.getWeight()) {
                System.out.println("This fruit box(" + this.getWeight() + ") is bigger than the other(" + box.getWeight() + ")");
                return true;
            } else if (this.getWeight() < box.getWeight()) {
                System.out.println("This fruit box(" + this.getWeight() + ") is smaller than the other(" + box.getWeight() + ")");
                return false;
            } else {
                System.out.println("Boxes are equal");
            }
            return false;
        }

        float getWeight() {
            if (fruit.isEmpty()) {
                return 0;
            }
            return fruit.size() * fruit.get(0).getWeight();
        }

        void add(T fruit) {
            this.fruit.add(fruit);
        }

        public void remove(T fruit) {
            this.fruit.remove(fruit);
        }

        public void remove(int index) {
            fruit.remove(index);
        }

    }
    //endregion
}
