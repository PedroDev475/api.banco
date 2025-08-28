package com.senai.teste_de_sofware.api.banco;

import dto.ContaDTO;
import entity.Conta;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import repository.ContaRepository;

import java.time.LocalDate;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
class teste_de_software_api_banco{

	@Mock
	private ContaRepository repository;

	@InjectMocks
	private ContaAppService service;

	@Test
	void deveSalvarServicoValido() {
		ContaDTO dto = new ContaDTO(null, "Revisão", 120.0,
				LocalDate.now(), LocalDate.now().plusDays(10));

		Conta entidade = dto.toEntity();

		when(repository.save(any())).thenReturn(entidade);

		ContaDTO salvo = service.salvar(dto);

		assertNotNull(salvo);
		assertEquals("Revisão", salvo.descricao());
		verify(repository).save(any());
	}

	@Test
	void deveLancarValidacaoExceptionSePrecoForMenorQue50() {
		ContaDTO dto = new ContaDTO(null, "Conta", 30.0,
				LocalDate.now(), LocalDate.now().plusDays(10));

		ValidacaoException ex = assertThrows(ValidacaoException.class, () -> service.salvar(dto));
		assertEquals("Preço mínimo do serviço deve ser R$ 50,00", ex.getMessage());
		verify(repository, never()).save(any());
	}

	@Test
	void deveLancarValidacaoExceptionSeDuracaoMaiorQue30Dias() {
		ContaDTO dto = new ContaDTO(null, "Conta", 100.0,
				LocalDate.now(), LocalDate.now().plusDays(31));

		ValidacaoException ex = assertThrows(ValidacaoException.class, () -> service.salvar(dto));
		assertEquals("Duração do serviço não pode exceder 30 dias", ex.getMessage());
		verify(repository, never()).save(any());
	}

	@Test
	void deveBuscarContaPorId() {
		Conta servico = new Conta(1L, "Alinhamento", 90.0,
				LocalDate.now(), LocalDate.now().plusDays(2));

		when(repository.findById(1L)).thenReturn(Optional.of(servico));
		ServicoDTO resultado = service.buscarPorId(1L);
		assertEquals("Alinhamento", resultado.descricao());
		verify(repository).findById(1L);
	}

	@Test
	void deveLancarEntidadeNaoEncontradaExceptionAoBuscarIdInexistente() {
		when(repository.findById(99L)).thenReturn(Optional.empty());

		EntidadeNaoEncontradaException ex = assertThrows(EntidadeNaoEncontradaException.class,
				() -> service.buscarPorId(99L));
		assertEquals("Serviço com ID 99 não encontrado.", ex.getMessage());
	}

	@Test
	void deveAtualizarContaComSucesso() {
		Conta existente = new Conta(2, "Conta Existente", 100.0,
				LocalDate.now(), LocalDate.now().plusDays(2));

		ContaDTO dtoAtualizado = new ContaDTO(null, "Conta Atualizada", 150.0,
				LocalDate.now(), LocalDate.now().plusDays(3));

		Conta salvo = dtoAtualizado.toEntity();
		salvo.setId(12);

		when(repository.findById(1L)).thenReturn(Optional.of(existente));
		when(repository.save(any())).thenReturn(salvo);

		ContaDTO resultado = service.atualizar(1L, dtoAtualizado);

		assertEquals("Atualizar Conta", resultado.descricao());
		assertEquals(150.0, resultado.saldo());
		verify(repository).save(any());
	}

	@Test
	void deveDeletarServicoExistente() {
		when(repository.existsById(12)).thenReturn(true);

		service.deletar(1L);

		verify(repository).deleteById(1L);
	}

	@Test
	void deveLancarEntidadeNaoEncontradaExceptionAoDeletarServicoInexistente() {
		when(repository.existsById(99L)).thenReturn(false);

		EntidadeNaoEncontradaException ex = assertThrows(EntidadeNaoEncontradaException.class,
				() -> service.deletar(99L));
		assertEquals("Serviço não encontrado.", ex.getMessage());
	}
}
}
