package com.springapp.mvc.web;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @author songkejun
 * @create 2018-01-03 15:05
 **/
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    public static final String DEFAULT_ENCODING = "UTF-8";
    private String encoding;
    private byte[] body;

    public MultiReadHttpServletRequest(HttpServletRequest httpServletRequest, String encoding) throws IOException {

        super(httpServletRequest);
        this.encoding = encoding;
        // Read the request body and save it as a byte array
        InputStream is = super.getInputStream();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        StreamUtils.copy(is, output);

        body = output.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamImpl(new ByteArrayInputStream(body));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        String enc = getCharacterEncoding();
        if (enc == null) {
            enc = encoding;
        }
        return new BufferedReader(new InputStreamReader(getInputStream(), enc));
    }

    private static  class ServletInputStreamImpl extends ServletInputStream {

        private InputStream is;

        public ServletInputStreamImpl(InputStream is) {
            this.is = is;
        }

        @Override
        public int read() throws IOException {
            return is.read();
        }

        @Override
        public boolean markSupported() {
            return false;
        }

        @Override
        public synchronized void mark(int i) {
            throw new RuntimeException(new IOException("mark/reset not supported"));
        }

        @Override
        public synchronized void reset() throws IOException {
            throw new IOException("mark/reset not supported");
        }
    }

}
