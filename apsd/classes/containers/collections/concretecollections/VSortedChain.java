package apsd.classes.containers.collections.concretecollections;

 import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.Chain;
 import apsd.interfaces.containers.collections.SortedChain;
 import apsd.interfaces.containers.sequences.DynVector;
 import apsd.interfaces.traits.Predicate;

/** Object: Concrete set implementation via (dynamic circular) vector. */
public class VSortedChain<Data extends Comparable<? super Data>> extends VChainBase<Data> implements SortedChain<Data> { // Must extend VChainBase and implements SortedChain

    public VSortedChain() {
        super();
    }

    public VSortedChain(VSortedChain<Data> chn) {
        super(chn);
    }

    public VSortedChain(TraversableContainer<Data> con) {
        super(con);
    }

    protected VSortedChain(DynVector<Data> vec) {
        super(vec);
    }

    @Override
    protected VSortedChain<Data> NewChain(DynVector<Data> vec) {
        return new VSortedChain<>(vec);
    }

    /* ************************************************************************ */
    /* Override specific member functions from InsertableContainer              */
    /* ************************************************************************ */

    @Override
    public boolean Insert(Data dat) {
        if (dat == null) return false;

        // Find the correct position to insert the new data
        Natural i = Natural.ZERO;
        while (i.compareTo(Size()) < 0) {
            Data current = vec.GetAt(i);
            if (((Comparable<Data>) current).compareTo(dat) >= 0) {
                break;
            }
            i = i.Increment();
        }

        // Insert the data at the found position
        vec.ShiftRight(i);
        vec.SetAt(dat, i);
        return true;
    }

    /* ************************************************************************ */
    /* Override specific member functions from Chain                            */
    /* ************************************************************************ */

    @Override
    public boolean InsertIfAbsent(Data dat) {
        if (dat == null) return false;

        Natural i = Natural.ZERO;
        while (i.compareTo(Size()) < 0) {
            Data current = vec.GetAt(i);
            int cmp = ((Comparable<Data>) current).compareTo(dat);
            if (cmp == 0) {
                return false; // Data already exists
            } else if (cmp > 0) {
                break; // Found the position to insert
            }
            i = i.Increment();
        }

        vec.ShiftRight(i);
        vec.SetAt(dat, i);
        return true;
    }

    @Override
    public void RemoveOccurrences(Data dat) {
        if (dat == null || Size().IsZero()) return;

        Natural i = vec.Search(dat);
        if (i == null) return;

        Natural count = Natural.ZERO;
        Natural pos = i;

        while (pos.compareTo(Size()) < 0 && vec.GetAt(pos).equals(dat)) {
            count = count.Increment();
            pos = pos.Increment();
        }

        for (Natural j = Natural.ZERO; j.compareTo(count) < 0; j = j.Increment()) {
            vec.AtNRemove(i);
        }
    }

    @Override
    public Chain<Data> SubChain(Natural startPosition, Natural endPosition) {
        DynVector<Data> subVec = vec.SubVector(startPosition, endPosition);
        return new VSortedChain<>(subVec);
    }

    @Override
    public void RemoveAt(Natural pos) {
        vec.AtNRemove(pos);
    }

    @Override
    public boolean TraverseForward(Predicate<Data> fun) {
        return vec.TraverseForward(fun);
    }

    @Override
    public boolean TraverseBackward(Predicate<Data> fun) {
        return vec.TraverseBackward(fun);
    }

    @Override
    public boolean Exists(Data dat) {
        if (dat == null) return false;
        return vec.Exists(dat);
    }

    @Override
    public int compareTo(Data o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
