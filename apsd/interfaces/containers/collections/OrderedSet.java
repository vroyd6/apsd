package apsd.interfaces.containers.collections;

public interface OrderedSet<Data extends Comparable<? super Data>> extends Set<Data> {

  default Data Min() {
      return FoldForward((dat, min) -> (min == null || dat.compareTo(min) < 0) ? dat : min, null);

  }

  // RemoveMin
  default void RemoveMin() {
      Remove(Min());
      }

  // MinNRemove
  default Data MinNRemove() {
      Data min = Min();
      Remove(min);
      return min;
  }

  // Max
  default Data Max() {
        return FoldForward((dat, max) -> (max == null || dat.compareTo(max) > 0) ? dat : max, null);
  }

  // RemoveMax
    default void RemoveMax() {
        Remove(Max());
        }

  // MaxNRemove
    default Data MaxNRemove() {
        Data max = Max();
        Remove(max);
        return max;
    }

  // Predecessor
    default Data Predecessor(Data dat) {
        final Data[] predecessor = (Data[]) new Object[1];
        this.TraverseForward(current -> {
            if (current.compareTo(dat) < 0) {
                if (predecessor[0] == null || current.compareTo(predecessor[0]) > 0) {
                    predecessor[0] = current;
                }
            }
            return false; // continua la traversata
        });
        return predecessor[0];
    }

  // RemovePredecessor
    default void RemovePredecessor(Data dat) {
        Data predecessor = Predecessor(dat);
        if (predecessor != null) {
            Remove(predecessor);
        }
    }

  // PredecessorNRemove
    default Data PredecessorNRemove(Data dat) {
        Data predecessor = Predecessor(dat);
        if (predecessor != null) {
            Remove(predecessor);
        }
        return predecessor;
    }

  // Successor
    default Data Successor(Data dat) {
        final Data[] successor = (Data[]) new Object[1];
        this.TraverseForward(current -> {
            if (current.compareTo(dat) > 0) {
                if (successor[0] == null || current.compareTo(successor[0]) < 0) {
                    successor[0] = current;
                }
            }
            return false; // continua la traversata
        });
        return successor[0];
    }

  // RemoveSuccessor
    default void RemoveSuccessor(Data dat) {
        Data successor = Successor(dat);
        if (successor != null) {
            Remove(successor);
        }
    }

  // SuccessorNRemove
    default Data successorNRemove(Data dat) {
        Data successor = Successor(dat);
        if (successor != null) {
            Remove(successor);
        }
        return successor;
    }

}
