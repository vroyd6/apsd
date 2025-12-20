package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.LinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;


/** Object: Concrete (static linear) vector implementation. */
public class Vector<Data> extends LinearVectorBase<Data>{

    public Vector() {
        super(Natural.ZERO);
    }

    public Vector(Natural inisize) {
        super(inisize);
    }

    public Vector(TraversableContainer<Data> con) {
        super(con);
    }

    protected Vector(Data[] arr) {
        super(arr);
    }

    @Override
    protected Vector<Data> NewVector(Data[] arr) {
        return new Vector<>(arr);
    }

    @Override
    public Natural Size() {
        return Natural.Of(arr.length);
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
    public Vector<Data> SubVector(Natural from, Natural to) {
        if (from == null || to == null)
            throw new NullPointerException("Natural cannot be null!");

        long start = from.ToLong();
        long end = to.ToLong();

        if (start < 0 || end > arr.length || start > end)
            throw new IndexOutOfBoundsException("Invalid subvector range!");

        int newsize = (int)(end - start);
        @SuppressWarnings("unchecked")
        Data[] subarr = (Data[]) new Object[newsize];

        System.arraycopy(arr, (int) start, subarr, 0, newsize);

        return new Vector<>(subarr);
    }


}
