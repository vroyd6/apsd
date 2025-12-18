package apsd.interfaces.containers.deqs;

// import apsd.interfaces.containers.base.ClearableContainer;
// import apsd.interfaces.containers.base.InsertableContainer;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Stack<Data> extends ClearableContainer, InsertableContainer<Data> { // Must extend ClearableContainer and InsertableContainer

  // Top
  public Data Top();
  // Pop
  public Data Pop();
  // TopNPop
  public void push(Data data);
  // SwapTop
  public Data SwapTop(Data data);
  // TopNSwap
  public Data TopNSwap(Data data);
  // Push
  public Data Push(Data data);

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

    @Override
    default void Clear() {
      while (isEmpty() == false) {
        Pop();
      }
    }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

    @Override
      default boolean Insert(Data dat) {
          Push(dat);
          return true;
      }

}
