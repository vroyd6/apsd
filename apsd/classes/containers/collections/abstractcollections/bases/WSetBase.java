package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.classes.containers.collections.abstractcollections.WSet;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract wrapper set base implementation via chain. */
abstract public class WSetBase<Data, Chn extends Chain<Data>> implements Set<Data> {

  protected Chn chn;

  public WSetBase() {ChainAlloc();}

  public WSetBase(Chn chn) {this.chn = chn;}

  public WSetBase(TraversableContainer<Data> con) {
      ChainAlloc();
      con.TraverseForward(dat -> {chn.InsertIfAbsent(dat); return false; });
  }

  public WSetBase(Chn chn, TraversableContainer<Data> con) {
      this.chn = chn;
      con.TraverseForward(dat -> {chn.InsertIfAbsent(dat); return false; });
  }

  abstract protected void ChainAlloc();

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() {
      return chn.Size();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

    @Override
    public void Clear() {
        vec.Clear();
    }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  @Override
  public boolean Remove (Data dat) {
      if (dat == null) return false;
      Natural pos = vec.Search(dat);
      if (pos = null) return false;
      vecShiftLeft(pos);
      return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  public ForwardIterator<Data> FIterator() {
      return vec.FIterator();
  }

    @Override
    public BackwardIterator<Data> BIterator() {
        return vec.BIterator();
    }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  // ...

}
