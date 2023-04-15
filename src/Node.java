import java.util.List;

public class Node {

    private List<Node> nodes;
    private List<Integer> internalKeys;
    private Node  dadNode;

    private Tree tree;

    public Node getDadNode() {
        return dadNode;
    }

    public void setDadNode(Node  dadNode) {
        this.dadNode = dadNode;
    }

    public Node(List<Node> nodes, List<Integer> internalKeys,Node dadNode,Tree tree) {
        this.nodes = nodes;
        this.internalKeys = internalKeys;
        this.dadNode = dadNode;
        this.tree = tree;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public Node() {
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Integer> getInternalKeys() {
        return internalKeys;
    }

    public void setInternalKeys(List<Integer> internalKeys) {
        this.internalKeys = internalKeys;
    }

    public void insertKeyInOrder(Integer nuevoEntero) {
        if (internalKeys.isEmpty()) { // Si la lista está vacía, se agrega el entero al principio
            internalKeys.add(nuevoEntero);
        } else { // Si no está vacía, se busca la posición donde insertarlo para mantener el orden
            int posicion = 0;
            while (posicion < internalKeys.size() && internalKeys.get(posicion) < nuevoEntero) {
                posicion++;
            }
            internalKeys.add(posicion, nuevoEntero);
        }
    }
}
