package com.example.trainogram.service.impl;

import com.example.trainogram.exception.Status434UserNotFound;
import com.example.trainogram.exception.Status435NoAuthorities;
import com.example.trainogram.exception.Status437PostNotFound;
import com.example.trainogram.exception.Status438SponsorPostNotFound;
import com.example.trainogram.model.Post;
import com.example.trainogram.model.SponsorPost;
import com.example.trainogram.model.User;
import com.example.trainogram.model.dto.SponsorReport;
import com.example.trainogram.repository.SponsorPostRepository;
import com.example.trainogram.service.PostService;
import com.example.trainogram.service.SponsorPostService;
import com.example.trainogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class SponsorPostServiceImpl implements SponsorPostService {

    // TODO: 07.12.2022 sponsor report



    private final SponsorPostRepository sponsorPostRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public SponsorPostServiceImpl(SponsorPostRepository sponsorPostRepository, PostService postService, UserService userService) {
        this.sponsorPostRepository = sponsorPostRepository;
        this.postService = postService;
        this.userService = userService;
    }


    @Override
    public SponsorPost createSponsorPost(String token, String postText, List<MultipartFile> file, Long sponsorId) throws IOException, Status434UserNotFound {

//        Post post = postRepository.save(Post.builder().postText(postText)
//                        .id(postRepository.max()+1)
//                        .postAuthor(author)
//                        .pubDate(LocalDateTime.now())
//                        .postPicture(saveImage(file, author.getId(), sponsorPostRepository.max()+1))
//                        .likes(0)
//                        .build());
        Post post = postService.createPost(token, postText, file);
        return sponsorPostRepository.save(SponsorPost.builder()
//                        .id(sponsorPostRepository.max().orElse(1L))
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
    public void deleteSponsorPost(String token, Long sponsorPostId) throws Status435NoAuthorities, Status437PostNotFound, IOException, Status438SponsorPostNotFound {
    /*Post sponsorPost = postService.findPostById(sponsorPostId);

    SponsorPost sponsorPost1 = sponsorPostRepository.findByPost(sponsorPost);
        System.out.println(sponsorPost1);

        if (sponsorPost1!=null) {

            postService.deletePost(token,sponsorPost.getId());
            sponsorPostRepository.delete(sponsorPost1);

        } else throw new Status438SponsorPostNotFound(sponsorPostId);*/



           SponsorPost sponsorPost = sponsorPostRepository.findById(sponsorPostId).orElseThrow(()->new Status438SponsorPostNotFound(sponsorPostId));
         User user = userService.findAuthenticatedUser(token);
        if (user.equals(sponsorPost.getPost().getPostAuthor()) || user.equals(sponsorPost.getUser())) {
            sponsorPostRepository.deleteById(sponsorPostId);
        }else throw new Status435NoAuthorities("delete");

    }

    @Override
    public void updateSponsorPost(String token, Long sponsorPostId, String postText, List<MultipartFile> file) throws IOException, Status438SponsorPostNotFound, Status435NoAuthorities, Status437PostNotFound {
        Post sponsorPost = postService.findPostById(sponsorPostId);
//                .orElseThrow(()->new Status438SponsorPostNotFound(sponsorPostId)).getPost();
//        User user = userService.findAuthenticatedUser(token);
        /*if (user.equals(sponsorPost.getPostAuthor())){
            if (postText != null) {
                sponsorPost.setPostText(postText);
            }
            if (!file.isEmpty()) {
                sponsorPost.setPostPicture(fileService.savePostImage(file, user.getId(), sponsorPost.getId()));
            }
        }*/
        postService.updatePost(token,sponsorPost.getId(), postText, file);
    }

    @Override
    public List<SponsorPost> findAllSponsorPosts() {
        return sponsorPostRepository.findAll();
    }

    @Override
    public List<SponsorReport> getReport(String token) throws Status434UserNotFound {
        User sponsor = userService.findAuthenticatedUser(token);
        List<SponsorPost> sponsorPosts = findAllSponsorPost(sponsor.getId());

        List<SponsorReport> reports = new ArrayList<>();

        sponsorPosts.forEach(sponsorPost -> reports.add(
                SponsorReport.builder()
                        .postId(sponsorPost.getPost().getId())
                        .likes(sponsorPost.getPost().getLikes())
                        .comments(sponsorPost.getPost().getComments().size()).build())
        );
        return reports;
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

}
