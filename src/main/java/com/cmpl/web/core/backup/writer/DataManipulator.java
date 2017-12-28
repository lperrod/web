package com.cmpl.web.core.backup.writer;

import java.util.List;

import com.cmpl.web.core.model.BaseEntity;
import com.cmpl.web.core.repository.BaseRepository;

public class DataManipulator<T extends BaseEntity> {

  private final BaseRepository<T> repository;

  public DataManipulator(BaseRepository<T> repository) {
    this.repository = repository;
  }

  public List<T> extractData() {
    return repository.findAll();
  }

  public void insertData(List<T> entities) {
    repository.saveAll(entities);
  }
}