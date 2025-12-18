package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.base.IterableContainer;

public interface Set<Data> extends Collection<Data>{ // Must extend Collection

  // Union
  default void union(Set<Data> other){
      other.TraverseForward(dat -> {
          this.Insert(dat);
          return false;
      });

  }

  // Difference
   default void intersection(Set<Data> other){
         this.Filter(dat -> other.Exists(dat));
   }

  // Intersection
   default void difference(Set<Data> other){
       other.TraverseForward(dat -> {
           this.Remove(dat);
           return false;
       });
   }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

    @Override
    default boolean IsEqual(IterableContainer<Data> dat) {
        if (dat == null) return false;
        if (this.Size() != dat.Size()) return false;

        final boolean[] allFound = { true };

        dat.TraverseForward(elem -> {
            final boolean[] found = { false };

            this.TraverseForward(e -> {
                if (e == null ? elem == null : e.equals(elem)) {
                    found[0] = true;
                    return true; // stop inner traversal early
                }
                return false;
            });

            if (!found[0]) {
                allFound[0] = false;
                return true; // stop outer traversal early
            }
            return false;
        });

        return allFound[0];
    }

}
