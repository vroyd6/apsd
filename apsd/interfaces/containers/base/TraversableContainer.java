package apsd.interfaces.containers.base;

 import apsd.classes.utilities.Box;
 import apsd.classes.utilities.MutableNatural;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.traits.Accumulator;
 import apsd.interfaces.traits.Predicate;

/** Interface: MembershipContainer con supporto all'attraversamento. */
public interface TraversableContainer<Data> extends MembershipContainer<Data> {


    boolean TraverseForward(Predicate<Data> fun);

    boolean TraverseBackward(Predicate<Data> fun);


    default <Acc> Acc FoldForward(Accumulator<Data, Acc> fun, Acc ini) {
        final Box<Acc> acc = new Box<>(ini);
        if (fun != null) TraverseForward(dat -> {
            acc.Set(fun.Apply(dat, acc.Get()));
            return false;
        });
        return acc.Get();
    }


    default <Acc> Acc FoldBackward(Accumulator<Data, Acc> fun, Acc ini) {
        final Box<Acc> acc = new Box<>(ini);
        if (fun != null) TraverseBackward(dat -> {
            acc.Set(fun.Apply(dat, acc.Get()));
            return false;
        });
        return acc.Get();
    }

    /* ************************************************************************ */
    /* Override specific member functions from Container                        */
    /* ************************************************************************ */

    @Override
    default Natural Size() {
        final MutableNatural size = new MutableNatural(0);
        TraverseForward(dat -> {
            size.Increment();
            return false;
        });
        return size.ToNatural();
    }

    /* ************************************************************************ */
    /* Override specific member functions from MembershipContainer              */
    /* ************************************************************************ */

    default boolean Exists(Predicate<Data> dat) {
        if (dat == null) {
            return false;
        }
        return TraverseForward(dat);
    }

}
