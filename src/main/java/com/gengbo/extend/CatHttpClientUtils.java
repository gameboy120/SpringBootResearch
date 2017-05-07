package com.gengbo.extend;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created  2016/12/20-10:59
 * Author : gengbo
 */
public class CatHttpClientUtils {
    public static void get(URI uri, Map<String, String> headers) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet(uri);
        final Map<String, String> maps = new HashMap<String, String>();
        Transaction t = Cat.newTransaction("service", uri.toString());
        Cat.Context catContext = new Cat.Context() {
            @Override
            public void addProperty(String key, String value) {
                maps.put(key, value);
            }

            @Override
            public String getProperty(String key) {
                return maps.get(key);
            }
        };
        try {
            Cat.logRemoteCallClient(catContext);
            setAttachment(catContext, get);

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                get.addHeader(entry.getKey(), entry.getValue());
            }
            response = client.execute(get);
            System.out.println(response.getStatusLine().getStatusCode());
            t.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            Cat.logError(e);
            t.setStatus(e);
        } finally {
            t.complete();
            try {
                if (null != response) {
                    response.close();
                }
                if (null != client) {
                    client.close();
                }

            } catch (Exception e) {

            }
        }
    }

    private static void setAttachment(Cat.Context context, HttpGet get) {
        get.addHeader(Cat.Context.ROOT, context.getProperty(Cat.Context.ROOT));
        get.addHeader(Cat.Context.PARENT, context.getProperty(Cat.Context.PARENT));
        get.addHeader(Cat.Context.CHILD, context.getProperty(Cat.Context.CHILD));
    }

}
