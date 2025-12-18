package apsd.classes.containers.deqs;

 import apsd.classes.containers.collections.concretecollections.VList;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.List;
 import apsd.interfaces.containers.deqs.Queue;

/** Object: Wrapper queue implementation. */
public class WQueue<Data> implements Queue<Data>{ // Must implement Queue

  protected final List<Data> lst;

  public WQueue() {lst = new Vlist<>();}

  public WQueue(List<Data> lst) {this.lst = lst;}

  public WQueue(TraversableContainer<Data> con) {
      lst = new VList<>();
        con.TraverseForward(dat -> {lst.InsertIfAbsent(dat); return false; });

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

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from Queue                            */
  /* ************************************************************************ */

  // ...

}
