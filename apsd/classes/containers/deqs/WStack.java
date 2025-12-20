package apsd.classes.containers.deqs;

 import apsd.classes.containers.collections.concretecollections.VList;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.List;
 import apsd.interfaces.containers.deqs.Stack;
 import apsd.interfaces.traits.Clearable;

/** Object: Wrapper stack implementation. */
public class WStack<Data> implements Stack<Data> {

   protected final List<Data> lst;

   public WStack() {
     this.lst = new VList<>();
   }

   public WStack(List<Data> lst) {
     if (lst == null) throw new NullPointerException("List cannot be null");
     this.lst = lst;
   }

   public WStack(TraversableContainer<Data> con) {
       if (con == null) throw new NullPointerException("Container cannot be null");
       this.lst = new VList<>();
       con.TraverseForward(dat -> {
           Push(dat);
           return false;
       });
   }

   public WStack(List<Data> lst, TraversableContainer<Data> con) {
       if (lst == null) throw new NullPointerException("List cannot be null");
       this.lst = lst;
       if (con != null) {
           con.TraverseForward(dat -> {
               lst.Insert(dat);
               return false;
           });
       }
   }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

    @Override
    public Natural Size() {
        return lst.Size();
    }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

    @Override
    public void Clear() {
        ((Clearable) lst).Clear();
    }

  /* ************************************************************************ */
  /* Override specific member functions from Stack                            */
  /* ************************************************************************ */

    @Override
    public void Push(Data item) {
        lst.Insert(item);
    }

    @Override
    public Data Top() {
        if (lst.Size().IsZero()) return null;
        return lst.GetAt(Natural.Of(lst.Size().ToLong() - 1));
    }

    @Override
    public void Pop() {
        if (!lst.Size().IsZero()) {
            lst.RemoveAt(Natural.Of(lst.Size().ToLong() - 1));
        }

    }

    @Override
    public void SwapTop(Data x) {
        if (lst.Size().IsZero()) {
            lst.Insert(x);
        } else {
            Natural last = Natural.Of(lst.Size().ToLong() - 1);
            lst.SetAt(x, last);
        }
    }

    @Override
    public Data TopNSwap(Data data) {
        if (lst.Size().IsZero()) {
            lst.Insert(data);
            return null;
        } else {
            Natural last = Natural.Of(lst.Size().ToLong() - 1);
            Data oldTop = lst.GetAt(last);
            lst.SetAt(data, last);
            return oldTop;
        }
    }

}
