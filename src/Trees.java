import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trees {
    //Vacio si les pasas A o nulo si le pasas P
    public static Boolean isTreeEmpty(TreeNode sourceNode){
        if(sourceNode==null)
            return true;
        else
            return false;
    }

    //HIJO MAS IZQ
    public static TreeNode getLeftSon(TreeNode node){
        if(node!=null){
            if(node.getNodes()!=null && node.getNodes().size()>0)
                return node.getNodes().get(0);
            else return null;
        }else
            return null;
    }

    //HERMANO DER
    public static TreeNode rightBrother(List<TreeNode> brotherNodes,TreeNode node){
        if(brotherNodes!=null){
            int i=0;
            boolean found=false;
            while(i<brotherNodes.size() && !found){
                if(node.equals(brotherNodes.get(i))){
                    found=true;
                }
                else
                    i++;
            }

            if(i == brotherNodes.size() - 1){
                if (brotherNodes.get(0).getKey() == node.getKey()) {
                    return null;
                }
            }

            if(i < brotherNodes.size() - 1)
                return brotherNodes.get(i+1);
            else
                return null;
        }else
            return null;
    }


    //INFO
    public Integer getData(TreeNode node){

        if(node!=null)
            return node.getKey();
        else
            return null;
    }

    //RAIZ

    public static TreeNode getTreeSource(TreeNode node){
        if (node==null)
            return null;
        else if(node.getDad()==null){
            //ES LA RAIZ
            return node;
        }else{
            return getTreeSource(node.getDad());
        }
    }

    //PADRE

    public static TreeNode getDad(TreeNode node){
        if(node!=null){
            return node.getDad();
        }else
            return null;
    }



}
