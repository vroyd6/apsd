package apsd.interfaces.containers.sequences;

// import apsd.classes.utilities.Natural;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto all'inserimento di un dato tramite posizione. */
public interface InsertableAtSequence<Data> extends Sequence<Data> { // Must extend Sequence

  // InsertAt
  void InsertAt(Data dato, Natural position);

  // InsertFirst
  default void InsertFirst(Data dato) {
    InsertAt(dato, Natural.ZERO);
  }

  // InsertLast
  default void InsertLast(Data dato){
    InsertAt(dato, Size());
  }

}
