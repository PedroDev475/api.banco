package controller;

import dto.ContaDTO;
import entity.Conta;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import service.ContaAPPService;

import java.util.List;

@Tag(name = "Loja de Celular", description = "Celulares em geral")
@RestController
@RequestMapping("/Produto")
public class ContaController {
    private final ContaAPPService service;


    public ContaController(ContaAPPService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar um novo celular",
            description = "Adicionar um celular ",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ContaDTO.class),
                            examples = @ExampleObject(name = "Exemplo valido",value = """
                                        {
                                          "descricao": "celular ",
                                          "Valor": 500.00,
                                          "dataIniciodaTransacao": "2025-08-10",
                                          "dataFinaldaTransacao": "2025-08-13"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Celular cadastrado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de Validação"),
                        content = @Content(
                                mediaType = "Aplication/json",
                         examples = {
                                 @ExampleObject(name = "Cadastro invalido", value = "\"numero maximo de cadastro e de 2 \""),
                                 @ExampleObject(name = "Duração extendida", value = "\" Tempo maximo de cadastro e de 5 dias \"")
                         }
                         }
    )

    )

}

    @PostMapping
    public ResponseEntity<ContaDTO> criar(@Valid @RequestBody ContaDTO dto) {
        var salvo = service.salvar(dto.toEntity());
        return ResponseEntity.ok(ContaDTO.fromEntity(salvo));
    }

    @Operation(
            summary = "Listar todos os celulares cadastrados",
            description = "Retorna todos os celulares cadastrados"
    )
    @GetMapping
    public List<ContaDTO> listar() {
        return service.listar().stream().map(ContaDTO::fromEntity).toList();
    }

    @Operation(
            summary = "Buscar celular por ID",
            description = "Retorna um celular existente a partir do seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "celular encontrado"),
                    @ApiResponse(responseCode = "404", description = "celular não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ContaDTO.fromEntity(service.buscarPorId(id)));
    }

    @Operation(
            summary = "Atualizar cadastro do celular",
            description = "Atualiza os dados do celular  com novas informações",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = ContaDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "atualizado"),
                    @ApiResponse(responseCode = "400", description = "Violação de regras de negócio")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ContaDTO> atualizar(@PathVariable Long id, @RequestBody ContaDTO dto) {
        var atualizado = service.atualizar(id, dto.toEntity());
        return ResponseEntity.ok(ContaDTO.fromEntity(atualizado));
    }

    @Operation(
            summary = "Deletar cadastro",
            description = "Remove um cadastro da base de dados a partir do seu ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Conta removida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

