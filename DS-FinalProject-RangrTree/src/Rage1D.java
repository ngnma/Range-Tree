public class Rage1D {
static int counter=0;
    public static void main(String[] args) {
        Node1D []sortedlist={new Node1D(22),new Node1D(27),new Node1D(4),new Node1D(7),
                new Node1D(20),new Node1D(9),new Node1D(1),new Node1D(15),
                new Node1D(17),new Node1D(12),new Node1D(14),new Node1D(24),
                new Node1D(25),new Node1D(29),new Node1D(3),new Node1D(31)};

        Node1D [] sortedlist2={new Node1D(1),new Node1D(2),new Node1D(3),new Node1D(4),new Node1D(5),new Node1D(6),new Node1D(7),new Node1D(8)};

//        Node1D [] sortedlist3={new Node1D(1),new Node1D(2),new Node1D(3),new Node1D(4)};


        Node1D head=makeTreex(sortedlist,16)[0];
        mymergesort(sortedlist,0,sortedlist.length-1);
        System.out.println(rangeSearch(head,20).index);
    }
    static Node1D[] makeTreex(Node1D[] list, int n) {
        counter++;
        if (n == 1) {
            return list;
        } else {
            Node1D[] newlist;
            if (n % 2 == 1) {
                newlist = new Node1D[(n / 2) + 1];
            } else {
                newlist = new Node1D[n / 2];
            }
            for (int i = 0; i < newlist.length; i++) {
                newlist[i] = new Node1D(0);
                newlist[i].l = list[2 * i];
                try {
                    newlist[i].r = list[(2 * i )+ 1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }

                Node1D p = newlist[i].l;
                while (p.r != null) {
                    p = p.r;
                }
                newlist[i].value = p.value;
            }
            if (n % 2 == 1) {
                return makeTreex(newlist, (n / 2)+1);
            } else {
                return makeTreex(newlist, n / 2);
            }
        }
    }
    static Node1D rangeSearch(Node1D head,int x){
        Node1D p= head;
        while (true){
            if(p.value>=x){
                p=p.l;
            }else{
                p=p.r;
            }
            if(p.r==null && p.l==null){break;}
        }
        return p;
    }
    static void mymergesort(Node1D[] a,int l,int r){
        if(l<r){
            int med=(l+r)/2;
            mymergesort(a,l,med);
            mymergesort(a,med+1,r);
            mergin2arrays(a,l,med,r);
        }

    }
    static void mergin2arrays(Node1D[] a,int l, int med, int r){
        Node1D [] a1=new Node1D[med-l+1];
        Node1D [] a2=new Node1D[r-med];
        for(int i=0;i<a1.length;i++) {
            a1[i] = new Node1D(a[l + i].value);
            a1[i].r=a[l+i].r;
            a1[i].l=a[l+i].l;
        }
        for(int i=0;i<a2.length;i++){
            a2[i]=new Node1D(a[med+1+i].value);
            a2[i].r=a[med+1+i].r;
            a2[i].l=a[med+1+i].l;
        }

        int i = 0, j = 0,k=l;
        while (i<a1.length && j<a2.length){
            if(a1[i].value<=a2[j].value){
                a[k].value=a2[i].value;
                a[k].l=a2[i].l;
                a[k].r=a2[i].r;
                i++;a[k].index=k;
            }else {
                a[k].value=a2[j].value;
                a[k].r=a2[j].r;
                a[k].l=a2[j].l;
                j++;a[k].index=k;
            }
            k++;
        }
        while (i<a1.length){
            a[k].value=a1[i].value;
            a[k].r=a1[i].r;
            a[k].l=a1[i].l;
            a[k].index=k;i++;k++;
        }
        while (j<a2.length){
            a[k].value=a2[j].value;
            a[k].r=a2[j].r;
            a[k].l=a2[j].l;
            a[k].index=k;j++;k++;
        }
    }
}
class Node1D{
    int value,index;
    Node1D l,r;
    Node1D(int value){
        this.value=value;
    }

}

