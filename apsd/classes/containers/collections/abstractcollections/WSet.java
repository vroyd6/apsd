package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WSetBase;
import apsd.classes.containers.collections.concretecollections.VList;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Object: Wrapper set implementation via chain. */
abstract public class WSet<Data> extends WSetBase<Data, Chain<Data>>{

  public WSet() {super();}

  public WSet(Chain<Data> chn) {
        super();
        if (chn != null) {
            chn.TraverseForward(dat -> {
                Insert(dat);
                return false;
            });
        }
  }

  public WSet(TraversableContainer<Data> con) {super(con);}

  public WSet(Chain<Data> chn, TraversableContainer<Data> con) {
        super();
        if (chn != null) {
            chn.TraverseForward(dat -> {
                Insert(dat);
                return false;
            });
        }
        if (con != null && con != chn) {
            con.TraverseForward(dat -> {
                Insert(dat);
                return false;
            });
        }
  }

  @Override
  protected void ChainAlloc() { chn = new VList<>(); }

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
        ForwardIterator<Data> it = chn.FIterator();
        while (it.IsValid()) {
            Data cur = it.GetCurrent();
            if (cur == null) {
                if (dat == null) return true;
            } else {
                if (cur.equals(dat)) return true;
            }
            it.Next();
        }
        return false;
    }

  @Override
  abstract protected WSetBase<Data, Chain<Data>> NewInstance();
}
