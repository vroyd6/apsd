package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.InsertableContainer;
import apsd.interfaces.containers.base.ResizableContainer;

public interface DynVector<Data> extends ResizableContainer, InsertableAtSequence<Data>, RemovableAtSequence<Data>, Vector<Data> { // Must extend ResizableContainer, InsertableAtSequence, RemovableAtSequence, and Vector

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  public default void InsertAt(Data dato, Natural position) {
      if (position == null) throw new NullPointerException("position cannot be null");
      long idx = position.ToLong();
      long sz = Size().ToLong();
      if (idx > sz) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size() + "!");
      // sposto a destra idx volte per creare spazio
      for (long i = 0; i < idx; i++) {
          this.ShiftRight(Natural.Of(1));
      }
      // inserisco l'elemento in prima posizione (ora libera)
      this.InsertFirst(dato);
      // riporto la sequenza alla posizione originale
      for (long i = 0; i < idx; i++) {
          this.ShiftLeft(Natural.Of(1));
      }
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

    @Override
    public default void RemoveAt(Natural position) {
        if (position == null) throw new NullPointerException("position cannot be null");
        long idx = position.ToLong();
        long sz = Size().ToLong();
        if (idx >= sz) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size() + "!");
        // sposto a sinistra idx volte per portare l'elemento in posizione 0
        for (long i = 0; i < idx; i++) {
            this.ShiftLeft(Natural.Of(1));
        }
        // rimuovo il primo (era l'elemento alla posizione richiesta)
        this.RemoveFirst();
        // riporto la sequenza alla posizione originale
        for (long i = 0; i < idx; i++) {
            this.ShiftRight(Natural.Of(1));
        }
    }
    /* ************************************************************************ */
  /* Specific member functions of Vector                                       */
  /* ************************************************************************ */



  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

    @Override
    Natural Size();

}
