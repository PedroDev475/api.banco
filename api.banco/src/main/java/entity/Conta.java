package entity;


import exception.RegraDeNegocioException;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private int cadastro;
    private LocalDate dataInicio;
    private LocalDate dataFim;


    public void validar() {
        if (this.cadastro >= 2) {
            throw new RegraDeNegocioException("Valor maximo de celulares vendidos");

        }
        if (this.dataInicio != null && this.dataFim != null) {
            long dias = ChronoUnit.DAYS.between(this.dataInicio, this.dataFim);
            if (dias <= 5) {
                throw new RegraDeNegocioException("O prazo do cadastro do celular termina em 5 dias ou menos");
            }
        }

    }
}


