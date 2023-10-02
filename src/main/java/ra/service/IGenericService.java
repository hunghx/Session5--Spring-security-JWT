package ra.service;

import java.util.List;

public interface IGenericService <T,E>{
    List<T> findAll();
    T findById(E id);
    T save(T t);
    void delete(E id);

}
