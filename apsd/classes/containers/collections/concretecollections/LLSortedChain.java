package apsd.classes.containers.collections.concretecollections;

 import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
 import apsd.classes.containers.collections.concretecollections.bases.LLNode;
 import apsd.classes.utilities.Box;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.Chain;
 import apsd.interfaces.containers.collections.SortedChain;
 import apsd.interfaces.containers.iterators.ForwardIterator;
 import apsd.interfaces.traits.Predicate;

/** Object: Concrete sorted chain implementation on linked-list. */
public class LLSortedChain<Data extends Comparable<? super Data>> extends LLChainBase<Data> implements SortedChain<Data> { // Must extend LLChainBase and implement SortedChain

   public LLSortedChain() { super(); }

   public LLSortedChain(LLSortedChain<Data> chn) { super(chn); }

   public LLSortedChain(TraversableContainer<Data> con) { super(con); }

   protected LLSortedChain(long size, LLNode<Data> head, LLNode<Data> tail) {
      super(size, head, tail);
   }

   @Override
 protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail) {
      return new LLSortedChain<Data>(size, head, tail);
   }

  /* ************************************************************************ */
  /* Specific member functions of LLSortedChain                               */
  /* ************************************************************************ */

 protected LLNode<Data> PredFind(Data dat){
    if(dat==null) {
        return null;
    }
    Box<LLNode<Data>> cur = headref;
    long len = size.ToLong();
    LLNode<Data> prd = null;
    while (len>0) {
        long newlen= (len-1)/2;
         Box<LLNode<Data>> nxt= cur;
         for(long idx=0; idx<newlen; idx++,nxt=nxt.Get().GetNext()) {

         }
         if(nxt.Get().Get().compareTo(dat)<0) {
             prd= nxt.Get();
             cur= nxt.Get().GetNext();
             len = len-newlen-1;
         } else {
          len =newlen;
         }
    }
    return prd;
 }

 protected LLNode<Data> PredPredFind(Data dat){
  if(dat==null) {
   return null;
  }
  ForwardIterator<Box<LLNode<Data>>> itr= FRefIterator();
  long len = size.ToLong();
  LLNode<Data> prdprd=null;
  while (len >1) {
   long newlen = len /2;
   ForwardIterator<Box<LLNode<Data>>> nxt = new ListFRefIterator((ListFRefIterator) itr);
   nxt.Next(newlen-1);
   LLNode<Data> tmp= nxt.GetCurrent().Get();
   nxt.Next();
   if(nxt.GetCurrent().Get().Get().compareTo(dat)<0) {
    prdprd= tmp;
    itr=nxt;
    len = len-newlen;
   }else {
    len =newlen;
   }
  }
  return prdprd;
 }
  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

    @Override
    public boolean Insert(Data dat) {
        if(dat==null) {
            throw new IllegalArgumentException("Data to insert cannot be null.");
        }
        LLNode<Data> prd= PredFind(dat);
        LLNode<Data> newnode= new LLNode<Data>(dat);
        if(prd==null) {
            newnode.SetNext(headref.Get());
            headref.Set(newnode);
            if(size.IsZero()) {
                tailref.Set(newnode);
            }
        } else {
            newnode.SetNext(prd.GetNext().Get());
            prd.GetNext().Set(newnode);
            if(prd==tailref.Get()) {
                tailref.Set(newnode);
            }
        }
        size.Increment();
        return true;
    }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

    @Override
    public boolean Remove(Data dat) {
        if(dat==null) {
            throw new IllegalArgumentException("Data to remove cannot be null.");
        }
        LLNode<Data> prd= PredPredFind(dat);
        LLNode<Data> target;
        if(prd==null) {
            target= headref.Get();
            if(target==null || target.Get().compareTo(dat)!=0) {
                return false;
            }
            headref.Set(target.GetNext().Get());
            if(size.IsOne()) {
                tailref.Set(null);
            }
        } else {
            target= prd.GetNext().Get();
            if(target==null || target.Get().compareTo(dat)!=0) {
                return false;
            }
            prd.GetNext().Set(target.GetNext().Get());
            if(target==tailref.Get()) {
                tailref.Set(prd);
            }
        }
        size.Decrement();
        return true;
    }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
    @Override
    public Natural Search (Data dat) {
     if(dat==null) {
      return null;
     }
     Box<LLNode<Data>> cur= headref;
     long len = size.ToLong();
     long pos=0;
     while(len>0) {
      len = len/2;
      Box<LLNode<Data>> nxt= cur;
      for(long idx= 0; idx<len; idx++, nxt= nxt.Get().GetNext()) {

      }
      Data elm= nxt.Get().Get();
      if(elm.compareTo(dat)==0) {
       return Natural.Of(pos+len);
      }else if(elm.compareTo(dat)<0) {
       cur = nxt;
       pos += len;
      }
     }
     return null;
    }

  /* ************************************************************************ */
  /* Override specific member functions from SortedSequence                   */
  /* ************************************************************************ */

    @Override
 public Natural SearchPredecessor (Data dat) {
     if(dat==null) {
      return null;
     }
     Box<LLNode<Data>> cur= headref;
     long len = size.ToLong();
     long pos=0;
     while(len>0) {
      long newlen = (len-1)/2;
      Box<LLNode<Data>> nxt= cur;
      for(long idx=0; idx<newlen; idx++, nxt= nxt.Get().GetNext()) {

      }
      Data elm= nxt.Get().Get();
      if(elm.compareTo(dat)<0) {
       cur = nxt.Get().GetNext();
       pos += newlen+1;
       len = len - newlen -1;
      }else {
       len = newlen;
      }
     }
     if(pos==0) {
      return null;
     }
     return Natural.Of(pos-1);
    }

    @Override
 public Natural SearchSuccessor (Data dat) {
     if(dat==null) {
      return null;
     }
     Box<LLNode<Data>> cur= headref;
     long len = size.ToLong();
     long pos=0;
     while(len>0) {
      long newlen = (len-1)/2;
      Box<LLNode<Data>> nxt= cur;
      for(long idx=0; idx<newlen; idx++, nxt= nxt.Get().GetNext()) {

      }
      Data elm= nxt.Get().Get();
      if(elm.compareTo(dat)<=0) {
       cur = nxt.Get().GetNext();
       pos += newlen+1;
       len = len - newlen -1;
      }else {
       len = newlen;
      }
     }
     if(pos==size.ToLong()) {
      return null;
     }
     return Natural.Of(pos);
    }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  @Override
 public Data Predecessor (Data dat) {
     Natural pos= SearchPredecessor(dat);
     if(pos==null) {
      return null;
     }
     return GetAt(pos);
    }

    @Override
 public void RemovePredecessor (Data dat) {
     Natural pos= SearchPredecessor(dat);
     if(pos!=null) {
      RemoveAt(pos);
     }
    }

    @Override
    public Data PredecessorNRemove(Data dat) {
     if(dat==null) {
      return null;
     }
     LLNode<Data> prd= PredPredFind(dat);
     Box<LLNode<Data>> cur = (prd==null)? headref:prd.GetNext();
     LLNode<Data> node= cur.Get();
     Data elm= node.Get();
     if(elm.compareTo(dat)>=0) {
      return null;
     }
     cur.Set(node.GetNext().Get());
     if(tailref.Get()==node) {
      tailref.Set(prd);
     }
     size.Decrement();
     return elm;
    }

 @Override
    public Data Successor (Data dat) {
        Natural pos= SearchSuccessor(dat);
        if(pos==null) {
        return null;
        }
        return GetAt(pos);
        }

        @Override
    public void RemoveSuccessor (Data dat) {
        Natural pos = SearchSuccessor(dat);

        if(pos!=null) {
         RemoveAt(pos);
        }
    }

    @Override
    public Data SuccessorNRemove(Data dat) {
     if(dat==null) {
      return null;
     }
     LLNode<Data> prd= PredFind(dat);
     Box<LLNode<Data>> cur = (prd==null)? headref:prd.GetNext();
     LLNode<Data> node= cur.Get();
     if(node==null) {
      return null;
     }
     Data elm= node.Get();
     if(elm.compareTo(dat)<=0) {
      return null;
     }
     cur.Set(node.GetNext().Get());
     if(tailref.Get()==node) {
      tailref.Set(prd);
     }
     size.Decrement();
     return elm;
    }

 /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

 @Override
 public boolean InsertIfAbsent(Data dat) {
  if(dat==null) {
   return false;
  }
  LLNode<Data> prd= PredFind(dat);
  Box<LLNode<Data>> cur = (prd==null) ? headref: prd.GetNext();
  LLNode<Data> node = cur.Get();

  if (node != null && node.Get().compareTo(dat) == 0) {
   return false;
  } else {
   LLNode<Data> newmode = new LLNode<>(dat,node);
   cur.Set(newmode);
   if(tailref.Get()==prd) {
    tailref.Set(newmode);
   }
   size.Increment();
   return true;
  }
 }

    @Override
    public void RemoveOccurrences(Data dat) {
        if(dat==null) {
        throw new IllegalArgumentException("Data to remove cannot be null.");
        }
        LLNode<Data> prd= PredPredFind(dat);
        Box<LLNode<Data>> cur= (prd==null)? headref:prd.GetNext();
        LLNode<Data> node= cur.Get();
        while(node!=null && node.Get().compareTo(dat)==0) {
           cur.Set(node.GetNext().Get());
           if(tailref.Get()==node) {
              tailref.Set(prd);
           }
        size.Decrement();
        node= cur.Get();
        }
    }

 @Override
 public Chain<Data> SubChain(Natural start, Natural end) {
  long s = start.ToLong();
  long e = end.ToLong();

  if (s < 0 || e > size.ToLong() || s > e) {
   throw new IndexOutOfBoundsException();
  }

  LLNode<Data> cur = headref.Get();
  for (long i = 0; i < s; i++) {
   cur = cur.GetNext().Get();
  }

  LLSortedChain<Data> sub = new LLSortedChain<>();
  for (long i = s; i < e; i++) {
   sub.Insert(cur.Get());
   cur = cur.GetNext().Get();
  }

  return sub;
 }

    @Override
 public void RemoveAt(Natural position) {
     long pos= position.ToLong();
     if(pos<0 || pos>= size.ToLong()) {
      throw new IndexOutOfBoundsException();
     }
     LLNode<Data> prd= null;
     Box<LLNode<Data>> cur= headref;
     for(long idx=0; idx<pos; idx++) {
      prd= cur.Get();
      cur= cur.Get().GetNext();
     }
     LLNode<Data> node= cur.Get();
     cur.Set(node.GetNext().Get());
     if(tailref.Get()==node) {
      tailref.Set(prd);
     }
     size.Decrement();
    }

    @Override
 public boolean TraverseForward(Predicate<Data> filter) {
     if (filter == null) return false;

        LLNode<Data> current = headref.Get();
        while (current != null) {
            if (filter.Apply(current.Get())) {
                return false;
            }
            current = current.GetNext().Get();
        }
        return false;
    }

    @Override
    public boolean TraverseBackward(Predicate<Data> filter) {
     if (filter == null) return false;

     java.util.ArrayList<Data> tmp = new java.util.ArrayList<>();

     LLNode<Data> cur = headref.Get();
     while (cur != null) {
      tmp.add(cur.Get());
      cur = cur.GetNext().Get();
     }

     for (int i = tmp.size() - 1; i >= 0; i--) {
      if (filter.Apply(tmp.get(i))) return true;
     }
     return false;
        }

        @Override
 public Natural Size() {
     return size.ToNatural();
    }

    @Override
    public boolean Exists(Data dat) {
     return Search(dat) != null;
    }

    @Override
    public Data Min() {
     if (isEmpty()) {
      return null;
     }
     return headref.Get().Get();
    }

    @Override
    public Data Max() {
     if (isEmpty()) {
      return null;
     }
     return tailref.Get().Get();
    }

    @Override
 public Data AtNRemove(Natural position) {
     long pos= position.ToLong();
     if(pos<0 || pos>= size.ToLong()) {
      throw new IndexOutOfBoundsException();
     }
     LLNode<Data> prd= null;
     Box<LLNode<Data>> cur= headref;
     for(long idx=0; idx<pos; idx++) {
      prd= cur.Get();
      cur= cur.Get().GetNext();
     }
     LLNode<Data> node= cur.Get();
     Data elm= node.Get();
     cur.Set(node.GetNext().Get());
     if(tailref.Get()==node) {
      tailref.Set(prd);
     }
     size.Decrement();
     return elm;
    }

 @Override
 public int compareTo(Data o) {
    throw new UnsupportedOperationException("compareTo is not supported for LLSortedChain.");
 }
}
