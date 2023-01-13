package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.PostLikeRepository;
import com.example.trainogram.repository.PostRepository;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostLikeRepository likeRepository;
    private final PictureService pictureService;



    @Override
    public Post createPost(String token, String postText, List<MultipartFile> file) throws IOException {
        User user = userService.findAuthenticatedUser(token);
        Long maxPostId = postRepository.max();
        long postId = maxPostId == null ? 0 : maxPostId+1;

        return postRepository.save(Post.builder()
                .id(postId)
                .postText(postText)
                .postAuthor(user)
//                .likes(0)
                .picture(pictureService.savePostImage(file, user.getId(), postId))

                .pubDate(LocalDateTime.now())
                .build());
    }


    @Override
    public void deletePost(String token, Long id) throws Status437PostNotFound, IOException, Status435NoAuthorities {
        Post post = findPostById(id);
        User user = userService.findAuthenticatedUser(token);
        if (userService.findAuthenticatedUser(token).equals(post.getPostAuthor()) || user.getRole().equals(Role.ADMIN) ){
            if(pictureService.deletePostFiles(post)) {

                postRepository.deleteIfExistsByPostAsSponsorPost(post);
                postRepository.delete(post);

            }
        }
        else throw new Status435NoAuthorities("delete");
        }



//    private boolean deletePostFiles(Post post) throws IOException {
//        File f = new File(folder+post.getPostAuthor().getId()+"\\post\\"+post.getId());
//        FileUtils.deleteDirectory(f);
//        return !f.exists();
//    }

    @Override
    public List<Post> findAllPostsByUser(String token) {
        User user = userService.findAuthenticatedUser(token);
        return postRepository.findAllByPostAuthor(user);
    }

    @Override
    public List<Post> findAllPostsByUser(Long id) throws Status434UserNotFound {
        User user = userService.findById(id);
        return postRepository.findAllByPostAuthor(user);
    }

    @Override
    public Post findPostById(Long id) throws Status437PostNotFound {
        return postRepository.findById(id).orElseThrow(()-> new Status437PostNotFound(id));

    }

//    @Override
//    public byte[] getPostPicture(String token, Long id) throws Status437PostNotFound {
//        String folder = "D:\\Games\\Projects\\Train-o-Gram\\src\\main\\resources\\static\\";
//        Post post = findPostById(id);
//        String path = "\\main\\resources\\static\\" + post.getPostAuthor().getId()+"\\post\\"+id+"\\"+post.getPostPicture();
//        System.out.println(path);
//        InputStream in = getClass().getResourceAsStream(path);
//        assert in != null;
//        byte[] result = null;
//        try {
//            result = IOUtils.toByteArray(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

//    @Override
//    public List<InputStream> getPostPicture(String token, Long id) throws Status437PostNotFound, IOException {
////        String folder = "D:\\Games\\Projects\\Train-o-Gram\\src\\main\\resources\\static\\";
//        Post post = findPostById(id);
//        return pictureService.retrievePostImage(post);
//
//    }

    @Override
    public void updatePost(String token, Long id, String postText, List<MultipartFile> file) throws IOException, Status437PostNotFound, Status435NoAuthorities {
        User user = userService.findAuthenticatedUser(token);
        Post post = findPostById(id);
        if (user.equals(post.getPostAuthor())) {
            if (postText != null) {
                post.setPostText(postText);
            }
            if (file != null) {
                post.setPicture(pictureService.savePostImage(file, user.getId(), post.getId()));
            }
            postRepository.save(post);
        }else throw new Status435NoAuthorities("update");
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

//    @Override
//    public Post findPostByComment(Comment comment) {
//        return postRepository.findPostByComment(comment);
//    }

//        public String saveImage(MultipartFile file, Long userId, Long postId) throws IOException {
//        byte[] bytes = file.getBytes();
//        String userPath = folder + userId.toString() + "\\post\\" + postId;
//        File file1 = new File(userPath);
//        file1.mkdirs();
//        Path path = Paths.get(userPath + File.separator + file.getOriginalFilename());
//        FileUtils.cleanDirectory(file1);
//        Files.write(path, bytes);
//        return file.getOriginalFilename();
//    }

}
