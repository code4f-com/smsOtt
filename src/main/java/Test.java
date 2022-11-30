
import com.tuanpla.utils.encrypt.Md5;

/*
 *  Copyright 2022 by Tuanpla
 *  https://tuanpla.com
 */
/**
 *
 * @author tuanp
 */
public class Test {

    public static void main(String[] args) {
        String secret = Md5.encryptMD5("Kme!$jokO3979" + "asdasd-asd-asdasdas");
        System.out.println(secret);
    }
}
