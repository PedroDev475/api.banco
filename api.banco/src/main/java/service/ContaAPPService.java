package service;



import dto.ContaDTO;
import entity.Conta;
import exception.EntidadeNaoEncontrada;
import exception.RegraDeNegocioException;
import org.springframework.stereotype.Service;
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
        Conta conta =  dto.toEntity();
        conta.validar();
        return ContaDTO.fromEntity(repository.save(conta));
    }

    public List<ContaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ContaDTO::fromEntity)
                .toList();

    }

    public ContaDTO buscarPorId(Long id) {
         Conta conta = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontrada("Conta com ID" +id+"Cadastro Encontrado."));
          return ContaDTO.fromEntity(conta );

    }

    public ContaDTO atualizar(Long id, ContaDTO cadastroAtualizado) {
      Conta contaExistente = repository.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontrada("Conta com ID" +id+ "Cadastro Encontrado."));

       Conta contaAtualizado = cadastroAtualizado.toEntity();

       contaAtualizado.setId(contaExistente.getId());

     contaAtualizado.validar();

     return ContaDTO.fromEntity(repository.save(contaAtualizado));
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

