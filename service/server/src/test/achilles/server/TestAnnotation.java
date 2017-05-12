package achilles.server;

import java.lang.reflect.Field;
import com.quancheng.achilles.dao.annotation.OdpsColumn;
import com.quancheng.achilles.dao.odps.model.OdpsFlyCheck;

public class TestAnnotation {
    public static void main(String[] args) {
        for (Field field : OdpsFlyCheck.class.getDeclaredFields()) {
            System.out.println(field.getAnnotation(OdpsColumn.class));
        }
    }
}
