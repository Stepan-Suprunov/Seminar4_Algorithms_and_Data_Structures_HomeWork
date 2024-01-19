public class Tree<V extends Comparable<V>> {
    private Node root;

    private class Node {
        private V value;
        private Color color;
        private Node leftchild;
        private Node righthild;
    }

    public boolean add(V value) {
        if (root != null) {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.BLACK;
            return result;
        } else {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }

    private boolean addNode(Node node, V value) {
        if (node.value.compareTo(value) == 0) return false;
        else {
            if (node.value.compareTo(value) > 0) {
                if (node.leftchild != null) {
                    boolean result = addNode(node.leftchild, value);
                    node.leftchild = rebalance(node.leftchild);
                    return result;
                } else {
                    node.leftchild = new Node();
                    node.leftchild.color = Color.BLACK;
                    node.leftchild.value = value;
                    return true;
                }
            } else {
                if (node.righthild != null) {
                    boolean result = addNode(node.righthild, value);
                    node.righthild = rebalance(node.righthild);
                    return result;
                } else {
                    node.righthild = new Node();
                    node.righthild.color = Color.BLACK;
                    node.righthild.value = value;
                    return true;
                }
            }
        }
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.righthild != null && result.righthild.color == Color.RED &&
                    (result.leftchild == null || result.leftchild.color == Color.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.leftchild != null && result.leftchild.color == Color.RED &&
                    result.leftchild.leftchild != null && result.leftchild.leftchild.color == Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.leftchild != null && result.leftchild.color == Color.RED &&
                    result.righthild != null && result.righthild.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
        }
        while (needRebalance);
        return result;
    }

    private Node rightSwap(Node node) {
        Node rightchild = node.righthild;
        Node betweenChild = rightchild.leftchild;
        rightchild.leftchild = node;
        node.righthild = betweenChild;
        rightchild.color = node.color;
        node.color = Color.RED;
        return rightchild;
    }

    private Node leftSwap(Node node) {
        Node leftchild = node.leftchild;
        Node betweenChild = leftchild.righthild;
        leftchild.righthild = node;
        node.leftchild = betweenChild;
        leftchild.color = node.color;
        node.color = Color.RED;
        return leftchild;
    }

    private void colorSwap(Node node) {
        node.righthild.color = Color.BLACK;
        node.leftchild.color = Color.BLACK;
        node.color = Color.RED;
    }

    private enum Color {
        RED, BLACK
    }
}
