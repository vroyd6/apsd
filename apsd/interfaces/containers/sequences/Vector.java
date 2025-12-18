package apsd.interfaces.containers.sequences;

import apsd.interfaces.containers.base.ReallocableContainer;
import apsd.classes.utilities.Natural;

public interface Vector<Data> extends ReallocableContainer, MutableSequence<Data> {

    //ShiftLeft
    default void ShiftLeft(Natural positions){
        ShiftLeft(positions, Natural.ONE);
    }

    default void ShiftLeft(Natural positions, Natural shift){
        for(Natural i = Natural.ZERO; i.Lessthan; i = i.Increment()){
            ShiftLeft(positions);
        }
    }

    //ShiftFirstLeft
    public void ShiftFirstLeft(Natural positions);

    //ShiftLastLeft
    public void ShiftLastLeft(Natural positions);

    //ShiftRight
    public void ShiftRight(Natural positions);

    //ShiftFirstRight
    public void ShiftFirstRight(Natural positions);

    //ShiftLastRight
    public void ShiftLastRight(Natural positions);

    /* ************************************************************************ */
    /* Override specific member functions from Container                        */
    /* ************************************************************************ */

    @Override
    Natural Size();
}
