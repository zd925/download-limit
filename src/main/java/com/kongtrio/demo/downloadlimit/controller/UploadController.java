package com.kongtrio.demo.downloadlimit.controller;

import com.kongtrio.demo.downloadlimit.exception.DHException;
import com.kongtrio.demo.downloadlimit.exception.ExceptionCode;
import com.kongtrio.demo.downloadlimit.exception.I18N;
import com.kongtrio.demo.downloadlimit.exception.I18NConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;

/**
 * @author DH
 * @date 2020-03-13 16:13
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

	@GetMapping("/test")
	public String downloadFile(@RequestParam("id") String id)  {
	throw new DHException(ExceptionCode.ERROR_CODE_NOT_AUTH, I18NConstants.PARAM_EXP);
	}
}
