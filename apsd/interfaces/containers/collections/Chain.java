package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.sequences.RemovableAtSequence;
import apsd.interfaces.traits.Predicate;

public interface Chain<Data> extends RemovableAtSequence<Data>, Set<Data>{

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
    default Natural Search(Data element){
        if (isEmpty()) return null;

        long index = 0;
    ForwardIterator<Data> iterator = FIterator();
         while (iterator.IsValid()){
            Data current = iterator.GetCurrent();
            if (current == null ? element == null : current.equals(element)) {
                return Natural.Of(index);
            }
            index++;
            iterator.Next();
        }
        return null;
  }

  default boolean Filter (Predicate<Data> fun) {
        throw new UnsupportedOperationException("Filter must be implemented by concrete class");
      }


      @Override
      default boolean IsEqual(IterableContainer<Data> other) {
        return Set.super.IsEqual(other);

}


}
