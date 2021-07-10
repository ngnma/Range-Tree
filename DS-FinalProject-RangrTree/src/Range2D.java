import java.text.DecimalFormat;
import java.util.*;

public class Range2D {
    public static void main(String[] args) {

        /*Description:
        1.input & make arryList of Nodes - > mlist
        2.sort mlist for x
        3.make tree 1D from mlist  & return headX - > set_head()/make_tree()
        4.rangeSearch for all nodes between [x1,x2] from mlist - > ArrayList allXPoint
        5.sort allXPoint for y
        6.make range tree 2D from allXPoint & return headY - > set_head()/make_tree()
        7.rangeSearch for all nodes between [y1,y2] from allXPoint - > ArrayList allYPoint
        8.print allYPoint list
         */


        //make list of all nodes
        ArrayList<NodeTree> mlist = new ArrayList<NodeTree>();
        //1.inputs
        Scanner scanner = new Scanner(System.in);
        int sizeOfList = scanner.nextInt();
        for (int i = 0; i < sizeOfList; i++) {
            double x = scanner.nextDouble();
            mlist.add(new NodeTree(x));
        }
        for (int i = 0; i < sizeOfList; i++) {
            double y = scanner.nextDouble();
            mlist.get(i).y = y;
        }

        //2.sorting x and
        Collections.sort(mlist, new mCompx());
        //3.make range tree 1 D and return headX
        NodeTree headX = new NodeTree(mlist).set_head();

        //loop for some different cases
        int counter = scanner.nextInt();
        for (int i = 0; i < counter; i++) {
            double x1 = scanner.nextDouble();
            double x2 = scanner.nextDouble();
            double y1 = scanner.nextDouble();
            double y2 = scanner.nextDouble();

            //4.search in x range
            ArrayList<NodeTree> allXPoint = new NodeTree(0, 0).rangeSearch1D(headX, x1, x2);//new node(0,0) = for calling method of NodeTree class
//            System.out.println("allx beg");
//            for (int k=0;k<allXPoint.size();k++)
//                System.out.println(allXPoint.get(k).x);
//            System.out.println("allx end");

            //5.sorting y
            Collections.sort(allXPoint, new mCompy());
            //6.make range tree 2 D and return headY
            NodeTree headY = new NodeTree(allXPoint).set_head();
            //7.search in y range
            if(headY==null){
                System.out.println("None");
            }else {
                ArrayList<NodeTree> allYPoint = new NodeTree(0, 0).rangeSearch2D(headY, y1, y2);//new node(0,0) = for calling method of NodeTree class
                //preint answers
                if (allYPoint.size() == 0) {
                    System.out.println("None");
                } else {
                    Collections.sort(allYPoint,new mCompy());
                    DecimalFormat format=new DecimalFormat("0.#");
                    for (int j = 0; j < allYPoint.size(); j++)
                        System.out.print(format.format( allYPoint.get(j).x) + " ");
                    for (int j = 0; j < allYPoint.size(); j++)
                        System.out.print(format.format(allYPoint.get(j).y) + " ");
                }

            }
        }

    }
}

class NodeTree {
    //filds
    double x, y;
    NodeTree r, l;
    ArrayList<NodeTree> children = new ArrayList<NodeTree>();//all nodes down this node

    //constructors
    NodeTree(double x) {
        this.x = x;
    }

    NodeTree(double x, double y) {
        this.x = x;
        this.y = y;
    }

    NodeTree(NodeTree l) {
        this.l = l;
    }

    NodeTree(ArrayList<NodeTree> children) {
        this.children = children;
    }

    //methods

    /*this method just return root after calling make_tree()*/
    NodeTree set_head() {
        try {
            return make_tree(children, true).get(0);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    /*this methode return a list with 1 element
      and set_head() will return than element ( root )*/
    ArrayList<NodeTree> make_tree(ArrayList<NodeTree> mlist, boolean isLeaf) {
        if(mlist.size()==0){// list is empty
            return mlist;
        }
        if (mlist.size()==1){//return root
            return mlist;
        } else {
            ArrayList<NodeTree> newlist = new ArrayList<>();
            int newSize;
            if (mlist.size() % 2 == 1) {
                newSize = (mlist.size() / 2) + 1;
            } else {
                newSize = (mlist.size() / 2);
            }
            for (int i = 0; i < newSize; i++) {
                newlist.add(new NodeTree(mlist.get(i * 2)));//add left child
                if (!isLeaf) {
                    newlist.get(i).children = new ArrayList<NodeTree>(mlist.get(i * 2).children);
                } else {
                    newlist.get(i).children = new ArrayList<NodeTree>();
                    newlist.get(i).children.add(mlist.get(i * 2));
                }
                try {
                    newlist.get(i).r = mlist.get((2 * i) + 1);
                    if (!isLeaf) {
                        newlist.get(i).children.addAll(mlist.get((i * 2) + 1).children);
                    } else {
                        newlist.get(i).children.add(mlist.get((i * 2) + 1));
                    }
                } catch (IndexOutOfBoundsException e) {
                }

                NodeTree p = newlist.get(i).l;
                while (p.r != null) {
                    p = p.r;
                }
                newlist.get(i).x = p.x;
                newlist.get(i).y = p.y;
            }
                return make_tree(newlist, false);

        }
    }

    /*this method will search for all nodes with x in range [x1,x2]*/
    ArrayList<NodeTree> rangeSearch1D(NodeTree p, double x1, double x2) {
        if(x1==x2 && x1==p.x){
            ArrayList<NodeTree> list=new ArrayList<NodeTree>();
            list.add(new NodeTree(p.x,p.y));
            return list;
        }
         if (p.x<=x2 && p.x>=x1){
            ArrayList<NodeTree> list = new ArrayList();
            for (int i = 0; i < p.children.size(); i++) {//for a tree  -> children size != 0
                if (p.children.get(i).x <= x2 && p.children.get(i).x >= x1) {
                    list.add(new NodeTree(p.children.get(i).x, p.children.get(i).y));
                }
            }
            if(p.children.size()==0 && p.x<= x2 && p.x>= x1)//for a leaf -> children size = 0
                list.add(new NodeTree(p.x,p.y));
            return list;
        }
        else if (p.x >= x2) {
            if (p.l != null)
                return rangeSearch1D(p.l, x1, x2);
        } else if (p.x < x1) {
            if (p.r != null)
                return rangeSearch1D(p.r, x1, x2);
        }


        return new ArrayList<NodeTree>();
    }

    /*this method will search for all nodes with y in range [y1,y2]-same as rangeSearch1D*/
    ArrayList<NodeTree> rangeSearch2D(NodeTree p, double y1, double y2) {
        if(y1==y2 && y1==p.y){
            ArrayList<NodeTree> list=new ArrayList<NodeTree>();
            list.add(new NodeTree(p.x,p.y));
            return list;
        }
        if (p.y<=y2 && p.y>=y1){
            ArrayList<NodeTree> list = new ArrayList();
            for (int i = 0; i < p.children.size(); i++) {//for a tree  -> children size != 0
                if (p.children.get(i).y <= y2 && p.children.get(i).y >= y1) {
                    list.add(new NodeTree(p.children.get(i).x, p.children.get(i).y));
                }
            }
            if(p.children.size()==0 && p.y<= y2 && p.y>= y1)//for a leaf -> children size = 0
                list.add(new NodeTree(p.x,p.y));
            return list;
        }
        else if (p.y >= y2) {
            if (p.l != null)
                return rangeSearch2D(p.l, y1, y2);

        } else if (p.y < y1) {
            if (p.r != null)
                return rangeSearch2D(p.r, y1, y2);
        }
        return new ArrayList<NodeTree>();
    }


//        NodeTree make_tree_from_root_to_leaf(NodeTree[] leaves,int first,int last){
//        int mid = (first + last) / 2;
//
//        if(first<last) {
//            leaves[mid].r = make_tree_from_root_to_leaf(leaves, mid + 1, last);
//            leaves[mid].l = make_tree_from_root_to_leaf(leaves, first, mid);
//        }
//
//        return leaves[mid];
//    }

}

class mCompx implements Comparator<NodeTree> {
    @Override
    public int compare(NodeTree o1, NodeTree o2) {
        return (int) (o1.x - o2.x);
    }
}

class mCompy implements Comparator<NodeTree> {
    @Override
    public int compare(NodeTree o1, NodeTree o2) {
        return (int) (o1.y - o2.y);
    }
}
