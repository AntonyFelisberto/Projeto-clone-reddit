package com.example.reddit.security;

import com.example.reddit.exceptions.SpringRedditException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import java.security.KeyStore;

@Service
@Repository
@Slf4j
public class JwtProvider {

    private KeyStore keyStore;

    public JwtProvider() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("\\springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        }catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException erroNoCarregamentoDeChave){
            throw new SpringRedditException("ERRO ENQUANTO CARREGANDO A CHAVE :"+erroNoCarregamentoDeChave.getMessage());
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(
                principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try{
            return (PrivateKey) keyStore.getKey("springblog","secret".toCharArray());
        }catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException erroNaRecuperacaoDeChave){
            throw new SpringRedditException("ERRO ENQUANTO RECUPERANDO A CHAVE PRIVADA :"+erroNaRecuperacaoDeChave);
        }
    }

}
