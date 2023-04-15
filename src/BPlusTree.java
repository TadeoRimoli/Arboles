public class BPlusTree {
    private int ways;
    private BPlusNode sourceNode;

    public BPlusTree() {
    }

    public BPlusTree(int ways, BPlusNode sourceNode) {
        this.ways = ways;
        this.sourceNode = sourceNode;
    }

    public int getWays() {
        return ways;
    }

    public void setWays(int ways) {
        this.ways = ways;
    }

    public BPlusNode getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(BPlusNode sourceNode) {
        this.sourceNode = sourceNode;
    }
}
