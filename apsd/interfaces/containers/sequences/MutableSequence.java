package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: Sequence & MutableIterableContainer con supporto alla scrittura tramite posizione. */
public interface MutableSequence<Data> extends Sequence<Data>, MutableIterableContainer<Data> { // Must extend Sequence and MutableIterableContainer

  // SetAt
    void SetAt(Data dato, Natural position);

  // GetNSetAt
    void GetNSetAt(Data[] data, Natural position, Natural n);

  // SetFirst
    void SetFirst(Data dato);

  // GetNSetFirst
    void GetNSetFirst(Data[] data, Natural n);

  // SetLast
    void SetLast(Data dato);

  // GetNSetLast
    void GetNSetLast(Data[] data, Natural n);

  // Swap
    void Swap(Natural pos1, Natural pos2);

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  MutableSequence<Data> SubSequence(Natural from, Natural to);

}
