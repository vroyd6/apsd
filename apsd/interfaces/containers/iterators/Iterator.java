package apsd.interfaces.containers.iterators;

/** Interface: Iteratore arbitrario. */
public interface Iterator<Data> {

  // IsValid
  public boolean IsValid();

  // Reset
  public void Reset();

  // GetCurrent
  public Data GetCurrent();

}
