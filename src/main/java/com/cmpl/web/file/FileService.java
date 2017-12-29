package com.cmpl.web.file;

import java.io.InputStream;

import com.cmpl.web.core.model.BaseException;

/**
 * Interface d'enregistrement de fichier
 * 
 * @author Louis
 *
 */
public interface FileService {

  /**
   * Enregistre un fichier sur le systeme dans le classpath Remplace un fichier existant avec le contenu
   * 
   * @param fileName
   * @param content
   * @return
   * @throws BaseException
   */
  void saveFileOnSystem(String fileName, String content);

  /**
   * Enregistre un media sur le systeme dans le classpath Remplace un fichier existant avec le contenu
   * 
   * @param fileName
   * @param content
   */
  void saveMediaOnSystem(String fileName, byte[] content);

  /**
   * Lire le contenu d'un fichier du classpath
   * 
   * @param fileName
   * @return
   */
  String readFileContentFromSystem(String fileName);

  /**
   * Lire le contenu d'un fichier dans un InputStream
   * 
   * @param fileName
   * @return
   */
  InputStream read(String fileName);

  /**
   * Lire le contenu des templates par defaut
   * 
   * @param templateName
   * @return
   */
  String readDefaultTemplateContent(String templateName);

}
