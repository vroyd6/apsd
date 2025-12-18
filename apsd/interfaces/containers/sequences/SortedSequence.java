package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.SortedIterableContainer;

/** Interface: Sequence & SortedIterableContainer. */
public interface SortedSequence<Data extends Comparable<? super Data>> extends Sequence<Data>, SortedIterableContainer<Data> { // Must extend Sequence and SortedIterableContainer

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

    @Override
    default boolean Exists(Data dat) {
        return Search(dat) != null;
    }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
    default Natural Search(Data dat){
        if (dat == null) return null;
        long left = 0;
        long right = Size().ToLong() - 1;
        while (left <= right) {
            long mid = (left + right) / 2;
            Data midVal = GetAt(Natural.Of(mid));
            int cmp = midVal.compareTo(dat);
            if (cmp < 0) left = mid + 1;
            else if (cmp > 0) right = mid - 1;
            else return Natural.Of(mid);
        }
        return null;
  }

}
