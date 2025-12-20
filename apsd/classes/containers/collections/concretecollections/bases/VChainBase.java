package apsd.classes.containers.collections.concretecollections.bases;

 import apsd.classes.containers.sequences.DynCircularVector;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.Chain;
 import apsd.interfaces.containers.iterators.BackwardIterator;
 import apsd.interfaces.containers.iterators.ForwardIterator;
 import apsd.interfaces.containers.iterators.MutableForwardIterator;
 import apsd.interfaces.containers.sequences.DynVector;
 import apsd.interfaces.containers.sequences.Sequence;
 import apsd.interfaces.traits.Predicate;

/** Object: Abstract list base implementation on (dynamic circular) vector. */
abstract public class VChainBase<Data> implements Chain<Data>{

    protected DynVector<Data> vec;

    public VChainBase() {
        this.vec = new DynCircularVector<>();
    }

    public VChainBase(TraversableContainer<Data> con) {
        this.vec = new DynCircularVector<>(con);
    }

    protected VChainBase(DynVector<Data>vec) {
        this.vec = vec;
    }

    abstract protected VChainBase<Data> NewChain(DynVector<Data> vec);

    /* ************************************************************************ */
    /* Override specific member functions from Container                        */
    /* ************************************************************************ */

    @Override
    public Natural Size() {
        return vec.Size();
    }

    /* ************************************************************************ */
    /* Override specific member functions from ClearableContainer               */
    /* ************************************************************************ */

    @Override
    public void Clear() {
        vec.Clear();
    }

    /* ************************************************************************ */
    /* Override specific member functions from RemovableContainer               */
    /* ************************************************************************ */

    public boolean Remove(Data dat) {
        if (dat == null) return false;

        Natural pos = vec.Search(dat);
        if (pos == null) return false;

        vec.AtNRemove(pos);
        return true;
    }

    /* ************************************************************************ */
    /* Override specific member functions from IterableContainer                */
    /* ************************************************************************ */

    @Override
    public MutableForwardIterator<Data> FIterator() {
        return vec.FIterator();
    }

    @Override
    public BackwardIterator<Data> BIterator() {
        return vec.BIterator();
    }

    /* ************************************************************************ */
    /* Override specific member functions from Sequence                         */
    /* ************************************************************************ */

    @Override
    public Data GetAt(Natural pos) {
        return vec.GetAt(pos);
    }

    @Override
    public Sequence<Data> SubSequence(Natural from, Natural to) {
        return vec.SubVector(from, to);
    }

    /* ************************************************************************ */
    /* Override specific member functions from RemovableAtSequence              */
    /* ************************************************************************ */

    @Override
    public Data AtNRemove(Natural pos) {
        return vec.AtNRemove(pos);
    }

    /* ************************************************************************ */
    /* Override specific member functions from Collection                       */
    /* ************************************************************************ */

    @Override
    public boolean Filter(Predicate<Data> fun) {
        long del=0;
        if (fun != null) {
            MutableForwardIterator<Data> wrt = (MutableForwardIterator<Data>) vec.FIterator();
            for (; wrt.IsValid();wrt.Next()) {
                if(!fun.Apply(wrt.GetCurrent())) {
                    del++;
                    wrt.SetCurrent(null);
                }
            }
            if (del>0) {
                wrt.Reset();
                MutableForwardIterator<Data> rdr = (MutableForwardIterator<Data>) vec.FIterator();
                for (;rdr.IsValid();rdr.Next()) {
                    if(rdr.GetCurrent()!=null) {
                        Data dat = rdr.GetCurrent();
                        rdr.SetCurrent(null);
                        wrt.SetCurrent(dat);
                        wrt.Next();
                    }
                }
                vec.Reduce(Natural.Of(del));
            }
        }
        return (del>0);
    }


    public Data GetFirst() throws IndexOutOfBoundsException {
        if (vec.Size().IsZero()) {
            throw new IndexOutOfBoundsException("Cannot get first element from an empty chain.");
        }
        return vec.GetAt(Natural.ZERO);
    }


    public Data GetLast() throws IndexOutOfBoundsException {
        Natural size = vec.Size();
        if (size.IsZero()) {
            throw new IndexOutOfBoundsException("Cannot get last element from an empty chain.");
        }

        Natural lastPos = size.Decrement();
        return vec.GetAt(lastPos);
    }

}
