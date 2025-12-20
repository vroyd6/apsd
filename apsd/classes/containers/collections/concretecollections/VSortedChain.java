package apsd.classes.containers.collections.concretecollections;

 import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.SortedChain;
 import apsd.interfaces.containers.sequences.DynVector;

/** Object: Concrete set implementation via (dynamic circular) vector. */
public class VSortedChain<Data> extends VChainBase implements SortedChain<Data> { // Must extend VChainBase and implements SortedChain

  public VSortedChain() {super();}

  public VSortedChain(VSortedChain<Data> chn) {super (chn);}

  public VSortedChain(TraversableContainer<Data> con) {super(con);}

   protected VSortedChain(DynVector<Data> vec) {super(vec);}

  @Override
    protected VSortedChain<Data> NewChain(DynVector<Data> vec) {
        return new VSortedChain<>(vec);
    }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  // ...

}
