package com.example.reddit.security;

import com.example.reddit.exceptions.SpringRedditException;
import com.example.reddit.model.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    public void init() throws CertificateException, IOException, NoSuchAlgorithmException {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        }catch (KeyStoreException | NoSuchAlgorithmException erroNoCarregamentoDeChave){
            throw new SpringRedditException("ERRO ENQUANTO CARREGANDO A CHAVE :"+erroNoCarregamentoDeChave);
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(
                principal.getUserName())
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
