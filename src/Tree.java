import java.util.List;

public class Tree {
    private int ways;

    private Node sourceNode;

    public Tree() {
    }

    public Tree(int ways, Node sourceNode) {
        this.ways = ways;
        this.sourceNode = sourceNode;
    }

    public int getWays() {
        return ways;
    }

    public void setWays(int ways) {
        this.ways = ways;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node sourceNode) {
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

    public void printBPlusTree() {
        printBPlusNode( (BPlusNode) this.sourceNode, "");
    }
    private void printBPlusNode(BPlusNode node, String prefix) {
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
