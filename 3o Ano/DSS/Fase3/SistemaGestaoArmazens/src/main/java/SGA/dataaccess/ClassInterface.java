package SGA.dataaccess;

import java.util.List;

public interface ClassInterface<K> {
    K key();

    ClassInterface<K> fromRow(List<String> row);

    List<String> toRow();

    K key(String k);
}
