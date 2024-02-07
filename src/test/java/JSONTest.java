import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPObject;
import org.junit.Test;

public class JSONTest {

    @Test
    public void json(){
    String j="{\"flag\":\"2\",\"info\":\"来迟一步，此类别券已抢完\"}";

        JSONObject jsonObject = JSON.parseObject(j);
        String string = jsonObject.getString("flag");
        System.out.println(string);
    }
}
