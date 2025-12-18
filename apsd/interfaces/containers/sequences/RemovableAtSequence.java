package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto alla rimozione di un dato tramite posizione. */
public interface RemovableAtSequence<Data> extends Sequence<Data>{ // Must extend Sequence

  // RemoveAt
  public void RemoveAt(Natural position);

  // AtNRemove
  public void AtNRemove(Natural position, Natural n);

  // RemoveFirst
  public void RemoveFirst();

  // FirstNRemove
  public void FirstNRemove(int n);

  // RemoveLast
  public void RemoveLast();

  // LastNRemove
  public void LastNRemove(int n);

}
