package com.cmpl.web.configuration.modules.backup;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.cmpl.web.backup.BackupImporterJob;
import com.cmpl.web.backup.reader.BackupImporter;
import com.cmpl.web.backup.reader.CSVReader;
import com.cmpl.web.backup.reader.CSVReaderImpl;
import com.cmpl.web.backup.reader.CarouselCSVParser;
import com.cmpl.web.backup.reader.CarouselItemCSVParser;
import com.cmpl.web.backup.reader.CommonParser;
import com.cmpl.web.backup.reader.MediaCSVParser;
import com.cmpl.web.backup.reader.MenuCSVParser;
import com.cmpl.web.backup.reader.MetaElementCSVParser;
import com.cmpl.web.backup.reader.NewsContentCSVParser;
import com.cmpl.web.backup.reader.NewsEntryCSVParser;
import com.cmpl.web.backup.reader.NewsImageCSVParser;
import com.cmpl.web.backup.reader.OpenGraphMetaElementCSVParser;
import com.cmpl.web.backup.reader.PageCSVParser;
import com.cmpl.web.backup.reader.StyleCSVParser;
import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.carousel.Carousel;
import com.cmpl.web.core.carousel.CarouselItem;
import com.cmpl.web.core.media.Media;
import com.cmpl.web.core.menu.Menu;
import com.cmpl.web.core.meta.MetaElement;
import com.cmpl.web.core.meta.OpenGraphMetaElement;
import com.cmpl.web.core.news.NewsContent;
import com.cmpl.web.core.news.NewsEntry;
import com.cmpl.web.core.news.NewsImage;
import com.cmpl.web.core.page.Page;
import com.cmpl.web.core.style.Style;

@Configuration
@PropertySource("classpath:/backup/backup.properties")
public class BackupImportConfiguration {

  @Value("${backupFilePath}")
  String backupFilePath;

  @Value("${actualitesFilePath}")
  String actualitesFilePath;

  @Value("${pagesFilePath}")
  String pagesFilePath;

  @Value("${mediaFilePath}")
  String mediaFilePath;

  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());



  @Bean
  public BackupImporter backupImporter(CSVReader csvReader) {
    return new BackupImporter(csvReader, backupFilePath, mediaFilePath, pagesFilePath, actualitesFilePath);
  }

  @Bean
  public MenuCSVParser menuCSVParser(DataManipulator<Menu> menuDataManipulator) {
    return new MenuCSVParser(dateFormatter, menuDataManipulator, backupFilePath);
  }
 
  @Bean
  public StyleCSVParser styleCSVParser(DataManipulator<Style> styleDataManipulator) {
    return new StyleCSVParser(dateFormatter, styleDataManipulator, backupFilePath);
  }

  @Bean
  public PageCSVParser pageCSVParser(DataManipulator<Page> pageDataManipulator) {
    return new PageCSVParser(dateFormatter, pageDataManipulator, backupFilePath);
  }

  @Bean
  public MediaCSVParser mediaCSVParser(DataManipulator<Media> mediaDataManipulator) {
    return new MediaCSVParser(dateFormatter, mediaDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselCSVParser carouselCSVParser(DataManipulator<Carousel> carouselDataManipulator) {
    return new CarouselCSVParser(dateFormatter, carouselDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselItemCSVParser carouselItemCSVParser(DataManipulator<CarouselItem> carouselItemDataManipulator) {
    return new CarouselItemCSVParser(dateFormatter, carouselItemDataManipulator, backupFilePath);
  }

  @Bean
  public MetaElementCSVParser metaElementCSVParser(DataManipulator<MetaElement> metaElementDataManipulator) {
    return new MetaElementCSVParser(dateFormatter, metaElementDataManipulator, backupFilePath);
  }

  @Bean
  public OpenGraphMetaElementCSVParser openGraphMetaElementCSVParser(
      DataManipulator<OpenGraphMetaElement> openGraphMetaElementDataManipulator) {
    return new OpenGraphMetaElementCSVParser(dateFormatter, openGraphMetaElementDataManipulator, backupFilePath);
  }

  @Bean
  public NewsEntryCSVParser newsEntryCSVParser(DataManipulator<NewsEntry> newsEntryDataManipulator) {
    return new NewsEntryCSVParser(dateFormatter, newsEntryDataManipulator, backupFilePath);
  }

  @Bean
  public NewsImageCSVParser newsImageCSVParser(DataManipulator<NewsImage> newsImageDataManipulator) {
    return new NewsImageCSVParser(dateFormatter, newsImageDataManipulator, backupFilePath);
  }

  @Bean
  public NewsContentCSVParser newsContentCSVParser(DataManipulator<NewsContent> newsContentDataManipulator) {
    return new NewsContentCSVParser(dateFormatter, newsContentDataManipulator, backupFilePath);
  }

  @Bean
  public CSVReader csvReader(MenuCSVParser menuCSVParser, StyleCSVParser styleCSVParser, PageCSVParser pageCSVParser,
      MediaCSVParser mediaCSVParser, CarouselCSVParser carouselCSVParser, CarouselItemCSVParser carouselItemCSVParser,
      MetaElementCSVParser metaElementCSVParser, OpenGraphMetaElementCSVParser openGraphMetaElementCSVParser,
      NewsEntryCSVParser newsEntryCSVParser, NewsImageCSVParser newsImageCSVParser,
      NewsContentCSVParser newsContentCSVParser) {
    List<CommonParser<?>> parsers = new ArrayList<>();
    parsers.add(menuCSVParser);
    parsers.add(styleCSVParser);
    parsers.add(pageCSVParser);
    parsers.add(mediaCSVParser);
    parsers.add(carouselCSVParser);
    parsers.add(carouselItemCSVParser);
    parsers.add(metaElementCSVParser);
    parsers.add(openGraphMetaElementCSVParser);
    parsers.add(newsEntryCSVParser);
    parsers.add(newsImageCSVParser);
    parsers.add(newsContentCSVParser);
    return new CSVReaderImpl(parsers);
  }

  @Bean
  @Qualifier("backupImportJob")
  public JobDetailFactoryBean backupImportJob() {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(BackupImporterJob.class);
    factoryBean.setGroup("backupImportJob");
    factoryBean.setName("backupImportJob");
    factoryBean.setDescription("Backup import of all the data");
    factoryBean.setDurability(true);
    return factoryBean;
  }

  @Bean
  @Qualifier("backupImportTrigger")
  public SimpleTriggerFactoryBean backupImportTrigger(JobDetail backupImportJob) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setName("Application backup import");
    factoryBean.setDescription("Startup backup import of the data of the application");
    factoryBean.setJobDetail(backupImportJob);
    factoryBean.setStartDelay(10 * 1000l);
    factoryBean.setRepeatCount(0);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

}