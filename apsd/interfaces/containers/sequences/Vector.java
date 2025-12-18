package apsd.interfaces.containers.sequences;

import apsd.interfaces.containers.base.ReallocableContainer;
import apsd.classes.utilities.Natural;

public interface Vector<Data> extends ReallocableContainer, MutableSequence<Data> {

    //ShiftLeft
    default void ShiftLeft(Natural positions){
        ShiftLeft(positions, Natural.ONE);
    }

    default void ShiftLeft(Natural positions, Natural shift){
        long index = ExcIfOutOfBound(positions);
        long size = Size().ToLong();
        long len = shift.ToLong();

        len = (len <= size - index) ? len : size - index;
        if (len > 0) {
            long iniwrt = index;
            long wrt = iniwrt;
            for (long rdr = wrt + len; rdr < size; rdr++, wrt++) {
                Natural natrdr = Natural.Of(rdr);
                SetAt(GetAt(natrdr), Natural.Of(wrt));
                SetAt(null, natrdr);
            }
            for (; wrt - iniwrt < len; wrt++) {
                SetAt(null, Natural.Of(wrt));
            }
        }
    }


    //ShiftFirstLeft
    default void ShiftFirstLeft(Natural positions){
        if (!isEmpty()) {
            ShiftLeft(Natural.ZERO, positions);
        }
    }

    //ShiftLastLeft
    default void ShiftLastLeft(Natural positions){
        ShiftLeft(isEmpty() ? Natural.ZERO : Size().Decrement(), positions);
    }

    //ShiftRight
    default void ShiftRight(Natural positions){
        ShiftRight(positions, Natural.ONE);
    }

    default void ShiftRight(Natural positions, Natural shift){
        long index = ExcIfOutOfBound(positions);
        long size = Size().ToLong();
        long len = shift.ToLong();
        len = (len <= size - index) ? len : size - index;
        if (len > 0) {
            long iniwrt = size - 1;
            long wrt = iniwrt;
            for (long rdr = wrt - len; rdr >= index; rdr--, wrt--) {
                Natural natrdr = Natural.Of(rdr);
                SetAt(GetAt(natrdr), Natural.Of(wrt));
                SetAt(null, natrdr);
            }
            for (; iniwrt - wrt < len; wrt--) {
                SetAt(null, Natural.Of(wrt));
            }
        }
    }

    //ShiftFirstRight
    default void ShiftFirstRight(Natural positions){
        if (!isEmpty()) {
            ShiftRight(Natural.ZERO, positions);
        }
    }

    //ShiftLastRight
    default void ShiftLastRight(Natural positions){
        ShiftRight(isEmpty() ? Natural.ZERO : Size().Decrement(), positions);
    }

    Vector<Data> SubVector(Natural start, Natural end);

    /* ************************************************************************ */
    /* Override specific member functions from Container                        */
    /* ************************************************************************ */

    @Override
    Natural Size();
}
