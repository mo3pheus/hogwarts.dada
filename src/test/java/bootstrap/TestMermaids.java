package bootstrap;

import junit.framework.TestCase;
import org.junit.Test;
import underWater.MermaidSong;
import util.EncryptionUtil;
import util.FileUtil;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestMermaids extends TestCase {
    public static final String CRYPTO_ALGORITHM = "AES/CBC/PKCS5Padding";

    @Test
    public void testMermaidSong() throws Exception {
        Path mermaidFile = Paths.get("src", "main", "resources", "data", "mermaidObject.dat");
        String mermaidFilePath = mermaidFile.toFile().getAbsolutePath();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(mermaidFilePath));
        MermaidSong mermaidSong = (MermaidSong) objectInputStream.readObject();
        System.out.println(mermaidSong.toString());
    }

    @Test
    public void testMermaidVocals() throws Exception {
        String password = "mermaidName";
        String salt = "salt";

        Path ivFilePath = Paths.get("src", "main", "resources", "data", "specData.txt").toAbsolutePath();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(Files.readAllBytes(ivFilePath));
        String encryptedContent = "Ahcx5G7Z5gzeBGmv+WtPrDvyuF6wVAVcDysx8Ur5bzv/Q+uCtRWU9x+TG0vElbnW";
        SecretKey secretKey = EncryptionUtil.getKeyFromPassword(password, salt);
        String decryptedContent = EncryptionUtil.decrypt(CRYPTO_ALGORITHM, encryptedContent, secretKey, ivParameterSpec);

        System.out.println("\nDecrypted content you need to send is = " + decryptedContent);
        assertTrue(decryptedContent != null);
    }
}
