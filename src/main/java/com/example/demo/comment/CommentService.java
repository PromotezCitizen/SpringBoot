package com.example.demo.comment;


import com.example.demo.post.PostInfo;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment push(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getComment(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent())
            return commentOptional.get();
        return null;
    }

    public void removeComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public CommentResponse getUsersPage(Integer pageNo, User user) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<Comment> page = commentRepository.findAllByUserOrderByIdDesc(pageable, user);

        CommentResponse res = makeCommentResponse(page);
        return res;
    }

    private CommentResponse makeCommentResponse(Page<Comment> page) {
        CommentResponse res = new CommentResponse(page.getTotalPages());

        page.getContent().forEach( comment -> {
            Boolean modified = comment.getCreateTime() == comment.getModifyTime() ? false : true;
            res.data.add(new CommentResponse.ResponseData(
                            comment.getId(),
                            comment.getComment(),
                            comment.getUser().getName(),
                            comment.getLike(),
                            comment.getModifyTime(),
                            modified,
                            new PostInfo(comment.getPost().getId(), comment.getPost().getTitle())
                    )
            );
        });
        return res;
    }
}
