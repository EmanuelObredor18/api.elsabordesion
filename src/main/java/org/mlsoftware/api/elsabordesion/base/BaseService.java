package org.mlsoftware.api.elsabordesion.base;

import org.mlsoftware.api.elsabordesion.security.data.entity.Identificable;

public interface BaseService<I, O, ID> {
  
  public String save(I inputDTO);
  
  public O update(Identificable<ID> entity);

  public void deleteById(ID id);

  public O getById(ID id);
}
