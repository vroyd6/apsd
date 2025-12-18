package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.traits.Predicate;

public interface OrderedList<Data extends Comparable<? super Data>> extends List<Data>, OrderedChain<Data> {

    @Override
    default boolean Filter(Predicate<Data> fun) {
        return OrderedChain.super.Filter(fun);
    }

    @Override
    default boolean IsEqual(IterableContainer<Data> other) {
        return OrderedChain.super.IsEqual(other);
    }

    @Override
    default boolean Insert(Data dat) {
        if (dat == null) {
            return false;
        }
        long i = 0;
        while (i < Size().ToLong() && dat.compareTo(GetAt(Natural.Of(i))) > 0) {
            i++;
        }
        AddAt(dat, Natural.Of(i));
        return true;
    }

    void AddAt(Data dat, Natural pos);
}
