/*
 * Name: Felix Najera
 * PID:  A17618969
 */

import java.util.*;

/**
 * Binary search tree implementation.
 *
 * @author Felix Najera
 * @since  8/27/2024
 */
public class BSTree<T extends Comparable<? super T>> implements Iterable {

    /* * * * * BST Instance Variables * * * * */

    private int nelems; // number of elements stored
    private BSTNode root; // reference to root node

    /* * * * * BST Node Inner Class * * * * */

    protected class BSTNode {

        T key;
        LinkedList<T> dataList;
        BSTNode left;
        BSTNode right;

        /**
         * A constructor that initializes the BSTNode instance variables.
         *
         * @param left     Left child
         * @param right    Right child
         * @param dataList Linked list of related info
         * @param key      Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, LinkedList<T> dataList, T key) {
            this.left = left;
            this.right = right;
            this.dataList = dataList;
            this.key = key;
        }

        /**
         * A constructor that initializes BSTNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty LinkedList for the node.
         *
         * @param left  Left child
         * @param right Right child
         * @param key   Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, T key) {
            this(left, right, new LinkedList<T>(), key);
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public T getKey() {
            return key;
        }

        /**
         * Return the left child of the node
         *
         * @return The left child of the node
         */
        public BSTNode getLeft() {

            return left;
        }

        /**
         * Return the right child of the node
         *
         * @return The right child of the node
         */
        public BSTNode getRight() {

            return right;
        }

        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public LinkedList<T> getDataList() {

            return dataList;
        }

        /**
         * Setter for left child of the node
         *
         * @param newleft New left child
         */
        public void setLeft(BSTNode newleft) {
            left = newleft;
        }

        /**
         * Setter for right child of the node
         *
         * @param newright New right child
         */
        public void setRight(BSTNode newright) {
            right = newright;
        }

        /**
         * Setter for the linked list of the node
         *
         * @param newData New linked list
         */
        public void setDataList(LinkedList<T> newData) {
            dataList = newData;
        }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewInfo(T data) {
            dataList.add(data);
        }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removeInfo(T data) {
            return dataList.remove(data);
        }
    }

    /* * * * * BST Methods * * * * */

    /**
     * 0-arg constructor that initializes root to null and nelems to 0
     */
    public BSTree() {
        nelems = 0;
        root = null;
    }

    /**
     * Return the root of BSTree. Returns null if the tree is empty
     *
     * @return The root of BSTree, null if the tree is empty
     */
    public BSTNode getRoot() {
        return root;
    }

    /**
     * Return the BST size
     *
     * @return The BST size
     */
    public int getSize() {
        return nelems;
    }

    /**
     * Insert a key into BST
     *
     * @param key
     * @return true if insertion is successful and false otherwise
     */
    public boolean insert(T key) {
        // insertHelper?
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        root = insertHelper(root, key);
        return true;
    }
    private BSTNode insertHelper(BSTNode node, T key) {
        if (node == null) {
            nelems++;
            return new BSTNode(null, null, key);
        }
        
        if (key.compareTo(node.key) < 0) {
            node.left = insertHelper(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insertHelper(node.right, key);
        }
        
        return node;
    }
    /**
     * Return true if the 'key' is found in the tree, false otherwise
     *
     * @param key To be searched
     * @return True if the 'key' is found, false otherwise
     * @throws NullPointerException If key is null
     */
    public boolean findKey(T key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        return findHelper(root, key);
    }
    private boolean findHelper(BSTNode node, T key) {
        if (node == null) {
            return false;
        }
        
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            return findHelper(node.left, key);
        } else if (compare > 0) {
            return findHelper(node.right, key);
        } else {
            return true;
        }
    }
    /**
     * Insert 'data' into the LinkedList of the node whose key is 'key'
     *
     * @param key  Target key
     * @param data To be added to key's LinkedList
     * @throws NullPointerException     If either key or data is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public void insertData(T key, T data) {
        if (key == null || data == null) {
            throw new NullPointerException("Both key and data must be non-null");
        }
        
        BSTNode node = findNode(root, key);
        if (node != null) {
            node.addNewInfo(data);
        } else {
            throw new IllegalArgumentException("Key not found in the tree");
        }
    }

    private BSTNode findNode(BSTNode node, T key) {
        if (node == null) {
            return null;
        }
        
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            return findNode(node.left, key);
        } else if (compare > 0) {
            return findNode(node.right, key);
        } else {
            return node;
        }
    }

    /**
     * Return the LinkedList of the node with key value 'key'
     *
     * @param key Target key
     * @return LinkedList of the node whose key value is 'key'
     * @throws NullPointerException     If key is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public LinkedList<T> findDataList(T key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        
        BSTNode node = findNode(root, key);
        if (node != null) {
            return node.getDataList();
        } else {
            throw new IllegalArgumentException("Key not found in the tree");
        }
    }

    /**
     * Return the height of the tree
     *
     * @return The height of the tree, -1 if BST is empty
     */
    public int findHeight() {
        return findHeightHelper(root);
    }

    /**
     * Helper for the findHeight method
     *
     * @param root Root node
     * @return int The height of the tree, -1 if BST is empty
     */
    private int findHeightHelper(BSTNode root) {
        if (root == null) {
            return -1;
        }
        
        return 1 + Math.max(findHeightHelper(root.left), findHeightHelper(root.right));
    }

    /* * * * * BST Iterator * * * * */

    public class BSTree_Iterator implements Iterator<T> {
        // stack of nodes
        private Stack<BSTNode> stack;

        public BSTree_Iterator() {
            stack = new Stack<>();
            populateStack(root);
        }


        private void populateStack(BSTNode node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        // where were these methods used earlier
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            BSTNode currentNode = stack.pop();
            T result = currentNode.getKey();

            if (currentNode.getRight() != null) {
                populateStack(currentNode.getRight());
            }

            return result;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BSTree_Iterator();
    }

    /* * * * * Extra Credit Methods * * * * */

    public ArrayList<T> intersection(Iterator<T> iter1, Iterator<T> iter2) {
        ArrayList<T> result = new ArrayList<>();

        while (iter1.hasNext() && iter2.hasNext()) {
            T elem1 = iter1.next();
            T elem2 = iter2.next();

            int compare = elem1.compareTo(elem2);
            if (compare == 0) {
                result.add(elem1);
            } else if (compare < 0) {
                iter1.next();
            } else {
                iter2.next();
            }
        }

        return result;
    }

    public T levelMax(int level) {
        if (level < 0) {
            return null;
        }

        ArrayList<T> nodesAtLevel = new ArrayList<>();
        traverseLevel(root, level, 0, nodesAtLevel);

        if (nodesAtLevel.isEmpty()) {
            return null;
        }

        T max = nodesAtLevel.get(0);
        for (T node : nodesAtLevel) {
            if (node.compareTo(max) > 0) {
                max = node;
            }
        }

        return max;
    }

    private void traverseLevel(BSTNode node, int targetLevel, int currentLevel, ArrayList<T> nodesAtLevel) {
        if (node == null) {
            return;
        }

        if (currentLevel == targetLevel) {
            nodesAtLevel.add(node.getKey());
        } else {
            traverseLevel(node.getLeft(), targetLevel, currentLevel + 1, nodesAtLevel);
            traverseLevel(node.getRight(), targetLevel, currentLevel + 1, nodesAtLevel);
        }
    }
}
