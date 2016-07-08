package org.auscope.nvcl.server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
 
/**
 * A thread to read the error stream (STDERR), in case anything went wrong.
 */
public class ProcessStreamReader extends Thread {
    /** Our access to the incoming characters on the stream */
    private BufferedReader reader;
    
    /** The characters read from the stream are appended to this
     * StringBuffer.  */
    private StringBuffer buffer = new StringBuffer();
    
    
    
    /**
     * Constructor.  Builds a <code>BufferedReader</code>.
     * @param inputStream
     */
    public ProcessStreamReader(InputStream inputStream) {
        InputStreamReader isr = new InputStreamReader( inputStream );
        reader = new BufferedReader( isr );
    }
    
    /* (non-Javadoc)
     * Runs until the input stream is closed.
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            // Take (and keep taking) a line from the reader.  This should go on
            // until the process dies.
            String temp;
            while (null != (temp = reader.readLine())) {
                buffer.append( temp );
            }
        } catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
    
    /**
     * @return Returns the contents of the buffer.
     */
    public String getString() {
        return buffer.toString();
    }
}
