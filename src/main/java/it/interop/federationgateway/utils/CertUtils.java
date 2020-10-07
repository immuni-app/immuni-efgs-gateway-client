package it.interop.federationgateway.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CertUtils {
	private static final Logger logger = LoggerFactory.getLogger(CertUtils.class);

  /**
   * Loads a certificate from resource directory.
   *
   * @param certificateFileName filename of cert
   * @return X509Certificate object holding the certificate
   */
  public static X509Certificate loadCertificateFromFile(String certificateFileName) {
    try {
      PEMParser parser = new PEMParser(new InputStreamReader(readFileFromRessource(certificateFileName)));
      while (parser.ready()) {
        Object pemContent = parser.readObject();

        if (pemContent instanceof X509CertificateHolder) {
          JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
          try {
            return converter.getCertificate((X509CertificateHolder) pemContent);
          } catch (CertificateException e) {
        	  logger.error("Cannot convert Certificate Holder to Certificate: {}", e.getMessage());
          }
        }
      }
      logger.error("Failed to load certificate: Certificate does not contain a certificate");
      return null;
    } catch (IOException e) {
    	logger.error("Failed to load certificate: {}", e.getMessage());
      return null;
    }
  }

  /**
   * Lods a private key from resource directory.
   *
   * @param privateKeyfileName filename of private key
   * @return PrivateKey object holding the private key
   */
  public static PrivateKey loadPrivateKeyFromFile(String privateKeyfileName) {
    try {
      PEMParser parser = new PEMParser(new InputStreamReader(readFileFromRessource(privateKeyfileName)));
      JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
      while (parser.ready()) {
        Object pemContent = parser.readObject();

        if (pemContent instanceof PrivateKeyInfo) {
          return converter.getPrivateKey((PrivateKeyInfo) pemContent);
        } else if (pemContent instanceof PEMKeyPair) {
          return converter.getPrivateKey(((PEMKeyPair) pemContent).getPrivateKeyInfo());
        }
      }
      logger.error("Failed to load private key: Certificate does not contain a private key");
      return null;
    } catch (IOException e) {
    	logger.error("Failed to load private key: {}", e);
      return null;
    }
  }

  private static InputStream readFileFromRessource(final String filePath) throws FileNotFoundException {
    return new FileInputStream(filePath);
  }
}
