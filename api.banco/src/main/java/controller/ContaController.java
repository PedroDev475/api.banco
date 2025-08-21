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

@Tag(name = "Conta", description = "Cadastro de Conta PIX")
@RestController
@RequestMapping("/servicos")
public class ContaController {
    private final ContaAPPService service;

    public ContaController(ContaAPPService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar uma nova conta",
            description = "Adiciona uma nova conta pix,e fazer um novo cadastro ",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Conta.class),
                            examples = @ExampleObject(value = """
                                        {
                                          "descricao": "Conta ",
                                          "Saldo": 120.00,
                                          "dataIniciodaTransacao": "2025-08-10",
                                          "dataFinaldaTransacao": "2025-08-13"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta cadastrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Violação de regras de negócio")
            }
    )
    @PostMapping
    public ResponseEntity<ContaDTO> criar(@Valid @RequestBody ContaDTO dto) {
        var salvo = service.salvar(dto.toEntity());
        return ResponseEntity.ok(ContaDTO.fromEntity(salvo));
    }

    @Operation(
            summary = "Listar todos os serviços",
            description = "Retorna todos os serviços cadastrados"
    )
    @GetMapping
    public List<ContaDTO> listar() {
        return service.listar().stream().map(ContaDTO::fromEntity).toList();
    }

    @Operation(
            summary = "Buscar serviço por ID",
            description = "Retorna um serviço existente a partir do seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Serviço encontrado"),
                    @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ContaDTO.fromEntity(service.buscarPorId(id)));
    }

    @Operation(
            summary = "Atualizar Conta",
            description = "Atualiza os dados de uma conta com novas informações",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = ContaDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Serviço atualizado"),
                    @ApiResponse(responseCode = "400", description = "Violação de regras de negócio")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ContaDTO> atualizar(@PathVariable Long id, @RequestBody ContaDTO dto) {
        var atualizado = service.atualizar(id, dto.toEntity());
        return ResponseEntity.ok(ContaDTO.fromEntity(atualizado));
    }

    @Operation(
            summary = "Deletar conta",
            description = "Remove uma conta da base de dados a partir do seu ID",
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

