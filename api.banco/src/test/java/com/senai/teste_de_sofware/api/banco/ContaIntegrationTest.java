package com.senai.teste_de_sofware.api.banco;


import com.fasterxml.jackson.databind.ObjectMapper;
import controller.ContaTesteSwaggerAplication;
import dto.ContaDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = ContaTesteSwaggerAplication.class)
@AutoConfigureMockMvc
public class ContaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCadastrarCelularValido() throws Exception{
        var dto = new ContaDTO(
                null,
                "Loja de celular",
                1,
                LocalDate.now(),
                LocalDate.now().plusMonths(5)
        );
        System.out.println(objectMapper.writeValueAsString(dto));

    mockMvc.perform(
            post("/contas")
                  .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto))
    )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.descricao").value("Loja de celular"));


}

    @Test
    void deveAtualizarCadastro() throws  Exception{
        var dto =new ContaDTO(
                null,
                "Loja de celular",
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(10)
        );

        var salvo = mockMvc.perform(post("/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();

        var servicoSalvo = objectMapper.readValue(salvo, ContaDTO.class);

        var atualizado = new ContaDTO(
                null,
                "Conta atualizada",
                1,
                servicoSalvo.dataInicio(),
                servicoSalvo.dataFim()
        );

        mockMvc.perform(
                        put("/contas/"+servicoSalvo.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Conta atualizada"));

    }


    @Test
    void deveDeletarConta() throws Exception {
        var dto = new ContaDTO(null, "Conta", 1,
                LocalDate.now(), LocalDate.now().plusDays(10));
        var salvo = mockMvc.perform(post("/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();

        var servico = objectMapper.readValue(salvo, ContaDTO.class);

        mockMvc.perform(delete("/contas/" + servico.id()))
                .andExpect(status().isNoContent());
    }

}