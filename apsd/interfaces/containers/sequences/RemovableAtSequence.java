package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto alla rimozione di un dato tramite posizione. */
public interface RemovableAtSequence<Data> extends Sequence<Data>{

  // RemoveAt
  void RemoveAt(Natural position);

  // AtNRemove
  default Data AtNRemove(Natural position){
    Data removed = GetAt(position);
    RemoveAt(position);
    return removed;
  }

  // RemoveFirst
  default void RemoveFirst(){
    if(Size().equals(Natural.ZERO)) return;
    RemoveAt(Natural.ZERO);
  }

  // FirstNRemove


  default Data FirstNRemove(){
      return AtNRemove(Natural.ZERO);
  }

  // RemoveLast
  default void RemoveLast(){
    if (Size().equals(Natural.ZERO)) return;
      RemoveAt(Size().Decrement());
  }

  // LastNRemove
  default Data LastNRemove(){;
        return AtNRemove(Size().Decrement());
  }

}
