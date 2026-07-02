package util;

import java.util.ArrayList;
import model.Putusan;

public interface Sortable {
    ArrayList<Putusan> sortByVonis(ArrayList<Putusan> var1);

    ArrayList<Putusan> sortByDenda(ArrayList<Putusan> var1);

    ArrayList<Putusan> sortByNama(ArrayList<Putusan> var1);
}
