package apsd.classes.containers.sequences.abstractbases;

 import apsd.classes.utilities.MutableNatural;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.iterators.MutableBackwardIterator;
 import apsd.interfaces.containers.iterators.MutableForwardIterator;
 import apsd.interfaces.containers.sequences.MutableSequence;
 import apsd.interfaces.containers.sequences.Vector;

/** Object: Abstract vector base implementation. */
abstract public class VectorBase<Data> implements Vector<Data> {

  protected Data[] arr;

     VectorBase() {
        super();
        ArrayAlloc(Natural.ZERO);
     }

     public VectorBase(Natural insize) {
       if (insize == null) {
        throw new NullPointerException("Natural cannot be null!");
       }
     ArrayAlloc(insize);
  }

  public VectorBase(TraversableContainer<Data> con) {
      if (con == null) {
          throw new NullPointerException("Traversable Container cannot be null!");
      }
      ArrayAlloc(con.Size());
      final MutableNatural idx = new MutableNatural();
      con.TraverseForward(dat -> {
          SetAt(dat, idx.GetNIncrement()); return false;
       });
  }

    protected VectorBase(Data[] arr) {
        this.arr = arr;
    }

    abstract protected VectorBase<Data> NewVector(Data[] arr);

 protected void ArrayAlloc(Natural insize) {
    if (insize == null) {
        throw new NullPointerException ("Natural cannot be null!");
    }
    long size = insize.ToLong();
    if (size >= Integer.MAX_VALUE) { throw new ArithmeticException("Overflow: size cannot exceed Integer.MAX_VALUE!"); }
    arr = (Data[]) new Object[(int) size];
 }



  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
    public void Clear() {
        ArrayAlloc(Natural.ZERO);
    }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

    @Override
        public Natural Capacity() {
            return Natural.Of(arr.length);
        }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

    protected class VectorFIterator implements MutableForwardIterator<Data> {
        protected long cur;

        public VectorFIterator() {
            cur = 0;
        }

        @Override
        public boolean IsValid() {
            return cur < arr.length;
        }

     @Override
     public void Reset() {
            cur = 0;
     }

     @Override
        public Data GetCurrent() {
            if (!IsValid()) {
                throw new IllegalStateException("Iterator terminated!");
            }
            return arr[(int) cur];
        }

        @Override
        public void Next() {
            if (!IsValid()) {
                throw new IllegalStateException("Iterator terminated!");
            }
            cur++;
        }

        @Override
        public Data DataNNext() {
            if (!IsValid()) {
                throw new IllegalStateException("Iterator terminated!");
            }
            return arr[(int) cur++];
        }

        @Override
        public void Next(Natural n){
            if (n == null) {
                throw new NullPointerException("Natural cannot be null!");
            }
            long step = n.ToLong();
            if (cur + step > arr.length) {
                throw new IllegalStateException("Iterator terminated!");
            }
            cur += step;
        }

     @Override
     public void SetCurrent(Data value) {
            if (!IsValid()) {
                throw new IllegalStateException("Iterator terminated!");
            }
            arr[(int) cur] = value;
     }
    }

    @Override
    public MutableForwardIterator<Data> FIterator() {
        return new VectorFIterator();
    }

    protected class VectorBIterator implements MutableBackwardIterator<Data>{
        protected long cur = Size().ToLong() - 1;

        @Override
        public boolean IsValid() {
            return (cur>=0);
        }

        @Override
        public void prev() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            cur--;
        }

        @Override
        public Data GetCurrent() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            return arr[(int) cur];
        }

        @Override
        public void Reset() {
            cur = Size().ToLong() - 1;
        }

        @Override
        public void SetCurrent(Data dat) {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            arr[(int) cur] = dat;
        }

        @Override
        public Data DataNPrev() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            return arr[(int) cur--];
        }

    }

    @Override
    public MutableBackwardIterator<Data> BIterator() {
        return new VectorBIterator();
    }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

    @Override
    public Vector<Data> SubSequence(Natural from, Natural to) {
        if (from == null || to == null)
            throw new NullPointerException("Natural cannot be null!");

        long start = from.ToLong();
        long end = to.ToLong();
        if (start < 0 || end > arr.length || start > end) {
            throw new IndexOutOfBoundsException("Invalid sub-sequence range!");
        }

        Data[] subArr = (Data[]) new Object[(int)(end - start)];
        System.arraycopy(arr, (int) start, subArr, 0, (int)(end - start));
        return NewVector(subArr);
    }

    @Override
    public Data GetAt(Natural index) {
        if (index == null) {
            throw new NullPointerException("Natural cannot be null!");
        }
        long idx = index.ToLong();
        if (idx < 0 || idx >= arr.length) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        return arr[(int) idx];
    }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

    @Override
    public void SetAt(Data dat, Natural index) {
        if (index == null) {
            throw new NullPointerException("Natural cannot be null!");
        }
        long idx = index.ToLong();
        if (idx < 0 || idx >= arr.length) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        arr[(int) idx] = dat;
    }


}
