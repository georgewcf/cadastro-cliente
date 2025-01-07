package com.api.cadastrocliente.repository;


import com.api.cadastrocliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByid(UUID id);
    void delete(Long id);
    boolean deleteAll = true;
}
