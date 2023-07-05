package com.example.demo.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoard(String name) {
        Optional<Board> boardOptional = boardRepository.findByName(name);
        if (boardOptional.isPresent())
            return boardOptional.get();
        return null;
    }

    public Board push(Board board) {
        return boardRepository.save(board);
    }

    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }
}
