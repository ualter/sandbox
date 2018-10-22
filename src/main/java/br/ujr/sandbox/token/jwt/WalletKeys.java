package br.ujr.sandbox.token.jwt;

import java.security.PrivateKey;
import java.security.PublicKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletKeys {
	
	private PublicKey  publicKey;
	private PrivateKey privateKey;

}
