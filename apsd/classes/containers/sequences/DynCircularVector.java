package apsd.classes.containers.sequences;

 import apsd.classes.containers.sequences.abstractbases.DynCircularVectorBase;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.sequences.DynVector;
 import apsd.interfaces.traits.Predicate;
 import org.junit.platform.reporting.shadow.org.opentest4j.reporting.events.core.Data;

/** Object: Concrete dynamic circular vector implementation. */
public class DynCircularVector<Data> extends DynCircularVectorBase<Data>{

    public DynCircularVector() {
        super(Natural.ONE);
        this.size=0L;
    }

    public DynCircularVector(Natural inisize) {
        super(inisize.ToLong() > 0 ? inisize : Natural.ONE);
        this.size = 0L;
    }

    public DynCircularVector(TraversableContainer<Data> con) {
        super(con);
    }

    protected DynCircularVector(Data[] arr) {
        super(arr);
        this.size = arr != null ? arr.length : 0;
    }

    @Override
    protected DynCircularVector<Data> NewVector() {
        return new DynCircularVector<>();
    }

    @Override
    public void InsertAt(Data dat, Natural pos) {
        long idx = pos.ToLong();
        if (idx < 0 || idx > size) throw new IndexOutOfBoundsException();

        if (size == arr.length) EnsureCapacity(Natural.ONE);

        for (long i = size; i > idx; i--) {
            arr[(int)((start + i) % arr.length)] = arr[(int)((start + i - 1) % arr.length)];
        }
        arr[(int)((start + idx) % arr.length)] = dat;
        size++;
    }

    @Override
    public Data AtNRemove(Natural pos) {
        long idx = pos.ToLong();
        if (idx < 0 || idx >= this.size) throw new IndexOutOfBoundsException();
        int realIdx = (int)((start + idx) % arr.length);
        Data removed = arr[realIdx];

        for (long i = idx; i < size - 1; i++) {
            arr[(int)((start + i) % arr.length)] = arr[(int)((start + i + 1) % arr.length)];
        }
        arr[(int)((start + size - 1) % arr.length)] = null;
        size--;
        return removed;
    }

    @SuppressWarnings("unchecked")
    @Override
    public apsd.interfaces.containers.sequences.DynVector<Data> SubVector(Natural start, Natural end) {
        long s = start.ToLong();
        long e = end.ToLong();

        if (s < 0 || e > size || s > e)
            throw new IndexOutOfBoundsException("Range non valido: " + s + " - " + e);

        Data[] subArr = (Data[]) new Object[(int)(e - s)];

        for (int i = 0; i < subArr.length; i++) {
            subArr[i] = arr[(int)((this.start + s + i) % arr.length)];
        }

        return (DynVector<Data>) NewVector(subArr);
    }

    @Override
    public boolean TraverseForward(Predicate<Data> fun) {
        for (int i = 0; i < size; i++) {
            int idx = (int)((start + i) % arr.length);
            if (fun.Apply(arr[idx])) return true;
        }
        return false;
    }

    @Override
    public boolean TraverseBackward(Predicate<Data> fun) {
        for (int i = (int)size - 1; i >= 0; i--) {
            int idx = (int)((start + i) % arr.length);
            if (fun.Apply(arr[idx])) return true;
        }
        return false;
    }

    @Override
    public boolean Exists(Data dat) {
        for (int i = 0; i < size; i++) {
            int idx = (int)((start + i) % arr.length);
            if (arr[idx] == null ? dat == null : arr[idx].equals(dat)) return true;
        }
        return false;
    }

    @Override
    public void RemoveAt(Natural pos) {
        AtNRemove(pos);
    }

    @Override
    protected DynCircularVector<Data> NewVector(Data[] arr) {
        return new DynCircularVector<>(arr);
    }

    @Override
    public Natural Size() {
        return Natural.Of(size);
    }


}
