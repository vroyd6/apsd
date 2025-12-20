package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.sequences.DynCircularVector;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.sequences.DynVector;

import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.interfaces.traits.Predicate;

/** Object: Concrete list implementation on (dynamic circular) vector. */
public class VList<Data> extends VChainBase<Data> implements List<Data> { // Must extend VChainBase and implement List

   public VList() {super();}

   public VList(TraversableContainer<Data> con) {
       super();
       if (con != null) {
           con.TraverseForward(dat -> {
               InsertAt(dat, Size());
               return false;
           });
       }
   }

   protected VList(DynVector<Data> vec) {
       super();
       this.vec = new DynCircularVector<>(vec);
   }

  protected VList<Data> NewChain() {
      return new VList<>();
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableIterableContainer         */
  /* ************************************************************************ */

    @Override
    public MutableForwardIterator<Data> FIterator() {
        return (MutableForwardIterator<Data>) vec.FIterator();
    }

    @Override
    public MutableBackwardIterator<Data> BIterator() {
        return (MutableBackwardIterator<Data>) vec.BIterator();
    }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

    @Override
    public void SetAt(Data dat, Natural pos) {
        vec.SetAt(dat, pos);
    }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

    @Override
    public void InsertAt(Data dat, Natural pos) {
        vec.ShiftRight(pos);
        vec.SetAt(dat, pos);
    }

    @Override
    public VList<Data> SubSequence(Natural start, Natural end) {
        DynVector<Data> subVec = vec.SubVector(start, end);
        return new VList<>(subVec);
    }

    @Override
    public boolean TraverseForward(Predicate<Data> fun) {
        return vec.TraverseForward(fun);
    }

    @Override
    public boolean TraverseBackward(Predicate<Data> fun) {
        return vec.TraverseBackward(fun);
    }

    @Override
    public boolean Exists(Data dat) {
        if (dat == null) return false;
        return vec.Exists(dat);
    }

    @Override
    public boolean InsertIfAbsent(Data dat) {
        if (!Exists(dat)) {
            InsertAt(dat, Size());
            return true;
        }
        return false;
    }

    @Override
    public void RemoveOccurrences(Data dat) {
        if (dat == null || Size().IsZero()) return;

        Natural i = vec.Search(dat);
        if (i == null) return;

        Natural count = Natural.ZERO;
        Natural pos = i;

        while (pos.compareTo(Size()) < 0 && vec.GetAt(pos).equals(dat)) {
            count = count.Increment();
            pos = pos.Increment();
        }
        if (count.IsZero()) return;
        vec.ShiftLeft(i, count);
    }

    @Override
    public void RemoveAt(Natural pos) {
        vec.AtNRemove(pos);
    }

    @Override
    public Chain<Data> SubChain(Natural start, Natural end) {
        DynVector<Data> subVec = vec.SubVector(start, end);
        return new VList<>(subVec);
    }



    @Override
    protected VChainBase<Data> NewChain(DynVector<Data> vec) {
        return new VList<>(vec);
    }


    @Override
    public List<Data> SubList(Natural fromIndex, Natural toIndex) {
        return SubSequence(fromIndex, toIndex);
    }
}
