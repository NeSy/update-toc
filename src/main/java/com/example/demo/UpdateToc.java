package com.example.demo;

import java.io.IOException;
import java.io.InputStream;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.toc.TocGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UpdateToc {

  @PostMapping("/updateToc")
  public void updateToc(InputStream docxIn, HttpServletResponse res) {
    try {
        WordprocessingMLPackage wordMLPackage = Docx4J.load(docxIn);
        new TocGenerator(wordMLPackage).updateToc(true, true);
        wordMLPackage.save(res.getOutputStream(), Docx4J.FLAG_SAVE_ZIP_FILE);
        res.flushBuffer();
    } catch (Docx4JException e) {
        e.printStackTrace();
        try {
            res.sendError(500, e.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        res.setStatus(500);
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}
