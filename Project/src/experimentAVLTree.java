public class experimentAVLTree {

        private final AVLNode VIRTUAL_NODE = new AVLNode();
        private int treeSize;
        private IAVLNode root;
        private IAVLNode minNode;
        private IAVLNode maxNode;

        experimentAVLTree () {
            this.root = VIRTUAL_NODE;
            minNode = VIRTUAL_NODE;
            maxNode = VIRTUAL_NODE;
        }

        experimentAVLTree (IAVLNode root) {
            if (root.isRealNode()) {
                this.root = root;
                this.treeSize = 1;
                minNode = root;
                maxNode = root;
            }
        }

        experimentAVLTree (IAVLNode root, int size) {
            if (root.isRealNode()) {
                this.root = root;
                this.treeSize = size;
                minNode = minNode();
                maxNode = maxNode();
            }
        }

        public boolean empty() {
            return !root.isRealNode();
        }

        public String search(int k) {
            IAVLNode x = root;
            while (x.isRealNode()) {
                if (x.getKey() == k) {
                    return x.getValue() ;
                }
                else if (x.getKey() < k){
                    x = x.getRight();
                }
                else {
                    x = x.getLeft();
                }
            }
            return null;
            }


        public int insert(int k, String i, int index, Integer[] counter) {
            if (search(k) != null) {
                return -1;
            }
            IAVLNode newNode = new AVLNode(k,i);
            treeSize += 1;

            if (this.empty()){
                updateMinMax(newNode);
                this.root = newNode;
                counter[index] = 1;
                return 1;
            }
            return AVLInsert(root, newNode, index, counter );
        }
//        public int insert(IAVLNode node) {
//            if (search(node.getKey()) != null) {
//                return -1;
//            }
//            treeSize += 1;
//            updateMinMax(node);
//            if (this.empty()){
//                this.root = node;
//                return 0;
//            }
//            return AVLInsert(root, node);
//        }
        private IAVLNode treeInsert(IAVLNode root, IAVLNode node, int index, Integer[] counter) {
            int key = node.getKey();
            IAVLNode x = maxTreePosition(key);
            counter[index] = maxTreePositionCount(key);
            //this will surely return the position on which we should insert the node. otherwise, this function would have not been called from insert
            x.upSize(); // raises the size field of x and its ancestors;
            node.setParent(x);
            if (key<x.getKey()) {
                x.setLeft(node);
                if (x.getRight() == VIRTUAL_NODE) {
                    x.setHeight(x.getHeight() + 1);
                }
            } else {
                x.setRight(node);
                if (x.getLeft() == VIRTUAL_NODE) {
                    x.setHeight(x.getHeight() + 1);
                }
            }
            return x; // the parent of the node we inserted
        }
        public IAVLNode treePosition(int k) { //returns a node with key k or the position in which we should insert it
            IAVLNode x = root;
            IAVLNode y = null;
            while (x.isRealNode()) {
                y = x;
                if (x.getKey() == k) {
                    return x;
                }
                else if (x.getKey() < k){
                    x = x.getRight();
                }
                else {
                    x = x.getLeft();
                }
            }
            return y;
        }
        public int CounterTreePosition(int k) { //returns a node with key k or the position in which we should insert it
        int counter = 1;
            IAVLNode x = root;
            IAVLNode y = null;
            while (x.isRealNode()) {
                y = x;
                if (x.getKey() == k) {
                    return counter;
                }
                else if (x.getKey() < k){
                    x = x.getRight();
                    counter++;
                }
                else {
                    x = x.getLeft();
                    counter++;
                }
            }
            return counter;
        }

        private IAVLNode maxTreePosition(int k) {
            IAVLNode x = maxNode;
            if (x.getKey() < k) {
                return maxNode;
            }
            if (x.getKey() == k) {
                return x;
            }
            while (x.getParent() != null) {
                if (x.isLeftChild()) {
                    if (x.getParent().getKey() < k) {
                        x = x.getParent();
                    }
                    if (!x.getRight().isRealNode()) {
                        return x;
                    } else {
                        experimentAVLTree subTree = new experimentAVLTree(x.getLeft(), x.getLeft().getSize());
                        return subTree.treePosition(k);
                    }
                    }
                else {
                    if (x.getParent().getKey() > k) {
                        x = x.getParent();
                    }
                    else {
                        if (!x.getLeft().isRealNode()) {
                            return x;
                        } else {
                            experimentAVLTree subTree = new experimentAVLTree(x.getLeft(), x.getLeft().getSize());
                            return subTree.treePosition(k);
                            }
                        }
                    }
                }
            return treePosition(k);
            }

        private int maxTreePositionCount(int k) {
            int counter = 1;
            IAVLNode x = maxNode;
            if (x.getKey() < k) {
                return 1;
            }
            if (x.getKey() == k) {
                return - 1;
            }
            while (x.getParent() != null) {
                if (x.isLeftChild()) {
                    if (x.getParent().getKey() < k) {
                        x = x.getParent();
                        counter++;
                    }
                    else if (!x.getRight().isRealNode()) {
                        return counter;
                    } else {
                        experimentAVLTree subTree = new experimentAVLTree(x.getLeft(), x.getLeft().getSize());
                        return counter + subTree.CounterTreePosition(k);
                    }
                }
                else {
                    if (x.getParent().getKey() > k) {
                        x = x.getParent();
                        counter++;
                    }
                    else {
                        if (!x.getLeft().isRealNode()) {
                            return counter;
                        } else {
                            experimentAVLTree subTree = new experimentAVLTree(x.getLeft(), x.getLeft().getSize());
                            return counter + subTree.CounterTreePosition(k);
                        }
                    }
                }
            }
            return counter;
        }
        private int AVLInsert(IAVLNode root, IAVLNode node, int index, Integer[] counter) {
            int total = 0;
            IAVLNode y = treeInsert(root,node, index, counter); //B is where i inserted A
            updateMinMax(node);
            //case 1 - y (B) had one son before insertion (was not a leaf). After inserting the node it has 2 sons
            //no changes needed
            if (y.getLeft() != VIRTUAL_NODE && y.getRight() != VIRTUAL_NODE) {
                return 0;
            }
            if (y.getParent() == null) {
                return  total;
            }
            int leftEdge ;
            int rightEdge;

            do {
                y = y.getParent();
                leftEdge = y.getHeight() - y.getLeft().getHeight();
                rightEdge = y.getHeight() - y.getRight().getHeight();
                if (rightEdge + leftEdge == 1) {
                    y.promote();
                    total++;
                }else {break;}
            } while (y.getParent() != null);
//		y = y.getParent();
//		int leftEdge = y.getHeight() - y.getLeft().getHeight();
//		int rightEdge = y.getHeight() - y.getRight().getHeight();

//			while (rightEdge + leftEdge == 1) { //case 2.1 - edges are 0,1 or 1,0
//					y.promote();
//					total += 1;
//				y = y.getParent();
//				if (y == null) { //added during documentation - we got to the root
//					return total;
//				}
//				leftEdge = y.getHeight() - y.getLeft().getHeight();
//				rightEdge = y.getHeight() - y.getRight().getHeight();
//			}

            if (leftEdge == 0 && rightEdge == 2) {
                total += fixCase02(y);
            }
            if (leftEdge == 2 && rightEdge == 0) {
                total += fixCase20(y);
            }
            return total;
        }
        private void rightRotation(IAVLNode x, IAVLNode y) { // x is left child of y, we turn y into the right child of x
            IAVLNode tempParent = y.getParent();
            IAVLNode tempChild = x.getRight();
            y.setParent(x);
            x.setRight(y);
            y.setLeft(tempChild);
            if (y.getLeft().isRealNode()) {
                y.getLeft().setParent(y);
            }
            x.setParent(tempParent);
            if (x.getParent() != null) {
                if (x.getParent().getRight() == y) { //if y was a right child, we put x as the new right child
                    x.getParent().setRight(x);
                } else {
                    x.getParent().setLeft(x);
                }
            }else {
                root = x;
            }
            y.setSize(y.getLeft().getSize()+y.getRight().getSize() + 1);
            x.setSize(x.getLeft().getSize()+x.getRight().getSize() + 1);
        }
        private void leftRotation(IAVLNode y, IAVLNode x) { // y is the right child of x, we turn x into the left child of y
            IAVLNode temp_parent = x.getParent();
            IAVLNode temp_child = y.getLeft(); //changed from IAVLNode temp_child = y.getRight(); during documentation
            x.setParent(y);
            y.setLeft(x);
            y.setParent(temp_parent);
            x.setRight(temp_child);

            if (x.getRight().isRealNode()) {
                x.getRight().setParent(x);
            }
            if (y.getParent() != null) {
                if (y.getParent().getRight() == x) { //if x was a right child, we put y as the new right child
                    y.getParent().setRight(y);
                } else {
                    y.getParent().setLeft(y);
                }
            } else {
                root = y;
            }
            x.setSize(x.getLeft().getSize()+x.getRight().getSize() + 1);
            y.setSize(y.getLeft().getSize()+y.getRight().getSize() + 1);
        }
        private int fixCase02(IAVLNode node) {
            int total = 0;
            IAVLNode left = node.getLeft();
            int rightHeight = left.getRight().getHeight();
            int leftHeight = left.getLeft().getHeight();
            int leftEdge = left.getHeight() - leftHeight;
            int rightEdge = left.getHeight() - rightHeight;

            if (leftEdge == 1 && rightEdge == 2) { //2.2
                rightRotation(left,node);
                node.demote();
                total += 2; //changed to 2 during documentation
            }
            else if (leftEdge == 2 && rightEdge == 1) { //2.3
                leftRotation(left.getRight(),left);
                left.demote();
                rightRotation(node.getLeft(),node);
                node.demote();
                node.getParent().promote();
                total += 5;
            }
            else if (leftEdge == 1 && rightEdge == 1){ //only possible in join()
                rightRotation(node,node.getParent());
                node.promote();
                total += 2;
            }
            return total;
        }
        private int fixCase1(IAVLNode node) {
            int total = 0;
            node = node.getParent();
            if (node == null) { //added during documentation - we got to the root
                return total;
            } else {
                node.promote();
                total += 1;
                int leftEdge = node.getHeight() - node.getLeft().getHeight();
                int rightEdge = node.getHeight() - node.getRight().getHeight();
            }
            return total;
        }
        private int fixCase20(IAVLNode node) {
            int total = 0;
            IAVLNode right = node.getRight();
            int rightHeight = right.getRight().getHeight();
            int leftHeight = right.getLeft().getHeight();
            int leftEdge = right.getHeight() - leftHeight;
            int rightEdge = right.getHeight() - rightHeight;

            if (leftEdge == 2 && rightEdge == 1) { //2.2
                leftRotation(right,node); // is this right
                node.demote();
                total += 2;
            }
            else if (leftEdge == 1 && rightEdge == 2) { //2.3
                rightRotation(right.getLeft(),right);
                right.demote();
                leftRotation(node.getRight(),node);
                node.demote();
                node.getParent().promote();
                total += 5;
            }
            else if (leftEdge == 1 && rightEdge == 1){ //only possible in join()
                leftRotation(node,node.getParent());
                node.promote();
                total += 2;
            }
            return total;
        }

        public int delete(int k) {
            int total = 0;
            if (search(k) == null) {
                return -1;
            }
            if (size() == 1) {
                this.root = VIRTUAL_NODE;
                minNode = VIRTUAL_NODE;
                maxNode = VIRTUAL_NODE;
                treeSize -= 1;
                return 0;
            }
            treeSize -= 1;
            IAVLNode node = treePosition(k);
            IAVLNode parent = node.getParent();

            if (node.getKey() == maxNode.getKey()) {
                maxNode = node.predecessor();
            }
            if (node.getKey() == minNode.getKey()) {
                minNode = node.successor();
            }
            if (node.isLeaf()) {
                deleteLeaf(node);
            } else if (node.isUnaryNode()) {
                deleteUnary(node);
            } else {
                deleteBinary(node);
            }


            if (parent == null) {
                return total;
            } else {
                node = (IAVLNode) parent;
            }
            int rightEdge;
            int leftEdge;

            rightEdge = node.getHeight()-node.getRight().getHeight();
            leftEdge = node.getHeight()-node.getLeft().getHeight();

            while (Math.abs(leftEdge - rightEdge) > 1 || leftEdge + rightEdge > 3) {
                if (leftEdge == 2 && rightEdge == 2) {
                    node.demote();
                    total += 1;
                }
                if (leftEdge == 3 && rightEdge == 1) {
                    node = node.getRight(); //y
                    rightEdge = node.getHeight()-node.getRight().getHeight();
                    leftEdge = node.getHeight()-node.getLeft().getHeight();
                    if (leftEdge == 1 && rightEdge == 1) {
                        node.getParent().demote();
                        node.promote();
                        leftRotation(node,node.getParent());
                        total += 3;
                        return total;
                    }
                    else if (leftEdge == 2 && rightEdge == 1) {
                        node.getParent().doubleDemote();
                        leftRotation(node,node.getParent());
                        node = node.getParent();
                        total += 3;
                    }
                    else if (leftEdge == 1 && rightEdge == 2) {
                        node.demote(); //y
                        rightRotation(node.getLeft(),node);
                        node = node.getParent(); //a
                        node.promote();
                        leftRotation(node,node.getParent());
                        node.getLeft().doubleDemote(); //z
                        total += 6;
                    }
                }if (leftEdge == 1 && rightEdge == 3) {
                    node = node.getLeft(); //y
                    rightEdge = node.getHeight()-node.getRight().getHeight();
                    leftEdge = node.getHeight()-node.getLeft().getHeight();
                    if (leftEdge == 1 && rightEdge == 1) {
                        node.getParent().demote();
                        node.promote();
                        rightRotation(node,node.getParent());
                        total += 3;
                        return total;
                    }
                    else if (leftEdge == 1 && rightEdge == 2) {
                        rightRotation(node,node.getParent());
                        node.getParent().doubleDemote();

                        node = node.getParent();
                        total += 3;
                    }
                    else if (leftEdge == 2 && rightEdge == 1) {
                        node.demote(); //y
                        leftRotation(node.getRight(),node);
                        node = node.getParent(); //a
                        node.promote();
                        rightRotation(node,node.getParent());
                        node.getRight().doubleDemote(); //z
                        total += 6;
                    }
                }
                if (node != null && node.getParent() != null) {
                    node = node.getParent();
                    rightEdge = node.getHeight() - node.getRight().getHeight();
                    leftEdge = node.getHeight() - node.getLeft().getHeight();
                } else {break;}
            }

            return total;
        }
        private void deleteLeaf(IAVLNode node) {
            IAVLNode parent = node.getParent();
            if (node.isLeftChild()) {
                parent.setLeft(VIRTUAL_NODE);
            } else {
                parent.setRight(VIRTUAL_NODE);
            }
            node.setParent(null);
            if (parent != null) {
                parent.downSize();
            }
        }
        private void deleteUnary(IAVLNode node) {
            if (node.getParent() == null) {
                if (node.getLeft().isRealNode()) {
                    root = node.getLeft();
                    root.setParent(null);
                    node.setLeft(null);
                }
                if (node.getRight().isRealNode()) {
                    root = node.getRight();
                    root.setParent(null);
                    node.setRight(null);
                }
            }
            else if (node.isLeftChild()) {
                if (node.getLeft().isRealNode()) {
                    node.getLeft().setParent(node.getParent());
                    node.getParent().setLeft(node.getLeft());
                } else {
                    node.getRight().setParent(node.getParent());
                    if (node.getParent() != null) {
                        node.getParent().setLeft(node.getRight());
                    }
                }
            }
            else {
                if (node.getLeft().isRealNode()) {
                    node.getLeft().setParent(node.getParent());
                    node.getParent().setRight(node.getLeft());
                } else {
                    node.getRight().setParent(node.getParent());
                    if (node.getParent() != null) {
                        node.getParent().setRight(node.getRight());
                    }
                }
            }
            if (node.getParent() != null) {
                node.getParent().downSize();
            }
        }
        private void deleteBinary(IAVLNode node) {
            IAVLNode successor = node.successor();
            successor.setHeight(node.getHeight());
            if (successor.isLeaf()) {
                deleteLeaf(successor);
            } else {
                deleteUnary(successor);
            }

            successor.setRight(node.getRight());
            successor.setLeft(node.getLeft());
            successor.setParent(node.getParent());
            node.getRight().setParent(successor);
            node.getLeft().setParent(successor);
            if (node.getParent() != null) {
                if (node.isLeftChild()) {
                    node.getParent().setLeft(successor);
                } else {
                    node.getParent().setRight(successor);
                }
                node.getParent().downSize();
            }
        }

        public String min() {
            if (empty()) {
                return null;
            }
            return minNode.getValue();
        }
        private IAVLNode minNode() {
            if (empty()) {
                return null;
            }
            IAVLNode x = getRoot();
            while (x.getLeft().isRealNode()) {
                x = x.getLeft();
            }
            return  x;
        }
        public String max() {
            if (empty()) {
                return null;
            }
            return maxNode.getValue();

        }
        private IAVLNode maxNode() {
            if (empty()) {
                return null;
            }
            IAVLNode x = getRoot();
            while (x.getRight().isRealNode()) {
                x = x.getRight();
            }
            return  x;
        }
        private void updateMinMax(IAVLNode node) {
            if (empty()) {
                minNode = node;
                maxNode = node;
            } else
            if (node.getKey() < minNode.getKey() || !minNode.isRealNode()) {
                minNode = node;
            }
            else if (node.getKey() > maxNode.getKey() || !minNode.isRealNode()) {
                maxNode = node;
            }

        }
        public int[] keysToArray() {
            int[] keyArray = new int[size()];
            if (empty()) {
                return keyArray;
            }
            IAVLNode pointer = minNode();
            for (int i = 0; i < size(); i++) {
                keyArray[i] = pointer.getKey();
                pointer = pointer.successor();
            }
            return keyArray;
        }
        public String[] infoToArray() {
            String[] infoArray = new String[size()];
            if (empty()) {
                return infoArray;
            }
            IAVLNode pointer = minNode();
            for (int i = 0; i < size(); i++) {
                infoArray[i] = pointer.getValue();
                pointer = pointer.successor();
            }
            return infoArray;
        }
        public int size() {
            return treeSize; // to be replaced by student code
        }
        public IAVLNode getRoot() { return root; }
//        public experimentAVLTree[] split(int x) {
//            experimentAVLTree rightTree = new experimentAVLTree();
//            experimentAVLTree leftTree = new experimentAVLTree();
//            IAVLNode node = treePosition(x);
//            if (node.getRight().isRealNode()) {
//                IAVLNode rightNode = node.getRight();
//                node.getRight().setParent(null);
//                node.setRight(VIRTUAL_NODE);
//                rightTree = new experimentAVLTree(rightNode,rightNode.getSize());
//            }
//            if (node.getLeft().isRealNode()) {
//                IAVLNode leftNode = node.getLeft();
//                node.getLeft().setParent(null);
//                node.setLeft(VIRTUAL_NODE);
//                leftTree = new experimentAVLTree(leftNode,leftNode.getSize());
//            }
//            while (node.getParent() != null) {
//                node = node.getParent();
//                IAVLNode right = node.getRight();
//                IAVLNode left = node.getLeft();
//                node.setRight(VIRTUAL_NODE);
//                node.setLeft(VIRTUAL_NODE);
//                if (right.isRealNode()) {
//                    right.setParent(null);
//                }
//                if (left.isRealNode()) {
//                    left.setParent(null);
//                }
//                if (node.getParent() != null) {
//                    if (node.isLeftChild()) {
//                        rightTree.join(node, (new experimentAVLTree(right, right.getSize())));
//                    } else {
//                        leftTree.join(node, (new experimentAVLTree(left, left.getSize())));
//                    }
//                }
//            }
//            return new experimentAVLTree[] {rightTree, leftTree};
//        }
//        public int join(IAVLNode x, experimentAVLTree t) {
//            //int total = 0;
//            if (this.empty()) {
//                return t.insert(x);
//            }
//            if (t.empty()) {
//                return insert(x);
//            }
//            IAVLNode thisRoot = getRoot();
//            IAVLNode otherRoot = t.getRoot();
//            if (thisRoot.getHeight() == otherRoot.getHeight()) {
//                thisRoot.setParent(x);
//                otherRoot.setParent(x);
//                this.root = x;
//                if (thisRoot.getKey() < otherRoot.getKey()) {
//                    x.setLeft(thisRoot);
//                    x.setRight(otherRoot);
//                }
//                else {
//                    x.setRight(otherRoot);
//                    x.setLeft(thisRoot);
//                }
//                x.updateSize();
//                treeSize = size() + t.size() + 1;
//                return 1;
//            }
//            if (thisRoot.getHeight() < otherRoot.getHeight()) { // case 1 - thisHeight < otherHeight && thisKeys < otherKeys
//                //joinDifferentSizes(x,this,t);
//                if (thisRoot.getKey() < otherRoot.getKey()) {
//                    IAVLNode b = otherRoot.getLeft();
//                    while (thisRoot.getHeight() < b.getHeight()) {
//                        b = b.getLeft(); //assuming there is left
//                    }
//                    IAVLNode c = b.getParent();
//                    thisRoot.setParent(x);
//                    x.setLeft(thisRoot);
//                    x.setParent(c);
//                    c.setLeft(x);
//                    b.setParent(x);
//                    x.setRight(b);
//                    x.setHeight(thisRoot.getHeight() + 1);
//                    x.updateSize();
//                    root = otherRoot;
//
//                    int leftEdge = c.getHeight() - x.getHeight();
//                    int rightEdge = c.getHeight() - c.getRight().getHeight();
//                    while (Math.abs(rightEdge-leftEdge) > 1 || leftEdge == 0 || rightEdge == 0) {
//                        if (leftEdge == 0 && rightEdge == 1) {
//                            c.promote();
//                        }
//                        if (leftEdge == 0 && rightEdge == 2) {
//                            //total +=
//                            fixCase02(c);
//                        }
//                        //if (leftEdge == 2 && rightEdge == 0) {
//                        //	total += fixCase20(c);
//                        //}
//                        if (c.getParent() != null) {
//                            c = c.getParent();
//                            rightEdge = c.getHeight() - c.getRight().getHeight();
//                            leftEdge = c.getHeight() - c.getLeft().getHeight();
//                        } else {
//                            break;
//                        }
//
//                    }
//
//                }
//                else if (thisRoot.getKey() > otherRoot.getKey()) { // case 2 - thisHeight < otherHeight && thisKeys > otherKeys
//                    IAVLNode b = otherRoot.getRight();
//                    while (thisRoot.getHeight() < b.getHeight()) {
//                        b = b.getRight(); //assuming there is right
//                    }
//                    IAVLNode c = b.getParent();
//                    thisRoot.setParent(x);
//                    x.setLeft(b);
//                    x.setRight(thisRoot);
//                    x.setParent(c);
//                    c.setRight(x);
//                    b.setParent(x);
//                    x.setHeight(thisRoot.getHeight() + 1);
//                    x.updateSize();
//                    root = otherRoot;
//
//                    int rightEdge = c.getHeight()-x.getHeight();
//                    int leftEdge = c.getHeight() - c.getLeft().getHeight();
//
//                    while (Math.abs(rightEdge-leftEdge)>1 || leftEdge == 0 || rightEdge == 0) {
//                        if (leftEdge == 1 && rightEdge == 0) {
//                            c.promote();
//                        }
//                        //if (leftEdge == 0 && rightEdge == 2) {
//                        //	total += fixCase02(c);
//                        //}
//                        if (leftEdge == 2 && rightEdge == 0) {
//                            //total +=
//                            fixCase20(c);
//                        }
//                        if (c.getParent() != null) {
//                            c = c.getParent();
//                            rightEdge = c.getHeight() - c.getRight().getHeight();
//                            leftEdge = c.getHeight() - c.getLeft().getHeight();
//                        } else {
//                            break;
//                        }
//                    }
//                }
//
//            }
//            else if (thisRoot.getHeight() > otherRoot.getHeight()) { // case 3 - thisHeight > otherHeight && thisKeys < otherKeys
//                //joinDifferentSizes(x,t,this);
//                if (thisRoot.getKey() < otherRoot.getKey()) {
//                    IAVLNode b = thisRoot.getRight();
//                    while (otherRoot.getHeight() < b.getHeight()) {
//                        b = b.getRight(); //assuming there is right
//                    }
//                    IAVLNode c = b.getParent();
//                    otherRoot.setParent(x);
//                    x.setLeft(b);
//                    x.setRight(otherRoot);
//                    x.setParent(c);
//                    c.setRight(x);
//                    b.setParent(x);
//                    x.setHeight(otherRoot.getHeight() + 1);
//                    x.updateSize();
//                    //root = thisRoot;
//
//                    int rightEdge = c.getHeight() - x.getHeight();
//                    int leftEdge = c.getHeight() - c.getLeft().getHeight();
//
//                    while (Math.abs(rightEdge-leftEdge)>1 || leftEdge == 0 || rightEdge == 0) {
//                        if (leftEdge == 1 && rightEdge == 0) {
//                            c.promote();
//                        }
//                        //if (leftEdge == 0 && rightEdge == 2) {
//                        //	total += fixCase02(c);
//                        //}
//                        if (leftEdge == 2 && rightEdge == 0) {
//                            //total +=
//                            fixCase20(c);
//                        }
//                        if (c.getParent() != null) {
//                            c = c.getParent();
//                            rightEdge = c.getHeight() - c.getRight().getHeight();
//                            leftEdge = c.getHeight() - c.getLeft().getHeight();
//                        } else {
//                            break;
//                        }
//                    }
//                }
//                else if (thisRoot.getKey() > otherRoot.getKey()) { // case 4 - thisHeight > otherHeight && thisKeys > otherKeys
//                    IAVLNode b = thisRoot.getLeft();
//                    while (otherRoot.getHeight() < b.getHeight()) {
//                        b = b.getLeft(); //assuming there is left
//                    }
//                    IAVLNode c = b.getParent();
//                    otherRoot.setParent(x);
//                    x.setLeft(otherRoot);
//                    x.setParent(c);
//                    c.setLeft(x);
//                    b.setParent(x);
//                    x.setRight(b);
//                    x.setHeight(otherRoot.getHeight() + 1);
//                    x.updateSize();
//                    //root = thisRoot;
//
//                    int leftEdge = c.getHeight() - x.getHeight();
//                    int rightEdge = c.getHeight() - c.getRight().getHeight();
//
//                    while (Math.abs(rightEdge-leftEdge)>1 || leftEdge == 0 || rightEdge == 0) {
//                        if (leftEdge == 0 && rightEdge == 1) {
//                            c.promote();
//                        }
//                        if (leftEdge == 0 && rightEdge == 2) {
//                            //total +=
//                            fixCase02(c);
//                        }
//                        //if (leftEdge == 2 && rightEdge == 0) {
//                        //	total += fixCase20(c);
//                        //}
//                        if (c.getParent() != null) {
//                            c = c.getParent();
//                            rightEdge = c.getHeight() - c.getRight().getHeight();
//                            leftEdge = c.getHeight() - c.getLeft().getHeight();
//                        } else {
//                            break;
//                        }
//                    }
//                }
//            }
//
//            return Math.abs(thisRoot.getHeight()-otherRoot.getHeight()) + 1;
//        }
//        private void joinDifferentSizes(IAVLNode x, experimentAVLTree lower, experimentAVLTree higher) {
//            IAVLNode lowerRoot = lower.getRoot();
//            IAVLNode higherRoot = higher.getRoot();
//            if (lowerRoot.getKey() < higherRoot.getKey()) {
//                IAVLNode b = higherRoot.getLeft();
//                while (lowerRoot.getHeight() < b.getHeight()) {
//                    b = b.getLeft(); //assuming there is left
//                }
//                IAVLNode c = b.getParent();
//                lowerRoot.setParent(x);
//                x.setLeft(lowerRoot);
//                x.setParent(c);
//                c.setLeft(x);
//                b.setParent(x);
//                x.setRight(b);
//                x.setHeight(lowerRoot.getHeight() + 1);
//                x.updateSize();
//                root = higherRoot;
//
//                int leftEdge = c.getHeight() - x.getHeight();
//                int rightEdge = c.getHeight() - c.getRight().getHeight();
//
//                while (Math.abs(rightEdge-leftEdge) > 1 || leftEdge == 0 || rightEdge == 0) { //added edges not 0 as condition so we can access first if
//                    if (leftEdge == 0 && rightEdge == 1) {
//                        c.promote();
//                    }
//                    if (leftEdge == 0 && rightEdge == 2) {
//                        //total +=
//                        fixCase02(c);
//                    }
//                    //if (leftEdge == 2 && rightEdge == 0) {
//                    //	total += fixCase20(c);
//                    //}
//                    if (c.getParent() != null) {
//                        c = c.getParent();
//                        rightEdge = c.getHeight() - c.getRight().getHeight();
//                        leftEdge = c.getHeight() - c.getLeft().getHeight();
//                    } else {
//                        break;
//                    }
//
//                }
//
//            }
//            else if (lowerRoot.getKey() > higherRoot.getKey()) { // case 2 - thisHeight < otherHeight && thisKeys > otherKeys
//                IAVLNode b = higherRoot.getRight();
//                while (lowerRoot.getHeight() < b.getHeight()) {
//                    b = b.getRight(); //assuming there is right
//                }
//                IAVLNode c = b.getParent();
//                lowerRoot.setParent(x);
//                x.setRight(lowerRoot);
//                x.setLeft(b);
//                b.setParent(x);
//                x.setParent(c);
//                c.setRight(x);
//                x.setHeight(lowerRoot.getHeight() + 1);
//                x.updateSize();
//                root = higherRoot;
//
//                int rightEdge = c.getHeight()-x.getHeight();
//                int leftEdge = c.getHeight() - c.getLeft().getHeight();
//
//                while (Math.abs(rightEdge-leftEdge)>1 || leftEdge == 0 || rightEdge == 0) {
//                    if (leftEdge == 1 && rightEdge == 0) {
//                        c.promote();
//                    }
//                    //if (leftEdge == 0 && rightEdge == 2) {
//                    //	total += fixCase02(c);
//                    //}
//                    if (leftEdge == 2 && rightEdge == 0) {
//                        //total +=
//                        fixCase20(c);
//                    }
//                    if (c.getParent() != null) {
//                        c = c.getParent();
//                        rightEdge = c.getHeight() - c.getRight().getHeight();
//                        leftEdge = c.getHeight() - c.getLeft().getHeight();
//                    } else {
//                        break;
//                    }
//                }
//            }
//
//        }

//        public int[] inOrder(IAVLNode x) { //x is root
//            int length = size();
//            int[] outputArray = new int[length];
//            int[] stack = new int[length];
//            int keyPointer = 0;
//            int key = root.getKey();
//            while (stack[0] != 0 || x.isRealNode()) {
//                if (x.isRealNode()) {
//                    stack[keyPointer] = x.getKey();
//                    keyPointer++;
//                    x = x.getLeft();
//                    key = x.getKey();
//                }
//                else {
//                    outputArray
//                    key = stack[keyPointer];
//
//                }
//            }
//            return outputArray;
//        }


        public interface IAVLNode {
            public int getKey(); // Returns node's key (for virtual node return -1).
            public String getValue(); // Returns node's value [info], for virtual node returns null.

            public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.

            public void setLeft(IAVLNode node); // Sets left child.

            public IAVLNode getRight(); // Returns right child, if there is no right child return null.

            public void setRight(IAVLNode node); // Sets right child.

            public IAVLNode getParent(); // Returns the parent, if there is no parent return null.

            public void setParent(IAVLNode node); // Sets parent.

            public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.

            public int getHeight(); // Returns the height of the node (-1 for virtual nodes).

            public void setHeight(int height); // Sets the height of the node.

            //added functions
            public int getSize();
            public void setSize(int size);
            public void promote();
            public void demote();
            public void doubleDemote();
            public boolean isUnaryNode();
            public boolean isLeaf();
            public boolean isLeftChild();
            public IAVLNode successor();
            public void downSize();
            public void upSize();
            public void updateSize();
            public IAVLNode predecessor();
        }

        /**
         * public class AVLNode
         *
         * If you wish to implement classes other than AVLTree
         * (for example AVLNode), do it in this file, not in another file.
         *
         * This class can and MUST be modified (It must implement IAVLNode).
         */
        public class AVLNode implements IAVLNode {
            private String info;
            private int key;
            private int height;
            private IAVLNode right;
            private IAVLNode left;
            private IAVLNode parent;
            private int size;

            private AVLNode() {
                this.height = -1;
                this.key = -1;
            }

            public AVLNode (int key, String info) {
                this.key = key;
                this.info = info;
                this.height = 0;
                this.size = 1;
                this.right = VIRTUAL_NODE;
                this.left = VIRTUAL_NODE;
                this.parent = null;
            }

            public int getKey() {
                return key;
            }

            public String getValue() {
                return info;
            }

            public IAVLNode getLeft() {
                return left;
            }

            public void setLeft(IAVLNode node) {
                this.left = node;
            }

            public IAVLNode getRight() {
                return right;
            }

            public void setRight(IAVLNode node) {
                this.right = node;
            }

            public IAVLNode getParent() {
                return parent;
            }

            public void setParent(IAVLNode node) {
                this.parent = node;
            }

            public boolean isRealNode() {
                return this.getHeight() != -1;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public void promote() {
                setHeight(getHeight() + 1);
            }

            public void demote() {
                setHeight(getHeight() - 1);
            }

            public void doubleDemote() {setHeight(getHeight() - 2);}

            public boolean isLeaf() {
                return (!getLeft().isRealNode() && !getRight().isRealNode());
            }

            public boolean isUnaryNode() {
                return getLeft().isRealNode() ^ getRight().isRealNode();
            }

            public boolean isLeftChild() {
                int parentKey = getParent().getKey();
                return parentKey > getKey();
                //this == getParent().getLeft()
            }


            public IAVLNode successor(){
                IAVLNode x = this;
                if (x.getKey() == maxNode.getKey()) {
                    return VIRTUAL_NODE;
                }
                if (x.getRight().isRealNode()) {
                    x = x.getRight();
                    while (x.getLeft().isRealNode()) {
                        x = x.getLeft();
                    }
                    return x;
                }
                while (!x.isLeftChild() && x.getParent() != null) {
                    x = x.getParent();
                }
                return x.getParent();
            }
            public IAVLNode predecessor(){
                IAVLNode x = this;
                if (x.getKey() == minNode.getKey()) {
                    return VIRTUAL_NODE;
                }
                if (x.getLeft().isRealNode()) {
                    x = x.getLeft();
                    while (x.getRight().isRealNode()) {
                        x = x.getRight();
                    }
                    return x;
                }
                while (x.isLeftChild() && x.getParent() != null) {
                    x = x.getParent();
                }
                return x.getParent();
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public void upSize() {
                IAVLNode node = this;
                while( node != null) {
                    node.setSize(node.getSize()+1);
                    node = node.getParent();
                }
            }

            public void downSize() {
                IAVLNode node = this;
                while( node != null) {
                    node.setSize(node.getSize()-1);
                    node = node.getParent();
                }
            }

            public void updateSize() {
                IAVLNode node = this;
                while (node != null) {
                    node.setSize(node.getLeft().getSize()+node.getRight().getSize() + 1);
                    node = node.getParent();
                }
            }

            //public AVLNode predecessor(){
            //	return null;
            //}
        }


}
