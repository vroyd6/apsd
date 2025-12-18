package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.OrderedSet;
import apsd.interfaces.containers.collections.SortedChain;

/** Object: Abstract wrapper set base implementation via chain. */
abstract public class WOrderedSetBase<Data extends Comparable<? super Data>, Chn extends SortedChain<Data>> extends WSetBase<Data, Chn> implements OrderedSet<Chn> {

    public WOrderedSetBase() {super();}

    public WOrderedSetBase(Chn chn) { super(chn);}

    public WOrderedSetBase(TraversableContainer<Data> con) {super(con);}

    public WOrderedSetBase(Chn chn, TraversableContainer<Data> con) {super(chn, con);}

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

    @Override
    public boolean isEqual(IterableContainer<Data> con) {return chn.isEqual (con);}

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  @Override
    public Data Min() {return chn.Min(); }

}
