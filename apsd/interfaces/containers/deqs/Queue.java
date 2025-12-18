package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Queue<Data> extends ClearableContainer, InsertableContainer<Data> { // Must extend ClearableContainer and InsertableContainer

  // Head
  public Data Head();
  // Dequeue
  public Data Dequeue();
  // HeadNDequeue
  public Data HeadNDequeue();
  // Enqueue
   public Data Enqueue(Data dat);

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  default void Clear() {
    while (isEmpty() == false) {
      Dequeue();
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
    default boolean Insert(Data dat) {
        Enqueue(dat);
        return true;
    }

}
