import java.util.Arrays;
public class myTest {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        int[] array = {1926, 385, 1805, 1300, 904, 1635, 896, 604, 1146, 1120, 56, 803, 1094, 445};
        for (int i:array) {
            tree.insert(i,"num"+i);
        }
        AVLTree.IAVLNode root = tree.getRoot();
        System.out.println(isBalanced(root));
}

    static boolean isBalanced(AVLTree.IAVLNode node) {
        int lh; /* for height of left subtree */

        int rh; /* for height of right subtree */

        /* If tree is empty then return true */
        if (node == null || node.isRealNode() == false)
            return true;

        /* Get the height of left and right sub trees */
        lh = height(node.getLeft());
        rh = height(node.getRight());

        if (Math.abs(lh - rh) <= 1
                && isBalanced(node.getLeft())
                && isBalanced(node.getRight()))
            return true;

        /* If we reach here then tree is not height-balanced */
        return false;
    }

    private static int height(AVLTree.IAVLNode node) {
        /* base case tree is empty */
        if (node == null)
            return 0;

        /* If tree is not empty then height = 1 + max of left
         height and right heights */
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }
}
