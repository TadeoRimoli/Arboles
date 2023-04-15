import java.util.List;

public class BTree {
    private int ways;
    private BNode sourceNode;

    public BTree(int ways) {
        this.ways = ways;
    }

    public int getWays() {
        return ways;
    }

    public void setWays(int ways) {
        this.ways = ways;
    }

    public BNode getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(BNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    public void printBTree() {
        printNode(this.sourceNode, "");
    }

    private void printNode(Node node, String prefix) {
        if (node != null) {
            System.out.println(prefix + "└── " + node.getInternalKeys());
            List<Node> children = node.getNodes();
            if (children != null) {
                for (int i = 0; i < children.size(); i++) {
                    printNode(children.get(i), prefix + (i == children.size() - 1 ? "    " : "│   "));
                }
            }
        }
    }
}
