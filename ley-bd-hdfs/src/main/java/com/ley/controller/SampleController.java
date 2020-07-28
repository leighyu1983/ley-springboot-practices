package com.ley.controller;

import com.ley.service.HdfsService;
import com.ley.service.WordCountServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SampleController {

    @Autowired
    HdfsService hdfsService;
    @Autowired
    WordCountServiceImpl wordCountService;

    @GetMapping("/mkdir/{path}")
    public void mkdir(@PathVariable("path") String path) throws Exception {
        FileSystem fileSystem = hdfsService.getFileSystem();
        boolean isOk = fileSystem.mkdirs(new Path(path));
        fileSystem.close();
    }

    /**
     * POST http://localhost:8080/upload/ley-1
     * Header: Content-Type   multipart/form-data
     * Body: file  (upload file)
     *
     * @param path
     * @param file
     * @throws Exception
     */
    @PostMapping("/upload/{path}")
    public void add(@PathVariable("path") String path, @RequestParam("file") MultipartFile file) throws Exception {
        FileSystem fs = hdfsService.getFileSystem();
        Path filePath = new Path(path + "/" + file.getOriginalFilename());
        FSDataOutputStream outputStream = fs.create(filePath);
        outputStream.write(file.getBytes());
        outputStream.close();
        fs.close();
    }

    @GetMapping("/etl")
    public String etl(@RequestParam("jobName") String jobName, @RequestParam("inputPath") String inputPath) throws Exception {
        String str="";
        System.setProperty("HADOOP_USER_NAME", "admin");
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return str="请输入作业名和文件路径";
        }
        wordCountService.wordCount(jobName, inputPath);
        return "统计完成，请前去hdfs相关路径下查看";
    }
}
