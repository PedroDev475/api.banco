package service;


import com.senai.oficina_teste_swagger.domain.entity.Servico;
import com.senai.oficina_teste_swagger.domain.exception.RegraDeNegocioException;
import com.senai.oficina_teste_swagger.domain.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
@Service
public class ContaAPPService {
    private final ContaAPPService repository;

    public ContaAPPService(ContaRepository repository) {
        this.repository = repository;
    }

    public Conta salvar(Conta) {
        validar(conta);
        return repository.save(conta);
    }

    public List<Conta> listar() {
        return repository.findAll();
    }

    public Conta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Conta não encontrado."));
    }

    public Conta atualizar(Long id, Conta contaAtualizada) {
      Conta existente = buscarPorId(id);
     contaAtualizada.setId(existente.getId());
        validar(contaAtualizada);
        return repository.save(contaAtualizada);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraDeNegocioException("Conta não encontrado.");
        }
        repository.deleteById(id);
    }

    private void validar(Conta conta) {
        if (conta.getPreco() < 10)
            throw new RegraDeNegocioException("Valor minimo na conta 10 reais");

        long dias = ChronoUnit.DAYS.between(conta.getDataInicio(), conta.getDataFim());
        if (dias > 100)
            throw new RegraDeNegocioException("A conta não poderá ficar inativa por 100 dias");
    }
}

