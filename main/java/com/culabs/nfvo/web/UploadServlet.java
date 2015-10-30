package com.culabs.nfvo.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.core.TemplateOperator;
import com.culabs.nfvo.model.db.DBNsd;
import com.culabs.nfvo.model.db.DBVnfd;
import com.culabs.nfvo.model.nsd.Nsd;
import com.culabs.nfvo.model.vnfd.Vnfd;
import com.culabs.nfvo.util.DirUtils;
import com.culabs.nfvo.util.NFVOUtils;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet
{

	private static Logger LOGGER = LoggerFactory.getLogger(UploadServlet.class);
	private static final String NS = "ns";
	private static final String VNF = "vnf";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { //这段代码用于测试连通性
	 * PrintWriter out = response.getWriter(); out.println(
	 * "<html><body><h1>This is a servlet TestServlet.</h1></body></html>");
	 * out.flush(); }
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String templateType = request.getParameter("templateType");
		// 上传时生成的临时文件保存目录
		String tempPath = DirUtils.getInstance().getTemplDir(
				DirUtils.TEMPLATE_TEMP);
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists())
		{
			// 创建临时目录
			tmpFile.mkdir();
		}
		// 使用Apache文件上传组件处理文件上传步骤：
		// 1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
		factory.setSizeThreshold(1024 * 50); // 设置缓冲区的大小为100KB,如果不指定，那么缓冲区的大小默认是10KB
		// 设置上传时生成的临时文件的保存目录
		factory.setRepository(tmpFile);
		// 2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 监听文件上传进度
		upload.setProgressListener(new ProgressListener() {
			public void update(long pBytesRead, long pContentLength, int arg2) {
				// 文件大小为：14608,当前已处理：4096 文件大小为：14608,当前已处理：7367
				LOGGER.info("File size:{}, Treated size:{}", pContentLength,
						pBytesRead);
			}
		});
		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!ServletFileUpload.isMultipartContent(request))
		{
			// 按照传统方式获取
			LOGGER.warn("This is a form field, not form file");
			return;
		}
		// 设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
		upload.setFileSizeMax(1024 * 1024);
		// 设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前为10MB
		upload.setSizeMax(1024 * 1024 * 10);

		// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form
		// 表单的输入项
		try
		{
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list)
			{
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField())
				{
					LOGGER.info(item.getFieldName() + "="
							+ item.getString("UTF-8"));
				} else
				{
					// 如果fileitem中封装的是上传文件，得到上传的文件名称
					String filename = item.getName();
					LOGGER.info("Template file name: {}.", filename);
					if (NFVOUtils.isEmpty(filename))
					{
						continue;
					}
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					if (VNF.equals(templateType))
					{
						TemplateOperator.importTemplate(in, Vnfd.class,
								DBVnfd.class);
					} else if (NS.equals(templateType))
					{
						TemplateOperator.importTemplate(in, Nsd.class,
								DBNsd.class);
					}
					in.close();
					item.delete();
					response.getWriter().write("0");
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e)
		{
			LOGGER.error("Import template file failed. The Cause: {}",
					e.getMessage());
			response.getWriter().write("-1");
		} catch (FileUploadBase.SizeLimitExceededException e)
		{
			LOGGER.error("Import template file failed. The Cause: {}",
					e.getMessage());
			response.getWriter().write("-1");
		} catch (Exception e)
		{
			LOGGER.error("Import template file failed. The Cause: {}",
					e.getMessage());
			response.getWriter().write("-1");
		}
	}
}
