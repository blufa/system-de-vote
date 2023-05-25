package com.senatic.votingserver.configuration.security.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.senatic.votingserver.configuration.security.constant.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private static final String SECRET_KEY = SecurityConstants.JWT_KEY;

  /**
    Extrae el nombre de usuario del token.
    @param token El token del usuario.
    @return El nombre de usuario correspondiente.
  */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
    Extrae un reclamo del token.
    @param token El token del cual extraer el reclamo.
    @param claimsResolver El resolver de reclamos.
    @param <T> El tipo de dato del reclamo a extraer.
    @return El reclamo extraído.
    */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
    Genera un token JWT para el usuario especificado utilizando los detalles del usuario.
    @param userDetails Los detalles del usuario.
    @return El token JWT generado.
    */
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
    Genera un token JWT que contiene los detalles de un usuario y cualquier otra reclamación adicional proporcionada.
    @param extraClaims Las reclamaciones adicionales a agregar al token.
    @param userDetails Los detalles del usuario.
    @return El token JWT generado.
    */
  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    Map<String, Object> rolesClaim = new HashMap<>();
    rolesClaim.put("role", userDetails.getAuthorities().stream().findFirst().get().getAuthority());
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .addClaims(rolesClaim)
        .compact();
  }

  /**
    Verifica si el token proporcionado es válido para los detalles del usuario proporcionados.
    @param token El token a verificar.
    @param userDetails Los detalles del usuario para verificar el token.
    @return true si el token es válido para los detalles del usuario, false de lo contrario.
    */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
    Verifica si el token ha expirado.
    @param token El token a verificar.
    @return true si el token ha expirado, false de lo contrario.
    */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
    Extrae la fecha de expiración del token.
    @param token El token JWT.
    @return La fecha de expiración del token.
    */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
    Extrae todas las reclamaciones (claims) del token.
    @param token El token del que extraer las reclamaciones.
    @return Todas las reclamaciones del token.
    */
  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
    Obtiene la clave utilizada para firmar los tokens JWT.
    @return La clave de firma.
    */
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
