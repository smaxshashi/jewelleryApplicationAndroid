package com.bansal.JewellaryApplication;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class MultipartRequest  extends com.android.volley.Request<String> {

    private final Response.Listener<String> listener;
    private final Map<String, String> params;

    public MultipartRequest(int method, String url, Map<String, String> params,
                            Response.Listener<String> responseListener,
                            Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (Exception e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + "volleyBoundary";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buildPart(dos, entry.getKey(), entry.getValue());
            }
            dos.writeBytes("--volleyBoundary--\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    private void buildPart(DataOutputStream dos, String key, String value) throws IOException {
        dos.writeBytes("--volleyBoundary\r\n");
        dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
        dos.writeBytes(value + "\r\n");
    }
}