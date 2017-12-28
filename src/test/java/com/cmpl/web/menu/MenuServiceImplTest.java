package com.cmpl.web.menu;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceImplTest {

  @Mock
  private MenuRepository menuRepository;

  @Spy
  @InjectMocks
  private MenuServiceImpl menuService;

  @Test
  public void testToDTO() throws Exception {

    Menu entity = new MenuBuilder().build();

    BDDMockito.doNothing().when(menuService).fillObject(BDDMockito.any(Menu.class), BDDMockito.any(MenuDTO.class));
    menuService.toDTO(entity);

    BDDMockito.verify(menuService, BDDMockito.times(1)).fillObject(BDDMockito.any(Menu.class),
        BDDMockito.any(MenuDTO.class));
  }

  @Test
  public void testToEntity() throws Exception {
    MenuDTO dto = new MenuDTOBuilder().build();

    BDDMockito.doNothing().when(menuService).fillObject(BDDMockito.any(MenuDTO.class), BDDMockito.any(Menu.class));
    menuService.toEntity(dto);

    BDDMockito.verify(menuService, BDDMockito.times(1)).fillObject(BDDMockito.any(MenuDTO.class),
        BDDMockito.any(Menu.class));
  }

  @Test
  public void testComputeMenuDTOToReturn() throws Exception {
    MenuDTO dto = new MenuDTOBuilder().id(123456789l).build();

    BDDMockito.doReturn(dto).when(menuService).toDTO(BDDMockito.any(Menu.class));
    BDDMockito.doReturn(Lists.newArrayList(dto)).when(menuService).computeMenus(BDDMockito.anyListOf(Menu.class));

    Menu entity = new MenuBuilder().build();
    BDDMockito.given(menuRepository.findByParentId(BDDMockito.anyString())).willReturn(Lists.newArrayList(entity));

    Assert.assertEquals(dto.getId(), menuService.computeMenuDTOToReturn(entity).getId());
  }

  @Test
  public void testComputeSubMenus() throws Exception {

    MenuDTO menuToAdd = new MenuDTOBuilder().build();

    BDDMockito.doReturn(menuToAdd).when(menuService).computeMenuDTOToReturn(BDDMockito.any(Menu.class));

    Assert.assertEquals(menuToAdd, menuService.computeMenus(Lists.newArrayList(new MenuBuilder().build())).get(0));
  }

  @Test
  public void testToListDTO() throws Exception {
    MenuDTO menuToAdd = new MenuDTOBuilder().build();
    BDDMockito.doReturn(Lists.newArrayList(menuToAdd)).when(menuService).computeMenus(BDDMockito.anyListOf(Menu.class));

    Assert.assertEquals(menuToAdd, menuService.toListDTO(Lists.newArrayList(new MenuBuilder().build())).get(0));
    BDDMockito.verify(menuService, BDDMockito.times(1)).computeMenus(BDDMockito.anyListOf(Menu.class));
  }

  @Test
  public void testGetMenus() throws Exception {
    MenuDTO menuDTOToFind = new MenuDTOBuilder().build();

    Menu menuToFind = new MenuBuilder().build();
    BDDMockito.given(menuRepository.findAll(BDDMockito.any(Sort.class))).willReturn(Lists.newArrayList(menuToFind));
    BDDMockito.doReturn(Lists.newArrayList(menuDTOToFind)).when(menuService)
        .toListDTO(BDDMockito.anyListOf(Menu.class));

    Assert.assertEquals(menuDTOToFind, menuService.getMenus().get(0));
  }
}
