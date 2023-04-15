import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        //vista para arbol n ario
         TreeView gui = new TreeView();
         gui.getFrame().setVisible(true);

         //ARBOL PERMITE UN NODE
        Tree tree1=new Tree(5,null);

        //INSERCION ARBOL B

        for(int i=10;i<63;i++){
            BTreeFunctions.insert(tree1,i);
        }
        tree1.printBTree();

        //INSERCION ARBOL B+

        Tree tree2=new Tree(5,null);

        for(int i=10;i<63;i++){
            BPlusFunctions.insert(tree2,i);
        }
        BPlusFunctions.printBPlusTree((BPlusNode) tree2.getSourceNode());

    }
}
