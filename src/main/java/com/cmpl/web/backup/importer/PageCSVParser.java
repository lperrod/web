package com.cmpl.web.backup.importer;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.core.backup.reader.CommonParser;
import com.cmpl.web.core.backup.writer.DataManipulator;
import com.cmpl.web.page.Page;

public class PageCSVParser extends CommonParser<Page> {

  public PageCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Page> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Page parseEntity(CSVRecord record) {
    Page pageParsed = new Page();

    List<Field> fieldsToParse = getFields(pageParsed.getClass());

    parseObject(record, pageParsed, fieldsToParse);

    return pageParsed;
  }

  @Override
  public String getParserName() {
    return "pages";
  }

}