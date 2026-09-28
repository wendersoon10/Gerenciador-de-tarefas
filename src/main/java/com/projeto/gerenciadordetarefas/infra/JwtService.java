package com.projeto.gerenciadordetarefas.infra;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


@Service
public class JwtService {

    //pega o valor da variavel de ambiente
    @Value("${JWT_SECRET}")
    private String secret;

    private SecretKey getSigningkey(){
        return Keys.hmacShaKeyFor(      //Transforma os bytes em uma SecretKey
                secret.getBytes(StandardCharsets.UTF_8) //Transforma a string em um conjunto de bytes
        );
    }

    public String gerarToker(String email){
        return Jwts.builder()
                .subject(email)  //É assim que posteriormente, o filtro poderá descobrir quem está fazendo a requisição
                .signWith(getSigningkey())  //Assina o token utilizando a SecretKey
                .compact(); //Monta o JWT final em formato de String
    }




}
