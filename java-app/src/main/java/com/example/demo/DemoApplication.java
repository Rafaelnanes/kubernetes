package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    @RequestMapping
    public class MyController {
        private static final Logger log = LoggerFactory.getLogger(MyController.class);
        private PrivateKey privateKey;

        /**
         * # Generate the initial RSA key
         * openssl genrsa -out private.pem 2048
         *
         * # Convert to PKCS#8 format
         * openssl pkcs8 -topk8 -inform PEM -outform PEM -in private.pem -out private_pkcs8.pem -nocrypt
         *
         * # Copy to clipboard (using xclip)
         * cat private_pkcs8.pem | grep -v "BEGIN\|END" | tr -d '\n' | xclip -selection clipboard
         */
        private static final String PRIVATE_KEY_BASE64 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDOn+XtZpntGymJi5h4FGdheh86N5d+4ofvgmaqIk8dxeipKpeMdXDxd2Vnlnc196Ebo8nOkUZ4tP+5Kberq471ThCKrXv2Z7vtEK49WxmExytXIdgWGNA9jecNaQQB8T8ep0vuPi+9w4zt95h0FpDQ2ueD6l3Eys5s213VvYJUyN9Xz+dKJliBXEnZFd39+W0LzoXxW2yqx/rju5SwDE43U0rkDeHFJNvfpFlvg4urQIuGC958+xleuBxSB4dfsNc8I1GPbyHJegdteli9C2R0gt4AZGMWEbQ51L1cx2beBo7ptfHslLavvYjW/rp1JN3Ttt55eL89pEGDd79jQKz5AgMBAAECggEAFhRccmRq4WfsJeBkFWd/bplUBJlpLRa8QO9LKjYKr0X1PpJ9vvV+cVx5WmWSl+foxYvYkPl8MYI3QLbrrhgMlDutz9aJrxiBqpZZkCvlIQepeCD2jCdFpWDoW59Tss4amRt2eonBpJe6E7nIBuuxuOe+Ip4PkWu8kuxUGCGCmlhAJdLnnhFVKzhyXr1oc95Lcr2/PhpLuRenyRqAk1slQDOixCf7JgDkWp4Ob8xWG01ZvtdtzmsPdFZC9u+5D30/wxxr8D+wYuTxlIKW68fcTkUD6Jyiv+Y8NW0wn6peZzidPR9VjGqAH83+vjiLAYresA0gRsan3C1QrYMvFnlAcwKBgQD1+FIfgXqeZqya8VeByQ5ZdGHEIanMPzMyjAQpf8jA3Cx4NRWblYhPXeU096qvl5/BMoZQZx5rCoVBJILiAHYdPaAFc91h6EAINIw/e7v8Y7v6UAQzM77kfUrIU3wm5leka7953z5/VpbNdDj0O3r8L4tPvW7ZnuGADLpb3DqQiwKBgQDXDNnaJcpFqawuzdJ1VY2AscFoF8Kq50Tj5tMPUa9r+AAq19wPmRCXawmC60u2aGsxao0OONNYic+JpaYhYG/DvC7zf52LtPJOwuy32m82Ul54TehMVB6SEY0OcO71XJ3lIlqB4d8ImgaZNheOvnW6Oc7te3eIQ9xRqTDLhTVFCwKBgCJCO+33csStOE08YBvLjRwYgaV3qtav1smrhsjg620xPWBgxGmvIhUr1sjR1gwu6ilWiC6arjnqdTTliPVBazIXazpXMPZ6CrEZ4I7XBQ/aNExg6LTY+DWK3eeY+SnR21MjTsACwAmsXcSv817RCggl9xlcIRxebLoRC8PPoLiRAoGBAKhBOcHgh13x3vHPv7Zl9YyXh0c2L+0ffVsComf99U54f0kGEvtxOvX9nTd9eYOCNzw2rZ5O/6zqRiLgJBdBKLDwb5u8YTuJm3vcLvG6SgEqeRePfQ0bU7/dU4vU+GGo0TERq9arLqHAQSFmnQ/mHJ5T1H8idud6kMqjobXax075AoGAbdp/bK8MtoTJzJSHnyaTXWUk+JV4KkaR7WYTC3D34Yvts7w2aysV2AHVtLjtXMxvIMVAHlZIM91mYqRERBDdjcMkW/V60jgF1lm4Zdm7H9D9yGX4KIo6iOP92HXPACnM26sO83LUB8tuAxYIxvQRNh8nvXHa2DsZjuPZMtotX7M=";

        @PostConstruct
        public void init() {
            try {
                String privateKey = System.getenv("MY_PRIVATE_KEY");
                if(!StringUtils.hasText(privateKey)){
                    log.info("Private key not found, running the default");
                    privateKey = PRIVATE_KEY_BASE64;
                }
                byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);

                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                this.privateKey = keyFactory.generatePrivate(keySpec);

                String value = System.getenv("MY_ENV");
                String message = String.format("My env value is: %s, encrypting value as: %s", value, getEncryptedEnv());
                log.info(message);
            } catch (Exception e) {
                log.error("Error loading RSA private key", e);
            }
        }

        @RequestMapping("/")
        public String hello() {
            log.info("Stepping into hello world");
            String value = System.getenv("MY_ENV");
            return String.format("My env value is: %s, encrypting value as: %s", value, getEncryptedEnv());
        }

        public String getEncryptedEnv() {
            try {
                String envValue = System.getenv("MY_ENV");
                if (envValue == null) {
                    return "Environment variable MY_ENV not found";
                }

                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, privateKey);

                byte[] encryptedBytes = cipher.doFinal(envValue.getBytes());

                return Base64.getEncoder().encodeToString(encryptedBytes);
            } catch (Exception e) {
                log.error("Error encrypting environment variable", e);
                return "Encryption error: " + e.getMessage();
            }
        }
    }
}