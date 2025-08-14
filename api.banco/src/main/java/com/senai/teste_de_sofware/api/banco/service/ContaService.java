package com.senai.teste_de_sofware.api.banco.service;


import entity.conta;

import java.util.List;

public class ContaService {
   private final  ContaRepository  repository;

   public ContaService (ContaRepository repository){
       this.repository = repository;
   }
   public List<conta>listarConnta(){
        return repository ();
    }
  public conta criarConta(conta conta){
       conta.setSaldo(conta.getSaldo());
      return conta;
  }
}
