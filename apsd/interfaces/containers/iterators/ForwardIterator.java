package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore in avanti. */
public interface ForwardIterator<Data> extends Iterator<Data>{ // Must extend Iterator

  // Next
    void Next(Natural current);
        default void Next() {
        Next(Natural.ONE);
        }

        default void Next(long n) {
            Next(Natural.Of(n));
        }



  // DataNNext
    default Data DataNNext() {
      Data dato = null;
      if (IsValid()) {
          dato = GetCurrent();
          Next();
      }
      return dato;
    }

   default boolean ForEachForward(Predicate<Data> p) {
     if (p != null) {
       while (IsValid()) {
         if (p.Apply(DataNNext())) { return true; }
       }
     }
     return false;
   }

}
