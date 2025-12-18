package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data> extends IterableContainer<Data> {

  // GetAt
    default Data GetAt(Natural pos) {
        long index = ExcIfOutOfBound(pos);
        ForwardIterator<Data> itr = FIterator();
        itr.Next(index);
        return itr.GetCurrent();
    }


  // GetFirst
    default Data GetNext(Natural pos) {
        return GetAt(Natural.ZERO);
    }
  // GetLast
    default Data GetLast(Natural pos) {
        return GetAt(isEmpty() ? Natural.ZERO : Size().Decrement());
    }

  // Search
    default Natural Search(Data dat) {
        final Box<Long> index = new Box<>(-1L);
        if (TraverseForward(elem -> {
            index.Set(index.Get()+1);
            return (elem == null && dat == null) || (elem != null && elem.equals(dat));
        })) {
            return Natural.Of(index.Get());
        }
        return null;
    }


  // IsInBound

   default long ExcIfOutOfBound(Natural num) {
     if (num == null) throw new NullPointerException("Natural number cannot be null!");
     long idx = num.ToLong();
     if (idx >= Size().ToLong()) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size() + "!");
     return idx;
   }

  // SubSequence
  default Sequence<Data> SubSequence(Natural start, Natural end) {
      if (start == null || end == null)
          throw new NullPointerException("Natural numbers cannot be null!");

      long s = start.ToLong();
      long e = end.ToLong();

      if (s < 0 || e > Size().ToLong() || s > e)
          throw new IndexOutOfBoundsException(
                  "Invalid subsequence range: [" + s + "," + e + "), size=" + Size()
          );

      Sequence<Data> original = this;

      return new Sequence<>() {

          @Override
          public Data GetAt(Natural pos) {
              long idx = pos.ToLong();
              if (idx < 0 || idx >= (e - s))
                  throw new IndexOutOfBoundsException("Index out of bounds in subsequence");
              return original.GetAt(Natural.Of(s + idx));
          }

          @Override
          public Natural Size() {
              return Natural.Of(e - s);
          }

          @Override
          public boolean isEmpty() {
              return Size().equals(Natural.ZERO);
          }

          @Override
          public boolean TraverseForward(apsd.interfaces.traits.Predicate<Data> fun) {
              if (fun == null) return false;
              for (long i = 0; i < Size().ToLong(); i++) {
                  if (fun.Apply(GetAt(Natural.Of(i)))) return true;
              }
              return false;
          }

          @Override
          public boolean TraverseBackward(apsd.interfaces.traits.Predicate<Data> fun) {
              if (fun == null) return false;
              for (long i = Size().ToLong() - 1; i >= 0; i--) {
                  if (fun.Apply(GetAt(Natural.Of(i)))) return true;
              }
              return false;
          }

          @Override
          public boolean Exists(Data dat) {
              for (long i = 0; i < Size().ToLong(); i++) {
                  Data current = GetAt(Natural.Of(i));
                  if (current == null ? dat == null : current.equals(dat)) return true;
              }
              return false;
          }

          @Override
          public void Clear() {
              throw new UnsupportedOperationException(
                      "Cannot Clear() a SubSequence view"
              );
          }
      };
  }


}
