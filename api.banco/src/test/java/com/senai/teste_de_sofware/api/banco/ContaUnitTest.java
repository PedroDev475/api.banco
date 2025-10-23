package com.senai.teste_de_sofware.api.banco;

import dto.ContaDTO;
import entity.Conta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ContaRepository;
import service.ContaAPPService;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContaUnitTest {

    @Mock
    private ContaRepository repository;

    @InjectMocks
    private ContaAPPService service;

    @Test
    void deveDeletarContaValida(){
        ContaDTO  dto =  new  ContaDTO (null ,"Cadastro" ,1,
                                LocalDate.now(),LocalDate.now().plusDays(5));
        Conta entidade =  dto.toEntity();
        when(repository.save(any())).thenReturn(entidade);

        ContaDTO salvo = Conta.salvar(dto);

        assertNotNull(salvo);
        assertEquals("Cadastro",salvo.descricao());
        verify(repository).save(any());
    }
    @Test
    void deveLancarVerificacaoExceptionSeONumeroDeDiasDoCadastroForMenor5Dias(){

    }
}
