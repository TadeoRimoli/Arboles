import java.util.List;

public class BPlusNode extends Node{

    private BPlusNode rightNode;

    public BPlusNode(List<Node> nodes, List<Integer> internalKeys, Node dadNode, Tree tree, BPlusNode rightNode) {
        super(nodes, internalKeys, dadNode, tree);
        this.rightNode = rightNode;
    }

    public BPlusNode(List<Node> nodes, List<Integer> internalKeys, Node dadNode, Tree tree) {
        super(nodes, internalKeys, dadNode, tree);
    }
    public BPlusNode() {
    }
    public BPlusNode(BPlusNode rightNode) {
        this.rightNode = rightNode;
    }

    public void setRightNode(BPlusNode rightNode) {
        this.rightNode = rightNode;
    }

    public BPlusNode getrightNode() {
        return rightNode;
    }


}
