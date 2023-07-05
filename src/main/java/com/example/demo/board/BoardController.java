package com.example.demo.board;

import com.example.demo.post.PostResponse;
import com.example.demo.post.PostService;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final PostService postService;
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<String>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        List<String> names = new ArrayList<>();

        boards.forEach( board -> names.add(board.getName()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(names);
    }

    @GetMapping("/{board_name}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable("board_name") String board_name,
                                                  @PathParam("pages") Integer page) {
        Board board = boardService.getBoard(board_name);
        if (board == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        PostResponse boardsPost = postService.getBoardsPage(page, board);
        BoardResponse res = new BoardResponse(board.getName(), board.getDescription());
        res.setData(boardsPost);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/dupChk")
    public ResponseEntity<Object> duplicateBoardnameCheck(@PathVariable("name") String name) {
        Board board = boardService.getBoard(name);
        return ResponseEntity
            .status(checkDup(board))
            .build();
    }

    @PostMapping("")
    public ResponseEntity<BoardResponseDetail> makeBoard(@RequestBody BoardRequest req,
                                            @RequestHeader("token") String token) {
        String nickname = token;
        User user = userService.findByName(nickname);
        Board board = new Board();
        board.setName(req.getName());
        board.setDescription(req.getDescription());
        board.setUser(user);

        board = boardService.push(board);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(makeDetailResponse(board));
    }

    @PutMapping("/{name}")
    public ResponseEntity<BoardResponseDetail> updateBoard(@PathVariable("name") String name,
                                                           @RequestBody BoardRequest req,
                                                           @RequestHeader("token") String token) {
        String nickname = token;
        Board board = boardService.getBoard(name);

        HttpStatus status = checkPossibility(board, nickname);
        if (status.equals(HttpStatus.BAD_REQUEST))
            return ResponseEntity
                    .status(status)
                    .build();

        board.setName(req.getName());
        board.setDescription(req.getDescription());
        board = boardService.push(board);

        return ResponseEntity
                .status(status)
                .body(makeDetailResponse(board));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> deleteBoard(@PathVariable("name") String name,
                                              @RequestHeader("token") String token) {
        String nickname = token;
        Board board = boardService.getBoard(name);

        HttpStatus status = checkPossibility(board, nickname);
        if (status.equals(HttpStatus.BAD_REQUEST))
            return ResponseEntity
                    .status(status)
                    .build();

        boardService.deleteBoard(board);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
    private HttpStatus checkDup(Board board) {
        if (board == null)
            return HttpStatus.OK;
        return HttpStatus.IM_USED;
    }

    private HttpStatus checkPossibility(Board board, String nickname) {
        if (board == null)
            return HttpStatus.BAD_REQUEST;
        else if (!board.getUser().getName().equals(nickname))
            return HttpStatus.BAD_REQUEST;
        return HttpStatus.OK;
    }

    private BoardResponseDetail makeDetailResponse(Board board) {
        return new BoardResponseDetail(
                board.getName(),
                board.getDescription(),
                board.getUser().getName()
        );
    }
}
