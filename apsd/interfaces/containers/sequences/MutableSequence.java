package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: Sequence & MutableIterableContainer con supporto alla scrittura tramite posizione. */
public interface MutableSequence<Data> extends Sequence<Data>, MutableIterableContainer<Data> { // Must extend Sequence and MutableIterableContainer

  // SetAt
    public void SetAt(Data dato, Natural position);

  // GetNSetAt
    public void GetNSetAt(Data[] data, Natural position, Natural n);

  // SetFirst
    public void SetFirst(Data dato);

  // GetNSetFirst
    public void GetNSetFirst(Data[] data, Natural n);

  // SetLast
    public void SetLast(Data dato);

  // GetNSetLast
    public void GetNSetLast(Data[] data, Natural n);

  // Swap
    public void Swap(Natural pos1, Natural pos2);

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
    public MutableSequence<Data> SubSequence(Data[] data, Natural position, Natural n);

}
