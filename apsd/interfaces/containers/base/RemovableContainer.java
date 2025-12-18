package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto alla rimozione di un dato. */
public interface RemovableContainer<Data> extends Container{

  // Remove
    public boolean Remove(Data dat) ;

  // RemoveAll
    default public boolean RemoveAll (TraversableContainer<Data> con) {
        final Box<Boolean> all = new Box<>(true);
        if (con!= null) con.TraverseForward(dat -> { all.Set(all.Get() && Remove(dat)); return false; });
          return all.Get();
    }

  // RemoveSome
    default public boolean RemoveSome (TraversableContainer<Data> con) {
          final Box<Boolean> some = new Box<>(false);
          if (con!= null) con.TraverseForward(dat -> { some.Set(some.Get() || Remove(dat)); return false; });
              return some.Get();
    }

}
