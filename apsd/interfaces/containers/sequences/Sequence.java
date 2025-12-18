package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data> extends IterableContainer<Data> {

  // GetAt
    default Data GetAt(Natural pos) {
        long index = ExcIfOutOfBound(pos);
        ForwardIterator<Data> itr = FIterator();
        itr.Next(index);
        return itr.GetCurrent();
    }

  // GetFirst
    default Data GetNext(Natural pos) {
        return GetAt(Natural.ZERO);
    }
  // GetLast
    default Data GetLast(Natural pos) {
        return GetAt(isEmpty() ? Natural.ZERO : Size().Decrement());
    }

  // Search
    default Natural Search(Data dat) {
        final Box<Long> index = new Box<>(-1L);
        if (TraverseForward(elem -> {
            index.Set(index.Get()+1);
            return (elem == null && dat == null) || (elem != null && elem.equals(dat));
        })) {
            return Natural.Of(index.Get());
        }
        return null;
    }


  // IsInBound

   default long ExcIfOutOfBound(Natural num) {
     if (num == null) throw new NullPointerException("Natural number cannot be null!");
     long idx = num.ToLong();
     if (idx >= Size().ToLong()) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size() + "!");
     return idx;
   }

  // SubSequence
    public Sequence<Data> SubSequence(Data[] data, Natural position, Natural n);

}
