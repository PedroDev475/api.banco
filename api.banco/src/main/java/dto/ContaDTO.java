package dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;



    public record ContaDTO(
            @Schema(description = "ID do conta", example = "1")
            Long id,
            @NotBlank(message = "Descrição é obrigatória")
            @Schema(description = "Descrição do serviço", example = "Pix")
            String descricao,

            @NotNull(message = "Preço é obrigatório")
            @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser positivo")
            @Schema(description = "Preço do serviço", example = "120.00")
            Double preco,

            @NotNull(message = "Data de início da transação")
            @Schema(description = "Data de início", example = "2025-08-05")
            LocalDate dataInicio,

            @NotNull(message = "Data de final da transação ")
            @Schema(description = "Data de fim", example = "2025-08-20")
            LocalDate dataFim
    ) {
        public static ContaDTO fromEntitys) {
            return new ServicoDTO(s.getId(), s.getDescricao(), s.getPreco(), s.getDataInicio(), s.getDataFim());
        }

        public Conta toEntity() {
            return Conta.builder()
                    .id(id)
                    .descricao(descricao)
                    .preco(preco)
                    .dataInicio(dataInicio)
                    .dataFim(dataFim)
                    .build();
        }
    }
}
