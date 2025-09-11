package service;



import entity.Conta;
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

    public Conta salvar(Conta conta) {
        validar(conta);
        return repository.save(conta);
    }

    public List<Conta> listar() {
        return repository.findAll();
    }

    public Conta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cadastro Encontrado."));
    }

    public Conta atualizar(Long id, Conta cadastroAtualizada) {
      Conta existente = buscarPorId(id);
     cadastroAtualizada.setId(existente.getId());
        validar(cadastroAtualizada);
        return repository.save(cadastroAtualizada);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraDeNegocioException("Deletar Cadastro.");
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

