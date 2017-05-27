package com.quancheng.achilles.service.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 * 仅支持单实例,多实例需要将版本号放到集中缓存或数据库中
 * @author zhuzhong
 *
 */
public class SynDownload {

    private final static Logger LOGGER = LoggerFactory.getLogger(SynDownload.class);
    private final static Executor EXECUTOR = Executors.newFixedThreadPool(5);
    public static void mkfile(DownloadBuilder<?> downloader, Object dataSource, EnumDownLoadModel model,
            Object[] pageParamter,Class<?>[] classes, HttpServletRequest request) {
        EXECUTOR.execute(new Syn<>(downloader, dataSource, model, pageParamter, classes,request.getRequestURI(),
                request.getRemoteUser()));
    }

    static class Syn<T> implements Runnable {
        Object[] pageParamter = null;
        EnumDownLoadModel model;
        Class<?>[] paramsClass;
        Object dataSource;
        DownloadBuilder<T> downloader;
        String userName;
        String url;

        @SuppressWarnings("unchecked")
        public void run() {
            Method methods = null;
            try {
                methods = dataSource.getClass().getMethod(model.MODEL_METHOD, paramsClass);
                ModelAndView mv = (ModelAndView) methods.invoke(dataSource, pageParamter);
                Page<T> page = (Page<T>) mv.getModel().get("page");
                Pageable pageables = null;
                Parameter[] params = methods.getParameters();
                int pageSizeIndex = 0;
                int pageNumberIndex = 0;
                downloader.append(page.getContent());
                for (int i = 0; i < params.length; i++) {
                    if (params[i].isAnnotationPresent(RequestParam.class)) {
                        if ("pageSize".equals(params[i].getAnnotation(RequestParam.class).value())) {
                            pageSizeIndex = i;
                        } else if ("pageNum".equals(params[i].getAnnotation(RequestParam.class).value())) {
                            pageNumberIndex = i;
                        }
                    }
                }
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    pageParamter[pageNumberIndex] = pageables.getPageNumber();
                    pageParamter[pageSizeIndex] = pageables.getPageSize();
                    mv = (ModelAndView) methods.invoke(dataSource, pageParamter);
                    downloader.append(((Page<T>) mv.getModel().get("page")).getContent());
                }
            } catch (Exception e) {
                LOGGER.error("Syn export error!", e);
            }
            try {
               String fileurl= downloader.ossUpload(userName,model);
               LOGGER.info(fileurl);
            } catch (Exception e) {
                LOGGER.error("Oss upload error!", e);
            }
        }

        public Syn(DownloadBuilder<T> downloader, Object dataSource, EnumDownLoadModel model, Object[] pageParamter,Class<?>[] classes, String url, String user) {
            super();
            this.url = url;
            this.userName = user;
            this.downloader = downloader;
            this.dataSource = dataSource;
            this.model = model;
            this.pageParamter = pageParamter;
            this.paramsClass= classes;
        }
    }
}
