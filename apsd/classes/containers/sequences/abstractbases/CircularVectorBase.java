package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Abstract (static) circular vector base implementation. */
abstract public class CircularVectorBase<Data> extends VectorBase<Data> {

  protected long start = 0L;

  public CircularVectorBase() { super();}

    public CircularVectorBase(Natural inisize) {
        super(inisize);
    }

    public CircularVectorBase(TraversableContainer<Data> con) {
        super(con);
    }

    protected CircularVectorBase(Data[] arr) {
        super(arr);
    }

    @SuppressWarnings("unchecked")
    protected void ArrayAlloc(Natural newsize) {
        long size = newsize.ToLong();
        if (size >= Integer.MAX_VALUE) {
            throw new ArithmeticException("Overflow: size cannot exceed Integer.MAX_VALUE!");
        }
        arr = (Data[]) new Object[(int) size];
        start = 0L;
    }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

    @Override
    public void Realloc(Natural newsize) {
        if (newsize == null) {
            throw new NullPointerException("Natural cannot be null!");
        }
        Data[] oldarr= arr;
        long oldstart = start;
        long minsize = Math.min(Size().ToLong(), newsize.ToLong());
        ArrayAlloc (newsize);
        for (long idx = 0; idx <minsize; idx++) {
            arr[(int) idx] = oldarr [(int) ((oldstart+idx)%oldarr.length)];
        }
    }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

    @Override
    public Data GetAt(Natural index) {
        if (index == null) {
            throw new NullPointerException("Natural cannot be null!");
        }
        if (index.ToLong() >= Size().ToLong()) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        return arr[(int) ((start + index.ToLong()) % arr.length)];
    }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

    @Override
    public void SetAt(Data dat, Natural index) {
        if (index == null) {
            throw new NullPointerException("Natural cannot be null!");
        }
        if (index.ToLong() >= Size().ToLong()) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        arr[(int) ((start + index.ToLong()) % arr.length)] = dat;
    }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

    @Override
    public void ShiftLeft(Natural index, Natural num) {
      if (index == null || num == null) {
          throw new NullPointerException("Natural cannot be null!");
      }
    long idx = index.ToLong();
    long size = Size().ToLong();
      long n = num.ToLong();
      long capacity = arr.length;

      if (idx < 0 || idx > size) {
          throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + size);
      }
      if (n < 0) {
          throw new IllegalArgumentException("Number of positions to shift cannot be negative: " + n);
      }
      if (n == 0 || idx == size) {
          return; // No shift needed
      }
      if (n > size - idx) {
          n = size - idx; // Adjust n to not exceed bounds
      }

      for (long i = idx; i < size - n; i++) {
          arr[(int) ((start + i) % capacity)] = arr[(int) ((start + i + n) % capacity)];
      }
      // Clear the vacated positions
      for (long i = size - n; i < size; i++) {
          arr[(int) ((start + i) % capacity)] = null;
      }
    }

    @Override
    public void ShiftRight(Natural index, Natural num) {
      if (index == null || num == null) {
          throw new NullPointerException("Natural cannot be null!");
      }

      long idx = index.ToLong();
      long size = Size().ToLong();
      long n = num.ToLong();
      long capacity = arr.length;

      if (idx < 0 || idx > size) {
          throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + size);
      }
      if (n < 0) {
          throw new IllegalArgumentException("Number of positions to shift cannot be negative: " + n);
      }
      if (n == 0 || idx == size) {
          return; // No shift needed
      }
      if (n > size - idx) {
          n = size - idx; // Adjust n to not exceed bounds
      }

      for (long i = size - 1; i >= idx; i--) {
          arr[(int) ((start + i + n) % capacity)] = arr[(int) ((start + i) % capacity)];
      }
      // Clear the vacated positions
      for (long i = idx; i < idx + n; i++) {
          arr[(int) ((start + i) % capacity)] = null;
      }
    }

}
