import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.util.Random;

public class EspecificFunctionsTrees {
    public static int findSmallestKey(TreeNode node) {
        if (node == null) {
            return Integer.MAX_VALUE;
        }
        int currentKey = node.getKey();
        int smallestChildKey = Integer.MAX_VALUE;
        if (node.getNodes() != null) {
            for (TreeNode child : node.getNodes()) {
                int childKey = findSmallestKey(child);
                if (childKey < smallestChildKey) {
                    smallestChildKey = childKey;
                }
            }
        }
        if (smallestChildKey < currentKey) {
            return smallestChildKey;
        } else {
            return currentKey;
        }
    }

    public static TreeNode findNodeByKey(TreeNode node, int keyToFind){
        System.out.println("Searching... "+ node.getKey());
        if(node==null)
            return null;

        if(keyToFind==node.getKey()){
            return node;
        }

        else{
            //find by sons
            TreeNode son=Trees.getLeftSon(node);
            if(son==null) return null;
            while(son!=null){
                TreeNode resultOfSons=findNodeByKey(son,keyToFind);
                if(resultOfSons!=null)
                    return resultOfSons;
                son=Trees.rightBrother(node.getNodes(),son);
            }
            return null;
        }
    }


    public static void insertNode(int key,int dadkey,TreeNode sourceNode){
        if(sourceNode==null)
            return ;

        TreeNode dadNode=findNodeByKey(sourceNode,dadkey);

        TreeNode newNode=new TreeNode(key,null,dadNode);

        if(dadNode.getNodes()!=null){
            dadNode.getNodes().add(newNode);
        }
        else{
            List<TreeNode> nodes=new ArrayList<>();
            nodes.add(newNode);
            dadNode.setNodes(nodes);
        }

        System.out.println("Nodo con clave: "+ key + " insertado.");
    }

    public static TreeNode deleteNode(int keyToDelete, TreeNode sourceNode){
        if(sourceNode==null || keyToDelete==sourceNode.getKey())
            return null;


        TreeNode node=findNodeByKey(sourceNode,keyToDelete);

        if(node!=null){
            node.getDad().getNodes().remove(node);
            if(node.getDad().getNodes().size()==0){
                node.getDad().setNodes(null);
            }
        }

        return sourceNode;
    }

    public static TreeNode createRandomTree(int maxLevels,TreeNode dadNode) {
        Random rand = new Random();
        int key = rand.nextInt(100); // número aleatorio para la clave del nodo
        int numNodes=rand.nextInt(5); //número aleatorio para el número de nodos hijos

        // si el noodo es el padre le agrega un hijo mas, porque rand.nextInt(5) puede darnos un cero y tener solamente como arbol una raiz.
        if(dadNode==null){
            numNodes+=1;
        }

        List<TreeNode> nodes = new ArrayList<>();

        if (maxLevels == 0) {
            // Si alcanzamos el límite de niveles permitidos, no creamos más nodos hijos
            TreeNode node=new TreeNode(key, null,dadNode);
            return node;
        }

        TreeNode node=new TreeNode(key, nodes,null);

        for (int i = 0; i < numNodes; i++) {
            nodes.add(createRandomTree(maxLevels - 1,node)); // llamamos a la función recursivamente con un nivel menos
        }
        if(dadNode==null){
            node.setDad(null);
        }else{
            node.setDad(dadNode);
        }
        return node;
    }

    public static void printTree(TreeNode root) {
        printTree(root, 0);
    }

    private static void printTree(TreeNode node, int level) {
        if (node == null) {
            return;
        }
        // Imprimir valor del nodo
        System.out.println(getIndentation(level) + node.getKey());
        // Imprimir subárboles recursivamente
        if (node.getNodes() != null) {
            for (TreeNode child : node.getNodes()) {
                printTree(child, level + 1);
            }
        }
    }

    private static String getIndentation(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("|  ");
        }
        sb.append("|->");
        return sb.toString();
    }

    public static String getTreeString(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        getTreeString(root, 0, sb);
        return sb.toString();
    }

    private static void getTreeString(TreeNode node, int level, StringBuilder sb) {
        if (node == null) {
            return;
        }
        // Agregar valor del nodo al StringBuilder
        sb.append(getIndentationString(level)).append(node.getKey()).append("\n");
        // Agregar subárboles recursivamente al StringBuilder
        if (node.getNodes() != null) {
            for (TreeNode child : node.getNodes()) {
                getTreeString(child, level + 1, sb);
            }
        }
    }

    private static String getIndentationString(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("|  ");
        }
        sb.append("|--");
        return sb.toString();
    }

    public static void persistTree(Component component, TreeNode sourceNode){
        FileOutputStream fos;
        try{
            fos= new FileOutputStream(new File("./treexml.xml"));
            XMLEncoder xmlEncoder= new XMLEncoder(fos);
            xmlEncoder.writeObject(sourceNode);
            xmlEncoder.close();
            fos.close();
            JOptionPane.showMessageDialog(component,"El arbol se ha guardado correctamente en el archivo treexml.xml");
        }catch(Exception e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(component,"Ha ocurrido un error al guardar el arbol");
        }
    }

    public static TreeNode getTreeFromXML(){
        FileInputStream fis;
        try{
            fis= new FileInputStream(new File("./treexml.xml"));
            XMLDecoder xmlDecoder= new XMLDecoder(fis);
            TreeNode sourceNode=(TreeNode)xmlDecoder.readObject();
            xmlDecoder.close();
            fis.close();
            JOptionPane.showMessageDialog(null,"El arbol se ha leido correctamente del archivo treexml.xml");
            return  sourceNode;
        }catch(Exception e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Ha ocurrido un error al leer el arbol");
            return  null;
        }
    }
}
