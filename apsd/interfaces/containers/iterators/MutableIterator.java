package apsd.interfaces.containers.iterators;

/** Interface: Iteratore mutabile. */
public interface MutableIterator<Data> extends Iterator<Data>{

  // SetCurrent
  public void SetCurrent(Data value);

}
