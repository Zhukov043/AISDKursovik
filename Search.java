import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

class Search{
    public static int linear (int arr[], int elem){
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == elem) return i;
        }
        return -1;
    }

    public static int binary (int arr[], int elem, int first, int last){
        if (last >= first){
            int mid = first + (last - first) / 2;
            if (arr[mid] == elem) return mid;
            if (arr[mid] > elem) return binary(arr, elem, first, mid - 1);
            return binary(arr, elem, mid + 1, last);
        }
        return -1;
    }

    public static int jump (int arr[], int elem){
        int step = (int) Math.sqrt(arr.length);
        int prevStep = 0;
        while (arr[Math.min(step, arr.length) - 1] < elem){
            prevStep = step;
            step += (int) Math.sqrt(arr.length);
            if (step > arr.length) return -1;
        }
        while (arr[prevStep] < elem){
            prevStep++;
            if (prevStep > Math.min(step, arr.length)) return -1; //ПРОВЕРИТЬ!
        }
        if (arr[prevStep] == elem) return prevStep;
        return -1;
    }

    public static int interpolation (int arr[], int elem){
        int start = 0;
        int last = arr.length - 1;
        while ((start <= last) && (elem >= arr[start]) &&
                (elem <= arr[last])){
            int pos = start + (((last - start) /
                    (arr[last] - arr[start]))*
                    (elem - arr[start]));
            if (arr[pos] == elem) return pos;
            if (arr[pos] < elem) start = pos + 1;
            else last = pos - 1;
        }
        return -1;
    }

    public static int exp (int arr[], int elem){
        if (arr[0] == elem) return 0;
        if (arr[arr.length - 1] == elem) return arr.length;

        int range = 1;

        while (range < arr.length && arr[range] <= elem) {
            range = range * 2;
        }
        return binary(arr, elem, range / 2, Math.min(range, arr.length));
    }
}
class Main{
    public static void main(String[] args){
        //int arr[] = new int[1000];
        int size = 10000000;
        double mean = size / 2;
        double otkl = 10;

        int[] arr = new int[size];
        Random rand = new Random();
//        do{
//            set.add(rand.nextInt((int) (arr.length * 1.5)));
//        }while (set.size() != arr.length);
//        int j = 0;
//        for (int i : set) {
//            arr[j] = i;
//            j++;
//        }
//        for (int i = 0; i < arr.length; i++){
//            arr[i] = rand.nextInt(Integer.MAX_VALUE);
//        }
        for (int i = 0; i < size; i++){
           arr[i] = (int) Math.round(mean + otkl * rand.nextGaussian());
        }
        Arrays.sort(arr);
        long start = System.nanoTime();
        Search.interpolation(arr, arr[size / 2]);
        long end = System.nanoTime();
        double time = (double) (end - start) / 1000000;
        //long time = end - start;
        System.out.println("Время: " + time);
        System.out.println(Search.interpolation(arr, arr[size/2]));
    }
 }
