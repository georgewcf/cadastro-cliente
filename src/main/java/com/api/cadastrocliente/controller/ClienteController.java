package com.api.cadastrocliente.controller;

import com.api.cadastrocliente.dto.ClienteDTO;
import com.api.cadastrocliente.model.Cliente;
import com.api.cadastrocliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {


    @Autowired
    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Criar e Salva cliente", description = "Cria e salva um novo cliente")
    public ResponseEntity<Object> criarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        if(clienteService.existsById(clienteDTO.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLITO: O 'ID' JÁ EXISTE.");
        }
        var cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.salvarCliente(cliente));
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de clientes")
    public List<Cliente> listarClientes() {
        return clienteService.buscarTodosClientes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um cliente pelo ID", description = "Retorna um cliente pelo ID")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public ResponseEntity<Object> buscarCliente(@PathVariable (value = "id") Long id) {

        Optional<Cliente> clienteOptional = clienteService.buscarClientePorID(id);
        return clienteOptional.<ResponseEntity<Object>>map(cliente ->
                ResponseEntity.status(HttpStatus.OK).body(cliente)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("CLIENTE NÃO ENCONTRADO."));
    }

    @GetMapping("/total")
    @Operation(summary = "Contar clientes", description = "Retorna o número total de clientes cadastrados no sistema")
    public long contarClientes() {
        return clienteService.contarClientes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCliente(@PathVariable(value = "id") Long id,
                                                   @RequestBody @Valid ClienteDTO clienteDTO) {
        Optional<Cliente> clienteOptional = clienteService.buscarClientePorID(id);
        if(clienteOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CLIENTE NÃO ENCONTRADO.");
        }

        var cliente = clienteOptional.get();
        cliente.setNome(clienteDTO.getClienteNome());
        cliente.setId(clienteDTO.getId());

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.salvarCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um cliente pelo ID", description = "Deleta um cliente pelo ID")
    public ResponseEntity<Object> excluirCliente(@PathVariable(value = "id") Long id) {
        Optional<Cliente> clienteOptional = clienteService.buscarClientePorID(id);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CLIENTE NÃO ENCONTRADO.");
        }
        clienteService.deletarClientePorID(id);
        return ResponseEntity.status(HttpStatus.OK).body("CLIENTE EXCLUIDO COM SUCESSO.");
    }

    @DeleteMapping
    @Operation(summary = "Deleta todos os clientes", description = "Deleta todos os clientes")
    public List<Cliente> excluirTodosOsClientes() {
        clienteService.buscarTodosClientes();
        clienteService.deletarTodosClientes();
        return clienteService.buscarTodosClientes();
    }
}
