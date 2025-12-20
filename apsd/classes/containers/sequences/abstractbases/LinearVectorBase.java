package apsd.classes.containers.sequences.abstractbases;

 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Abstract (static) linear vector base implementation. */
abstract public class LinearVectorBase<Data> extends VectorBase<Data>{ // Must extend VectorBase

  protected LinearVectorBase() { super(Natural.ZERO);}

  protected LinearVectorBase(Natural initialSize) { super(initialSize);}

  protected LinearVectorBase(TraversableContainer<Data> con) { super(con); }

    protected LinearVectorBase(Data[] arr) { super(arr); }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  public void Realloc(Natural newsize) {
      if (newsize == null) {throw new NullPointerException("Natural cannot be null!");}
      Data[] oldarr = arr;
      long minsize = Math.min(Size().ToLong(), newsize.ToLong());
      ArrayAlloc(newsize);
      System.arraycopy(oldarr, 0, arr, 0, (int)minsize);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Data GetAt(Natural pos) {return arr[(int)ExcIfOutOfBound(pos)];}

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

    @Override
    public void SetAt(Data dat, Natural pos) {arr[(int)ExcIfOutOfBound(pos)] = dat;}

}
