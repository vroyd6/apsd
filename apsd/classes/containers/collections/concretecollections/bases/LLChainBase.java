package apsd.classes.containers.collections.concretecollections.bases;

 import apsd.classes.containers.sequences.Vector;
 import apsd.classes.utilities.Box;
 import apsd.classes.utilities.MutableNatural;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.Chain;
 import apsd.interfaces.containers.iterators.BackwardIterator;
 import apsd.interfaces.containers.iterators.ForwardIterator;
 import apsd.interfaces.containers.iterators.MutableBackwardIterator;
 import apsd.interfaces.containers.iterators.MutableForwardIterator;
 import apsd.interfaces.containers.sequences.Sequence;
 import apsd.interfaces.traits.Predicate;

/** Object: Abstract chain base implementation on linked-list. */
abstract public class LLChainBase<Data> implements Chain<Data>{ // Must implement Chain

    protected final MutableNatural size = new MutableNatural();
    protected final Box<LLNode<Data>> headref = new Box<>();
    protected final Box<LLNode<Data>> tailref = new Box<>();

    LLChainBase() {
    }

    public LLChainBase(TraversableContainer<Data> con) {
        size.Assign(con.Size());
        final Box<Boolean> first = new Box<>(true);
        con.TraverseForward(dat -> {
            LLNode<Data> node = new LLNode<>(dat);
            if (first.Get()) {
                headref.Set(node);
                first.Set(false);
            } else {
                tailref.Get().SetNext(node);
            }
            tailref.Set(node);
            return false;
        });
    }

    // NewChain
    protected LLChainBase(long size, LLNode<Data> head, LLNode<Data> tail) {
        this.size.Assign(size);
        headref.Set(head);
        tailref.Set(tail);
    }

    abstract protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail);

    /* ************************************************************************ */
    /* Specific member functions from LLChainBase                               */
    /* ************************************************************************ */

    protected class DistFRefIterator implements ForwardIterator<Box<LLNode<Data>>> {

        protected Box<LLNode<Data>> cur = headref;

        public ListFRefIterator() {
        }

        ;

        public ListFRefIterator(ListFRefIterator itr) {
            cur = itr.cur;
        }

        @Override
        public boolean IsValid() {
            return !cur.IsNull();
        }

        @Override
        public void Reset() {
            cur = headref;
        }

        @Override
        public Box<LLNode<Data>> GetCurrent() {
            if (!isValid()) throw new IlligalStateException("Iterator terminated!");
        }

        @Override
        public void Next() {
            if (!isValid()) throw new IlligalStateException("Iterator terminated!");
            cur = cur.Get().GetNext();
        }
    }

    /* ************************************************************************ */
    /* Override specific member functions from Container                        */
    /* ************************************************************************ */

    @Override
    public Natural Size() {
        return size.ToNatural();
    }

    /* ************************************************************************ */
    /* Override specific member functions from ClearableContainer               */
    /* ************************************************************************ */

    @Override
    public void Clear() {
        size.Zero();
        headref.Set(null);
        tailref.Set(null);
    }

    /* ************************************************************************ */
    /* Override specific member functions from RemovableContainer               */
    /* ************************************************************************ */

    @Override
    public boolean Remove(Data dat) {
        if (dat == null) return false;
        final Box<LLNode<Data>> prd = new Box<>();
        return FRefIterator().ForEachForward(cur -> {
            LLNode<Data> node = cur.Get();
            if (node.Get().equals(dat)) {
                cur.Set(node.GetNext().Get());
                if (tailref.Get() == node) {
                    tailref.Set(prd.Get());
                }
                size.Decrement();
                return true;
            }
            prd.Set(node);
            return false;
        });
    }

    /* ************************************************************************ */
    /* Override specific member functions from IterableContainer                */
    /* ************************************************************************ */

    protected class ListFIterator implements MutableForwardIterator<Data> {

        protected final ForwardIterator<Box<LLNode<Data>>> itr;

        public ListFIterator() {
            itr = FRefIterator();
        }

        public ListFIterator(ListFIterator itr) {
            this.itr = itr.itr;
        }

        @Override
        public boolean IsValid() {
            return itr.IsValid();
        }

        @Override
        public void Reset() {
            itr.Reset();
        }

        @Override
        public Data GetCurrent() {return itr.GetCurrent().Get().Get();}

        @Override
        public void SetCurrent (Data dat) {
            if (dat == null) return;
            itr.GetCurrent().Get().Set(dat);
        }

        @Override
        public void Next() {
            itr.Next();
        }

        /* ************************************************************************ */
        /* Override specific member functions from Sequence                         */
        /* ************************************************************************ */

        // ...


        /* ************************************************************************ */
        /* Override specific member functions from RemovableAtSequence              */
        /* ************************************************************************ */

        // ...

        /* ************************************************************************ */
        /* Override specific member functions from Collection                       */
        /* ************************************************************************ */

        // ...

    }
}
