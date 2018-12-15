package com.file.manager.junit;


import com.file.manager.model.FileModel;
import com.file.manager.model.FileModelDTO;
import com.file.manager.repository.FileRepository;
import com.file.manager.service.FileManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FileRepositoryJUnitTest {

    private final static String FILENAME = "jUnitTest.java";

    @Before
    public void before() throws UnsupportedEncodingException {
        FileModelDTO fileModelDTO = new FileModelDTO();
        fileModelDTO.setFileName(FILENAME);
        FileModel fileModel = new FileModel();
        fileModel.setName(FILENAME);
        fileModel.setToken(fileManager.generateToken(fileModelDTO));
        fileRepository.save(fileModel);
    }

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileManager fileManager;

    @Test
    public void findModelByName() {
        FileModel fileModel = fileRepository.findByName(FILENAME);
        Assert.assertNotNull(fileModel);
    }

    @Test
    public void findModelByToken() throws UnsupportedEncodingException {
        FileModelDTO fileModelDTO = new FileModelDTO();
        fileModelDTO.setFileName(FILENAME);
        String token = fileManager.generateToken(fileModelDTO);
        FileModel fileModel = fileRepository.findByToken(token);
        Assert.assertNotNull(fileModel);
    }

    @After
    public void after() {
        FileModel fileModel = fileRepository.findByName(FILENAME);
        fileRepository.delete(fileModel);
    }
}
