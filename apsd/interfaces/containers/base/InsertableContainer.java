package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container{

  boolean Insert(Data dat);


  default boolean InsertAll (TraversableContainer<Data> con) {
      final Box<Boolean> all = new Box<>(true);
      if (con!= null) con.TraverseForward(dat -> { all.Set(all.Get() && Insert(dat)); return false; });
        return all.Get();
  }

  default boolean InsertSome (TraversableContainer<Data> con) {
        final Box<Boolean> some = new Box<>(false);
        if (con!= null) con.TraverseForward(dat -> { some.Set(some.Get() || Insert(dat)); return false; });
            return some.Get();
  }





}
