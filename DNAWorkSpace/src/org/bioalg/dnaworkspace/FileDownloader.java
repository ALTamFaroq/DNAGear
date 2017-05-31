/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.dnaworkspace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author faroq
 */
public abstract class FileDownloader {
    
    public static boolean downloadAndSave(URL url, FileObject path) {
        
        File courceFolder= FileUtil.toFile(path);
        FileObject originialFile=null;
        File tmpfile=null;
        try {
            
            originialFile=FileUtil.createData(path, url.getFile());
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        if(originialFile==null) return false;
        try {
            if(url==null) return false;
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        File original=FileUtil.toFile(originialFile);
        
            
                        FileOutputStream fos = new FileOutputStream(original);
                        fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            
        
            
        } catch (IOException ex) {
            Logger.getLogger(FileDownloader.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    
        // if sucesses remove the old one and rename the tmp file
        
        
            
        
    
    return true;
    }
}
