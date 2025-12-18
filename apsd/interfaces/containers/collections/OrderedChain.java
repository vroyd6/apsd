package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.traits.Predicate;

public interface OrderedChain<Data extends Comparable<? super Data>> extends Chain<Data>, OrderedSet<Data>{

    @Override
    default boolean Filter(Predicate<Data> fun) {
        return Chain.super.Filter(fun);
    }

    @Override
    default boolean IsEqual(IterableContainer<Data> other) {
        return Chain.super.IsEqual(other);
    }
}
