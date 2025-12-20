package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.traits.Predicate;

/** Object: Concrete dynamic (linear) vector implementation. */
public class DynVector<Data> extends DynLinearVectorBase<Data> { // Must extend DynLinearVectorBase

  public DynVector() {
    super(Natural.ONE);
    this.size = 0L;
  }

  public DynVector(Natural inisize){
    super(inisize.ToLong() > 0 ? inisize : Natural.ONE);
    this.size = 0L;
  }

  public DynVector(TraversableContainer<Data> con){
    super(con);
  }

  protected DynVector(Data[] arr) {
    super(arr);
    this.size = arr != null ? arr.length : 0;
  }

  @Override
    protected DynLinearVectorBase<Data> NewVector(Data[] arr) {
        return new DynVector<>(arr);
    }

    @Override
    public void InsertAt(Data dat, Natural pos) {
        long idx = pos.ToLong();
        if (idx < 0 || idx > size) throw new IndexOutOfBoundsException();

        ShiftRight(pos, Natural.ONE);
        arr[(int)idx] = dat;
    }

    @Override
    public Data AtNRemove(Natural pos) {
        long idx = pos.ToLong();
        if (idx < 0 || idx >= size) throw new IndexOutOfBoundsException();
        Data removed = arr[(int)idx];

        for (long i = idx; i < size - 1; i++) {
            arr[(int)i] = arr[(int)(i+1)];
        }
        arr[(int)(--size)] = null;
        return removed;
    }

    @Override
    public DynVector<Data> SubVector(Natural from, Natural to) {
        long start = from.ToLong();
        long end = to.ToLong();
        if (start < 0 || end > size || start > end) throw new IndexOutOfBoundsException();

        Data[] subArr = (Data[]) new Object[(int)(end - start)];
        for (long i = start; i < end; i++) {
            subArr[(int)(i - start)] = arr[(int)i];
        }
        return new DynVector<>(subArr);
    }

    @Override
    public void ShiftRight(Natural pos, Natural num) {
        if (pos == null || num == null)
            throw new NullPointerException("Natural cannot be null!");

        long idx = pos.ToLong();
        long shift = num.ToLong();

        if (shift <= 0) return;
        if (idx < 0 || idx > size)
            throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + size);

        EnsureCapacity(num);

        for (long i = size - 1; i >= idx; i--) {
            arr[(int)(i + shift)] = arr[(int)i];
        }

        for (long i = idx; i < idx + shift; i++) {
            arr[(int)i] = null;
        }

        size += shift;

    }

    @Override
    public void ShiftLeft(Natural pos, Natural num) {
        long idx = pos.ToLong();
        long len = Math.min(num.ToLong(), size - idx);
        for (long i = idx; i + len < size; i++) {
            arr[(int)i] = arr[(int)(i + len)];
        }
        for (long i = size - len; i < size; i++) arr[(int)i] = null;
        size -= len;
    }

    @Override
    public boolean TraverseForward(Predicate<Data> func) {
        for (long i = 0; i < size; i++) {
            if (!func.Apply(arr[(int)i])) return false;
        }
        return true;
    }

    @Override
    public boolean TraverseBackward(Predicate<Data> func) {
        for (long i = size - 1; i >= 0; i--) {
            if (!func.Apply(arr[(int)i])) return false;
        }
        return true;
    }

    @Override
    public boolean Exists(Data dat) {
        for (long i = 0; i < size; i++) {
            if ((arr[(int)i] == null && dat == null) || (arr[(int)i] != null && arr[(int)i].equals(dat))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void RemoveAt(Natural pos) {
        long idx = pos.ToLong();
        if (idx < 0 || idx >= size) throw new IndexOutOfBoundsException();

        for (long i = idx; i < size - 1; i++) {
            arr[(int)i] = arr[(int)(i + 1)];
        }
        arr[(int)(--size)] = null;
    }

    public Natural Size() {
        return Natural.Of(size);
    }

    @Override
    public void Reduce(Natural num) {
        long reduceBy = num.ToLong();
        if (reduceBy <= 0) return;
        if (reduceBy > size) reduceBy = size;

        size -= reduceBy;
        for (long i = size; i < size + reduceBy; i++) {
            arr[(int)i] = null;
        }

        Shrink();
    }

}
