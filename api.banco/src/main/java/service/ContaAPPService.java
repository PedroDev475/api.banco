package service;



import dto.ContaDTO;
import entity.Conta;
import exception.EntidadeNaoEncontrada;
import exception.RegraDeNegocioException;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;
import repository.ContaRepository;

import java.time.temporal.ChronoUnit;
import java.util.List;
@Service
public class ContaAPPService {
    private final ContaRepository repository;

    public ContaAPPService(ContaRepository repository) {
        this.repository =  repository;
    }

    public ContaDTO salvar(ContaDTO dto) {
        ContaDTO conta = dto.toEntity();
        Conta.validar();
        return ContaDTO.fromEntity(repository.save(conta));
    }

    public List<ContaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ContaDTO::fromEntity)
                .toList();

    }

    public ContaDTO buscarPorId(Long id) {
        return ContaDTO.fromEntity(
                repository.findById(id)

                .orElseThrow(() -> new EntidadeNaoEncontrada("Conta com ID" +id+"Cadastro Encontrado."))
        ).toEntity();
    }

    public Conta atualizar(Long id, ContaDTO cadastroAtualizado) {
      Conta existente = repository.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontrada("Conta com ID" +id+ "Cadastro Encontrado."));

      cadastroAtualizado = dtoAtualizado.toEntity();
     atualizado.setId(existente.getId());

     atualizado.validar();
     return ContaDTO.fromEntity(repository.save(atualizado));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontrada("Conta com ID." +id+ "não encontrado.");
        }
        repository.deleteById(id);
    }



    private void validar(Conta conta) {
        if (conta.getCadastro()  == 2 )
            throw new RegraDeNegocioException("Valor maximo de celulares cadastrados");
        long dias = ChronoUnit.DAYS.between(conta.getDataInicio(), conta.getDataFim());
        if (dias ==  5)
            throw new RegraDeNegocioException("O cadastro do celular acaba em 5 dias  ");
    }
}

