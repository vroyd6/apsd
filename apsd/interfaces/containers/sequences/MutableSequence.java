package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: Sequence & MutableIterableContainer con supporto alla scrittura tramite posizione. */
public interface MutableSequence<Data> extends Sequence<Data>, MutableIterableContainer<Data> {

 void SetAt(Data dat, Natural pos);

  default Data GetNSetAt(Data dat, Natural pos) {
    Data old = GetAt(pos);
    SetAt(dat, pos);
    return old;
  }

  default void SetFirst(Data dat) {
    SetAt(dat, Natural.ZERO);
  }

  default Data GetNSetFirst(Data dat) {
    return GetNSetAt(dat, Natural.ZERO);
  }

  default void SetLast(Data dat) {
    SetAt(dat, isEmpty() ? Natural.ZERO : Size().Decrement());
  }

  default Data GetNSetLast(Data dat) {
    return GetNSetAt(dat, isEmpty() ? Natural.ZERO : Size().Decrement());
  }

  default void Swap(Natural pos1, Natural pos2) {
    Data temp = GetAt(pos1);
    SetAt(GetAt(pos2), pos1);
    SetAt(temp, pos2);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  MutableSequence<Data> SubSequence(Natural from, Natural to);


}
