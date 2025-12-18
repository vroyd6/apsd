package apsd.interfaces.containers.sequences;

// import apsd.classes.utilities.Natural;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto all'inserimento di un dato tramite posizione. */
public interface InsertableAtSequence<Data> extends Sequence<Data> { // Must extend Sequence

  // InsertAt
    public void InsertAt(Data dato, Natural position);

  // InsertFirst
    public void InsertFirst(Data dato);

  // InsertLast
    public void InsertLast(Data dato);

}
