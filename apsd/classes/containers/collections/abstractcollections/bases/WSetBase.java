package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.classes.containers.collections.abstractcollections.WSet;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
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
        chn.Clear();
    }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
    public boolean Insert (Data dat) {
        if (dat == null) return false;
        return chn.Insert(dat);
    }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

    @Override
    public boolean Remove(Data dat) {
        if (dat == null || chn == null) return false;

        ForwardIterator<Data> itr = chn.FIterator();
        long index = 0;

        while (itr.IsValid()) {
            Data current = itr.GetCurrent();
            if (dat.equals(current)) {
                chn.RemoveAt(Natural.Of(index));
                return true;
            }
            itr.Next();
            index++;
        }

        return false;
    }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

    @Override
    public MutableForwardIterator<Data> FIterator(){
        return chn.FIterator();
    }

    @Override
    public BackwardIterator<Data> BIterator() {
        return chn.BIterator();
    }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

    @Override
    public boolean Filter(Predicate<Data> pred) {
        return chn.Filter(pred);
    }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

    @Override
    public void intersection(Set<Data> other) {
        if (other == null) return;
        chn.Filter(dat -> other.Exists(dat));
    }

    abstract protected WSetBase<Data, Chn> NewInstance();

}
