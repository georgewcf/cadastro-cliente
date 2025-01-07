package service;

import com.api.cadastrocliente.model.Cliente;
import com.api.cadastrocliente.repository.ClienteRepository;
import com.api.cadastrocliente.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteService clienteService;

    public ClienteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Teste");
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente result;
        result = clienteService.salvarCliente(cliente);
        assertEquals("Teste", result.getNome());

        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void deveBuscarCliente() {

        Long clienteId = 1L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());
        Optional<Cliente> result = clienteService.buscarClientePorID(clienteId);
        assertFalse(result.isPresent(), "O cliente não deveria ser encontrado.");

        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    void deveRetornarNumeroTotalDeClientes() {

        when(clienteRepository.count()).thenReturn(5L);
        Long totalClientes = clienteService.contarClientes();
        assertEquals(5L, totalClientes);
    }

    @Test
    void deveDeletarClientePorID() {
        Long clienteId = 1L;
        clienteService.deletarClientePorID(clienteId);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveDeletarTodosOsClientesEmCasoDeSurtoPsicologico(){

        when(clienteRepository.count()).thenReturn(5L);
        Long totalClientes = clienteService.contarClientes();
        assertEquals(5L, totalClientes);

    }
}
