--Di seguito sono riportati i passi per generare un CA, i certificati server e client ed il trust che importa la CA
--Questi certificati vanno generati solo a titolo di esempio. I certificati generati per l'archetipo potrebbero essere 
scaduti e quindi vanno rigenerati per effettuare una prova.

--Passi per la CA
keytool -genkeypair -keyalg RSA -keysize 2048 -validity 2365 -alias ca -dname "CN=ca,O=HMS,S=SE" -keystore ca.jks -storepass password
keytool -exportcert -rfc -alias ca -keystore ca.jks -storepass password > ca.pem
cat ca.pem | keytool -importcert -alias ca -noprompt -keystore trust.jks -storepass password


--Passi per il server
keytool -genkeypair -keyalg RSA -keysize 2048 -validity 365 -alias server -dname "CN=localhost.com,O=HMS,S=SE" -keystore server.jks -storepass password
keytool -certreq -alias server -storepass password -keystore server.jks | keytool  -gencert -alias ca -rfc -keystore ca.jks -storepass password > server.pem
cat ca.pem | keytool -importcert -alias ca -noprompt -keystore server.jks -storepass password
cat ca.pem server.pem | keytool -importcert -alias server -keystore server.jks -storepass password


--Passi per il client
keytool -genkeypair -keyalg RSA -keysize 2048 -validity 365 -alias client -dname "CN=client,O=HMS,S=SE" -keystore client.jks -storepass password
keytool -certreq -alias client -keystore client.jks -storepass password | keytool -gencert -alias ca -rfc -keystore ca.jks -storepass password> client.pem
cat ca.pem | keytool -importcert -alias ca -noprompt -keystore client.jks -storepass password
cat ca.pem client.pem | keytool -importcert -alias client -keystore client.jks -storepass password
