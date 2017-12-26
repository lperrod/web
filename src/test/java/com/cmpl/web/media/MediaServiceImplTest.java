package com.cmpl.web.media;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.FileService;

@RunWith(MockitoJUnitRunner.class)
public class MediaServiceImplTest {

  @Spy
  @InjectMocks
  private MediaServiceImpl mediaService;

  @Mock
  private ContextHolder contextHolder;
  @Mock
  private FileService fileService;
  @Mock
  private MediaRepository mediaRepository;

  @Test
  public void testFindByName() throws Exception {
    MediaDTO result = new MediaDTO();

    BDDMockito.doReturn(result).when(mediaService).toDTO(BDDMockito.any(Media.class));
    BDDMockito.given(mediaRepository.findByName(BDDMockito.anyString())).willReturn(new Media());

    Assert.assertEquals(result, mediaService.findByName("someName"));
  }

  @Test
  public void testToEntity() throws Exception {
    MediaDTO dto = new MediaDTO();

    BDDMockito.doNothing().when(mediaService).fillObject(BDDMockito.any(MediaDTO.class), BDDMockito.any(Media.class));
    mediaService.toEntity(dto);

    BDDMockito.verify(mediaService, BDDMockito.times(1)).fillObject(BDDMockito.any(MediaDTO.class),
        BDDMockito.any(Media.class));
  }

  @Test
  public void testToDTO() throws Exception {
    Media entity = new Media();

    BDDMockito.doNothing().when(mediaService).fillObject(BDDMockito.any(Media.class), BDDMockito.any(MediaDTO.class));
    mediaService.toDTO(entity);

    BDDMockito.verify(mediaService, BDDMockito.times(1)).fillObject(BDDMockito.any(Media.class),
        BDDMockito.any(MediaDTO.class));
  }

  @Test
  public void testDownload() throws Exception {

    String mediaName = "someMediaName";
    String path = "src/test/resources/img/base64Image.txt";
    FileInputStream is = new FileInputStream(new File(path));
    BDDMockito.given(fileService.read(BDDMockito.anyString())).willReturn(is);

    Assert.assertEquals(is, mediaService.download(mediaName));
  }

  @Test
  public void testUpload() throws Exception {

    MultipartFile multiPartFile = BDDMockito.mock(MultipartFile.class);
    BDDMockito.given(multiPartFile.getOriginalFilename()).willReturn("test.pdf");
    BDDMockito.given(multiPartFile.getContentType()).willReturn("application/pdf");
    BDDMockito.given(multiPartFile.getSize()).willReturn(1l);
    BDDMockito.given(multiPartFile.getBytes()).willReturn(new byte[]{});
    BDDMockito.given(contextHolder.getMediaDisplayPath()).willReturn("http://poc.lperrod.cardiweb.com/public/img");

    BDDMockito.doNothing().when(fileService).saveMediaOnSystem(BDDMockito.anyString(), BDDMockito.any(byte[].class));

    MediaDTO mediaToCreate = new MediaDTO();
    BDDMockito.doReturn(mediaToCreate).when(mediaService).createEntity(BDDMockito.any(MediaDTO.class));

    Assert.assertEquals(mediaToCreate, mediaService.upload(multiPartFile));
  }

}
