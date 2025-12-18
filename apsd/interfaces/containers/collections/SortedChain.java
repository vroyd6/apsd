package apsd.interfaces.containers.collections;

 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.IterableContainer;
 import apsd.interfaces.containers.sequences.SortedSequence;
 import apsd.interfaces.traits.Predicate;

public interface SortedChain<Data extends Comparable<? super Data>> extends OrderedChain<Data>, SortedSequence<Data> { // Must extend OrderedChain and SortedSequence

  // SearchPredecessor
    default Natural SearchPredecessor(Data dat) {
        if (isEmpty() || dat == null) return null;

        Natural predIndex = null;
        for (Natural i = Natural.ZERO; i.compareTo(Size()) < 0; i = i.Increment()) {
        Data current = GetAt(i);
        if (current.compareTo(dat) < 0) {
            predIndex = i;
        } else {
            break;
        }
        }
        return predIndex;
    }

  // SearchSuccessor
    default Natural SearchSuccessor(Data dat) {
        if (isEmpty() || dat == null) return null;

        for (Natural i = Natural.ZERO; i.compareTo(Size()) < 0; i = i.Increment()) {
        Data current = GetAt(i);
        if (current.compareTo(dat) > 0) {
            return i;
        }
        }
        return null;
    }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

    @Override
    default Natural Search(Data dat) {
        if (isEmpty() || dat == null) return null;

        for (Natural i = Natural.ZERO; i.compareTo(Size()) < 0; i = i.Increment()) {
        if (GetAt(i).compareTo(dat) == 0) {
            return i;
        }
        }

        return null;
    }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

   default void Intersection(SortedChain<Data> chn) {
     Natural i = Natural.ZERO, j = Natural.ZERO;
     while (i.compareTo(Size()) < 0 && j.compareTo(chn.Size()) < 0) {
       int cmp = GetAt(i).compareTo(chn.GetAt(j));
       if (cmp < 0) {
         RemoveAt(i);
       } else {
         j = j.Increment();
         if (cmp == 0) { i = i.Increment(); }
       }
     }
     while (i.compareTo(Size()) < 0) {
       RemoveAt(i);
     }
   }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

    @Override
    default Data Max() {
        if (isEmpty()) return null;
        return GetAt(Size().Decrement());
    }

    @Override
    default Data Min() {
        if (isEmpty()) return null;
        return GetAt(Natural.ZERO);
    }

    @Override
    default void RemoveMax() {
        if (!isEmpty()) {
            RemoveAt(Size().Decrement());
        }
    }

    @Override
    default void RemoveMin() {
        if (!isEmpty()) {
            RemoveAt(Natural.ZERO);
        }
    }

    @Override
    default Data MaxNRemove() {
        if (isEmpty()) return null;
        Data maxData = GetAt(Size().Decrement());
        RemoveAt(Size().Decrement());
        return maxData;
    }

    @Override
    default Data MinNRemove() {
        if (isEmpty()) return null;
        Data minData = GetAt(Natural.ZERO);
        RemoveAt(Natural.ZERO);
        return minData;
    }

    @Override
    default Data Predecessor(Data dat) {
        Natural predIndex = SearchPredecessor(dat);
        if (predIndex != null) {
            return GetAt(predIndex);
        }
        return null;
    }

    @Override
    default void RemovePredecessor(Data dat) {
        Natural predIndex = SearchPredecessor(dat);
        if (predIndex != null) {
            RemoveAt(predIndex);
        }
    }

    @Override
    default Data PredecessorNRemove(Data dat) {
        Natural predIndex = SearchPredecessor(dat);
        if (predIndex != null) {
            Data predData = GetAt(predIndex);
            RemoveAt(predIndex);
            return predData;
        }
        return null;
    }

    @Override
    default boolean Filter(Predicate<Data> fun) { return OrderedChain.super.Filter(fun);
    }

    @Override
    default boolean IsEqual(IterableContainer<Data> other) {
        return OrderedChain.super.IsEqual(other);
    }

}

