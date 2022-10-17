package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status438SponsorPostNotFound;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import com.example.trainogram.model.User;
import com.example.trainogram.repository.SponsorPostRepository;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.SponsorPostService;
import com.example.trainogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class SponsorPostServiceImpl implements SponsorPostService {

    private final SponsorPostRepository sponsorPostRepository;
    private final PostService postService;
    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public SponsorPostServiceImpl(SponsorPostRepository sponsorPostRepository, PostService postService, UserService userService, FileService fileService) {
        this.sponsorPostRepository = sponsorPostRepository;
        this.postService = postService;
        this.userService = userService;
        this.fileService = fileService;
    }


    @Override
    public SponsorPost createSponsorPost(String token, String postText, MultipartFile file, Long sponsorId) throws IOException, Status434UserNotFound {

//        Post post = postRepository.save(Post.builder().postText(postText)
//                        .id(postRepository.max()+1)
//                        .postAuthor(author)
//                        .pubDate(LocalDateTime.now())
//                        .postPicture(saveImage(file, author.getId(), sponsorPostRepository.max()+1))
//                        .likes(0)
//                        .build());
        Post post = postService.createPost(token, postText, file);
        return sponsorPostRepository.save(SponsorPost.builder()
                        .id(sponsorPostRepository.max()+1)
                        .post(post)
                        .user(userService.findById(sponsorId))
                        .build());
    }

    @Override
    public SponsorPost findSponsorPost(Long sponsorPostId) throws Status438SponsorPostNotFound {
        return sponsorPostRepository.findById(sponsorPostId)
                .orElseThrow(()->new Status438SponsorPostNotFound(sponsorPostId));
    }

    @Override
    public List<SponsorPost> findAllSponsorPost(Long sponsorId) throws Status434UserNotFound{
        if (sponsorId!=null && userService.findById(sponsorId)!=null) {
            return sponsorPostRepository.findAllByUserId(sponsorId);
        }else throw new Status434UserNotFound(sponsorId);
    }

    @Override
    public void deleteSponsorPost(String token, Long sponsorPostId) throws Status438SponsorPostNotFound, Status435NoAuthorities {
    SponsorPost sponsorPost = sponsorPostRepository.findById(sponsorPostId)
            .orElseThrow(()->new Status438SponsorPostNotFound(sponsorPostId));
    User user = userService.findAuthenticatedUser(token);
        if (user.equals(sponsorPost.getUser()) || user.equals(sponsorPost.getPost().getPostAuthor())) {
            sponsorPostRepository.deleteById(sponsorPostId);
        }else throw new Status435NoAuthorities("delete");
    }

    @Override
    public void updateSponsorPost(String token, Long sponsorPostId, String postText, MultipartFile file) throws IOException, Status438SponsorPostNotFound {
        SponsorPost sponsorPost = sponsorPostRepository.findById(sponsorPostId)
                .orElseThrow(()->new Status438SponsorPostNotFound(sponsorPostId));
        User user = userService.findAuthenticatedUser(token);
        Post post = sponsorPost.getPost();
        if (user.equals(post.getPostAuthor())){
            if (postText != null) {
                post.setPostText(postText);
            }
            if (!file.isEmpty()) {
                post.setPostPicture(fileService.savePostImage(file, user.getId(), post.getId()));
                }
            sponsorPostRepository.save(sponsorPost);
        }
    }


//    public String saveImage(MultipartFile file, Long userId, Long postId) throws IOException {
//        byte[] bytes = file.getBytes();
//        String folder = "D:\\Games\\Projects\\Train-o-Gram\\src\\main\\resources\\static\\";
//        String userPath = folder +userId.toString()+"\\post\\"+postId;
//        File file1 = new File(userPath);
//        file1.mkdirs();
//        Path path = Paths.get(userPath+File.separator+file.getOriginalFilename());
//        FileUtils.cleanDirectory(file1);
//        Files.write(path,bytes);
//        return file.getOriginalFilename();
//    }




    /*@Override
    public SponsorPost addSponsorPost(Post post, User sponsor) {
        SponsorPost sponsorPost = new SponsorPost();
        sponsorPost.setPost(post);
        sponsorPost.setUser(sponsor);
        return sponsorPostRepository.save(sponsorPost);
    }

    @Override
    public void deleteSponsorPost(Long id) {
        sponsorPostRepository.deleteById(id);
    }

    @Override
    public List<SponsorPost> findAllSponsorPostsByUser(Long id) {
        return sponsorPostRepository.findAllByUserId(id);
    }

    @Override
    public SponsorPost findBySponsorPostId(Long id) {
        return sponsorPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No post with this id"));
    }

    @Override
    public void updateSponsorPost(SponsorPost sponsorPost) {
        sponsorPostRepository.save(sponsorPost);
    }*/
}
