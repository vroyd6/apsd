package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WOrderedSetBase;
import apsd.classes.containers.collections.abstractcollections.bases.WSetBase;
import apsd.classes.containers.collections.concretecollections.VSortedChain;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.SortedChain;

import apsd.classes.containers.collections.abstractcollections.bases.WOrderedSetBase;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.interfaces.traits.Predicate;

/** Object: Wrapper ordered set implementation via ordered chain. */
public class WOrderedSet<Data extends Comparable <? super Data>>extends WOrderedSetBase<Data,SortedChain<Data>> {

    public WOrderedSet() {
        super();
    }

    public WOrderedSet(SortedChain<Data> chn) {
        super (chn);
    }

    public WOrderedSet(TraversableContainer<Data> con) {
        super(con);
    }

    public WOrderedSet(SortedChain<Data> chn, TraversableContainer<Data> con) {
        super (chn,con);
    }

    @Override
    protected void ChainAlloc() {
        chn = new VSortedChain<Data>();
    }

    @Override
    public boolean TraverseForward(Predicate<Data> fun) {
        if (fun == null) return false;
        return chn.FIterator().ForEachForward(fun);
    }


    @Override
    public boolean TraverseBackward(Predicate<Data> fun) {
        if (fun == null) return false;
        return chn.BIterator().ForEachBackward(fun);
    }


    @Override
    public boolean Exists(Data dat) {
        if (dat == null) return false;
        return chn.Exists(dat);
    }


    @Override
    protected WSetBase<Data, SortedChain<Data>> NewInstance() {
        return new WOrderedSet<>();
    }


}