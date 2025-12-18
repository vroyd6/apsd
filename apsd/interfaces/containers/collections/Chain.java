package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.RemovableAtSequence;

public interface Chain<Data> extends RemovableAtSequence<Data>{ // Must extend RemovableAtSequence

  // InsertIfAbsent
   public void InsertIfAbsent(Data element);

  // RemoveOccurrences
  public void RemoveOccurrences(Data element);

  // SubChain
  public Chain<Data> SubChain(Natural startPosition, Natural endPosition);

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
    public Natural Search(Data element);


}
