
#### 1. Generate the Private Key
```shell
$ openssl genrsa -des3 -passout pass:changeit -out server.pass.key 2048
$ openssl rsa -passin pass:changeit -in server.pass.key -out server.key
$ rm server.pass.key
$ mv server.key privateKey.key
```

### 2. Create Certificate Signing Request (CSR) utilizing the RSA Private Key
```shell
openssl req -new -key privateKey.key -out server.csr
```

### 3. Generate a file named v3.ext with the below listed contents:
```shell
authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names
 
[alt_names]
DNS.1 = <specify-the-same-common-name-that-you-used-while-generating-csr-in-the-last-step>
```

### 4. Create the SSL Certificate utilizing the CSR created
```shell
openssl x509 -req -sha256 -extfile v3.ext -days 365 -in server.csr -signkey privateKey.key -out serverCertificate.crt
```
This file `serverCertificate.crt` is itself the Certificate that can be loaded to a TrustStore like KeyChaing MacOs, it contains only the Public Key. 


### 5. Create a PKCS12 (or PFX) from a PEM Certificate file (.crt created before)
This step is optional, it is a creation of a PKCS12 file, that also can be loaded to a TrustStore, but with both included on it (Private and Public Keys).
  
```shell  
openssl pkcs12 -export -out serverCertificate.pfx -inkey privateKey.key -in serverCertificate.crt
```
The PKCS12 file contains our Private and Public Key inside the PFX file generated file.

### Summary Files
- `privateKey.key`:       Generated private key
- `server.csr`:           Certificate Signing Request
- `serverCertificate.crt`: Certificate (only Public Key inside)
- `serverCertificate.pfx`: Certificate - binary format of a Certificate with Private and Public Keys inside of it.

source:
`https://ksearch.wordpress.com/2017/08/22/generate-and-import-a-self-signed-ssl-certificate-on-mac-osx-sierra/`
