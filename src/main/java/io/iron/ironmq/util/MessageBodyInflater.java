package io.iron.ironmq.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.InflaterInputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Sergei Barinov
 *
 */
public class MessageBodyInflater {
    public static String inflateBody(String body) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = null;
        byte[] decompressedData = null;
        try {
            in = new InflaterInputStream(new ByteArrayInputStream(
                    Base64.decodeBase64(body)));
            getBytes(in, out);
            out.flush();
            decompressedData = out.toByteArray();
            return new String(decompressedData, Charset.forName("UTF-8"));
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
            if (out != null)
                out.close();
            out = null;

        }
    }

    public static void getBytes(InputStream in, OutputStream out)
            throws IOException {

        byte[] buffer = new byte[4196];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        out.flush();
    }
}
