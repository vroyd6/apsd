package apsd.classes.containers.sequences.abstractbases;

 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic circular vector base implementation. */
abstract public class DynCircularVectorBase<Data> extends CircularVectorBase<Data> implements DynVector<Data> { // Must extend CircularVectorBase and implement DynVector

  protected long size = 0L;

  public DynCircularVectorBase() {
        super(Natural.Of(1));
        this.size = 0L;
        this.start = 0L;
  }

    public DynCircularVectorBase(TraversableContainer<Data> con) {
        super();
        if (con == null) {
            this.size = 0L;
            return;
        }

        ArrayAlloc(con.Size());
        this.size = 0L;

        con.TraverseForward(dat -> {
            long pos = (start + size) % arr.length;
            arr[(int) pos] = dat;
            size++;
            return false;
        });
    }

    public DynCircularVectorBase(Natural inisize) {
        super(inisize);
        this.size = 0L;
        this.start = 0L;
    }

    protected DynCircularVectorBase(Data[] arr) {
        super(arr);
        this.size = arr != null ? arr.length : 0;
        this.start = 0L;
    }

    abstract protected DynCircularVectorBase<Data> NewVector();

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

    @Override
    public Natural Size() {
        return Natural.Of(size);
    }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

    @Override
    public void Clear() {
        super.Clear();
        this.size = 0L;
    }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

    @Override
    public void Realloc(Natural newsize) {
        if (newsize == null) throw new NullPointerException("Natural cannot be null!");

        Data[] oldarr = arr;
        long oldstart = start;

        long newLen = newsize.ToLong();
        long minsize = Math.min(size, newLen);

        ArrayAlloc(newsize);

        for (long idx = 0; idx < minsize; idx++) {
            arr[(int) idx] = oldarr[(int) ((oldstart + idx) % oldarr.length)];
        }

        if (size > arr.length) {
            size = arr.length;
        }
    }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

    protected void EnsureCapacity(Natural num) {
        if (num == null) throw new NullPointerException("Natural cannot be null!");
        long n = num.ToLong();
        if (n <= 0) return;

        long requiredCapacity = size + n;
        long currentCapacity = arr.length;

        if (requiredCapacity > currentCapacity) {
            long newCapacity = Math.max(currentCapacity * 2, requiredCapacity);
            Realloc(Natural.Of(newCapacity));
        }
    }

  @Override
    public void Expand(Natural newsize) {
        if (newsize == null) throw new NullPointerException("Natural cannot be null!");
        if (newsize.ToLong() <= size) return;

        Realloc(newsize);
    }

    @Override
    public void Reduce(Natural num) {
        if (num == null) throw new NullPointerException("Natural cannot be null!");
        long n = num.ToLong();
        if (n <= 0) return;

        if (n >= this.size) {
            Clear();
            return;
        }

        long newSize = this.size - n;

        long capacity = arr.length;

        for (long i = newSize; i < this.size; i++) {
            long realIdx = (start + i) % capacity;
            arr[(int) realIdx] = null;
        }

        this.size = newSize;
    }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

   protected void ArrayAlloc(Natural inisize) {
       if (inisize == null) throw new NullPointerException("Natural cannot be null!");
       long n = inisize.ToLong();
       if (n <= 0) n = 1L;
       arr = (Data[]) new Object[(int) n];
       start = 0L;
   }

   @Override
    public void ShiftRight(Natural pos) {
       if (pos == null) throw new NullPointerException("Natural cannot be null!");
       long p = pos.ToLong();
       if (p > size) throw new IndexOutOfBoundsException("Index out of bounds: " + p + "; Size: " + size + "!");

       EnsureCapacity(pos);

       long capacity = arr.length;

       for (long i = size; i > p; i--) {
           long fromIdx = (start + i - 1) % capacity;
           long toIdx = (start + i) % capacity;
           arr[(int) toIdx] = arr[(int) fromIdx];
       }

       size++;
   }

   @Override
    public void ShiftLeft(Natural pos, Natural num) {
        if (pos == null || num == null) throw new NullPointerException("Natural cannot be null!");

        long idx = pos.ToLong();
        if (idx < 0 || idx >= size)
            throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + size);

        long n = num.ToLong();
        if (n <= 0) return;
        if (n > size - idx) n = size - idx;

        long capacity = arr.length;

        for (long i = idx; i < size - n; i++) {
            long fromIdx = (start + i + n) % capacity;
            long toIdx = (start + i) % capacity;
            arr[(int) toIdx] = arr[(int) fromIdx];
        }

        for (long i = size - n; i < size; i++) {
            long realIdx = (start + i) % capacity;
            arr[(int) realIdx] = null;
        }

        size -= n;
    }

    @Override
    public Data GetAt(Natural pos) {
        if (pos == null) throw new NullPointerException("Natural cannot be null!");
        long idx = pos.ToLong();
        if (idx < 0 || idx >= size)
            throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + size);

        long realIdx = (start + idx) % arr.length;
        return arr[(int) realIdx];
    }

    @Override
    public void SetAt(Data dat, Natural pos) {
        if (pos == null) throw new NullPointerException("Natural cannot be null!");
        long idx = pos.ToLong();
        if (idx < 0 || idx >= size)
            throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + size);

        long realIdx = (start + idx) % arr.length;
        arr[(int) realIdx] = dat;
    }

}
