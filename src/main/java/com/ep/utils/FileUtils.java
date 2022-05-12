package com.ep.utils;

import com.google.common.annotations.VisibleForTesting;
import org.testng.annotations.Test;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class FileUtils {


    public void zip(String folderToBeZipped, String targetFile) {
        ZipUtil.pack(new File(folderToBeZipped), new File(targetFile));
    }

    public void unzip(String folderToBeUnzipped, String outputFile) {
        ZipUtil.unpack(new File(folderToBeUnzipped), new File(outputFile));
    }

}
