public class myTest {
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        for (int aValues4 : values4) {
            avlTree.insert(aValues4, "" + aValues4);
        }
        int n = 0;
        for (int aValues4 : values4) {
            avlOperations += avlTree.delete(values4[aValues4 - 1]);
            if (avlTree.size() > 0) {
                // while avlTree is not empty, checking the min & max values
                if ((!avlTree.max().equals(avlTree.max())) ||
                        (!avlTree.min().equals(avlTree.min()))) {
                    n++;
                }
            } else {
                // if all items were deleted from avlTree, check if RBTree is empty as well
                if (!avlTree.empty()) {
                    n++;
                }
            }
        }
        for (int val : values4) {
            // checking that all the values that were supposed to be deleted are not in the RBTree
            if (!(avlTree.search(val) == null)) {
                n++;
            }
        }
        System.out.println(n == 0);
    }

    //fakeTree = null;
     ActualAVLTree actualTree = null;
     AVLTree avlTree = null;

    // create array of values between 800-1800
    // like this - 800, 801, 802, 803, 804
    static int[] valuesTemp = new int[1000];
    {
        for (int j = 0; j < valuesTemp.length; j++) {
            valuesTemp[j] = 800 + j;
        }
    }

    // mix the values - create a new list of values taken
    // one from the start one from the end, alternately
    // i.e. values[0], values[-1], values[1], values[-2] ...
    static int[] values = new int[1000];
    {
        int k = 0;
        for (int j = 0; j < (values.length / 2); j++) {
            values[k] = valuesTemp[j];
            k++;
            values[k] = valuesTemp[valuesTemp.length - 1 - j];
            k++;
        }
    }

    // create custom array of values

    static int[] values3 = new int[]{17, 6, 1, 19, 18, 3, 2, 10, 13, 12,
            20, 15, 4, 11, 7, 16, 9, 5, 8, 14, 28};
    static int[] values4 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    static int actualOperations = 0;
    static int avlOperations = 0;
}
