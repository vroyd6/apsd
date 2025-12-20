package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.CircularVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.Vector;

/** Object: Concrete (static) circular vector implementation. */
public class CircularVector<Data> extends CircularVectorBase<Data> {

   public CircularVector() {super();}

   public CircularVector(Natural inisize) {super(inisize);}

   public CircularVector(TraversableContainer<Data> con) {super(con);}

   protected CircularVector(Data[] arr) {super(arr);}

    @Override
    protected CircularVector<Data> NewVector(Data[] arr) {
        return new CircularVector<>(arr);
    }

    @Override
    public Natural Size() {
        return Natural.Of(arr != null ? arr.length : 0);
    }

    @Override
    public boolean TraverseForward(apsd.interfaces.traits.Predicate<Data> fun) {
        if (fun == null) return false;
        for (Data d : arr) {
            if (fun.Apply(d)) return true;
        }
        return false;
    }

    @Override
    public boolean TraverseBackward(apsd.interfaces.traits.Predicate<Data> fun) {
        if (fun == null) return false;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (fun.Apply(arr[i])) return true;
        }
        return false;
    }

    @Override
    public boolean Exists(Data dat) {
        for (Data d : arr) {
            if (d == null ? dat == null : d.equals(dat)) return true;
        }
        return false;
    }

    @Override
    public Vector<Data> SubVector(Natural start, Natural end) {
        if (start == null || end == null)
            throw new NullPointerException("Natural cannot be null!");

        long s = start.ToLong();
        long e = end.ToLong();

        if (s < 0 || e > arr.length || s > e)
            throw new IndexOutOfBoundsException("Invalid subsequence range!");

        @SuppressWarnings("unchecked")
        Data[] subarr = (Data[]) new Object[(int)(e - s)];
        System.arraycopy(arr, (int)s, subarr, 0, (int)(e - s));

        return NewVector(subarr);
    }

}
