package cmpl.web.controller.front;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class RobotsControllerTest {

  @Spy
  @InjectMocks
  private RobotsController controller;

  @Test
  public void testPrintRobot_Ok() throws Exception {
    MockHttpServletResponse response = new MockHttpServletResponse();
    controller.printRobot(response);
  }

}
