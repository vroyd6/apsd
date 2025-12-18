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
        Natural pos = Search(dat);
        if (pos.Equals(Natural.NEGATIVE_ONE)) {
            return false;
        }
        Data found = GetAt(pos);
        return found.equals(dat);
    }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
    Natural Search(Data dat);

}
