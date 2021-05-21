import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class GeneralTreeNode {
    String key;
    Vector<GeneralTreeNode> child = new Vector<>();

    public static GeneralTreeNode newNode(String key){
        GeneralTreeNode temp = new GeneralTreeNode();
        temp.key = key;
        return temp;
    }

    public static void LevelOrderTraversal(GeneralTreeNode root)
    {
        if (root == null)
            return;

        Queue<GeneralTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty())
        {
            int n = queue.size();

            while (n > 0)
            {
                GeneralTreeNode p = queue.peek();
                queue.remove();
                System.out.print(p.key + " ");

                for (int i = 0; i < p.child.size(); i++)
                    queue.add(p.child.get(i));
                n--;
            }
            System.out.println();
        }

    }

}
