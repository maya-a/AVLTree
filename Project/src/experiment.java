import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class experiment {
    public static void main(String[] args) {
        for (int i=1; i<=5; i++ ) {
            int size = (int)(1000 * Math.pow(2,i));
            Integer[] maxToMinArray = new Integer[size];
            Integer[] inOrderArray = new Integer[size];
            for (int j = 0; j < size; j++) {
                maxToMinArray[j] = maxToMinArray.length - j;
                inOrderArray[j] = j+1;
            }
            Integer[] randomArray = maxToMinArray.clone();
            List<Integer> intList = Arrays.asList(randomArray);
            Collections.shuffle(intList);
            intList.toArray(randomArray);

            experimentAVLTree MTMTree = new experimentAVLTree();
            experimentAVLTree randomTree = new experimentAVLTree();

            //Integer[] MRMSearchCounter = new Integer[size];
            Integer[] randomSearchCounter = new Integer[size];
            for (int node = 0; node < size; node++) {
                //MTMTree.insert(maxToMinArray[node],"num" + maxToMinArray[node], node, MRMSearchCounter);
                randomTree.insert(randomArray[node], "num" + randomArray[node], node, randomSearchCounter);
            }

            /**
            returns the amount of index replacements
             */
//            int maxCounter = 0;
//            int randomCounter = 0;
//            for(int k = 0; k < size - 1; k++){
//                for (int l = k + 1; l < size; l++){
//                    if (maxToMinArray[k]<inOrderArray[l]) {
//                        maxCounter++;
//                    }
//                    if (randomArray[k]<inOrderArray[l]) {
//                        randomCounter++;
//                    }
//                }
//            }
//            System.out.println("for "+ i + " maxCounter is " + maxCounter);
//            System.out.println("for "+ i + " randomCounter is " + randomCounter);

            int maxSearchCounter = 0;
            int randSearchCounter = 0;

            for (int index = 0; index < size; index++){
                //if (MRMSearchCounter[index] != -1) {
                //    maxSearchCounter += MRMSearchCounter[index];
                //}
                if (randomSearchCounter[index] != -1) {
                    randSearchCounter += randomSearchCounter[index];
                }
            }
            //System.out.println("for "+ i + " maxSearchCounter is " + maxSearchCounter);
            System.out.println("for "+ i + " randomSearchCounter is " + randSearchCounter);

            //System.out.println(Arrays.toString(maxToMinArray));
            //System.out.println(Arrays.toString(randomArray));
            //System.out.println("---");
        }
    }
}
