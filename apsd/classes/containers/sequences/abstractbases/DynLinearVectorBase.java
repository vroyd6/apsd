package apsd.classes.containers.sequences.abstractbases;

 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic linear vector base implementation. */
abstract public class DynLinearVectorBase<Data> extends LinearVectorBase<Data> implements DynVector<Data> { // Must extend LinearVectorBase and implement DynVector

    protected long size = 0L;

  public DynLinearVectorBase() {
      super(Natural.Of(1));
      this.size = 0L;
  }

    public DynLinearVectorBase(Natural insize) {
        super(insize);
        this.size = 0L;
    }

    public DynLinearVectorBase(TraversableContainer<Data> con) {
        super(con);
        this.size = con != null ? con.Size().ToLong() : 0L;
    }

    protected DynLinearVectorBase(Data[] arr) {
        super(arr);
        this.size = arr != null ? arr.length : 0;
    }

    @SuppressWarnings("unchecked")
    protected void ArrayAlloc(Natural newsize) {
        long newLen = newsize.ToLong();

        if (newLen == 0) newLen = 1;
        if (newLen >= Integer.MAX_VALUE) {
            throw new ArithmeticException("Overflow: size cannot exceed Integer.MAX_VALUE!");
        }
        Data[] newArr = (Data[]) new Object[(int) newLen];
        long limit = Math.min(size, newLen);
        for (int i = 0; i < limit; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

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
        for (long i = 0L; i < size; i++) {
            arr[(int) i] = null;
        }
        size = 0L;
    }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

    @Override
    public void Realloc(Natural newsize) {
        ArrayAlloc(newsize);
        if (size > newsize.ToLong()) {
            size = newsize.ToLong();
        }
    }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

   protected void EnsureCapacity(Natural mincapacity) {
       if (mincapacity == null) throw new NullPointerException("Natural cannot be null!");

       if (arr.length < mincapacity.ToLong()) {
           Natural newsize = Natural.Of(arr.length * 2L);
           if (newsize.ToLong() < mincapacity.ToLong()) {
               newsize = mincapacity;
           }
           Realloc(newsize);
       }
   }

   @Override
    public void Expand(Natural num) {
       if (num == null) throw new NullPointerException("Natural cannot be null!");

       EnsureCapacity(Natural.Of(size + num.ToLong()));
   }
}
