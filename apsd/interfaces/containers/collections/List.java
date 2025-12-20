package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.InsertableAtSequence;
import apsd.interfaces.containers.sequences.MutableSequence;

public interface List<Data> extends MutableSequence<Data>, InsertableAtSequence<Data>, Chain<Data> {

  // SubList
  List<Data> SubList(Natural fromIndex, Natural toIndex);

  default boolean Insert(Data dat) {
    InsertFirst(dat);
    return true;
  }
}