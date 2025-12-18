package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.InsertableContainer;
import apsd.interfaces.containers.base.ResizableContainer;

public interface DynVector<Data> extends ResizableContainer, InsertableAtSequence<Data>, RemovableAtSequence<Data>, Vector<Data> { // Must extend ResizableContainer, InsertableAtSequence, RemovableAtSequence, and Vector

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
    void InsertAt(Data data, Natural position);
  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

    @Override
    Data AtNRemove(Natural position);
    /* ************************************************************************ */
  /* Specific member functions of Vector                                       */
  /* ************************************************************************ */

    @Override
    void ShiftLeft(Natural position, Natural number);

    void ShiftRight(Natural position, Natural number);

    DynVector<Data> SubVector(Natural start, Natural end);


  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

    @Override
    Natural Size();

}
