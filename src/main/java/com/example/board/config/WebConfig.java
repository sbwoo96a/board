package com.example.board.config;

import com.example.board.domain.board.BoardService;
import com.example.board.encrypt.BCryptImpl;
import com.example.board.encrypt.EncryptHelper;
import com.example.board.interceptor.AuthInterceptor;
import com.example.board.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**";
    private String savepath = "file:///C:/develop/upload-files/";

    private final BoardService boardService;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savepath);
    }

    @Bean
    public EncryptHelper encryptHelper() {
        return new BCryptImpl();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/boards", "/login", "/css/**",
                        "/signup", "/*.icon", "/error", "/board/*");

        registry.addInterceptor(new AuthInterceptor(boardService))
                .order(2)
                .addPathPatterns("/board/*/**");
    }
}
