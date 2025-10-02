package dto;


import entity.Conta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;



public record ContaDTO(
            @Schema(description = "ID do conta", example = "1")
            Long id,
            @NotBlank(message = "Descrição é obrigatória")
            @Schema(description = "Descrição", example = "Loja de celular")
            String descricao,

            @NotNull(message = "Cadastro é obrigatorio")
            @DecimalMin(value = "1.0", inclusive = true, message = "Cadastro Invalido")
            @Schema(description = "Cadastro invalido")
            int cadastro,

            @NotNull(message = "Data de início da transação")
            @Schema(description = "Data de início", example = "2025-08-05")
            LocalDate dataInicio,

            @NotNull(message = "Data  final da transação ")
            @Schema(description = "Data de fim", example = "2025-08-10")
            LocalDate dataFim
    ) {
        public static ContaDTO fromEntity (Conta s) {
            return new ContaDTO(
                    s.getId(),
                    s.getDescricao(),
                     s.getCadastro(),
                    s.getDataInicio(),
                    s.getDataFim()
            );

        }

    public Conta toEntity() {
            return Conta.builder()
                    .id(this.id)
                    .descricao(this.descricao)
                    .cadastro(this.cadastro)
                    .dataInicio(this.dataInicio)
                    .dataFim(this.dataFim)
                    .build();
        }
    }

