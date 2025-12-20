package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;


public interface Stack<Data> extends ClearableContainer, InsertableContainer<Data> { // Must extend ClearableContainer and InsertableContainer


  Data Top();

  void Pop();

  void SwapTop(Data data);

  Data TopNSwap(Data data);

  void Push(Data data);

    default Data TopNPop() {
        Data top = Top();
        Pop();
        return top;
    }

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
