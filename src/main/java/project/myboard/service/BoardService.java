package project.myboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.myboard.dto.BoardDto;
import project.myboard.entity.BoardEntity;
import project.myboard.repository.BoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardDto> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public BoardDto getBoardById(Long id) {
        BoardEntity board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        return convertEntityToDto(board);
    }

    public BoardDto createBoard(BoardDto boardDto) {
        BoardEntity board = convertDtoToEntity(boardDto);
        BoardEntity savedBoard = boardRepository.save(board);
        return convertEntityToDto(savedBoard);
    }

    public BoardDto updateBoard(Long id, BoardDto boardDto) {
        BoardEntity board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        BoardEntity updatedBoard = boardRepository.save(board);
        return convertEntityToDto(updatedBoard);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    private BoardDto convertEntityToDto(BoardEntity board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        return boardDto;
    }

    private BoardEntity convertDtoToEntity(BoardDto boardDto) {
        BoardEntity board = new BoardEntity();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        return board;
    }
}
