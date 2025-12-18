package apsd.interfaces.containers.iterators;

// import apsd.classes.utilities.Natural;
// import apsd.interfaces.traits.Predicate;

import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore all'indietro. */
public interface BackwardIterator<Data> extends Iterator<Data>{ // Must extend Iterator

  // Prev
  void prev();
    default void prev(int passaggi) {
      for (int i = 0; i<passaggi && IsValid(); i++) {
          prev();
      }

  }

  // DataNPrev
  Data DataNPrev();
    default Data DataNPrev(int passaggi) {
        Data dato = null;
        for (int i = 0; i<passaggi && IsValid(); i++) {
            dato = GetCurrent();
            prev();
        }
        return dato;
    }

  // ForEachBackward
    default boolean ForEachBackward(Predicate<Data> p) {
        if (p == null) {
            while(this.IsValid()) {
                if (p.Apply(DataNPrev())) {
                    return true;
                }
            }
        }
        return false;
    }

}
