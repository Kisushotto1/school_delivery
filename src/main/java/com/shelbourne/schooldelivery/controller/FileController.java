package com.shelbourne.schooldelivery.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import com.shelbourne.schooldelivery.service.IFileService;
import com.shelbourne.schooldelivery.entity.File;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Shelby
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/file")

public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private IFileService fileService;

    //查询所有
    @GetMapping
    public List<File> findAll() {
        return fileService.list();
    }

    //新增或删除接口
    @PostMapping
    public boolean save(@RequestBody File file) {
        return fileService.saveOrUpdate(file);
    }

    //删除请求接口
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {//这里的“id”必须和DeleteMapping里面的名字一样
        return fileService.removeById(id);
    }

    //批量删除请求接口
    @PostMapping("/batchDel")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {//这里的“id”必须和DeleteMapping里面的名字一样
        return fileService.removeBatchByIds(ids);
    }

    //简单条件+分页查询
    @GetMapping("/page")
    public IPage<File> findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String filename) {
        IPage<File> page = new Page<>(pageNum, pageSize);
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();

        if (!"".equals(filename)) {//加条件判断可以查询null值
            queryWrapper.like("filename", filename);
        }

        queryWrapper.orderByDesc("id");
        return fileService.page(page, queryWrapper);
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();//获取文件名，不带路径，如 untitled.jpg
        String type = FileUtil.extName(originalFilename);//获取文件类型，如jpg
        long size = file.getSize();
        //存储到磁盘
        java.io.File uploadPath = new java.io.File(fileUploadPath);
        //判断目录是否存在，不存在则创建
        if (!uploadPath.exists()) {
            if (uploadPath.mkdirs()) {
                System.out.println("创建目录：" + fileUploadPath + "成功！");
            } else {
                System.out.println("创建目录：" + fileUploadPath + "失败！");
            }
        }
        //定义文件唯一标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        java.io.File storageFile = new java.io.File(fileUploadPath + fileUuid);

        //把获取的文件存储到磁盘
        try {
            file.transferTo(storageFile);
        } catch (IOException e) {
            System.out.println("文件存储失败！");
            e.printStackTrace();
        }
        String url = "http:localhost:9090/file/download/" + fileUuid;

        //存储到数据库
        File saveFile = new File();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(url);
        fileService.save(saveFile);
        return url;
    }

    /**
     * 文件下载路径：http:localhost:9090/file/download/xxx
     *
     * @param fileUuid
     * @param response
     * @throws IOException
     */
    @GetMapping("/download/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        java.io.File file = new java.io.File(fileUploadPath + fileUuid);//获取文件流
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");

//        把服务器端的文件写入response
        os.write(FileUtil.readBytes(file));
        os.flush();
        os.close();
    }
}

