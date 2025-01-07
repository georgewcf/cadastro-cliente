package com.api.cadastrocliente.service;

import com.api.cadastrocliente.model.Cliente;
import com.api.cadastrocliente.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {


    final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean existsById(@NotBlank Long id) {
        return clienteRepository.existsById(id);
    }

    public Optional<Cliente> buscarClientePorID(Long id){
        return clienteRepository.findById(id);
    }

    public List<Cliente> buscarTodosClientes(){
        return clienteRepository.findAll();
    }

    public Long contarClientes() {
        return clienteRepository.count();
    }

    @Transactional
    public Cliente salvarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void deletarClientePorID(Long id){
        clienteRepository.deleteById(id);
    }

    @Transactional
    public void deletarTodosClientes(){
        if (contarClientes() > 1){

        clienteRepository.deleteAll();
        }
    }
 }

