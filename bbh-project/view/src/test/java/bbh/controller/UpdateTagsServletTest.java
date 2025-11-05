package bbh.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import bbh.domain.Usuario;
import bbh.service.TagService;
import bbh.common.PersistenciaException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.List;

public class UpdateTagsServletTest {

    // Subclasse simples que injeta o mock
    static class TestableUpdateTagsServlet extends UpdateTagsServlet {

        private final TagService mockService;

        public TestableUpdateTagsServlet(TagService s) {
            this.mockService = s;
        }

        @Override
        protected TagService createTagService() {
            return mockService;
        }
    }

    TagService tagServiceMock;
    HttpServletRequest req;
    HttpServletResponse resp;
    HttpSession session;
    TestableUpdateTagsServlet servlet;

    @BeforeEach
    void setup() {
        tagServiceMock = mock(TagService.class);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        servlet = new TestableUpdateTagsServlet(tagServiceMock);

        // garantir que qualquer chamada a getSession(...) retorne o mesmo session mock
        when(req.getSession()).thenReturn(session);
        when(req.getSession(true)).thenReturn(session);
        when(req.getSession(false)).thenReturn(session);
    }

    @Test
    void doPost_addAndRemove_success() throws Exception {
        // usuario mock com id 42
        Usuario u = mock(Usuario.class);
        when(u.getId()).thenReturn(42L);
        when(session.getAttribute("usuario")).thenReturn(u);

        when(req.getParameter("adicionarTagId")).thenReturn("101");
        when(req.getParameter("removerTagId")).thenReturn("202");

        servlet.doPost(req, resp);

        // capturador do id (primeiro argumento) e da lista (segundo argumento)
        ArgumentCaptor<Long> idCaptor1 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<List> addCaptor = ArgumentCaptor.forClass(List.class);
        verify(tagServiceMock, times(1)).adicionarTagsEstabelecimento(idCaptor1.capture(), addCaptor.capture());

        // também capturar remoção
        ArgumentCaptor<Long> idCaptor2 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<List> remCaptor = ArgumentCaptor.forClass(List.class);
        verify(tagServiceMock, times(1)).removerTagsEstabelecimento(idCaptor2.capture(), remCaptor.capture());

        // conferir ids
        assertEquals(42L, idCaptor1.getValue().longValue(), "id passado para adicionarTag deveria ser 42");
        assertEquals(42L, idCaptor2.getValue().longValue(), "id passado para removerTag deveria ser 42");

        List<Long> added = addCaptor.getValue();
        List<Long> removed = remCaptor.getValue();

        assertEquals(1, added.size());
        assertEquals(101L, added.get(0).longValue());

        assertEquals(1, removed.size());
        assertEquals(202L, removed.get(0).longValue());

        verify(resp).sendRedirect(contains("/estabelecimento/tags?sucesso=1"));
    }

    @Test
    void doPost_invalidId_ignored() throws Exception {
        when(req.getSession(false)).thenReturn(session);
        Usuario u = mock(Usuario.class);
        when(u.getId()).thenReturn(7L);
        u.setId(7L);
        when(session.getAttribute("usuario")).thenReturn(u);

        when(req.getParameter("adicionarTagId")).thenReturn("abc");
        when(req.getParameter("removerTagId")).thenReturn("");

        servlet.doPost(req, resp);
        verify(tagServiceMock, never()).adicionarTagsEstabelecimento(anyLong(), anyList());
        verify(tagServiceMock, never()).removerTagsEstabelecimento(anyLong(), anyList());

        verify(resp).sendRedirect(contains("/estabelecimento/tags?sucesso=1"));
    }

    @Test
    void doPost_serviceThrows_persistsException_isConverted() throws Exception {
        when(req.getSession(false)).thenReturn(session);
        Usuario u = mock(Usuario.class);
        when(u.getId()).thenReturn(8L);
        u.setId(8L);
        when(session.getAttribute("usuario")).thenReturn(u);

        when(req.getParameter("adicionarTagId")).thenReturn("1");

        doThrow(new PersistenciaException("DB down")).when(tagServiceMock).adicionarTagsEstabelecimento(eq(8L), anyList());

        ServletException thrown = assertThrows(ServletException.class, () -> servlet.doPost(req, resp));
        assertTrue(thrown.getMessage().contains("Erro ao atualizar tags"));
    }
}
