import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class experiment {
    public static void main(String[] args) {
        /**
         Q1
         */

        //for (int i=1; i<=5; i++ ) {
            //int size = (int)(1000 * Math.pow(2,i));
            //Integer[] maxToMinArray = new Integer[size];
            //Integer[] inOrderArray = new Integer[size];
            //for (int j = 0; j < size; j++) {
                //maxToMinArray[j] = maxToMinArray.length - j;
                //inOrderArray[j] = j+1;
            //}
            //Integer[] randomArray = maxToMinArray.clone();
            //List<Integer> intList = Arrays.asList(randomArray);
            //Collections.shuffle(intList);
            //intList.toArray(randomArray);

            //experimentAVLTree MTMTree = new experimentAVLTree();
            //experimentAVLTree randomTree = new experimentAVLTree();

            //Integer[] MRMSearchCounter = new Integer[size];
            //Integer[] randomSearchCounter = new Integer[size];
            //for (int node = 0; node < size; node++) {
                //MTMTree.insert(maxToMinArray[node],"num" + maxToMinArray[node], node, MRMSearchCounter);
                //randomTree.insert(randomArray[node], "num" + randomArray[node], node, randomSearchCounter);
            //}

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
//            System.out.println("---");
            /**
             returns search cost as the sum of path
             lengths' form maxNode to the node to which
             we want to attach the new node
             */
            //int maxSearchCounter = 0;
            //int randSearchCounter = 0;
            //Integer[] MRMSearchCounter = new Integer[size];
            //Integer[] randomSearchCounter = new Integer[size];

            //for (int index = 0; index < size; index++){
                //if (MRMSearchCounter[index] != -1) {
                //    maxSearchCounter += MRMSearchCounter[index];
                //}
                //if (randomSearchCounter[index] != -1) {
                //    randSearchCounter += randomSearchCounter[index];
                //}
            //}
            //System.out.println("for "+ i + " maxSearchCounter is " + maxSearchCounter);
            //System.out.println("for "+ i + " randomSearchCounter is " + randSearchCounter);

            //System.out.println(Arrays.toString(maxToMinArray));
            //System.out.println(Arrays.toString(randomArray));
            //System.out.println("---");
        //}
        /**
         Q2
         */
        for (int i=1; i<=10; i++) {
            int size = (int) (1000 * Math.pow(2, i));
            Integer[] inOrderArray = new Integer[size];
            for (int j = 0; j < size; j++) {
                inOrderArray[j] = j + 1;
            }

            Integer[] randomArray = inOrderArray.clone();
            List<Integer> intList = Arrays.asList(randomArray);
            Collections.shuffle(intList);
            intList.toArray(randomArray);


            experimentAVLTree2 randomSplitTree = new experimentAVLTree2();
            experimentAVLTree2 leftMaxSplitTree = new experimentAVLTree2();

            for (int node = 0; node < size; node++) {
                randomSplitTree.insert(randomArray[node], "num" + randomArray[node]);
                leftMaxSplitTree.insert(randomArray[node], "num" + randomArray[node]);
            }
            experimentAVLTree2.IAVLNode x = leftMaxSplitTree.getRoot();
            if (x.getLeft().isRealNode()) {
                x = x.getLeft();
                while (x.getRight().isRealNode()) {
                    x = x.getRight();
                }
            }
            //System.out.println(Arrays.toString(randomArray));
            int[] joinCostLeft = leftMaxSplitTree.split(x.getKey());
            Random rand = new Random();
            int y = rand.nextInt(size) + 1;

            int[] joinCostRandom = randomSplitTree.split(y);

            int maxInRandom = 0;
            int sumInRandom = 0;
            int maxInLeft = 0;
            int sumInLeft = 0;

            double leftLength = 0;
            double randomLength = 0;
            for (int cost = 0; cost < joinCostLeft.length; cost++) {
                if (joinCostLeft[cost] != 0) {
                    sumInLeft += joinCostLeft[cost];
                    leftLength++;
                }
                if (joinCostLeft[cost] > maxInLeft){
                    maxInLeft = joinCostLeft[cost];
                }
            }

            for (int cost = 0; cost < joinCostRandom.length; cost++) {
                if (joinCostRandom[cost] != 0) {
                    sumInRandom += joinCostRandom[cost];
                    randomLength++;
                }
                if (joinCostRandom[cost] > maxInRandom){
                    maxInRandom = joinCostRandom[cost];
                }
            }
            System.out.println("i=" + i);
            System.out.println("average random " + sumInRandom/randomLength);
            System.out.println("max random " + maxInRandom);
            System.out.println("average  maxLeft " + sumInLeft/leftLength);
            System.out.println("max maxLeft " + maxInLeft);
        }

    }
}

