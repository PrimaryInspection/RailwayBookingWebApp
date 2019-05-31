package controller.servlet;

import controller.command.common.Command;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTest {
    String page = null;
    ControllerHelper controllerHelper = ControllerHelper.getInstance();


    @Test
    public void processRequest() throws ServletException, IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse responce = mock(HttpServletResponse.class);
        Command command = controllerHelper.getCommand(request);
        page = command.execute(request, responce);

        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(page)).thenReturn(dispatcher);

    }
}