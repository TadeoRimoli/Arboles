import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BPlusFunctions {
    public static void insert(Tree tree, Integer key) {

        // Las inserciones se hacen en nodos hoja.
        //No hay raiz, debemos crearla
        BPlusNode nodeToInspect = null;
        if (tree.getSourceNode() == null) {
            List<Integer> internalKeys = new ArrayList<>();
            internalKeys.add(key);
            BPlusNode sourceNode = new BPlusNode(null, internalKeys, null,tree,null);
            tree.setSourceNode(sourceNode);
        } else {
            //Node to inspect al salir del bucle seria el nodo en el que debemos insertar
            nodeToInspect = (BPlusNode) findLeafNode(tree.getSourceNode(), key);
            //Si el nodo hoja tiene menos elementos que el máximo número de elementos legales
            //entonces hay lugar para uno más
            if (nodeToInspect.getInternalKeys().size() < nodeToInspect.getTree().getWays() - 1) {
                //insertar respteando el orden de los elementos
                nodeToInspect.insertKeyInOrder(key);
            } else {
                //no hay lugar para uno mas en el nodo que corresponde
                //De otra forma, el nodo debe ser dividido en dos nodos.
                /*Se escoge el valor medio entre los elementos del nodo y el nuevo elemento.
                Los valores menores que el valor medio se colocan en el nuevo nodo izquierdo,
                y los valores mayores que el valor medio se colocan en el nuevo nodo derecho;
                el valor medio actúa como valor separador.
                El valor separador se debe colocar en el nodo padre, lo que puede provocar que el padre sea dividido en dos, y así sucesivamente.
                */

                nodeToInspect.insertKeyInOrder(key);
                Integer middleKey;

                if (nodeToInspect.getInternalKeys().size() % 2 != 0) {
                    //vias pares, al agregar uno, internals keys impares
                    middleKey = nodeToInspect.getInternalKeys().get((tree.getWays() / 2));
                } else {
                    //si hay 4 nodos, elige el segundo, redonde pa abajo
                    middleKey = nodeToInspect.getInternalKeys().get(tree.getWays() / 2);
                }
                //eliminar lo que este a la derecha de middle key y middle key, new Node a la derecha
                BPlusNode newNode = new BPlusNode(null, null, nodeToInspect.getDadNode(),tree,null);
                nodeToInspect.setRightNode(newNode);
                List<Integer> subList = nodeToInspect.getInternalKeys().subList(nodeToInspect.getInternalKeys().indexOf(middleKey), nodeToInspect.getInternalKeys().size());
                List<Integer> subListToPersist = new ArrayList<>(subList);
//                subListToPersist.remove(middleKey);
                newNode.setInternalKeys(subListToPersist);

                subList.clear();
                BPlusNode newSourceNode = new BPlusNode();
                List<Integer> list = new ArrayList<>();

                List<Node> listNodes = new ArrayList<>();
                listNodes.add(nodeToInspect);
                listNodes.add(newNode);
                list.add(middleKey);

                if (nodeToInspect.getDadNode() != null) {
                    nodeToInspect.getDadNode().getNodes().add(newNode);
                    if (nodeToInspect.getDadNode().getInternalKeys().size() < tree.getWays() - 1) {
                        nodeToInspect.getDadNode().insertKeyInOrder(middleKey);
                    } else {
                        //split del padre
                        splitNode((BPlusNode) nodeToInspect.getDadNode(), middleKey);
                    }
                } else {
                    // new source node
                    newSourceNode.setDadNode(nodeToInspect.getDadNode());
                    if (nodeToInspect.getDadNode() != null && nodeToInspect.getDadNode().getNodes() != null)
                        listNodes.addAll(nodeToInspect.getDadNode().getNodes());
                    nodeToInspect.setDadNode(newSourceNode);
                    newNode.setDadNode(newSourceNode);
//                    newNode.getInternalKeys().remove(middleKey);
                    newSourceNode.setNodes(listNodes);
                    newSourceNode.setTree(tree);
                    newSourceNode.setInternalKeys(list);
                    tree.setSourceNode(newSourceNode);
                }
            }
        }
    }
    private static void splitNode(BPlusNode actualNode,int middleKey){
        actualNode.insertKeyInOrder(middleKey);
        if(actualNode.getInternalKeys().size() % 2 != 0){
            //vias pares, al agregar uno, internals keys impares
            middleKey = actualNode.getInternalKeys().get((actualNode.getTree().getWays()/2));
        }else{
            //si hay 4 nodos, elige el segundo, redonde pa abajo
            middleKey = actualNode.getInternalKeys().get(actualNode.getTree().getWays()/2);
        }
        Integer index=actualNode.getInternalKeys().indexOf(middleKey);
        BPlusNode newRightNode = new BPlusNode();
        List<Integer> subList=new ArrayList<>(actualNode.getInternalKeys().subList(index,actualNode.getInternalKeys().size()));
        List<Integer> subListToPersist=new ArrayList<>(subList);
        subListToPersist.remove((Integer) middleKey);
        newRightNode.setTree(actualNode.getTree());
        newRightNode.setInternalKeys(subListToPersist);
        newRightNode.setNodes(new ArrayList<>(actualNode.getNodes().subList(index+1, actualNode.getInternalKeys().size()+1)));
        for (Node child : newRightNode.getNodes()) {
            child.setDadNode(newRightNode);
        }
        actualNode.setNodes(actualNode.getNodes().subList(0,index+1));
        actualNode.setInternalKeys(new ArrayList<>(actualNode.getInternalKeys().subList(0,index)));
        actualNode.setRightNode(null);

        if(actualNode==actualNode.getTree().getSourceNode()){
            BPlusNode newRoot = new BPlusNode();
            List<Integer> keys= new ArrayList<>();
            keys.add(middleKey);
            newRoot.setInternalKeys(keys);
            newRoot.setNodes(Arrays.asList(actualNode, newRightNode));
            newRoot.setTree(actualNode.getTree());
            actualNode.setDadNode(newRoot);
            newRightNode.setDadNode(newRoot);
            actualNode.getTree().setSourceNode(newRoot);
        }else{
            newRightNode.setDadNode(actualNode.getDadNode());
            List <Node> list = new ArrayList<>(actualNode.getDadNode().getNodes());
            list.add(newRightNode);
            actualNode.getDadNode().setNodes(list);
            // set right brother
//actualNode.getTree().getSourceNode().getInternalKeys().size()<actualNode.getTree().getWays()-1)
            if(actualNode.getDadNode().getInternalKeys().size()<actualNode.getTree().getWays()-1)
                actualNode.getDadNode().insertKeyInOrder(middleKey);
            else{
                ((BPlusNode) actualNode.getDadNode()).setRightNode(null);
                splitNode((BPlusNode) actualNode.getDadNode(),middleKey);
            }
        }
    }
    public static boolean searchNodeByKey(Tree tree,int key){
        if(tree==null || tree.getSourceNode()==null){
//            System.out.println("El arbol o el nodo raiz es nulo...");
            return false;
        }else{
            return searchNodeInTree(tree.getSourceNode(),key);
        }
    }

    private static boolean searchNodeInTree (Node actualNode,int key){
        int i=0;
        boolean find=false;
        if(actualNode.getInternalKeys().contains(key)){
//            System.out.println("Nodo con key " + key + " encontrado en nodo raiz, indice "+actualNode.getInternalKeys().indexOf(key) );
            return true;
        }else
        if(actualNode.getNodes()==null || actualNode.getNodes().isEmpty()){
//                System.out.println("El arbol no contiene un nodo con la key " + key);
            return false;
        }
        else{
            //busca en que nodo buscar la key
            return searchNodeInTree(searchNodeToSearchOrInsert(actualNode,key),key);
        }
    }
    public static Node searchNodeToSearchOrInsert(Node actualNode,int key){
        boolean find=true;
        int i=0;

        while(i<actualNode.getInternalKeys().size() && find){
            if(actualNode.getInternalKeys().get(i)>key)
                find=false;
            else
                i++;
        }
        return actualNode.getNodes().get(i);
    }
    public static Node findLeafNode(Node actualNode,int nuevoEntero) {
        while (actualNode.getNodes()!=null && !actualNode.getNodes().isEmpty()) { // mientras no sea una hoja
            int posicion = 0;
            while (posicion < actualNode.getInternalKeys().size() && actualNode.getInternalKeys().get(posicion) <= nuevoEntero) {
                posicion++;
            }
            actualNode = actualNode.getNodes().get(posicion); // se mueve al siguiente nodo
        }
        return actualNode; // retorna el nodo hoja encontrado
    }

    public static void printBPlusTree(BPlusNode node) {
        if (node == null) {
            return;
        }
        // Si el nodo es una hoja, imprimimos sus elementos
        if (node.getNodes()==null || node.getNodes().isEmpty()) {
            for (int key : node.getInternalKeys()) {
                System.out.print(key + " ");
            }
            // Si hay un nodo derecho, seguimos recorriendo
            if (node.getrightNode() != null) {
                printBPlusTree(node.getrightNode());
            }
        } else {
            // Si el nodo no es una hoja, seguimos recorriendo por el hijo más a la izquierda
            if(node.getrightNode()!=null)
                System.out.print(node.getInternalKeys().toString() + " Tienen hermano derecho");
            printBPlusTree((BPlusNode) node.getNodes().get(0));
        }
    }
}
