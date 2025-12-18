package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.base.IterableContainer;

public interface Set<Data> extends Collection<Data>{ // Must extend Collection

  // Union
  public Set<Data> union(Set<Data> other);

  // Difference
  public Set<Data> intersection(Set<Data> other);

  // Intersection
   public Set<Data> difference(Set<Data> other);

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

    @Override
    default boolean isEqual(IterableContainer<Data> dat) {
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
