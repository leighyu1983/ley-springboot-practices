package com.ley.controller;

import com.ley.service.HdfsService;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SampleController {

	@Autowired HdfsService hdfsService;

	@GetMapping("/mkdir/{path}")
	public void mkdir(@PathVariable("path")String path) throws Exception {
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
	public void add(@PathVariable("path")String path, @RequestParam("file") MultipartFile file) throws Exception {
		FileSystem fs = hdfsService.getFileSystem();
		Path filePath = new Path(path + "/" + file.getOriginalFilename());
		FSDataOutputStream outputStream = fs.create(filePath);
		outputStream.write(file.getBytes());
		outputStream.close();
		fs.close();
	}

	@GetMapping("/delete")
	public void delete() {

	}

	@GetMapping("/get")
	public String get() {
		return null;
	}
}
