package apsd.classes.containers.deqs;

 import apsd.classes.containers.collections.concretecollections.VList;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.List;
 import apsd.interfaces.containers.deqs.Queue;

/** Object: Wrapper queue implementation. */
public class WQueue<Data> implements Queue<Data>{

  protected final List<Data> lst;

  public WQueue() {lst = new VList<>();}

  public WQueue(List<Data> lst) {this.lst = lst;}

  public WQueue(TraversableContainer<Data> con) {
      lst = new VList<>();
        con.TraverseForward(dat -> {lst.InsertIfAbsent(dat); return false; });

  }

    public WQueue(List<Data> lst, TraversableContainer<Data> con) {
        if (lst == null) throw new NullPointerException("List cannot be null");
        this.lst = lst;
        if (con != null) {
            con.TraverseForward(dat -> {
                Enqueue(dat);
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
        while (lst.Size().ToLong() > 0) {
            lst.RemoveAt(Natural.ZERO);
        }
    }

  /* ************************************************************************ */
  /* Override specific member functions from Queue                            */
  /* ************************************************************************ */

    @Override
    public Data Head() {
        if (lst.Size().IsZero()) return null;
        return lst.GetAt(Natural.ZERO);
    }

    @Override
    public void Dequeue() {
        if (lst.Size().IsZero()) return;
        lst.RemoveAt(Natural.ZERO);
    }

    @Override
    public Data HeadNDequeue(){
        Data head = Head();
        Dequeue();
        return head;
    }

    @Override
    public Data Enqueue(Data dat){
        lst.InsertAt(dat, lst.Size());
        return dat;
    }
}
