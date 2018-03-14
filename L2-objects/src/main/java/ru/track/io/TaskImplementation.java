package ru.track.io;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.track.io.vendor.Bootstrapper;
import ru.track.io.vendor.FileEncoder;
import ru.track.io.vendor.ReferenceTaskImplementation;

import java.io.*;
import java.net.InetSocketAddress;
import java.io.InputStream;


public final class TaskImplementation implements FileEncoder {

    /**
     * @param finPath  where to read binary data from
     * @param foutPath where to write encoded data. if null, please create and use temporary file.
     * @return file to read encoded data from
     * @throws IOException is case of input/output errors
     */
    @NotNull
    public File encodeFile(@NotNull String finPath, @Nullable String foutPath) throws IOException {
        /* XXX: https://docs.oracle.com/javase/8/docs/api/java/io/File.html#deleteOnExit-- */
        final File fin = new File(finPath);
        final File fout;

        if (foutPath != null) {
            fout = new File(foutPath);
        } else {
            fout = File.createTempFile("based_file_", ".txt");
            fout.deleteOnExit();
        }

        InputStream inputStream = new FileInputStream(fin);
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fout));
        byte[] buf = new byte[3]; // a buffer to store our binary information from a file
        int numOfBytesRead;

        while (true) {
            numOfBytesRead = inputStream.read(buf);
            if (numOfBytesRead == -1)
                break;

            switch (numOfBytesRead) {
                case 3:
                    byte ch = (byte) toBase64[((buf[0] & 252) >> 2)];
                    outputStream.write(ch);
                    ch = (byte) toBase64[buf[0] & 3 + ((buf[1] & 240) >>> 4)];
                    outputStream.write(ch);
                    ch = (byte) toBase64[buf[1] & 15 + ((buf[2] & 192) >>> 6)];
                    outputStream.write(ch);
                    ch = (byte) toBase64[buf[2] & 63];
                    outputStream.write(ch);
                    break;

                case 2:
                    ch = (byte) toBase64[((buf[0] & 252) >>> 2)];
                    outputStream.write(ch);
                    ch = (byte) toBase64[buf[0] & 3 + ((buf[1] & 240) >>> 4)];
                    outputStream.write(ch);
                    ch = (byte) toBase64[buf[1] & 15];
                    outputStream.write(ch);
                    ch = '=';
                    outputStream.write(ch);
                    break;

                case 1:
                    ch = (byte) toBase64[((buf[0] & 252) >>> 2)];
                    outputStream.write(ch);
                    ch = (byte) toBase64[buf[0] & 3];
                    outputStream.write(ch);
                    ch = (byte) '=';
                    outputStream.write(ch);
                    outputStream.write(ch);
                    break;
            }
        }


        outputStream.close();
        inputStream.close();
        return fout;
//        throw new UnsupportedOperationException(); // TODO: implement
    }


    private static final char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    public static void main(String[] args) throws Exception {
//        final FileEncoder encoder = new ReferenceTaskImplementation();
        final FileEncoder encoder = new TaskImplementation();
        // NOTE: open http://localhost:9000/ in your web browser
        (new Bootstrapper(args, encoder))
                .bootstrap("", new InetSocketAddress("127.0.0.1", 9000));
    }

}
