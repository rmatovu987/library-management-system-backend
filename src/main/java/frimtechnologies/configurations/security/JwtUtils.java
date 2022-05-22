package frimtechnologies.configurations.security;

import frimtechnologies.auth.domains.User;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtSignatureException;
import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.Date;

@ApplicationScoped
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Inject
	JWTParser parser;

	@Inject
	JsonWebToken jwt;

	private final String jwtSecret = "k3ofk/o23-ij230DFos03k40g9m49vcm0wek320kdfinjg94itkg90f0xksle,34>0i49iF3o4F90vdHPs#p";

	public String generateJwtToken(String email) {
		User user = User.findByEmail(email).get();

		int jwtExpirationMs = 86400000;
		return Jwt.subject(user.email).issuedAt(new Date().toInstant())
				.expiresAt(new Date((new Date()).getTime() + jwtExpirationMs).toInstant())
				.upn(user.firstName + " " + user.lastName).issuer("Library Management System Auth server").signWithSecret(jwtSecret);

	}

	public String getUserNameFromJwtToken(String token) {

		try {
			jwt = parser.verify(token, jwtSecret);

			return jwt.getSubject();

		} catch (ParseException e) {
			logger.error("Parse Exception: {}", e.getMessage());
			throw new WebApplicationException("Access Denied.", 401);

		} catch (Exception e) {
			logger.error("Null Exception: {}", e.getMessage());
			throw new WebApplicationException("Access Denied.", 401);
		}

	}

	public boolean validateJwtToken(String authToken, HttpServerRequest request) {

		try {
			jwt = parser.verify(authToken, jwtSecret);

			return true;

		} catch (JwtSignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
			return false;

		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
			return false;

		} catch (ParseException e) {
			logger.error("Parse Exception: {}", e.getMessage());
			return false;

		} catch (Exception e) {
			logger.error("Null Exception: {}", e.getMessage());
			return false;
		}

	}

}
