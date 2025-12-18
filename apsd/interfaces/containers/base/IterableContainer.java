package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.traits.Accumulator;
import apsd.interfaces.traits.Predicate;

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<Data> extends TraversableContainer<Data> {

    //FIterator
    MutableForwardIterator<Data> FIterator();

    //BIterator
    BackwardIterator<Data> BIterator();

    //IsEqual
    default boolean IsEqual(IterableContainer<Data> con) {
        if (con == null) {
            return false;
        }
        ForwardIterator<Data> it1 = this.FIterator();
        ForwardIterator<Data> it2 = con.FIterator();
        while (it1.IsValid() && it2.IsValid()) {
            Data d1 = it1.GetCurrent();
            Data d2 = it2.GetCurrent();
            if ((d1 == null && d2 != null) || (d1 != null && !d1.equals(d2))) {
                return false;
            }
            it1.Next();
            it2.Next();
        }
        return it1.IsValid() && it2.IsValid();
    }





    /* ************************************************************************ */
    /* Override specific member functions from TraversableContainer             */
    /* ************************************************************************ */

    @Override
    boolean TraverseForward(Predicate<Data> fun);

    @Override
    boolean TraverseBackward(Predicate<Data> fun);

    @Override
    default <Acc> Acc FoldForward(Accumulator<Data, Acc> fun, Acc ini) {
        final Box<Acc> acc = new Box<>(ini);
        if (fun != null) {
            this.TraverseForward(dat -> {
                acc.Set(fun.Apply(dat, acc.Get()));
                return false;
            });
        }
        return acc.Get();
    }

    @Override
    default <Acc> Acc FoldBackward(Accumulator<Data, Acc> fun, Acc ini) {
        final Box<Acc> acc = new Box<>(ini);
        if (fun != null) {
            this.TraverseBackward(dat -> {
                acc.Set(fun.Apply(dat, acc.Get()));
                return false;
            });
        }
        return acc.Get();
    }

}