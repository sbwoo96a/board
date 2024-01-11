package com.example.board.domain.post;

import com.example.board.domain.file.PostFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostForm {

    private Long id;
    private String title;
    private String content;

    private List<MultipartFile> formFile;
    private List<String> originalFileName; //원본 파일 이름
    private List<String> storedFileName;  //서버 저장용 파일 이름
    private int fileAttached;   //파일 첨부 여부(첨부 1, 미첨부 0)

    public PostForm(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static PostForm toPostForm(Post post) {
        PostForm postForm = new PostForm();
        postForm.setId(post.getId());
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        if (post.getFileAttached() == 0) {
            postForm.setFileAttached(post.getFileAttached());
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            postForm.setFileAttached(post.getFileAttached());
            for (PostFile postFile : post.getPostFiles()) {
                originalFileNameList.add(postFile.getOriginalFileName());
                storedFileNameList.add(postFile.getStoredFileName());
            }
            postForm.setOriginalFileName(originalFileNameList);
            postForm.setStoredFileName(storedFileNameList);
        }
        return postForm;
    }
}
