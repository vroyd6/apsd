package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<Data> extends TraversableContainer<Data> {

    //FIterator
    MutableForwardIterator<Data> FIterator();

    //BIterator
    BackwardIterator<Data> BIterator();

    //isEqual
    default boolean isEqual(IterableContainer<Data> con) {
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



}