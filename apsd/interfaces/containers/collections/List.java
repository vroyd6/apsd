package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.InsertableAtSequence;
import apsd.interfaces.containers.sequences.MutableSequence;

public interface List<Data> extends MutableSequence<Data>, InsertableAtSequence<Data>, Chain<Data>{ // Must extend MutableSequence, InsertableAtSequence, and Chain

  // SubList
  List<Data> subList(Natural fromIndex, Natural toIndex);

  /* ************************************************************************ */
  /* Override specific member functions from ExtensibleContainer              */
  /* ************************************************************************ */

  @Override
    public Natural search(Data element);

}
