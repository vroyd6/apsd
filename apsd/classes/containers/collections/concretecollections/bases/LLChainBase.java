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

    public LLChainBase() {
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

    protected class ListFRefIterator implements ForwardIterator<Box<LLNode<Data>>> {

        protected Box<LLNode<Data>> cur = headref;

        public ListFRefIterator() {
        }


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
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            return cur;
        }

        @Override
        public void Next(Natural current) {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            cur = cur.Get().GetNext();
        }

        @Override
        public void Next() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            cur = cur.Get().GetNext();
        }

        @Override
        public Box<LLNode<Data>> DataNNext() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            Box<LLNode<Data>> ret = cur;
            cur = cur.Get().GetNext();
            return ret;
        }



    }
    protected ForwardIterator<Box<LLNode<Data>>> FRefIterator() {
        return new ListFRefIterator();
    }



        protected class ListBRefIterator implements BackwardIterator <Box<LLNode<Data>>>{
            protected long cur = -1L;
            protected Vector<Box<LLNode<Data>>> arr= null;

            public ListBRefIterator() {
                Reset();
            }

            public ListBRefIterator(ListBRefIterator itr) {
                cur = itr.cur;
                arr = new Vector<>(Size());
            }

            @Override
            public boolean IsValid() {
                return (cur>= 0 && cur<Size().ToLong());
            }

            @Override
            public void Reset() {
                cur = -1L;
                if(Size().IsZero()) {
                    arr=null;
                }else {
                    arr = new Vector<>(Size());
                    for(Box<LLNode<Data>> ref = headref; !ref.IsNull(); ref= ref.Get().GetNext()) {
                        arr.SetAt(ref, Natural.Of(++cur));
                    }
                }
            }

            @Override
            public Box<LLNode<Data>> GetCurrent(){
                if(!IsValid()) {
                    throw new IllegalStateException("Iterator terminated!");
                }
                return arr.GetAt(Natural.Of(cur));
            }



            @Override
            public void prev() {
                if(!IsValid()) {
                    throw new IllegalStateException("Iterator terminated!");
                }
                cur--;
            }

            @Override
            public Box<LLNode<Data>> DataNPrev() {
                if(!IsValid()) {
                    throw new IllegalStateException("Iterator terminated!");
                }
                Box<LLNode<Data>> ret = arr.GetAt(Natural.Of(cur));
                cur--;
                return ret;
            }
        }

        protected BackwardIterator<Box<LLNode<Data>>> BRefIterator(){
            return new ListBRefIterator();
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

        Box<LLNode<Data>> prevref = null;
        for (Box<LLNode<Data>> curref = headref; !curref.IsNull(); curref = curref.Get().GetNext()) {
            if (dat.equals(curref.Get().Get())) {
                if (prevref == null) {
                    headref.Set(curref.Get().GetNext().Get());
                    if (headref.IsNull()) {
                        tailref.Set(null);
                    }
                } else {
                    prevref.Get().SetNext(curref.Get().GetNext().Get());
                    if (curref.Get() == tailref.Get()) {
                        tailref.Set(prevref.Get());
                    }
                }
                size.Decrement();
                return true;
            }
            prevref = curref;
        }
        return false;
    }

    /* ************************************************************************ */
    /* Override specific member functions from IterableContainer                */
    /* ************************************************************************ */

    protected class ListFIterator implements MutableForwardIterator<Data> {

        protected final ForwardIterator<Box<LLNode<Data>>> itr;

        public ListFIterator() {
            this.itr = new ListFRefIterator();
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
        public Data GetCurrent() {
            return itr.GetCurrent().Get().Get();
        }

        @Override
        public void SetCurrent(Data dat) {
            if (dat == null) return;
            itr.GetCurrent().Get().Set(dat);
        }

        @Override
        public void Next(Natural current) {
            itr.Next(current);

        }
    }

        @Override
        public MutableForwardIterator<Data> FIterator() {
            return new ListFIterator();
        }

        protected class ListBIterator implements MutableBackwardIterator<Data> {

            protected final BackwardIterator<Box<LLNode<Data>>> itr;

            public ListBIterator() {
                this.itr = new ListBRefIterator();
            }

            public ListBIterator(ListBIterator itr) {
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
            public Data GetCurrent() {
                return itr.GetCurrent().Get().Get();
            }

            @Override
            public void SetCurrent(Data dat) {
                if (dat == null) return;
                itr.GetCurrent().Get().Set(dat);
            }

            @Override
            public void prev() {
                itr.prev();
            }

            @Override
            public Data DataNPrev() {
                return itr.DataNPrev().Get().Get();
            }

        }

        /* ************************************************************************ */
        /* Override specific member functions from Sequence                         */
        /* ************************************************************************ */

        @Override
        public Data GetFirst() {
            if (headref.IsNull()) throw new IllegalStateException("Chain is empty!");
            return headref.Get().Get();
        }

        @Override
        public Data GetLast() {
            if (tailref.IsNull()) throw new IllegalStateException("Chain is empty!");
            return tailref.Get().Get();
        }

    @Override
    public Sequence <Data> SubSequence(Natural from, Natural to){
        long f = from.ToLong(), t= to.ToLong();
        if (f>t || t>= size.ToLong()) {
            return null;
        }
        final Box<Long> idx= new Box<>(0L);
        final Box<LLNode<Data>> headlst= new Box<>();
        final Box<LLNode<Data>> taillst= new Box<>();
        TraverseForward(dat->{
            if(idx.Get()>t) {
                return true;
            }
            LLNode<Data> node = new LLNode<>(dat);
            if(idx.Get()==f) {
                headlst.Set(node);
            }else if(idx.Get()>f){
                taillst.Get().SetNext(node);
            }
            taillst.Set(node);
            idx.Set(idx.Get()+1);
            return false;
        });
        return NewChain(t-f+1, headlst.Get(), taillst.Get());

    }



        /* ************************************************************************ */
        /* Override specific member functions from RemovableAtSequence              */
        /* ************************************************************************ */

        @Override
        public Data AtNRemove(Natural pos) {
            long index = pos.ToLong();
            if (index >= size.ToLong()) {
                throw new IndexOutOfBoundsException("Index out of bounds: " + index + "; Size: " + size + "!");
            }

            Box<LLNode<Data>> prevref = null;
            Box<LLNode<Data>> curref = headref;
            for (long i = 0; i < index; i++) {
                prevref = curref;
                curref = curref.Get().GetNext();
            }

            Data ret = curref.Get().Get();

            if (prevref == null) {
                headref.Set(curref.Get().GetNext().Get());
                if (headref.IsNull()) {
                    tailref.Set(null);
                }
            } else {
                prevref.Get().SetNext(curref.Get().GetNext().Get());
                if (curref.Get() == tailref.Get()) {
                    tailref.Set(prevref.Get());
                }
            }

            size.Decrement();
            return ret;
        }

        @Override
        public void RemoveFirst() {
            if(headref.IsNull()) {
                return;
            }
            LLNode<Data> node=headref.Get();
            if(tailref.Get()==node) {
                headref.Set(null);
                tailref.Set(null);
            }else {
                headref.Set(headref.Get().GetNext().Get());
            }
            size.Decrement();
        }

        @Override
        public void RemoveLast() {
            if(tailref.IsNull()) {
                return;
            }
            LLNode<Data> node=tailref.Get();
            if(headref.Get()==node) {
                headref.Set(null);
                tailref.Set(null);
            }else {
                Box<LLNode<Data>> prevref = null;
                for (Box<LLNode<Data>> curref = headref; !curref.IsNull(); curref = curref.Get().GetNext()) {
                    if (curref.Get() == node) {
                        break;
                    }
                    prevref = curref;
                }
                if (prevref != null) {
                    prevref.Get().SetNext(null);
                    tailref.Set(prevref.Get());
                }
            }
            size.Decrement();
        }

        @Override
    public Data FirstNRemove() {
            LLNode<Data> node= headref.Get();
            if(node==null) {
                return null;
            }
            Data dat = node.Get();
            RemoveFirst();
            return dat;
        }

        @Override
    public Data LastNRemove() {
            LLNode<Data> node= tailref.Get();
            if(node==null) {
                return null;
            }
            Data dat = node.Get();
            RemoveLast();
            return dat;
        }

        /* ************************************************************************ */
        /* Override specific member functions from Collection                       */
        /* ************************************************************************ */

        @Override
    public boolean Filter(Predicate<Data> fun) {
            MutableNatural oldsize=size;
            if(fun!= null) {
                ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
                LLNode<Data> prd = null;
                while (itr.IsValid()) {
                    Box<LLNode<Data>> cur = itr.GetCurrent();
                    LLNode<Data> node = cur.Get();
                    if(fun.Apply(node.Get())) {
                        itr.Next();
                    }else {
                        cur.Set(node.GetNext().Get());
                        if(tailref.Get()==node) {
                            tailref.Set(prd);
                            size.Decrement();
                        }
                        prd=node;
                    }
                }
            }
            return !size.equals(oldsize);
        }


}
