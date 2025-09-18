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
    private int Cadastro;
    private LocalDate dataInicio;
    private LocalDate dataFim;


    }




