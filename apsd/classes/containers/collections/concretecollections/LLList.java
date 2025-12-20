package apsd.classes.containers.collections.concretecollections;

 import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
 import apsd.classes.containers.collections.concretecollections.bases.LLNode;
 import apsd.classes.utilities.Box;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.Chain;
 import apsd.interfaces.containers.collections.List;
 import apsd.interfaces.containers.iterators.ForwardIterator;
 import apsd.interfaces.containers.iterators.MutableBackwardIterator;
 import apsd.interfaces.containers.iterators.MutableForwardIterator;
 import apsd.interfaces.containers.sequences.MutableSequence;
 import apsd.interfaces.traits.Predicate;

/** Object: Concrete list implementation on linked-list. */
public class LLList<Data> extends LLChainBase<Data> implements List<Data> { // Must extend LLChainBase and implement List

   public LLList(){

   }

   public LLList(TraversableContainer<Data> con) {
 	  super(con);
   }

   protected LLList(long size, LLNode<Data> head, LLNode<Data> tail){
        	  super(size,head,tail);
   }

  // NewChain
    @Override
    protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail){
     	  return new LLList<>(size,head,tail);
    }

  /* ************************************************************************ */
  /* Override specific member functions from MutableIterableContainer         */
  /* ************************************************************************ */

    @Override
    public MutableForwardIterator<Data> FIterator(){
   	  return new ListFIterator();
    }

    @Override
    public MutableBackwardIterator<Data> BIterator(){
   	  return new ListBIterator();
    }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

    @Override
    public void SetAt(Data dat, Natural pos) {
        if (dat == null) {
            return;
        }
        if (pos == null) {
            throw new NullPointerException("Natural cannot be null!");
        }
        long idx = pos.ToLong();
        if (idx < 0 || idx >= Size().ToLong()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size());
        }

        LLNode<Data> cur = headref.Get();
        for (long i = 0; i < idx; i++) {
            cur = cur.GetNext().Get();
        }
        cur.Set(dat);
    }

    @Override
    public void SetFirst(Data dat) {
        if (dat == null) {
            return;
        }
        if (Size().ToLong() == 0) {
            throw new IllegalStateException("List is empty!");
        }
        headref.Get().Set(dat);
    }

    @Override
    public void SetLast(Data dat) {
        if (dat == null) {
            return;
        }
        if (Size().ToLong() == 0) {
            throw new IllegalStateException("List is empty!");
        }
        tailref.Get().Set(dat);
    }

    @Override
    public MutableSequence<Data> SubSequence(Natural start, Natural end) {
        return (MutableSequence<Data>) super.SubSequence(start, end);
    }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  public void InsertAt (Data dat, Natural pos) {
      if(dat==null) {
          return;
      }
      if(pos==null) {
          throw new NullPointerException("Natural number cannot be null!");
      }
      long idx= pos.ToLong();
      long n = Size().ToLong();

      if (idx < 0 || idx > n)
          throw new IndexOutOfBoundsException("Index out of bounds: " + idx);

      if (idx == 0) {
          InsertFirst(dat);
          return;
      }

      if (idx == n) {
          InsertLast(dat);
          return;
      }

      LLNode<Data> cur = headref.Get();
      LLNode<Data> prev = null;

      for (long i = 0; i < idx; i++) {
          prev = cur;
          cur = cur.GetNext().Get();
      }

      LLNode<Data> newnode = new LLNode<>(dat, cur);
      prev.SetNext(newnode);
      size.Increment();
  }

    @Override
    public void InsertFirst(Data dat) {
        if(dat==null) {
            return;
        }
        LLNode<Data> newnode = new LLNode<>(dat,  headref.Get());
        headref.Set(newnode);
        if(tailref.IsNull()) {
            tailref.Set(newnode);
        }
        size.Increment();
    }

    @Override
    public void InsertLast(Data dat) {
        if(dat==null) {
            return;
        }
        LLNode<Data> newnode = new LLNode<>(dat, null);
        if(tailref.IsNull()) {
            headref.Set(newnode);
            tailref.Set(newnode);
        } else {
            tailref.Get().SetNext(newnode);
            tailref.Set(newnode);
        }
        size.Increment();
    }

    @Override
    public boolean TraverseForward(Predicate<Data> box) {
        LLNode<Data> current = headref.Get();
        while (current != null) {
            if (box.Apply(current.Get())) {
                return true;
            }
            current = current.GetNext().Get();
        }
        return false;
    }

    @Override
    public boolean TraverseBackward(Predicate<Data> box) {
        if (box == null) return false;
        LLNode<Data> cur = headref.Get();
        java.util.ArrayList<LLNode<Data>> arr = new java.util.ArrayList<>();

        while (cur != null) {
            arr.add(cur);
            cur = cur.GetNext().Get();
        }

        for (int i = arr.size() - 1; i >= 0; i--) {
            if (box.Apply(arr.get(i).Get()))
                return true;
        }
        return false;
    }

    @Override
    public boolean InsertIfAbsent(Data dat) {
        if (dat == null) {
            return false;
        }
        LLNode<Data> current = headref.Get();
        while (current != null) {
            if (current.Get().equals(dat)) {
                return false; // Element already exists
            }
            current = current.GetNext().Get();
        }
        InsertLast(dat); // Insert at the end if not found
        return true;
    }

    @Override
    public void RemoveOccurrences(Data dat) {
        if (dat == null) {
            return;
        }
        LLNode<Data> current = headref.Get();
        LLNode<Data> previous = null;

        while (current != null) {
            if (current.Get().equals(dat)) {
                if (previous == null) {
                    headref.Set(current.GetNext().Get());
                } else {
                    previous.SetNext(current.GetNext().Get());
                }
                size.Decrement();
            } else {
                previous = current;
            }
            current = current.GetNext().Get();
        }

        if (tailref.Get() != null && tailref.Get().Get().equals(dat)) {
            tailref.Set(previous);
        }
    }

    @Override
    public void RemoveAt(Natural pos) {
        if (pos == null) {
            throw new NullPointerException("Natural number cannot be null!");
        }
        long idx = pos.ToLong();
        long n = Size().ToLong();

        if (idx < 0 || idx >= n) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + idx);
        }

        LLNode<Data> current = headref.Get();
        LLNode<Data> previous = null;

        for (long i = 0; i < idx; i++) {
            previous = current;
            current = current.GetNext().Get();
        }

        if (previous == null) {
            headref.Set(current.GetNext().Get());
        } else {
            previous.SetNext(current.GetNext().Get());
        }

        if (tailref.Get() == current) {
            tailref.Set(previous);
        }

        size.Decrement();
    }

    @Override
    public Chain<Data> SubChain(Natural start, Natural end) {
        if (start == null || end == null) {
            throw new NullPointerException("Natural numbers cannot be null!");
        }
        long s = start.ToLong();
        long e = end.ToLong();
        long n = Size().ToLong();

        if (s < 0 || e > n || s > e) {
            throw new IndexOutOfBoundsException("Invalid start or end indices: " + s + ", " + e);
        }

        LLList<Data> sublist = new LLList<>();
        LLNode<Data> current = headref.Get();

        for (long i = 0; i < e; i++) {
            if (i >= s) {
                sublist.InsertLast(current.Get());
            }
            current = current.GetNext().Get();
        }

        return sublist;
    }

    @Override
    public Data AtNRemove(Natural pos) {
        if (pos == null) {
            throw new NullPointerException("Natural number cannot be null!");
        }
        long idx = pos.ToLong();
        long n = Size().ToLong();

        if (idx < 0 || idx >= n) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + idx);
        }

        LLNode<Data> current = headref.Get();
        LLNode<Data> previous = null;

        for (long i = 0; i < idx; i++) {
            previous = current;
            current = current.GetNext().Get();
        }

        Data data = current.Get();

        if (previous == null) {
            headref.Set(current.GetNext().Get());
        } else {
            previous.SetNext(current.GetNext().Get());
        }

        if (tailref.Get() == current) {
            tailref.Set(previous);
        }

        size.Decrement();
        return data;
    }

    @Override
    public boolean Exists(Data dat) {
        if (dat == null) {
            return false;
        }
        LLNode<Data> current = headref.Get();
        while (current != null) {
            if (current.Get().equals(dat)) {
                return true;
            }
            current = current.GetNext().Get();
        }
        return false;
    }

    @Override
    public List<Data> subList(Natural fromIndex, Natural toIndex) {
        return (List<Data>) this.SubSequence(fromIndex, toIndex);
    }

}
