package apsd.classes.containers.sequences.abstractbases;

 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Abstract (static) linear vector base implementation. */
abstract public class LinearVectorBase<Data> extends VectorBase<Data>{ // Must extend VectorBase

  // LinearVectorBase

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  public void Realloc(Natural newsize) {
      if (newsize == null) {throw new NullPointerException("Natural cannot be null!");}
      Data[] oldarr = arr;
      long minsize = Math.min(Size().Tolong(), newsize.Tolong());
      ArrayAlloc(newsize)
      System.arraycopy(oldarr, 0, arr, 0, minsize);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Data GetAt(Natural pos) {return arr[ExcIfOutOfBound(pos)];}

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  // ...

}
