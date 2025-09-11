package entity;


import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private int Cadastro;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
