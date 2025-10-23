package controller;




import dto.ContaDTO;
import entity.Conta;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ContaAPPService;

import java.util.List;

@Tag(name = "Contas", description = "Gerenciamento de Contas Para o Cadastro de celulares")
@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaAPPService service;

    public ContaController(ContaAPPService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar um novo conta",
            description = "Adiciona um novo serviço à base de dados após validações de preço e duração",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ContaDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                          "descricao": "Celular cadastrado",
                                          "cadastro":, "1,0"
                                          "dataInicio": "2025-08-05",
                                          "dataFim": "2025-08-10"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Cadastro invalido", value = "\"O maximo de cadastro é de 2\""),
                                            @ExampleObject(name = "Duração  do cadastro  excedida", value = "\"O tempo maximo de cadastro foi excedido\"")
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ContaDTO> criar(@Valid @org.springframework.web.bind.annotation.RequestBody ContaDTO dto) {
        ContaDTO contaSalva = service.salvar(dto);
        return ResponseEntity
                .status(201)
                .body(contaSalva);
    }

    @Operation(
            summary = "Listar todas as contas",
            description = "Retorna todos os serviços cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<ContaDTO>> listar() {
        return ResponseEntity
                .ok(service.listar());

    }

    @Operation(
            summary = "Buscar contas por ID",
            description = "Retorna uma conta existente a partir do seu ID",
            parameters = {
                    @Parameter(name = "id", description = "ID da conta  a ser buscado", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta encontrada"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com ID 99 não encontrado.\"")
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar uma Conta",
            description = "Atualiza os dados de uma conta  existente com novas informações",
            parameters = {
                    @Parameter(name = "id", description = "ID da Conta a ser atualizado", example = "1")
            },
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ContaDTO.class),
                            examples = @ExampleObject(name = "Exemplo de atualização", value = """
                        {
                          "descricao": "Conta concluida",
                          "cadastro": 1,
                          "dataInicio": "2025-08-01",
                          "dataFim": "2025-08-10"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Cadastro inválido", value = "\"Valor maximo de celulares cadastrados é 2 \""),
                                            @ExampleObject(name = "Duração excedida", value = "\"O tempo de duração do cadastro é de 5 dias\"")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Serviço não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Serviço com ID 99 não encontrado.\"")
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ContaDTO> atualizar(@PathVariable Long id, @Valid @org.springframework.web.bind.annotation.RequestBody ContaDTO dto) {
        ContaDTO contaAtualizada = service.atualizar(id, dto);
        return ResponseEntity.ok(contaAtualizada);
    }

    @Operation(
            summary = "Deletar uma conta",
            description = "Remove uma conta da base de dados a partir do seu ID",
            parameters = {
                    @Parameter(name = "id", description = "ID do serviço a ser deletado", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Conta removida com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com ID 99 não encontrado.\"")
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}