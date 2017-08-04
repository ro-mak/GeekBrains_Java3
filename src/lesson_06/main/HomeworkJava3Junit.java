

public class HomeworkJava3Junit {

    //region Task1
    public int[] task1(int[] array) {
        if (array == null || array.length == 0) {
            throw new NullPointerException("Array is null or empty");
        }
        int indexOfFour = findIndexOfFour(array);
        int integersToCopy = array.length - indexOfFour - 1;
        int[] temp = new int[integersToCopy];

        System.arraycopy(array, indexOfFour + 1, temp, 0, integersToCopy);
        return temp;
    }

    private int findIndexOfFour(int[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == 4) {
                return i;
            }
        }
        throw new RuntimeException();
    }

    //endregion

    //region Task2
    public boolean task2(int[] array) {
        boolean foundFours = false;
        boolean foundOnes = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4 && !foundFours) foundFours = true;
            if (array[i] == 1 && !foundOnes) foundOnes = true;
            if (array[i] != 4 & array[i] != 1) return false;
        }
        return foundFours && foundOnes;
    }

    //endregion
}
