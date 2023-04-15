import java.util.List;
import java.util.Objects;

public class TreeNode {
    private int key;
    private List<TreeNode> nodes;
    private TreeNode dad;

    public TreeNode(int key, List<TreeNode> nodes,TreeNode dad) {
        this.key = key;
        this.nodes = nodes;
        this.dad = dad;
    }

    public TreeNode() {
    }

    public int getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return key == treeNode.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }
    public TreeNode getDad() {
        return dad;
    }

    public void setDad(TreeNode dad) {
        this.dad = dad;
    }

}
