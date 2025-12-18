package apsd.classes.containers.collections.concretecollections.bases;

 import apsd.classes.containers.sequences.DynCircularVector;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.Chain;
 import apsd.interfaces.containers.iterators.BackwardIterator;
 import apsd.interfaces.containers.iterators.ForwardIterator;
 import apsd.interfaces.containers.iterators.MutableForwardIterator;
 import apsd.interfaces.containers.sequences.DynVector;
 import apsd.interfaces.containers.sequences.Sequence;
 import apsd.interfaces.traits.Predicate;

/** Object: Abstract list base implementation on (dynamic circular) vector. */
abstract public class VChainBase<Data> implements Chain<Data>{

  protected final DynVector<Data> vec;

  public VChainBase() {
        vec = new DynCircularVector<>();
  }

    public VChainBase(TraversableContainer<Data> con) {
        vec = new DynCircularVector<>(con);

    }

  protected VChainBase(DynVector<Data> vec) {
      this.vec = vec;
  }

  abstract protected VChainBase<Data> NewChain(DynVector<Data> vec);



  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

    @Override
    public Natural Size() {
        return vec.Size();
    }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

    @Override
    public void Clear() {
        vec.Clear();
    }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  // ...

}
