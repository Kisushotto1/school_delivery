package com.shelbourne.schooldelivery.service.impl;

import com.shelbourne.schooldelivery.entity.File;
import com.shelbourne.schooldelivery.mapper.FileMapper;
import com.shelbourne.schooldelivery.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Shelby
 * @since 2022-03-04
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
