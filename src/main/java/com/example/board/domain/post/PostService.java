package com.example.board.domain.post;

import com.example.board.domain.file.PostFile;
import com.example.board.domain.file.PostFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;

    //게시글 저장
    @Transactional
    public Long savePost(PostForm form) throws IOException {
        //파일 첨부 여부에 따라 로직 분리
        if (form.getFormFile().isEmpty()) {
            //첨부 파일 없음
            Post post = Post.toPostEntity(form);
            postRepository.save(post);
            return post.getId();
        } else {
            //첨부 파일 있음
            /*
              1. form에 담긴 파일을 꺼냄
              2. 파일의 이름 가져옴
              3. 서버 저장용 이름을 만듦
              4. 저장 경로 설정
              5. 해당 경로에 파일 저장
              6. post에 해당 데이터 save 처리
              7. file_table에 해당 데이터 save 처리
             */
            Post post = Post.toPostFileEntity(form);
            postRepository.save(post);
            Post findPost = postRepository.findById(post.getId());

            for (MultipartFile formFile : form.getFormFile()) {
//            MultipartFile formFile = form.getFormFile();   //1.
                String originalFilename = formFile.getOriginalFilename();   //2.
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;    //3.
                String savePath = "C:/develop/upload-files/" + storedFileName;  //4.
                formFile.transferTo(new File(savePath));    //5.

                PostFile postFile = PostFile.toPostFileEntity(findPost, originalFilename, storedFileName);
                postFileRepository.save(postFile);

            }
            return post.getId();
        }
    }

    //게시글 수정
    @Transactional
    public Long updatePost(PostForm form) {
        Post findPost = postRepository.findById(form.getId());
        findPost.updatePost(form);
        return findPost.getId();
    }

    //게시글 상세 조회
    public PostForm findPostById(Long id) {
        Post findPost = postRepository.findById(id);
        return PostForm.toPostForm(findPost);
    }

    //게시글 목록 가져오기
    public List<PostForm> findAllPosts() {
        List<PostForm> formList = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            PostForm postForm = PostForm.toPostForm(post);
            formList.add(postForm);
        }
        return formList;
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long id) {
        postRepository.delete(id);
    }
}
